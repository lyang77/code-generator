package pro.buer.code.generator.impl;

import java.util.List;

import pro.buer.code.generator.util.StringUtil;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public class SkipPrefixNameHandlerImpl extends NameHandlerImpl{

    @Override
    protected String parseBeanName(String columnName){
        List<String> words = StringUtil.words(columnName);
        words.remove(0);
        String name = "";
        for(String word : words){
            name += StringUtil.upCaseFirst(word);
        }
        return name;
    }
}
