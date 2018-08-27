package com.github.search.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-08-31
 * Time: 10:32
 */
public class NumberValidationUtils {

    private static boolean isMatch(String regex, String orginal){
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    /**
     * 正整数判断
     * 对于正整数而言，可以带+号，第一个数字不能为0
     * @param orginal
     * @return
     */
    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    /**
     * 负整数判断
     * 对于负整数而言，必须带负号，第一个数字也不能为0
     * @param orginal
     * @return
     */
    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    /**
     * 整数判断
     * 对于整数而言，实际是由0，正整数和负整数组成的，所以偷个懒用前两个方法一起判断
     * @param orginal
     * @return
     */
    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    /**
     * 正小数
     * 对于正小数而言，可以考带+号，并考虑两种情况，第一个数字为0和第一个数字不为0，第一个数字为0时，则小数点后面应该不为0，第一个数字不为0时，小数点后可以为任意数字
     * @param orginal
     * @return
     */
    public static boolean isPositiveDecimal(String orginal){
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 负小数
     * 对于负小数而言，必须带负号，其余都同上
     * @param orginal
     * @return
     */
    public static boolean isNegativeDecimal(String orginal){
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 小数
     * 对于小数，可以带正负号，并且带小数点就行了，但是至少保证小数点有一边不为空，所以这里还是分左边不为空和右边不为空的情况
     * @param orginal
     * @return
     */
    public static boolean isDecimal(String orginal){
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    /**
     * 实数
     * 实数比较简单，，要么是整数，要么是小数
     * @param orginal
     * @return
     */
    public static boolean isRealNumber(String orginal){
        return isWholeNumber(orginal) || isDecimal(orginal);
    }
}
