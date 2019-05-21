package pro.buer.code.generator.impl;

import java.util.List;

import pro.buer.code.generator.NameHandler;
import pro.buer.code.generator.domain.Bean;
import pro.buer.code.generator.domain.Field;
import pro.buer.code.generator.util.StringUtil;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public class NameHandlerImpl implements NameHandler {

    @Override
    public void processBeanName(Bean bean) {
        String name = parseBeanName(bean.getTableName());
        bean.setBeanName(name);
        bean.setFirstLowBeanName(firstLowString(name));
    }

    @Override
    public void processFieldName(Field field) {
        String name = parseFieldName(field.getKeyName() == null ? field.getColumnName() : field.getKeyName());

        String fileName="";
        if(name.endsWith("OptStatus")){
            fileName= StringUtil.lowCaseFirst(name);
        }else {
            fileName = StringUtil.lowCaseFirst(field.getColumnName().substring(field.getColumnName().indexOf("_") + 1, field.getColumnName().length()));
            if (fileName.endsWith("long")) {
                fileName = StringUtil.lowCaseFirst(name);
            }
        }

        field.setFieldName(fileName);
        if (field.getJavaType().equalsIgnoreCase("boolean")) {
            if (name.toLowerCase().startsWith("is")) {
                name = name.substring(2);
                field.setFieldName(StringUtil.lowCaseFirst(name));
                field.setSetName("set" + name);
            }
            field.setGetName("is" + name);
        }
    }

    protected String parseBeanName(String columnName) {
        List<String> words = StringUtil.words(columnName);
        String name = "";
        for (String word : words) {
            name += StringUtil.upCaseFirst(word);
        }
        return name;
    }

    protected String parseFieldName(String columnName) {
        List<String> words = StringUtil.words(columnName);
        String name = "";
        for (String word : words) {
            name += StringUtil.upCaseFirst(word.toLowerCase());
        }
        return name;
    }

    private String firstLowString(String name) {
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
