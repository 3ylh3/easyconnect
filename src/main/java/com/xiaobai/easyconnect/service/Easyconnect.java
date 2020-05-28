package com.xiaobai.easyconnect.service;

import com.xiaobai.easyconnect.Entity;

/**
 * easyconnect接口
 *
 * @author yin_zhj
 * @date 2020/5/28
 */
public interface Easyconnect {
    public Entity postJson(String code, Entity message);
}
