package com.github.search.utils.str2page;

import com.github.search.enums.str2page.MethodPatternEnum;
import com.github.search.enums.str2page.RangeInnerPatternEnum;
import com.github.search.enums.str2page.RangeOuterPatternEnum;
import com.github.search.enums.str2page.RangeSimplePatternEnum;
import com.github.search.pub.ValueEntity;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月25日
 * @modifytime:
 */
public class MetaConvertUtils {

    private static final String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    private static final String DEFAULT_METHOD_PATTERN = "^([^:]+):\\{([^:]+):([^:]+)\\}$" ;   // 1.a:{wildcard:W?F*HW}
    private static final String DEFAULT_SIMPLE_PATTERN = "^([^:<>= ]+):([^:\\{\\}\\[\\]]+)$";    // 2.a:123

    private static final String DOUBLE_OUTER_RANGE_PATTERN = "^([^<>=]+)(<=|<|>=|>)([^\\|]+)\\|([^<>=]+)(<=|<|>=|>)([^\\|]+)$"; // a<=yyyy-MM-dd HH:mm:ss|a>=yyyy-MM-dd HH:mm:ss
    private static final String DOUBLE_INNER_RANGE_PATTERN = "^([^<>=]+)(<=|<|>=|>)([^<>= ]+)(<=|<|>=|>)([^<>=]+)$";  // yyyy-MM-dd HH:mm:ss<=a<=yyyy-MM-dd HH:mm:ss

    private static final String SIMPLE_RANGE_PATTERN = "^([^<>=]+)(<=|<|>=|>)([^\\|<>=]+)$";

    /**
     * 入口
     * @param notFlag
     * @param andOrFlag
     * @param metadata
     * @return
     */
    public static ValueEntity convertEntity (int notFlag , int andOrFlag , String metadata) {
        ValueEntity ve = convertMethodEntity(notFlag, andOrFlag, metadata);
        if(ve == null) {
            ve = convertSimpleEntity(notFlag, andOrFlag, metadata);
        }
        if (ve == null) {
            ve = convertRangeEntity(notFlag, andOrFlag, metadata);
        }
        return ve;
    }

    /**
     *
     * @param notFlag
     * @param andOrFlag
     * @param metadata a:{wildcard:W?F*HW}
     * @return
     */
    private static ValueEntity convertMethodEntity(int notFlag , int andOrFlag , String metadata) {
        Pattern pattern = Pattern.compile(DEFAULT_METHOD_PATTERN);
        Matcher matcher = pattern.matcher(metadata);
        if(matcher.find()) {
            String field = matcher.group(1);
            String method = matcher.group(2);
            String arge = matcher.group(3);
            MethodPatternEnum byMethod = MethodPatternEnum.getByMethod(method);
            ValueEntity ve = new ValueEntity.Builder(field,new Object[]{arge}).rule(byMethod.getCode()).build();
            ve.setNotFlag(notFlag);
            ve.setAndOrFlag(andOrFlag);
            return ve;
        }
        return null;
    }

    /**
     *
     * @param notFlag
     * @param andOrFlag
     * @param metadata  field:value
     * @return
     */
    private static ValueEntity convertSimpleEntity(int notFlag , int andOrFlag , String metadata) {
        Pattern pattern = Pattern.compile(DEFAULT_SIMPLE_PATTERN);
        Matcher matcher = pattern.matcher(metadata);
        if(matcher.find()) {
            String field = matcher.group(1);
            String value = matcher.group(2);
            String[] split = StringUtils.split(value, "|");
            ValueEntity ve = new ValueEntity.Builder(field,split).build();
            ve.setNotFlag(notFlag);
            ve.setAndOrFlag(andOrFlag);
            return ve;
        }
        return null;
    }

    /**
     * 范围查询
     * @param notFlag
     * @param andOrFlag
     * @param metadata
     * @return
     */
    private static ValueEntity convertRangeEntity(int notFlag , int andOrFlag , String metadata) {
        ValueEntity ve  = null;
        ve  = convertInnerRange(notFlag, andOrFlag, metadata);
        if (ve == null) {
            ve = convertOuterRange(notFlag, andOrFlag, metadata);
        }
        if (ve == null) {
            ve = convertSimpleRange(notFlag, andOrFlag, metadata);
        }
        return ve;
    }


