package com.github.search.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-08-29
 * Time: 20:23
 */
public class URISplitUtils {

    public static Map<String, String> splitQuery(String url) throws UnsupportedEncodingException {

        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url;
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;

    }
}
