package com.chenpp.samples.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/1/22 10:57
 */
@Data
@Setting(shards = 1, replicas = 0, refreshInterval = "5s")
@Document(indexName = "elastic", createIndex = false)
public class FileInfo {
    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String content;
    @JsonProperty("meta")
    private Map<String, String> meta;
    @JsonProperty("file")
    private Map<String, String> file;
    @JsonProperty("path")
    private Map<String, String> path;
}
