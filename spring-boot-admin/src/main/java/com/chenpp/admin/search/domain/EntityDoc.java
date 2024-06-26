package com.chenpp.admin.search.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/6/17 19:03
 */
@Data
@Document(indexName = "common_entity")
@JsonIgnoreProperties(value = "_class")
public class EntityDoc {

    @Id
    @Field
    private String id;

    @Field(store = true, type = FieldType.Keyword)
    private String uid;

    @Field(type = FieldType.Keyword)
    private String label;

    @Field(analyzer = "ik_smart", store = true, searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String name;

    @Field(store = true, type = FieldType.Object)
    private Map<String, Object> properties;
}
