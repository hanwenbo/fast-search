package com.github.search.convert;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * Description: int类型反序列化工具
 * User: zhubo
 * Date: 2017-09-19
 * Time: 11:27
 */
public class IntDeserializer implements JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String val = jsonElement.getAsString();
        Double doubleVal = Double.parseDouble(val);
        Long longVal  = Math.round(doubleVal);
        if(doubleVal.doubleValue() == longVal.longValue()){
            return longVal.intValue();
        }
        System.out.println(doubleVal.doubleValue() + " --> " + longVal.intValue() );
        return longVal.intValue();
    }
}
