package com.github.search.convert;

import com.github.search.utils.NumberValidationUtils;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-08-31
 * Time: 10:01
 */
public class MapDeserialize implements JsonDeserializer<Map<String,Object>> {
    @Override
    public Map<String, Object> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Map<String,Object> map = new HashMap<String,Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonElement.getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry:entrySet){
            String key = entry.getKey();
            Object value = null;
            if(entry.getValue() instanceof JsonArray){
                JsonArray jsonArray = entry.getValue().getAsJsonArray();
                Iterator<JsonElement> iterator = jsonArray.iterator();
                //TODO

            }else{
                int flag = isNumber(entry.getValue().getAsString());
                if(flag == 0){
                    value = entry.getValue().getAsString();
                }else if (flag == 1){
                    value = entry.getValue().getAsInt();
                }else if(flag == 2){
                    value = entry.getValue().getAsLong();
                }else if(flag == 3){
                    value = entry.getValue().getAsDouble();
                }
                map.put(key,value);
            }

        }
        return map;
    }


    /**
     * 判断是不是int类型的数字
     * @param str
     * @return
     *  String 返回0
     *  是int类型返回1
     *  是long 返回2
     *  是double 返回3
     */
    public  int isNumber(String str) {
        boolean isNegative = NumberValidationUtils.isWholeNumber(str);
        if(isNegative){
            Long value = null;
            try{
                value = new Long(str);
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
            if(value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE){
                return 1;
            }else{
                return 2;
            }
        }else if(NumberValidationUtils.isDecimal(str)){
            BigDecimal big = new BigDecimal(str);
            if(big.compareTo(new BigDecimal(Double.MAX_VALUE)) == 1 && big.compareTo(new BigDecimal(Double.MIN_VALUE)) == -1){
                return 0;
            }else{
                return 3;
            }
        }else{
            return 0;
        }
    }
}
