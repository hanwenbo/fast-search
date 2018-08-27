package com.github.search.index.manage;

import com.carrotsearch.hppc.ObjectLookupContainer;
import com.github.search.pub.settings.IndexAnalyzeInfo;
import com.github.search.pub.settings.IndexTypeEntity;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月29日
 * @modifytime:
 */
public class ClusterIndexStatus {

    private static final String ANALYSIS_ANALYZER_PREFIX    =    "^index\\.analysis\\.analyzer\\.([^\\.]+)\\..*$";



    public static void main(String[] args) {
        String str = "index.analysis.analyzer.chinese_analyzer.char_filter.0";
        Pattern p = Pattern.compile(ANALYSIS_ANALYZER_PREFIX);
        Matcher matcher = p.matcher(str);
        while(matcher.find()) {
            String group = matcher.group(1);
            System.out.println(group);
        }
    }


    public static List<IndexAnalyzeInfo> indexAnalyzeGet(TransportClient client) {
        List<IndexAnalyzeInfo> infos= new ArrayList<>();
        Pattern p = Pattern.compile(ANALYSIS_ANALYZER_PREFIX);
        GetSettingsResponse response = client.admin().indices().prepareGetSettings().get();
        System.out.println(response);
        ImmutableOpenMap<String, Settings> settings = response.getIndexToSettings();
        ObjectLookupContainer<String> indices = settings.keys();
        if(!indices.isEmpty()){
            String[] strings = indices.toArray(String.class);
            for (String s: strings) {
                if(StringUtils.isNotBlank(s)){
                    IndexAnalyzeInfo analyze = new IndexAnalyzeInfo();
                    analyze.setIndexName(s.trim());
                    Settings setting = settings.get(s);
                    Set<String> keys = setting.keySet();
                    Set<String> analyzers = new HashSet<>();
                    for (String k : keys) {
                        Matcher matcher = p.matcher(k);
                        while(matcher.find()) {
                            String analyzerName = matcher.group(1);
                            if(StringUtils.isNotBlank(analyzerName)){
                                analyzers.add(analyzerName.trim());
                            }
                        }
                    }
                    analyze.setAnalyzers(analyzers);
                    infos.add(analyze);
                }

            }
        }
        return infos;
    }

    public static List<IndexTypeEntity> indexTypeGet(TransportClient client) {

        ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = client.admin().indices().prepareGetMappings().get().getMappings();
        if(mappings.size() > 0){
            List<IndexTypeEntity> returnList = new ArrayList<>(mappings.size());
            ObjectLookupContainer<String> keys = mappings.keys();
            Object[] objects = keys.toArray();
            for(int i = 0 ; i < objects.length ; i++) {
                IndexTypeEntity entity = new IndexTypeEntity();
                String key = (String) objects[i];
                entity.setIndexName(key);
                ImmutableOpenMap<String, MappingMetaData> objectObjectCursors = mappings.get(key);
                ObjectLookupContainer<String> types = objectObjectCursors.keys();
                Object[] typeKey = types.toArray();
                List<String> typeKeys = new ArrayList<>();
                if(typeKey != null && typeKey.length > 0) {
                    for(int j = 0 ; j < typeKey.length ;j ++){
                        typeKeys.add((String) typeKey[j]);
                    }
                }
                entity.setTypeNames(typeKeys);
                returnList.add(entity);
            }
            return returnList;
        }
        return null;
    }
}
