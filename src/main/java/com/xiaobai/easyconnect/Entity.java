package com.xiaobai.easyconnect;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段entity
 *
 * @author yin_zhj
 * @date 2020/5/28
 */
public class Entity {
    private static Map<String, String> map = new HashMap<>();

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }
}
