package com.chenpp.admin.search.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author April.Chen
 * @date 2024/6/26 17:21
 */
@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchCustomConversions customConversions(ObjectMapper objectMapper) {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new IgnoreClassWritingConverter(objectMapper));
        return new ElasticsearchCustomConversions(converters);
    }

    // 如果使用的是ElasticsearchRestClient，可能还需要配置RestClient

}