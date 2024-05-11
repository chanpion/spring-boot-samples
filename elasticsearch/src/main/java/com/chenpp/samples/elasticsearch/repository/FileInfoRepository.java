package com.chenpp.samples.elasticsearch.repository;

import com.chenpp.samples.elasticsearch.entity.FileInfo;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author April.Chen
 * @date 2024/1/22 11:10
 */
public interface FileInfoRepository extends ElasticsearchRepository<FileInfo, String> {

    @Highlight(fields = {
            @HighlightField(name = "title"),
            @HighlightField(name = "author")
    })
    @Query("{\"match\":{\"title\":\"?0\"}}")
    SearchHits<FileInfo> find(String keyword);

}
