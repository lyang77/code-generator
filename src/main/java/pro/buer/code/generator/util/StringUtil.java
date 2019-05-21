package pro.buer.code.generator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 * create date:2017/7/27
 * remark:
 */
public final class StringUtil{

    static final Pattern WORD_REGEX_UNDERLINE = Pattern.compile("[a-zA-Z0-9]+");

    public static String upCaseFirst(String input){
        if(input.length() > 0){
            char[] cs = input.toCharArray();
            cs[0] = Character.toUpperCase(cs[0]);
            return String.valueOf(cs);
        }
        return input;
    }

    public static String lowCaseFirst(String input){
        if(input.length() > 0){
            input= input.replace("_","");
//            char[] cs = input.toCharArray();
//            cs[0] = Character.toLowerCase(cs[0]);
            return (new StringBuilder()).append(Character.toLowerCase(input.charAt(0))).append(input.substring(1)).toString();
        }
        return input;
    }

    public static List<String> words(String input){
        if(input == null){
            return null;
        }
        List<String> words = new ArrayList<>();
        if(input.contains("_")){
            Matcher matcher = WORD_REGEX_UNDERLINE.matcher(input);
            while(matcher.find()){
                words.add(matcher.group());
            }
        } else{
            int start = 0;
            try{
                for(int i = 0; i < input.length(); i++){
                    char c = input.charAt(i);
                    if(i == input.length() - 1){
                        words.add(input.substring(start, input.length()));
                        break;
                    }
                    if(isUpper(c) && isLower(input.charAt(i + 1)) && i > 0){
                        words.add(input.substring(start, i));
                        start = i;
                        continue;
                    }
                    if(isLower(c) && isUpper(input.charAt(i + 1))){
                        words.add(input.substring(start, i + 1));
                        start = i + 1;
                        continue;
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(words.isEmpty()){
                words.add(input);
            }
        }
        return words;
    }

    private static boolean isUpper(char c){
        return c > 64 && c < 91;
    }

    private static boolean isLower(char c){
        return c > 96 && c < 123;
    }

    private static boolean isNumber(char c){
        return c > 47 && c < 58;
    }
}
