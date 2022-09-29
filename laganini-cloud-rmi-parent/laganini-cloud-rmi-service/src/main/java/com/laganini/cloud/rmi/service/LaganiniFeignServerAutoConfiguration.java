package com.laganini.cloud.rmi.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LaganiniFeignServerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ServerControllerAdvice serverControllerAdvice() {
        return new ServerControllerAdvice();
    }

}
