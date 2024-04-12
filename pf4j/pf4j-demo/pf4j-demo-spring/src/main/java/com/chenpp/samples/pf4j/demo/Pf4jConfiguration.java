package com.chenpp.samples.pf4j.demo;

import org.pf4j.spring.SpringPluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author April.Chen
 * @date 2024/4/12 13:52
 */
@Configuration
public class Pf4jConfiguration {

    @Bean
    public SpringPluginManager pluginManager() {
        return new SpringPluginManager();
    }

    @Bean
    @DependsOn("pluginManager")
    public Greetings greetings() {
        return new Greetings();
    }

}
