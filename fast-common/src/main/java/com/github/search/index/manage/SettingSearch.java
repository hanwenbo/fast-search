package com.github.search.index.manage;

import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月21日
 * @modifytime:
 */
public class SettingSearch {


    /**
     *
     * 只创建索引 ,使用的默认设置 ,并没有映射
     *
     * @param client
     * @param index
     * @return
     */
    public static Map<String, Object> indexSetting(TransportClient client , String index) {
        GetSettingsResponse response = client.admin().indices().prepareGetSettings(index).get();
        ImmutableOpenMap<String, Settings> settings = response.getIndexToSettings();
        Settings setting = settings.get(index);
        if (setting != null && !setting.isEmpty()) {
            Map<String, Object> asStructuredMap = setting.getAsStructuredMap();
            return asStructuredMap;
        }
        return null;
    }


}
