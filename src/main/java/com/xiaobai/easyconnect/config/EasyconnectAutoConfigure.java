package com.xiaobai.easyconnect.config;

import com.xiaobai.easyconnect.service.Easyconnect;
import com.xiaobai.easyconnect.service.impl.EasyconnectImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *自动装配类
 *
 * @author yin_zhj
 * @date 2020/5/28
 */
@Configuration
@EnableConfigurationProperties(EasyconnectProperties.class)
public class EasyconnectAutoConfigure {
    @Autowired
    private EasyconnectProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public Easyconnect initJsonEasyconnect() {
        return new EasyconnectImpl(properties.getUrl(), properties.getEncoding());

    }
}
