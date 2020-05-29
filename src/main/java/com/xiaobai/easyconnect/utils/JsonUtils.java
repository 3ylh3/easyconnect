package com.xiaobai.easyconnect.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaobai.easyconnect.common.Entity;

import java.util.Map;

/**
 * json解析工具类
 *
 * @author yin_zhj
 * @date 2020/5/28
 */
public class JsonUtils {
    private static final String START = "$";

    public static String parseReqJson(JSONObject jsonObject, Entity entity) {
        JSONObject result = new JSONObject();
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            if(entry.getValue() instanceof JSONObject) {
                String value = parseReqJson((JSONObject) entry.getValue(), entity);
                if(null == value) {
                    value = "";
                }
                result.put(key, JSONObject.parseObject(value));
            } else if(entry.getValue() instanceof JSONArray) {
                JSONArray array = (JSONArray) entry.getValue();
                JSONArray tmp = new JSONArray();
                for(int i = 0;i < array.size();i++) {
                    String value = parseReqJson((JSONObject) array.get(i), entity);
                    tmp.add(JSONObject.parseObject(value));
                }
                result.put(key, tmp);
            }
            else {
                String value = (String)entry.getValue();
                if(value.startsWith(START)) {
                    String val = entity.get(value.substring(1));
                    if(null == val) {
                        val = "";
                    }
                    result.put(key, val);
                } else {
                    result.put(key, value);
                }
            }
        }
        return JSON.toJSONString(result);
    }

    public static void parseRspStr(JSONObject json, String str, Entity entity) {
        JSONObject strJson = JSONObject.parseObject(str);
        for(Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();
            if(entry.getValue() instanceof JSONObject) {
                parseRspStr((JSONObject) entry.getValue(), strJson.getString(key), entity);
                String val = strJson.getString(key);
                if(null == val) {
                    val = "";
                }
                entity.put(key, val);
            } else if(entry.getValue() instanceof JSONArray) {
                JSONArray array = (JSONArray) entry.getValue();
                JSONArray tmp = strJson.getJSONArray(key);
                for(int i = 0;i < array.size();i++) {
                    entity.put(key + "_" + i, JSON.toJSONString(tmp.get(i)));
                }
                entity.put(key + "_size", String.valueOf(array.size()));
            }
            else {
                String value = (String)entry.getValue();
                if(value.startsWith(START)) {
                    String val = strJson.getString(value.substring(1));
                    if(null == val) {
                        val = "";
                    }
                    entity.put(key, val);
                } else {
                    entity.put(key, value);
                }
            }
        }
    }
}
