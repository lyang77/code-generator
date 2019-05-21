package pro.buer.code.generator.domain.configuration;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public class Builder{

    private String template;
    private String fullPath;
    private String type;

    public String getTemplate(){ return template; }

    public void setTemplate(String template){
        this.template = template;
    }

    public String getFullPath(){ return fullPath; }

    public void setFullPath(String fullPath){
        this.fullPath = fullPath;
    }

    public String getType(){ return type; }

    public void setType(String type){
        this.type = type;
    }
}
