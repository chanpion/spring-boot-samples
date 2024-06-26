package com.chenpp.admin.search.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/6/26 17:19
 */
@WritingConverter
public class IgnoreClassWritingConverter implements Converter<Object, Map<String, Object>> {
    private final ObjectMapper objectMapper;

    public IgnoreClassWritingConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> convert(Object source) {
        // 使用Jackson或其他方式转换对象到Map，并移除_class字段
        Map<String, Object> map = objectMapper.convertValue(source, Map.class);
        map.remove("_class");
        return map;
    }
}