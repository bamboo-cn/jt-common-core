package com.bamboo.common.autoconfigure.bamboo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: bamboo
 * Time: 2018/11/25/025
 * Describe: 自动配置类
 * 根据条件判断是否要自动配置，创建Bean
 */
@Configuration
@EnableConfigurationProperties(BambooServerProperties.class)
@ConditionalOnClass(BambooServer.class)//判断BambooServer这个类在类路径中是否存在,当存在时创建
@ConditionalOnProperty(prefix = "bamboo",value = "enabled",matchIfMissing = true)
public class BmbooServiceAutoConfiguration {

    @Autowired
    private BambooServerProperties mistraServiceProperties;

    @Bean(name = "bambooServer")
    @ConditionalOnMissingBean(BambooServer.class)//当容器中没有这个Bean实例时(BambooServer)就自动配置这个Bean，Bean的参数来自于BambooServerProperties
    public BambooServer mistraService(){
        BambooServer mistraService = new BambooServer();
        mistraService.setName(mistraServiceProperties.getName());
        return mistraService;
    }
}

