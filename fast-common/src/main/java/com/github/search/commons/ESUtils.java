package com.github.search.commons;

import com.github.search.enums.AndOrEnums;
import com.github.search.enums.NotFlagEnum;
import com.github.search.page.BoolPager;
import com.github.search.page.LucenePager;
import com.github.search.pub.SearchFactor;
import com.github.search.pub.SearchType;
import com.github.search.pub.ValueEntity;
import com.github.search.pub.ValuePackage;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 工具类
 * User: zhubo
 * Date: 2017-08-29
 * Time: 16:28
 */
public class ESUtils {

    /**
     * 设置排序 高亮 和 分页
     * @param builder
     * @param pager
     * @return
     */
    public static SearchRequestBuilder getResponse(SearchRequestBuilder builder , LucenePager pager){
        //设置排序
        if(pager.getSortFields() != null && pager.getSortFields().size() > 0){
            Map<String,Integer> map = pager.getSortFields();
            Set<String> values = map.keySet();
            for (String field :values) {
                Integer value = map.get(field);
                SortBuilder sortBuilder = null;
                if(value.intValue() == SearchFactor.ASC){
                    sortBuilder = SortBuilders.fieldSort(field).order(SortOrder.ASC);
                }else{
                    sortBuilder = SortBuilders.fieldSort(field).order(SortOrder.DESC);
                }
                builder.addSort(sortBuilder);
            }
        }

        //设置高亮
        if(pager.getHighFields() != null && pager.getHighFields().length > 0){
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            for (String field : pager.getHighFields()) {
                highlightBuilder.field(field);
            }
            if(pager.getPreTag() == null || pager.getPostTag() == null){
                highlightBuilder.preTags("<b>").postTags("</b>");
            }else{
                highlightBuilder.preTags(pager.getPreTag()).postTags(pager.getPostTag());
            }
            builder.highlighter(highlightBuilder);
        }

        builder = builder.setFrom((int)pager.getStart()).setSize((int)pager.getPageSize());
        return builder;
    }


    /**
     * 根据pager信息获取 BoolQueryBuilder类 信息
     * @param pager
     * @return
     */
    public static BoolQueryBuilder getBoolQueryBuilder(BoolPager pager){

        //初始化boolQuery
        BoolQueryBuilder boolQueryBuilder = null;
        if(pager.getQuery() != null && pager.getQuery().size() > 0) {
            boolQueryBuilder = QueryBuilders.boolQuery();
        }
        List<ValuePackage> query = pager.getQuery();
        if(query != null) {
            for (ValuePackage pack: query) {
                List<ValueEntity> entitys = pack.getEntitys();
                BoolQueryBuilder bqb = QueryBuilders.boolQuery();
                for (ValueEntity entity: entitys) {
                    QueryBuilder queryBuilder = getQueryBuilder(entity.getField(),entity);
                    if(entity.getNotFlag() == NotFlagEnum.NEGATIVE.getCode()){
                        bqb.mustNot(queryBuilder);
                    }else {
                        bqb.must(queryBuilder);
                    }
                }
                if(pack.getNotFlag() == NotFlagEnum.NEGATIVE.getCode()){
                    bqb = QueryBuilders.boolQuery().mustNot(bqb);
                }
                if(pack.getAndOrFlag() == AndOrEnums.AND.getCode()){
                    boolQueryBuilder.must(bqb);
                } else {
                    boolQueryBuilder.should(bqb);
                    boolQueryBuilder.minimumShouldMatch(1);
                }
            }
        }
        return boolQueryBuilder;
    }


