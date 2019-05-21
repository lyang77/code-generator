package pro.buer.code.generator.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.stream.Collectors;

import pro.buer.code.generator.domain.configuration.ExtendField;
import pro.buer.code.generator.util.AssertUtil;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public class Bean {

    private String tableName;
    private String beanName;
    private String comment;
    private Set<String> imports = new HashSet<>();
    private TreeSet<Field> fields;
    private TreeSet<Field> excluedPkFields;
    private TreeSet<ExtendField> extendFields;
    private Map<String, String> enumMap;
    private String extendsClass;
    private Set<String> ifs = new HashSet<>();
    private String pk;
    private Boolean createCustomSelect;
    private Boolean apiCheck;
    private String code;
    private TreeSet<Field> queryFields;
    private String firstLowBeanName;

    public String getFirstLowBeanName() {
        return firstLowBeanName;
    }

    public void setFirstLowBeanName(String firstLowBeanName) {
        this.firstLowBeanName = firstLowBeanName;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public Boolean getApiCheck() {
        return apiCheck;
    }

    public void setApiCheck(Boolean apiCheck) {
        this.apiCheck = apiCheck;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TreeSet<Field> getFields() {
        return fields;
    }

    public void setFields(TreeSet<Field> fields) {
        this.fields = fields;
        this.excluedPkFields=new TreeSet<>();
        if(this.fields!=null) {
            this.excluedPkFields.addAll(this.fields);
            if (getPkFields() == null || getPkFields().size() == 0) {
                return;
            }
            this.excluedPkFields.remove(getPkFields().get(0));
        }
    }

    public TreeSet<Field> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(TreeSet<Field> queryFields) {
        this.queryFields = queryFields;
    }

    public TreeSet<ExtendField> getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(TreeSet<ExtendField> extendFields) {
        this.extendFields = extendFields;
    }

    public Set<String> getImports() {
        return imports;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }

    public String getExtendsClass() {
        return extendsClass;
    }

    public void setExtendsClass(String extendsClass) {
        this.extendsClass = extendsClass;
    }

    public Set<String> getIfs() {
        return ifs;
    }

    public void setIfs(Set<String> ifs) {
        this.ifs = ifs;
    }

    public List<Field> getPkFields() {
        return fields.stream().filter(x -> x.isPk() && !x.isOnlyQuery()).collect(Collectors.toList());
    }

    public List<Field> getNormalFields() {
        return fields.stream().filter(x -> !AssertUtil.isTrue(x.isPk()) && !AssertUtil.isTrue(x.isOnlyQuery())).collect(Collectors.toList());
    }

    public TreeSet<Field> getExcluedPkFields() {
        return excluedPkFields;
    }

    public void setExcluedPkFields(TreeSet<Field> excluedPkFields) {
        this.excluedPkFields = excluedPkFields;
    }

    public String getPkColumnNames() {
        StringJoiner joiner = new StringJoiner(",");
        getPkFields().forEach(field -> joiner.add(field.getColumnName()));
        return joiner.toString();
    }

    public String getNormalColumnNames() {
        StringJoiner joiner = new StringJoiner(",");
        getNormalFields().forEach(field -> joiner.add(field.getColumnName()));
        return joiner.toString();
    }

    public String getColumnNames() {
        StringJoiner joiner = new StringJoiner(",");
        getFields().stream().filter(x -> !AssertUtil.isTrue(x.isOnlyQuery())).forEach(field -> joiner.add(field.getColumnName()));
        return joiner.toString();
    }

    public String getExcluedPkColumnNames() {
        StringJoiner joiner = new StringJoiner(",");
        List<Field> fields=getPkFields();
        getFields().forEach(x->{
            if(fields==null||fields.size()==0){
                return;
            }
            if(AssertUtil.isTrue(x.isOnlyQuery())||fields.get(0).equals(x)){
                return;
            }
            joiner.add(x.getColumnName());
        });
        return joiner.toString();
    }


    public Map<String, String> getEnumMap() {
        return enumMap;
    }

    public void setEnumMap(Map<String, String> enumMap) {
        this.enumMap = enumMap;
    }


    public Boolean getCreateCustomSelect() {
        return createCustomSelect;
    }

    public void setCreateCustomSelect(Boolean createCustomSelect) {
        this.createCustomSelect = createCustomSelect;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
