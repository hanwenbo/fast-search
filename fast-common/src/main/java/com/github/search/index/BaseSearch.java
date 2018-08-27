package com.github.search.index;

import com.github.search.commons.ESUtils;
import com.github.search.page.BoolPager;
import com.github.search.page.LucenePager;
import com.github.search.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description: 索引查询类
 * User: zhubo
 * Date: 2017-08-29
 * Time: 16:07
 */
public class BaseSearch {


    /**
     * 根据索引ID搜索
     * @param client
     * @param _index
     * @param _type
     * @param _id
     */
    public static String findById(TransportClient client,String _index , String _type ,String _id){
        GetResponse getResponse = client.prepareGet(_index, _type, _id).execute().actionGet();
        return getResponse.getSourceAsString();
    }

    /**
     * 根据pager搜索
     * @param client
     * @param pager
     */
    public static LucenePager simpleQuery(TransportClient client , LucenePager pager) throws IOException{

        SearchRequestBuilder builder = client.prepareSearch(pager.get_index())
                .setTypes(pager.get_type())
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        String[] strs = pager.getFields();
        //设置返回字段
        if(strs != null && strs.length >0){
            builder.setFetchSource(pager.getFields(),null);
        }
        //设置query查询
        if(StringUtils.isNotBlank(pager.getQueryField()) && StringUtils.isNotBlank(pager.getQueryValue())){
            QueryBuilder queryBuilder = QueryBuilders.matchQuery(pager.getQueryField() , pager.getQueryValue());
            builder.setQuery(queryBuilder);
        }

        if(StringUtils.isNotBlank(pager.getFilterField()) && StringUtils.isNotBlank(pager.getFilterValue())){
            QueryBuilder queryBuilder = QueryBuilders.termQuery(pager.getFilterField() , pager.getFilterValue());
            builder.setPostFilter(queryBuilder);
        }

        builder = ESUtils.getResponse(builder,pager);
        SearchResponse response  =  builder.setExplain(true).get();

        SearchHit[] searchHits = response.getHits().getHits();
        long totalRows = response.getHits().getTotalHits();
        List<String> result = new ArrayList<String>();
        for(SearchHit searchHit:searchHits){
            Map<String, Object> source = searchHit.getSource();
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            highlightFields.forEach((key,value)->{
                source.put(key,value.fragments()[0].toString());
            });
            result.add(JsonUtils.obj2Str(source));
        }
        pager.setTotalRows(totalRows);
        pager.setResult(result);
        return pager;
    }


    public static BoolPager boolQuery(TransportClient client, BoolPager pager) throws IOException{
        SearchRequestBuilder builder = client.prepareSearch(pager.get_index())
                .setTypes(pager.get_type())
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        // collapse(builder , pager);
        // distinct(builder,pager);
        String[] strs = pager.getFields();
        //设置返回字段
        if(strs != null && strs.length >0){
            builder.setFetchSource(pager.getFields(),null);
        }

        //根据pager初始化boolQuery
        BoolQueryBuilder boolQueryBuilder = ESUtils.getBoolQueryBuilder(pager);

        if(boolQueryBuilder != null){
            builder.setQuery(boolQueryBuilder);
        }

        //设置排序 高亮 和 分页
        builder = ESUtils.getResponse(builder,pager);
        SearchResponse response  =  builder.setExplain(true).get();
        long totalRows = response.getHits().getTotalHits();
        List<String> result = new ArrayList<String>();
        SearchHit[] searchHits = response.getHits().getHits();
        for(SearchHit searchHit:searchHits){
            result.add(JsonUtils.obj2Str(searchHit.getSource()));
        }
        pager.setTotalRows(totalRows);
        pager.setResult(result);
        if(pager.getFields()!= null && "id".equals(pager.getFields()[0])){
            List<Long> ids = new ArrayList<Long>();
            for (String jsonStr : result) {
                ids.add((Long) JsonUtils.getValue(jsonStr,"id"));
            }
            pager.setIds(ids);
        }
        return pager;
    }

    /**
     * 根据ids 查询列表
     * @param client
     * @param pager pager.ids
     * @return
     * @throws IOException
     */
    public static BoolPager idsQuery(TransportClient client, BoolPager pager) throws IOException{
        if(pager.getIds() != null){
            List<String> ids = pager.getIds().stream().map(item -> item.toString()).collect(Collectors.toList());

            QueryBuilder queryBuilder = QueryBuilders.idsQuery(pager.get_type()).addIds(ids.toArray(new String[ids.size()]));
            SearchResponse response = client.prepareSearch(pager.get_index()).setTypes(pager.get_type()).setQuery(queryBuilder).execute().actionGet();
            long totalRows = response.getHits().getTotalHits();
            SearchHit[] searchHits = response.getHits().getHits();
            List<String> result = new ArrayList<String>();
            for(SearchHit searchHit:searchHits){
                result.add(JsonUtils.obj2Str(searchHit.getSource()));
            }
            pager.setTotalRows(totalRows);
            pager.setResult(result);
        }
        return pager;
    }

    public static void collapse(SearchRequestBuilder builder, BoolPager pager) {
        if(pager.getFields() != null && pager.getFields().length != 0) {
            CollapseBuilder collapse = new CollapseBuilder(pager.getFields()[0]);
            builder.setCollapse(collapse);
        }
    }



}
