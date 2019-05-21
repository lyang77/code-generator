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
public class Configuration{

    private List<String> actives;
    private Profile activeProfile;
    private Map<String,String> typeMap;
    private Map<String,Profile> profileMap;
    private List<String> profiles;
    private Map<String,Builder> builderMap;

    public Configuration(){
        profileMap = new HashMap<>();
    }

    public List<String> getActives(){
        return actives;
    }

    public void setActives(List<String> actives){
        this.actives = actives;
    }

    public Profile getActiveProfile(){ return activeProfile; }

    public void setActiveProfile(Profile activeProfile){
        this.activeProfile = activeProfile;
    }

    public List<String> getProfiles(){ return profiles; }

    public void setProfiles(List<String> profiles){
        this.profiles = profiles;
    }

    public Map<String,String> getTypeMap(){ return typeMap; }

    public void setTypeMap(Map<String,String> typeMap){
        this.typeMap = typeMap;
    }

    public Map<String,Profile> getProfileMap(){ return profileMap; }

    public void setProfileMap(Map<String,Profile> profileMap){
        this.profileMap = profileMap;
    }

    public Map<String,Builder> getBuilderMap(){ return builderMap; }

    public void setBuilderMap(Map<String,Builder> builderMap){
        this.builderMap = builderMap;
    }

    public String toJavaType(String jdbcType){
        if(typeMap.containsKey(jdbcType.toLowerCase())){
            return typeMap.get(jdbcType.toLowerCase());
        }
        return "String";
    }
}
