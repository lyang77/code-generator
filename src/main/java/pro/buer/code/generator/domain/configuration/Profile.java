package pro.buer.code.generator.domain.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public class Profile{

    private String name;
    private String output;
    private String nameHandler;
    private String commentHandler;
    private String templageEnginel;
    private Database database;
    private String basePackage;
    private String enumPackage;
    private List<String> modules;
    private Map<String,Module> moduleMap;
    private Map<String,Builder> builderMap;

    public Profile(){
        moduleMap = new HashMap<>();
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getOutput(){
        return output;
    }

    public void setOutput(String output){
        this.output = output;
    }

    public String getNameHandler(){
        return nameHandler;
    }

    public void setNameHandler(String nameHandler){
        this.nameHandler = nameHandler;
    }

    public String getCommentHandler(){
        return commentHandler;
    }

    public void setCommentHandler(String commentHandler){
        this.commentHandler = commentHandler;
    }

    public String getTemplageEnginel(){
        return templageEnginel;
    }

    public void setTemplageEnginel(String templageEnginel){
        this.templageEnginel = templageEnginel;
    }

    public Database getDatabase(){
        return database;
    }

    public void setDatabase(Database database){
        this.database = database;
    }

    public String getBasePackage(){
        return basePackage;
    }

    public void setBasePackage(String basePackage){
        this.basePackage = basePackage;
    }

    public String getEnumPackage(){
        return enumPackage;
    }

    public void setEnumPackage(String enumPackage){
        this.enumPackage = enumPackage;
    }

    public List<String> getModules(){
        return modules;
    }

    public void setModules(List<String> modules){
        this.modules = modules;
    }

    public Map<String,Module> getModuleMap(){
        return moduleMap;
    }

    public void setModuleMap(Map<String,Module> moduleMap){
        this.moduleMap = moduleMap;
    }

    public Map<String,Builder> getBuilderMap(){
        return builderMap;
    }

    public void setBuilderMap(Map<String,Builder> builderMap){
        this.builderMap = builderMap;
    }
}
