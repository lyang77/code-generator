package pro.buer.code.generator.domain;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public class Field implements Comparable{

    private String jdbcType;
    private String javaType;
    private String columnName;
    private String keyName;
    private String fieldName;
    private String setName;
    private String getName;
    private String comment;
    private boolean allowNull;
    private boolean pk;
    private Integer length;
    private Integer digits;
    private boolean in;
    private boolean like;
    private boolean onlyQuery;
    private boolean onlyFilter;

    public String getJdbcType(){
        return jdbcType;
    }

    public void setJdbcType(String jdbcType){
        this.jdbcType = jdbcType;
    }

    public String getJavaType(){
        return javaType;
    }

    public void setJavaType(String javaType){
        this.javaType = javaType;
    }

    public String getColumnName(){
        return columnName;
    }

    public void setColumnName(String columnName){
        this.columnName = columnName;
    }

    public String getKeyName(){
        return keyName;
    }

    public void setKeyName(String keyName){
        this.keyName = keyName;
    }

    public String getFieldName(){
        return fieldName;
    }

    public void setFieldName(String fieldName){
        this.fieldName = fieldName;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getSetName(){
        return setName;
    }

    public void setSetName(String setName){
        this.setName = setName;
    }

    public String getGetName(){
        return getName;
    }

    public void setGetName(String getName){
        this.getName = getName;
    }

    public Boolean isAllowNull(){
        return allowNull;
    }

    public void setAllowNull(boolean allowNull){
        this.allowNull = allowNull;
    }

    public Integer getLength(){
        return length;
    }

    public void setLength(Integer length){
        this.length = length;
    }

    public Boolean isPk(){
        return pk;
    }

    public void setPk(boolean pk){
        this.pk = pk;
    }

    public Integer getDigits(){
        return digits;
    }

    public void setDigits(Integer digits){
        this.digits = digits;
    }

    public Boolean isIn(){
        return in;
    }

    public void setIn(boolean in){
        this.in = in;
    }

    public Boolean isLike(){
        return like;
    }

    public void setLike(boolean like){
        this.like = like;
    }

    public Boolean isOnlyQuery(){
        return onlyQuery;
    }

    public void setOnlyQuery(boolean onlyQuery){
        this.onlyQuery = onlyQuery;
    }

    public boolean isOnlyFilter(){
        return onlyFilter;
    }

    public void setOnlyFilter(boolean onlyFilter){
        this.onlyFilter = onlyFilter;
    }

    @Override
    public int compareTo(Object o){
        Field field = (Field) o;
        return (this.columnName + this.fieldName).compareTo(field.columnName + field.fieldName);
    }
}
