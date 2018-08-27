package com.github.search.convert;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * Description: 序列化的时候起作用
 * User: zhubo
 * Date: 2017-09-25
 * Time: 10:03
 */
public class DoubleSerializer implements JsonSerializer<Double>{

    @Override
    public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
        if(aDouble == aDouble.longValue()){
            return new JsonPrimitive(aDouble.longValue());
        }else{
            return new JsonPrimitive(aDouble);
        }
    }
}
