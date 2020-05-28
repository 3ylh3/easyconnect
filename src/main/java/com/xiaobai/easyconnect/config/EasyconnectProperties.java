package com.xiaobai.easyconnect.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *配置信息类
 *
 * @author yin_zhj
 * @date 2020/5/28
 */
@Data
@ConfigurationProperties("easyconnect")
public class EasyconnectProperties {
    /**
     * 请求url
     */
    private String url;
    /**
     * 编码格式
     */
    private String encoding;
}
