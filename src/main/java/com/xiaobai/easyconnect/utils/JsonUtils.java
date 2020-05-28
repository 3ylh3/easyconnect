package com.xiaobai.easyconnect.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaobai.easyconnect.Entity;

import java.util.Map;

/**
 * json解析工具类
 *
 * @author yin_zhj
 * @date 2020/5/28
 */
public class JsonUtils {
    private static final String START = "$";

    public static String parseReqJson(JSONObject jsonObject, Entity map) {
        JSONObject result = new JSONObject();
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            if(entry.getValue() instanceof JSONObject) {
                String value = parseReqJson((JSONObject) entry.getValue(), map);
                if(null == value) {
                    value = "";
                }
                result.put(key, JSONObject.parseObject(value));
            } else {
                String value = (String)entry.getValue();
                if(value.startsWith(START)) {
                    String val = map.get(value.substring(1));
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
            } else {
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
