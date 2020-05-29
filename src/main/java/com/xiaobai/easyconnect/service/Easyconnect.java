package com.xiaobai.easyconnect.service;

import com.xiaobai.easyconnect.common.Entity;

/**
 * easyconnect接口
 *
 * @author yin_zhj
 * @date 2020/5/28
 */
public interface Easyconnect {
    Entity postJson(String code, Entity message);
    Entity postXml(String code, Entity message);
}
