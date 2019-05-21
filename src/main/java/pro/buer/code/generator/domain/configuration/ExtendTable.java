package pro.buer.code.generator.domain.configuration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import pro.buer.code.generator.domain.Field;

/**
 * @author buer
 * @version 2017-11-14 14:13
 */
public class ExtendTable{
	
	private String pk;
	private String code;
	private Boolean createCustomSelect;
	private Boolean apiCheck;
    private Set<String> imports = new HashSet<>();
    private TreeSet<ExtendField> fields;
    private TreeSet<Field> queryFields;
    private Map<String,String> enums;
    private Map<String,String> keys;
    private String extendsClass;
    private Set<String> ifs = new HashSet<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getCreateCustomSelect() {
        return createCustomSelect;
    }

    public Boolean getApiCheck() {
        return apiCheck;
    }

    public void setApiCheck(Boolean apiCheck) {
        this.apiCheck = apiCheck;
    }

    public void setCreateCustomSelect(Boolean createCustomSelect) {
        this.createCustomSelect = createCustomSelect;
    }

    public String getPk() {
    	return pk;
    }
    
    public void setPk(String pk) {
    	this.pk = pk;
    }
    
    public Set<String> getImports(){
        return imports;
    }

    public void setImports(Set<String> imports){
        this.imports = imports;
    }

    public TreeSet<ExtendField> getFields(){
        return fields;
    }

    public void setFields(TreeSet<ExtendField> fields){
        this.fields = fields;
    }

    public Map<String,String> getEnums(){
        return enums;
    }

    public void setEnums(Map<String,String> enums){
        this.enums = enums;
    }

    public Map<String,String> getKeys(){
        return keys;
    }

    public void setKeys(Map<String,String> keys){
        this.keys = keys;
    }

    public TreeSet<Field> getQueryFields(){
        return queryFields;
    }

    public void setQueryFields(TreeSet<Field> queryFields){
        this.queryFields = queryFields;
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
	
}
