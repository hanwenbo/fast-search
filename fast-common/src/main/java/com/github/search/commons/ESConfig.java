package com.github.search.commons;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-08-29
 * Time: 16:02
 */
public class ESConfig {

    private static Properties props = new Properties();
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    static{
        try {
            InputStream is = null;
            Resource resource = resourceLoader.getResource("elasticsearch.properties");
            is = resource.getInputStream();
            props.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getValue(String key){
        return props.getProperty(key);
    }

    public static void updateProperties(String key,String value) {
        props.setProperty(key, value);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(props);
    }
}
