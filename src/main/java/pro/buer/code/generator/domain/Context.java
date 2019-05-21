package pro.buer.code.generator.domain;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public class Context{

    private Bean bean;
    private String packageName;
    private String output;

    public Bean getBean(){ return bean; }

    public void setBean(Bean bean){
        this.bean = bean;
    }

    public String getPackageName(){ return packageName; }

    public void setPackageName(String packageName){
        this.packageName = packageName;
    }

    public String getOutput(){ return output; }

    public void setOutput(String output){
        this.output = output;
    }
}
