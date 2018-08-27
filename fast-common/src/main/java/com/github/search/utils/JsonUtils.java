package com.github.search.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-08-29
 * Time: 15:56
 */
public class JsonUtils {

    /**
     * 实现格式化的时间字符串转时间对象
     */
    private static final String DATEFORMAT_default = "yyyy-MM-dd HH:mm:ss";


    /**
     * 获取简单json字符串中指定的属性 数字有Double 和 Long类型两种
     * @param json
     * @param name
     * @return
     * @throws IOException
     */
    public static Object getValue(String json , String name) throws IOException{

        Gson gson = GsonUtils.getMapConvertGson();

        Map<String,Object> map = gson.fromJson(json,new TypeToken<Map<String,Object>>(){}.getType());

        Object returnVal = map.get(name);

        return returnVal;
    }

    /**
     * 获取对象对应的json字符串 去除null值
     * @param obj
     * @return
     * @throws IOException
     */
    public static String obj2Str(Object obj)throws IOException{

        Gson gson = GsonUtils.getDoubleSerializerGson();

        String jsonStr = gson.toJson(obj);

        return jsonStr;
    }

    public static <T> T str2Obj(String jsonStr,Class<T> tClass){
        Gson gson = GsonUtils.getDoubleSerializerGson();
        return gson.fromJson(jsonStr,tClass);
    }


    /**
     * 使用默认的gson对象进行反序列化
     *
     * @param json
     * @param typeToken
     * @return
     */
    public static <T> T fromJsonDefault(String json, TypeToken<T> typeToken) {
        Gson gson = new Gson();
        return gson.fromJson(json, typeToken.getType());
    }

