package pro.buer.code.generator.domain.configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public class Module{

    private String name;
    private List<String> tables;
    private List<String> domainImports;
    private List<String> serviceImports;
    private List<String> daoImports;
    private Map<String,ExtendTable> extendTableMap;
    private String extendsClass;
    private Set<String> ifs = new HashSet<>();

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<String> getTables(){
        return tables;
    }

    public void setTables(List<String> tables){
        this.tables = tables;
    }

    public List<String> getDomainImports(){
        return domainImports;
    }

    public void setDomainImports(List<String> domainImports){
        this.domainImports = domainImports;
    }

    public List<String> getServiceImports(){
        return serviceImports;
    }

    public void setServiceImports(List<String> serviceImports){
        this.serviceImports = serviceImports;
    }

    public List<String> getDaoImports(){
        return daoImports;
    }

    public void setDaoImports(List<String> daoImports){
        this.daoImports = daoImports;
    }

    public Map<String,ExtendTable> getExtendTableMap(){
        return extendTableMap;
    }

    public void setExtendTableMap(Map<String,ExtendTable> extendTableMap){
        this.extendTableMap = extendTableMap;
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
