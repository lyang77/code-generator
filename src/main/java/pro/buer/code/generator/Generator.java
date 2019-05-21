package pro.buer.code.generator;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.JSON;
import pro.buer.code.generator.domain.Bean;
import pro.buer.code.generator.domain.Field;
import pro.buer.code.generator.domain.configuration.Builder;
import pro.buer.code.generator.domain.configuration.Configuration;
import pro.buer.code.generator.domain.configuration.Database;
import pro.buer.code.generator.domain.configuration.ExtendTable;
import pro.buer.code.generator.domain.configuration.Module;
import pro.buer.code.generator.domain.configuration.Profile;
import pro.buer.code.generator.impl.NameHandlerImpl;
import pro.buer.code.generator.impl.TemplateEngineImpl;
import pro.buer.code.generator.util.FileUtil;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public class Generator {

    protected Connection conn;
    protected DatabaseMetaData metaData;
    protected NameHandler nameHandler;
    protected TemplateEngine templateEngine;
    protected CommentHandler commentHandler;

    protected Profile parseConfiguration(Configuration configuration, String active) {
        String json;
        Profile profile;
        Module module;
        if (configuration.getProfiles() != null && configuration.getProfiles().size() > 0) {
            for (String profileFile : configuration.getProfiles()) {
                List<File> profileFiles = FileUtil.findFiles(profileFile);
                for (File pf : profileFiles) {
                    json = FileUtil.readFile(pf);
                    profile = JSON.parseObject(json, Profile.class);
                    if (profile.getModules() != null && profile.getModules().size() > 0) {
                        for (String moduleFile : profile.getModules()) {
                            List<File> moduleFiles = FileUtil.findFiles(moduleFile);
                            for (File mf : moduleFiles) {
                                json = FileUtil.readFile(mf);
                                module = JSON.parseObject(json, Module.class);
                                profile.getModuleMap().put(module.getName(), module);
                            }
                        }
                    }
                    configuration.getProfileMap().put(profile.getName(), profile);
                }
            }
        }
        if (configuration.getActives() != null) {
            configuration.setActiveProfile(configuration.getProfileMap().get(active));
        } else {
            configuration.setActiveProfile(configuration.getProfileMap().values().stream().findFirst().get());
        }
        return configuration.getActiveProfile();
    }

    public void run(Configuration config) {
        try {
            for (String active : config.getActives()) {
                Profile profile = parseConfiguration(config, active);
                if (profile.getNameHandler() != null) {
                    nameHandler = (NameHandler) Class.forName(profile.getNameHandler()).newInstance();
                } else {
                    nameHandler = new NameHandlerImpl();
                }
                if (profile.getCommentHandler() != null) {
                    commentHandler = (CommentHandler) Class.forName(profile.getCommentHandler()).newInstance();
                } else {
                    commentHandler = null;
                }
                if (profile.getTemplageEnginel() != null) {
                    templateEngine = (TemplateEngine) Class.forName(profile.getTemplageEnginel()).newInstance();
                } else {
                    templateEngine = new TemplateEngineImpl();
                }
                Map<String, Builder> map = new HashMap<>(config.getBuilderMap());
                if (profile.getBuilderMap() != null) {
                    profile.getBuilderMap().forEach((kt, vt) -> map.put(kt, vt));
                }
                Class.forName(profile.getDatabase().getDriverClass());
                Database database = profile.getDatabase();
                Properties properties = new Properties();
                properties.setProperty("user", database.getUser());
                properties.setProperty("password", database.getPassword());
                properties.setProperty("remarks", "true");
                properties.setProperty("useInformationSchema", "true");
                conn = DriverManager.getConnection(database.getUrl(), properties);
                metaData = conn.getMetaData();
                String txt = "";
                profile.getModuleMap().forEach((k, v) -> {
                    try {
                        v.setName(k);
                        List<Bean> beans = getBeans(config, nameHandler, v);
                        beans.forEach(x -> {
                            System.out.println(x.getTableName());
                            map.forEach((kt, vt) -> {
                                String content = templateEngine.parseTemplate(profile, x, v, vt);
                                String fullName = templateEngine.parseExpression(profile, x, v, vt.getFullPath());
                                Path path = Paths.get(profile.getOutput(), fullName.replace(".", "/") + vt.getType());
                                if (path.toString().contains("dubbo.txt")) {
                                    FileUtil.writeFileAndAdd(path.toString(), content);
                                } else {
                                    FileUtil.writeFile(path.toString(), content);
                                }

                            });

                        });
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public List<Bean> getBeans(Configuration configuration, NameHandler nameHandler, Module module) throws SQLException {
        List<Bean> beans = new ArrayList<>();
        Map<String, String> tableCommentMap = null;
        if (commentHandler != null) {
            tableCommentMap = commentHandler.getTableComment(conn);
        }
        for (String tableName : module.getTables()) {
            ResultSet tableSet = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
            while (tableSet.next()) {
                Bean bean = new Bean();
                bean.setTableName(tableSet.getString("TABLE_NAME"));

                //类继承处理
                {
                    String extendsClass = module.getExtendsClass();
                    applyExtendsClassToBean(extendsClass, bean);
                }

                //接口实现处理
                {
                    Set<String> implementsIf = module.getIfs();
                    applyImplementInterfaceToBean(implementsIf, bean);
                }

                Map<String, String> enumMap = null;
                Map<String, String> keyMap = null;
                if (module.getExtendTableMap() != null) {
                    ExtendTable extendTable = module.getExtendTableMap().get(bean.getTableName());
                    if (extendTable != null) {
                        bean.setPk(extendTable.getPk());
                        bean.setExtendFields(extendTable.getFields());
                        bean.getImports().addAll(extendTable.getImports());
                        bean.setEnumMap(extendTable.getEnums());
                        bean.setFields(extendTable.getQueryFields());
                        bean.setCode(extendTable.getCode());
                        bean.setCreateCustomSelect(extendTable.getCreateCustomSelect());
                        bean.setQueryFields(extendTable.getQueryFields());
                        enumMap = extendTable.getEnums();
                        keyMap = extendTable.getKeys();
                        applyExtendsClassToBean(extendTable.getExtendsClass(), bean);
                        applyImplementInterfaceToBean(extendTable.getIfs(), bean);
                    }
                }
                Map<String, String> fieldCommentMap = null;
                if (commentHandler != null) {
                    bean.setComment(tableCommentMap.get(bean.getTableName()));
                    fieldCommentMap = commentHandler.getFieldComment(conn, bean.getTableName());
                } else {
                    bean.setComment(tableSet.getString("REMARKS"));
                }

                nameHandler.processBeanName(bean);
                TreeSet<Field> fields = new TreeSet<>();
                TreeSet<String> pkColumns = new TreeSet<>();
                ResultSet pkSet = metaData.getPrimaryKeys(null, null, bean.getTableName());
                while (pkSet.next()) {
                    pkColumns.add(pkSet.getString("COLUMN_NAME"));
                }
                ResultSet columnSet = metaData.getColumns(null, null, bean.getTableName(), null);
                while (columnSet.next()) {
                    String columnName = columnSet.getString("COLUMN_NAME");
                    if (columnName.equals("opt_status")) {
                        continue;
                    }
                    Field field;
                    if (bean.getFields() != null) {
                        Optional<Field> optional = bean.getFields().stream().filter(x -> x.getColumnName().equals(columnName) && !x.isOnlyQuery()).findAny();
                        if (optional.isPresent()) {
                            field = optional.get();
                        } else {
                            field = new Field();
                        }
                    } else {
                        field = new Field();
                    }
                    field.setColumnName(columnName);
                    field.setPk(pkColumns.contains(field.getColumnName()));
                    field.setJdbcType(columnSet.getString("TYPE_NAME"));
                    field.setDigits(columnSet.getInt("DECIMAL_DIGITS"));
                    field.setLength(columnSet.getInt("COLUMN_SIZE"));
                    field.setAllowNull(columnSet.getBoolean("NULLABLE"));
                    if (fieldCommentMap != null) {
                        String str = fieldCommentMap.get(field.getColumnName());
                        String s = Optional.ofNullable(str).map(item -> item.replaceAll("[\\r\\n\\t]", ""))
                                .orElse(null);
                        field.setComment(s);
                    } else {
                        String remarks = columnSet.getString("REMARKS");
                        String s = Optional.ofNullable(remarks).map(item -> item.replaceAll("[\\r\\n\\t]", ""))
                                .orElse(null);
                        field.setComment(s);
                    }
                    if (field.getColumnName().toLowerCase().startsWith("is")) {
                        field.setJavaType("Boolean");
                    } else if ("bigint identity".equalsIgnoreCase(field.getJdbcType())) {
                        field.setJavaType("Long");
                    } else {
                        if (enumMap != null && enumMap.containsKey(field.getColumnName())) {
                            String enumTypeName = enumMap.get(field.getColumnName());

                            //如枚举类型位于子包,则类型名称位于最后一个dot后
                            enumTypeName = fetchClassNamePart(enumTypeName);

                            field.setJavaType(enumTypeName);
                        } else {
                            field.setJavaType(configuration.toJavaType(field.getJdbcType()));
                        }
                        if (keyMap != null && keyMap.containsKey(field.getColumnName())) {
                            field.setKeyName(keyMap.get(field.getColumnName()));
                        }
                    }
                    nameHandler.processFieldName(field);
                    fields.add(field);
                }
                if(bean.getFields() == null){
                    bean.setFields(fields);
                }
                if(bean.getFields().size()<fields.size()){
                    fields.addAll(bean.getFields());
                    bean.setFields(fields);
                } else{
                    bean.getFields().addAll(fields);
                }
                beans.add(bean);
            }
        }
        return beans;
    }

    /**
     * 为实体添加继承类配置，如果扩展配置也有此配置则为替换，如果为接口则为添加。
     * 如果类或接口为全名，则增加到import项，定义只保留名称，泛型除外，如果有同学能有时间使用正则解析出JAVA泛型语法内容则更好
     * 并且泛型目前只能用名称，而不能用全名，会导致类名解析错误，只能用名称，并且手动添加import项
     */
    private void applyExtendsClassToBean(String extendsClass, Bean bean) {
        if (Objects.nonNull(extendsClass) && extendsClass.length() > 0) {
            if (containDot(extendsClass)) {
                bean.getImports().add(fetchFullClassNamePart(extendsClass));
                bean.setExtendsClass(fetchClassNamePart(extendsClass));
            } else {
                bean.setExtendsClass(extendsClass);
            }
        }
    }

    /**
     * @see #applyExtendsClassToBean(String, Bean)
     */
    private void applyImplementInterfaceToBean(Set<String> implementsIf, Bean bean) {
        //TODO 如被重新设置则已导入的IMPORT不会被删除
        if (Objects.nonNull(implementsIf) && !implementsIf.isEmpty()) {
            for (String impl : implementsIf) {
                if (containDot(impl)) {
                    bean.getImports().add(fetchFullClassNamePart(impl));
                    bean.getIfs().add(fetchClassNamePart(impl));
                } else {
                    bean.getIfs().add(impl);
                }
            }
        }
    }

    private boolean containDot(String source) {
        return source.indexOf('.') >= 0;
    }

    private String fetchClassNamePart(String source) {
        int idx = source.lastIndexOf('.');
        if (idx >= 0) {
            return source.substring(idx + 1);
        }
        return source;
    }

    private String fetchFullClassNamePart(String source) {
        int idx = source.lastIndexOf('<');
        if (idx >= 0) {
            return source.substring(0, idx);
        }
        return source;
    }
}