    public static <T> T fromJsonDefault(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    /**
     * json字符串转list或者map
     *
     * @param json
     * @param typeToken
     * @return
     */
    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        Gson gson = new GsonBuilder()
                /**
                 * 重写map的反序列化
                 */
                .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
                }.getType(), new GsonUtils.MapTypeAdapter()).create();

        return gson.fromJson(json, typeToken.getType());

    }

    /**
     * json字符串转bean对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T fromJson(String json, Class<T> cls) {

        Gson gson = new GsonBuilder().setDateFormat(DATEFORMAT_default)
                .create();

        return gson.fromJson(json, cls);

    }

    /**
     * 对象转json
     *
     * @param obj
     * @param format json字符串是否进行格式化设置
     * @return
     */
    public static String toJson(Object obj, boolean format) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        /**
         * 设置默认时间格式
         */
        gsonBuilder.setDateFormat(DATEFORMAT_default);

        /**
         * 添加格式化设置
         */
        if (format) {
            gsonBuilder.setPrettyPrinting();
        }

        Gson gson = gsonBuilder.create();

        return gson.toJson(obj);
    }


    /**
     * 过滤掉json字符串中的执行属性
     * @param jsonStr
     * @param str
     * @return
     */
    public static String jsonSkipField(String jsonStr , String[] str){
        Gson gson = GsonUtils.getDoubleSerializerGson();
        Map<String,Object> map = gson.fromJson(jsonStr,new TypeToken<HashMap<String,Object>>(){}.getType());
        Set<String> set = map.keySet();
        for (String s : str ) {
            if(set.contains(s)){
                map.remove(s);
            }
        }
        return gson.toJson(map);
    }

    /**
     * 修改json key名称
     * @param source
     * @param rep
     * @return
     */
    public static JsonElement replaceKey(JsonElement source,Map<String, String> rep) {
        if (source == null || source.isJsonNull()) {
            return JsonNull.INSTANCE;
        }
        if (source.isJsonPrimitive()) {
            return source;
        }
        if (source.isJsonArray()) {
            JsonArray jsonArr = source.getAsJsonArray();
            JsonArray jsonArray = new JsonArray();
            jsonArr.forEach(item -> {
                jsonArray.add(replaceKey(item, rep));
            });
            return jsonArray;
        }
        if (source.isJsonObject()) {
            JsonObject jsonObj = source.getAsJsonObject();
            Iterator<Map.Entry<String, JsonElement>> iterator = jsonObj.entrySet().iterator();
            JsonObject newJsonObj = new JsonObject();
            iterator.forEachRemaining(item -> {
                String key = item.getKey();
                JsonElement value = item.getValue();
                if (rep.containsKey(key)) {
                    String newKey = rep.get(key);
                    key = newKey;
                }
                newJsonObj.add(key, replaceKey(value, rep));
            });

            return newJsonObj;
        }
        return JsonNull.INSTANCE;
    }



    /**
     * 修改json 中某个键的值
     * @param source
     * @param rep
     * @return
     */
    public static JsonElement replaceValue(JsonElement source,Map<String, String> rep) {
        if (source == null || source.isJsonNull()) {
            return JsonNull.INSTANCE;
        }
        if (source.isJsonPrimitive()) {
            return source;
        }
        if (source.isJsonArray()) {
            JsonArray jsonArr = source.getAsJsonArray();
            JsonArray jsonArray = new JsonArray();
            jsonArr.forEach(item -> {
                jsonArray.add(replaceValue(item, rep));
            });
            return jsonArray;
        }
        if (source.isJsonObject()) {
            JsonObject jsonObj = source.getAsJsonObject();
            Iterator<Map.Entry<String, JsonElement>> iterator = jsonObj.entrySet().iterator();
            JsonObject newJsonObj = new JsonObject();
            iterator.forEachRemaining(item -> {
                String key = item.getKey();
                JsonElement value = item.getValue();
                if (rep.containsKey(key)) {
                    String newValue = rep.get(key);
                    value = new JsonParser().parse(newValue);
                }
                newJsonObj.add(key, replaceValue(value, rep));
            });

            return newJsonObj;
        }
        return JsonNull.INSTANCE;
    }



    /**
     * 代替或者添加
     * @param source
     * @param rep
     * @return
     */
    public static JsonElement replaceOrAddValue(JsonElement source,Map<String, String> rep) {
        if (source == null || source.isJsonNull()) {
            return JsonNull.INSTANCE;
        }
        if (source.isJsonPrimitive()) {
            return source;
        }
        if (source.isJsonArray()) {
            JsonArray jsonArr = source.getAsJsonArray();
            return jsonArr;
        }
        if (source.isJsonObject()) {
            JsonObject jsonObj = source.getAsJsonObject();
            Iterator<Map.Entry<String, JsonElement>> iterator = jsonObj.entrySet().iterator();
            JsonObject newJsonObj = new JsonObject();
            iterator.forEachRemaining(item -> {
                String key = item.getKey();
                JsonElement value = item.getValue();
                if(rep.containsKey(key)){
                    newJsonObj.add(key, new JsonParser().parse(rep.get(key)));
                    rep.remove(rep);
                }else{
                    newJsonObj.add(key,item.getValue());
                }
            });

           Iterator<String> keys = rep.keySet().iterator();
           keys.forEachRemaining(item -> {
               String value = rep.get(item);
               newJsonObj.add(item,new JsonParser().parse(value));
           });

            return newJsonObj;
        }
        return JsonNull.INSTANCE;
    }



    public static void main(String[] args) {
        /*Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",1231231231);
        map.put("labelIds",new Integer[]{1,2,3,4,5,6});

        String gson = new Gson().toJson(map);
        System.out.println(gson);

        System.out.println(jsonSkipField(gson,new String[]{"ids"}));*/
        /*String abc = "{\"labelIds\":[1,2,3],\"readNum\":23,\"articleCategoryId\":\"aaaaaaaaaaaaaaaa\",\"id\":\"1231231313123123\"}";
        JsonElement parse = new JsonParser().parse(abc);
        HashMap<String, String> rep = new HashMap<String, String>();
        rep.put("id", "123123");
        JsonElement replaceKey = replaceValue(parse, rep);
        System.out.println(replaceKey.toString());*/

        String abc = "{\"labelIds\":[1,2,3],\"readNum\":23,\"articleCategoryId\":\"aaaaaaaaaaaaaaaa\"}";
        JsonElement parse = new JsonParser().parse(abc);
        HashMap<String, String> rep = new HashMap<String, String>();
        rep.put("id", "123123");
        JsonElement replaceKey = replaceOrAddValue(parse, rep);
        System.out.println(replaceKey.toString());


    }

}
