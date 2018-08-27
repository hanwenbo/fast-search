package com.github.search.index.manage;

import com.github.search.pub.settings.TokenFactor;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 分词搜索
 * @time: 2018年06月28日
 * @modifytime:
 */
public class AnalyzeSearch {


    public static List<TokenFactor> analyze(TransportClient client, String indexName , String analyzer , String analyzeText){
        AnalyzeRequestBuilder request = new AnalyzeRequestBuilder(client, AnalyzeAction.INSTANCE);
        request.setIndex(indexName);
        request.setAnalyzer(analyzer);
        request.setText(analyzeText);
        AnalyzeResponse analyzeTokens = request.execute().actionGet();
        List<AnalyzeResponse.AnalyzeToken> tokens = analyzeTokens.getTokens();
        System.out.println(tokens);
        List<TokenFactor> list = new ArrayList<>();
        tokens.forEach(item -> {
            TokenFactor token = new TokenFactor();
            token.setTerm(item.getTerm());
            token.setStartOffset(item.getStartOffset());
            token.setEndOffset(item.getEndOffset());
            token.setPosition(item.getPosition());
            token.setPositionLength(item.getPositionLength());
            token.setAttributes(item.getAttributes());
            token.setType(item.getType());
            list.add(token);
        });
        return list;
    }

}