    private static ValueEntity convertOuterRange(int notFlag , int andOrFlag , String metadata){

        Pattern pattern = Pattern.compile(DOUBLE_OUTER_RANGE_PATTERN);
        Matcher matcher = pattern.matcher(metadata);
        if(matcher.find()) {
          /*  System.out.println(matcher.group(1));// a
            System.out.println(matcher.group(2));// <=
            System.out.println(matcher.group(3));// yyyy-MM-dd HH:mm:ss
            System.out.println(matcher.group(4));// a
            System.out.println(matcher.group(5));// >=
            System.out.println(matcher.group(6));// yyyy-MM-dd HH:mm:ss*/
            String field = matcher.group(1);
            String v1 = matcher.group(3);
            String v2 = matcher.group(6);

            String f1 = matcher.group(2);
            String f2 = matcher.group(5);
            if(f1.contains(">")) {
                f1 = matcher.group(5);
                f2 = matcher.group(2);

                v1 = matcher.group(6);
                v2 = matcher.group(3);
            }
            boolean isTime = false;
            if(isTime(v1) && isTime(v2)){
                isTime = true;
            }
            RangeOuterPatternEnum range = RangeOuterPatternEnum.getByMethod(f1, f2);
            int searchType = 0;
            if(range != null && isTime){
                searchType = range.getDateCode();
            }else if (range != null && !isTime) {
                searchType = range.getNumCode();
            }else {
                return null;
            }
            ValueEntity ve = new ValueEntity.Builder(field, new Object[]{v1,v2}).rule(searchType).build();
            ve.setNotFlag(notFlag);
            ve.setAndOrFlag(andOrFlag);
            return ve;
        }
        return null;
    }

    private static ValueEntity convertInnerRange(int notFlag , int andOrFlag , String metadata) {
        Pattern pattern = Pattern.compile(DOUBLE_INNER_RANGE_PATTERN);
        Matcher matcher = pattern.matcher(metadata);
        int searchType = 0;

        /*System.out.println(matcher.group(1));//yyyy-MM-dd HH:mm:ss
        System.out.println(matcher.group(2));//<=
        System.out.println(matcher.group(3));//a
        System.out.println(matcher.group(4));//<=
        System.out.println(matcher.group(5));//yyyy-MM-dd HH:mm:ss*/

        if(matcher.find()) {
            boolean isTime = false;
            String v1 = matcher.group(1);
            String v2 = matcher.group(5);
            if(isTime(v1) && isTime(v2)){
                isTime = true;
            }
            String f1 = matcher.group(2);
            String f2 = matcher.group(4);

            if(matcher.group(2).contains(">")) {
                f1 = matcher.group(4).replace('>','<');
                f2 = matcher.group(2).replace('>','<');
                v1 = matcher.group(5);
                v2 = matcher.group(1);
            }
            RangeInnerPatternEnum range = RangeInnerPatternEnum.getByMethod(f1, f2);
            if(range != null && isTime){
                searchType = range.getDateCode();
            }else if (range != null && !isTime) {
                searchType = range.getNumCode();
            }else {
                return null;
            }
            ValueEntity ve = new ValueEntity.Builder(matcher.group(3), new Object[]{v1,v2}).rule(searchType).build();
            ve.setNotFlag(notFlag);
            ve.setAndOrFlag(andOrFlag);
            return ve;
        }
        return null;
    }

    private static boolean isTime(String timeStr){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR);
        try{
            sdf.parse(timeStr);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    private static ValueEntity convertSimpleRange(int notFlag , int andOrFlag , String metadata){
        Pattern pattern = Pattern.compile(SIMPLE_RANGE_PATTERN);
        Matcher matcher = pattern.matcher(metadata);
        if(matcher.find()) {
            System.out.println(matcher.group(1));// a
            System.out.println(matcher.group(2));// <=
            System.out.println(matcher.group(3));// yyyy-MM-dd HH:mm:ss
            String field = matcher.group(1);
            String f1 = matcher.group(2);
            String v1 = matcher.group(3);
            boolean isTime = isTime(v1);
            int searchType = 0;
            RangeSimplePatternEnum range = RangeSimplePatternEnum.getByMethod(f1);
            if(range != null && isTime){
                searchType = range.getDateCode();
            }else if (range != null && !isTime) {
                searchType = range.getNumCode();
            }else {
                return null;
            }
            ValueEntity ve = new ValueEntity.Builder(field, new Object[]{v1}).rule(searchType).build();
            ve.setNotFlag(notFlag);
            ve.setAndOrFlag(andOrFlag);
            return ve;
        }
        return null;
    }



    public static void main(String[] args) {
        // ValueEntity valueEntity = convertEntity(1, 1, "a:{wildcard:W?F*HW}");
        // ValueEntity valueEntity1 = convertMethodEntity(1, 1, "a:{wildcard:W?F*HW}");
        // ValueEntity valueEntity = convertSimpleEntity(1, 1, "a:123");
        String s1 = "yyyy-MM-dd HH:mm:ss<=a<=yyyy-MM-dd HH:mm:ss";
        String s2 = "a<=yyyy-MM-dd HH:mm:ss|a>=yyyy-MM-dd HH:mm:ss";
        String s3 = "field<yyyy-MM-dd HH:mm:ss";

        //convertInnerRange(1, 1, s1);
        // convertOuterRange(1, 1, s2);

        convertSimpleRange(1,1,s3);


    }


}
