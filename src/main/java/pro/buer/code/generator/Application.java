package pro.buer.code.generator;

import com.alibaba.fastjson.JSON;
import pro.buer.code.generator.domain.configuration.Configuration;
import pro.buer.code.generator.util.FileUtil;

import java.time.LocalDateTime;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public class Application {

    public static void main(String[] args) {
        //解析命令行
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option<String> configOperation = parser.addStringOption('c', "config");
        try {
            parser.parse(args);
            String configfile = parser.getOptionValue(configOperation, "/configuration.json");
            String json = FileUtil.readFile(configfile);
            Configuration config = JSON.parseObject(json, Configuration.class);
            Generator generator = new Generator();
            generator.run(config);
            System.out.println(LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//      String name="Index_CustomerSymbolName";
//        name= lowCaseFirst(name);
//    }
//
//    public static String lowCaseFirst(String input){
//        if(input.length() > 0){
//            input= input.replace("_","");
//            char[] cs = input.toCharArray();
//            cs[0] = Character.toLowerCase(cs[0]);
//            return String.valueOf(cs);
//        }
//        return input;
//    }

}