    /**
     * Unit 逻辑 & | 运算
     * @param field
     * @param valueEntity
     * @return
     */
    private static QueryBuilder getQueryBuilder(String field , ValueEntity valueEntity){

        switch (valueEntity.getType()) {
            case SearchType.TERM_QUERY:
                Object[] value = valueEntity.getValue();
                if(value != null && value.length == 1) {
                    return QueryBuilders.termQuery(field, value[0]);
                } else if (value != null && value.length > 1 && valueEntity.getAndOrFlag() == AndOrEnums.OR.getCode()) {
                    return QueryBuilders.termsQuery(field, Arrays.asList(valueEntity.getValue()));
                }else if (value != null && value.length > 1 && valueEntity.getAndOrFlag() == AndOrEnums.AND.getCode()){
                    return QueryBuilders.termQuery(field, value[0]);
                }
            case SearchType.MATCH_QUERY:
                return QueryBuilders.matchQuery(field, valueEntity.getValue()[0]);
            case SearchType.PREFIX_QUERY:
                return QueryBuilders.prefixQuery(field, (String) valueEntity.getValue()[0]);
            case SearchType.WILDCARD_QUERY:
                return QueryBuilders.wildcardQuery(field,(String) valueEntity.getValue()[0]);
            case SearchType.REGEXP_QUERY:
                return QueryBuilders.regexpQuery(field,(String) valueEntity.getValue()[0]);
            case SearchType.DATE_STRING_RANGE_INNER_QUERY:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    return QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").gte(valueEntity.getValue()[0]).lte(valueEntity.getValue()[1]);
                } else {

                }
            case SearchType.DATE_STRING_RANGE_INNER_QUERY_LEFT_OPEN_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    return QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").gt(valueEntity.getValue()[0]).lt(valueEntity.getValue()[1]);
                } else {

                }
            case SearchType.DATE_STRING_RANGE_INNER_QUERY_LEFT_OPEN_RIGHT_CLOSE:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    return QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").gt(valueEntity.getValue()[0]).lte(valueEntity.getValue()[1]);
                } else {

                }
            case SearchType.DATE_STRING_RANGE_INNER_QUERY_LEFT_CLOSE_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    return QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").gte(valueEntity.getValue()[0]).lt(valueEntity.getValue()[1]);
                } else {

                }
            case SearchType.DATE_STRING_RANGE_INNER_QUERY_LEFT_CLOSE:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").gte(valueEntity.getValue()[0]);
                }
            case SearchType.DATE_STRING_RANGE_INNER_QUERY_LEFT_OPEN:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").gt(valueEntity.getValue()[0]);
                }
            case SearchType.DATE_STRING_RANGE_INNER_QUERY_RIGHT_CLOSE:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").lte(valueEntity.getValue()[0]);
                }
            case SearchType.DATE_STRING_RANGE_INNER_QUERY_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").lt(valueEntity.getValue()[0]);
                }
            case SearchType.DATE_OBJ_RANGE_INNER_QUERY:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    return QueryBuilders.rangeQuery(field).gte(valueEntity.getValue()[0]).lte(valueEntity.getValue()[1]);
                }else{

                }
            case SearchType.DATE_OBJ_RANGE_QUERY_LEFT_OPEN_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    return QueryBuilders.rangeQuery(field).gt(valueEntity.getValue()[0]).lt(valueEntity.getValue()[1]);
                }else{

                }
            case SearchType.DATE_OBJ_RANGE_INNER_QUERY_LEFT_CLOSE:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).gte(valueEntity.getValue()[0]);
                } else {

                }
            case SearchType.DATE_OBJ_RANGE_INNER_QUERY_LEFT_OPEN:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).gt(valueEntity.getValue()[0]);
                } else {

                }
            case SearchType.DATE_OBJ_RANGE_INNER_QUERY_RIGHT_CLOSE:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).lte(valueEntity.getValue()[0]);
                }
            case SearchType.DATE_OBJ_RANGE_INNER_QUERY_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).lt(valueEntity.getValue()[0]);
                }

            case SearchType.DATE_STRING_RANGE_OUTER_QUERY:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    BoolQueryBuilder bq =  QueryBuilders.boolQuery();
                    bq.should(QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").lte(valueEntity.getValue()[0]));
                    bq.should(QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").gte(valueEntity.getValue()[1]));
                    return bq;
                } else {

                }
            case SearchType.DATE_STRING_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    BoolQueryBuilder bq =  QueryBuilders.boolQuery();
                    bq.should(QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").lt(valueEntity.getValue()[0]));
                    bq.should(QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").gt(valueEntity.getValue()[1]));
                    return bq;
                } else {

                }

            case SearchType.DATE_STRING_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_CLOSE:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    BoolQueryBuilder bq =  QueryBuilders.boolQuery();
                    bq.should(QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").lt(valueEntity.getValue()[0]));
                    bq.should(QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").gte(valueEntity.getValue()[1]));
                    return bq;
                } else {

                }
            case SearchType.DATE_STRING_RANGE_OUTER_QUERY_LEFT_CLOSE_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    BoolQueryBuilder bq =  QueryBuilders.boolQuery();
                    bq.should(QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").lte(valueEntity.getValue()[0]));
                    bq.should(QueryBuilders.rangeQuery(field).format("yyyy-MM-dd HH:mm:ss").gt(valueEntity.getValue()[1]));
                    return bq;
                } else {

                }










            case SearchType.NUMBER_RANGE_INNER_QUERY:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    return QueryBuilders.rangeQuery(field).gte(valueEntity.getValue()[0]).lte(valueEntity.getValue()[1]);
                }else{

                }
            case SearchType.NUMBER_RANGE_INNER_QUERY_LEFT_OPEN_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    return QueryBuilders.rangeQuery(field).gt(valueEntity.getValue()[0]).lt(valueEntity.getValue()[1]);
                }else{

                }
            case SearchType.NUMBER_RANGE_INNER_QUERY_LEFT_CLOSE_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    return QueryBuilders.rangeQuery(field).gte(valueEntity.getValue()[0]).lt(valueEntity.getValue()[1]);
                }else{

                }
            case SearchType.NUMBER_RANGE_INNER_QUERY_LEFT_OPEN_RIGHT_CLOSE:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    return QueryBuilders.rangeQuery(field).gt(valueEntity.getValue()[0]).lte(valueEntity.getValue()[1]);
                }else{

                }
            case SearchType.NUMBER_RANGE_INNER_QUERY_LEFT_CLOSE:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).gte(valueEntity.getValue()[0]);
                } else {

                }
            case SearchType.NUMBER_RANGE_INNER_QUERY_LEFT_OPEN:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).gt(valueEntity.getValue()[0]);
                } else {

                }
            case SearchType.NUMBER_RANGE_INNER_QUERY_RIGHT_CLOSE:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).lte(valueEntity.getValue()[0]);
                }else{

                }
            case SearchType.NUMBER_RANGE_INNER_QUERY_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null){
                    return QueryBuilders.rangeQuery(field).lte(valueEntity.getValue()[0]);
                }else{

                }

            case SearchType.NUMBER_RANGE_OUTER_QUERY:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    BoolQueryBuilder bq =  QueryBuilders.boolQuery();
                    bq.should(QueryBuilders.rangeQuery(field).lte(valueEntity.getValue()[0]));
                    bq.should(QueryBuilders.rangeQuery(field).gte(valueEntity.getValue()[1]));
                    return bq;
                } else {

                }
            case SearchType.NUMBER_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    BoolQueryBuilder bq =  QueryBuilders.boolQuery();
                    bq.should(QueryBuilders.rangeQuery(field).lt(valueEntity.getValue()[0]));
                    bq.should(QueryBuilders.rangeQuery(field).gt(valueEntity.getValue()[1]));
                    return bq;
                } else {

                }

            case SearchType.NUMBER_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_CLOSE:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    BoolQueryBuilder bq =  QueryBuilders.boolQuery();
                    bq.should(QueryBuilders.rangeQuery(field).lt(valueEntity.getValue()[0]));
                    bq.should(QueryBuilders.rangeQuery(field).gte(valueEntity.getValue()[1]));
                    return bq;
                } else {

                }
            case SearchType.NUMBER_RANGE_OUTER_QUERY_LEFT_CLOSE_RIGHT_OPEN:
                if(valueEntity.getValue()[0] != null && valueEntity.getValue()[1] != null){
                    BoolQueryBuilder bq =  QueryBuilders.boolQuery();
                    bq.should(QueryBuilders.rangeQuery(field).lte(valueEntity.getValue()[0]));
                    bq.should(QueryBuilders.rangeQuery(field).gt(valueEntity.getValue()[1]));
                    return bq;
                } else {

                }
            default:
                return QueryBuilders.termQuery(field, valueEntity.getValue()[0]);
        }
    }




}
