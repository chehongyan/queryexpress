/*
 * Filename	CharacterParser.java
 * Company	上海乐问-浦东分公司。
 * @author	LuRuihui
 * @version	0.1
 */
package com.sortlistview;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;

/**
 * Java汉字转换为拼音
 * 
 */
public class CharacterParser {

    public static String converCnToPY(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        StringBuffer output = new StringBuffer();

        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    Arrays.sort(temp);
                    output.append(temp[0]);
                } else if (Character.toString(input[i]).matches("[A-Z]+")) {
                    output.append(Character.toString(input[i]));
                } else if (Character.toString(input[i]).matches("[a-z]+")) {
                    output.append(Character.toString(input[i]).toUpperCase());
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output.toString();
    }

}
