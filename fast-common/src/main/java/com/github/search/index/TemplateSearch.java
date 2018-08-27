package com.github.search.index;

import com.github.search.index.manage.BaseIndex;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:模板查询(Mustache)
 * User: zhubo
 * Date: 2017-11-13
 * Time: 8:49
 */
public class TemplateSearch {


    /**
     * @param client
     * @param _index
     * @param _type
     * @param template 模板名称
     * @param map
     * @return
     */
    public static SearchResponse searchTemplateFile(TransportClient client, String _index , String _type,String template, Map<String,Object> map){
        SearchResponse response = new SearchTemplateRequestBuilder(client)
                .setScript(template)
                .setScriptType(ScriptType.FILE)
                .setScriptParams(map)
                .setRequest(new SearchRequest().indices(_index).types(_type))
                .get()
                .getResponse();

        return response;
    }


    /**
     *
     * @param client
     * @param _index
     * @param _type
     * @param templateStr
     * @param map
     * @return
     */
    public static SearchResponse searchTemplateInline(TransportClient client, String _index, String _type , String templateStr , Map<String,Object> map){
        SearchResponse response = new SearchTemplateRequestBuilder(client)
                .setScript(templateStr)
                .setScriptType(ScriptType.INLINE)
                .setScriptParams(map)
                .setRequest(new SearchRequest().indices(_index).types(_type))
                .get()
                .getResponse();

        return response;
    }

    /**
     *
     * @param client
     * @param _index
     * @param _type
     * @param path
     * @param map
     * @return
     * @throws IOException
     */
    public static SearchResponse searchTemplatePath(TransportClient client, String _index, String _type , String path , Map<String,Object> map) throws IOException{
        ClassLoader classLoader = BaseIndex.class.getClassLoader();
        String jsonStr = IOUtils.toString(classLoader.getResourceAsStream(path),"UTF-8");
        return TemplateSearch.searchTemplateInline(client,_index,_type,jsonStr,map);
    }


}
