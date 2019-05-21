package pro.buer.code.generator.domain.configuration;

import pro.buer.code.generator.util.StringUtil;

/**
 *
 *
 * create date:2017/7/27
 * remark:
 */
public class ExtendField implements Comparable{

    private String field;
    private String setName;
    private String getName;
    private String comment;
    private String type;

    public String getField(){
        return field;
    }

    public void setField(String field){
        this.field = field;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getSetName(){
        return setName == null ? "set" + StringUtil.upCaseFirst(field) : setName;
    }

    public void setSetName(String setName){
        this.setName = setName;
    }

    public String getGetName(){
        return getName == null ? "get" + StringUtil.upCaseFirst(field) : getName;
    }

    public void setGetName(String getName){
        this.getName = getName;
    }

    @Override
    public int compareTo(Object o){
        ExtendField field = (ExtendField) o;
        return this.field.compareTo(field.field);
    }
}
