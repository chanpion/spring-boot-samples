package com.chenpp.samples.elasticsearch.service;

import com.chenpp.samples.elasticsearch.entity.FileInfo;
import com.chenpp.samples.elasticsearch.repository.FileInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2024/1/22 11:10
 */
@Slf4j
@Service
public class FileInfoService {

    @Resource
    private FileInfoRepository fileInfoRepository;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Page<FileInfo> findAll(Pageable pageable) {
        Page<FileInfo> page = fileInfoRepository.findAll(pageable);
        log.info("page: {}", page);
        return page;
    }


    public Page<FileInfo> search(FileInfo fileInfo, String[] fields, Pageable pageable) {
        Page<FileInfo> page = fileInfoRepository.searchSimilar(fileInfo, fields, pageable);
        log.info("page: {}", page);
        return page;
    }


    public void add(FileInfo fileInfo) {
        elasticsearchRestTemplate.save(fileInfo);
    }

    public void update(String indexName, FileInfo fileInfo) {
    }

    public SearchHits<FileInfo> search(String filename, String[] keywords, String[] fields) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MoreLikeThisQueryBuilder moreLikeThisQueryBuilder = QueryBuilders.moreLikeThisQuery(keywords);
        boolQueryBuilder.must(QueryBuilders.wildcardQuery("file.filename", "*" + filename + "*"));
        boolQueryBuilder.must(moreLikeThisQueryBuilder);
        queryBuilder.withQuery(boolQueryBuilder);
        PageRequest page = PageRequest.of(0, 15);
        queryBuilder.withPageable(page);
        SearchHits<FileInfo> searchHits = elasticsearchRestTemplate.search(queryBuilder.build(), FileInfo.class);
        log.info("hists: {}", searchHits);
        return searchHits;
    }

}
