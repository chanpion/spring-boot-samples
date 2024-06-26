package com.chenpp.admin.controller;

import com.chenpp.admin.domain.AppResponse;
import com.chenpp.admin.domain.Entity;
import com.chenpp.admin.search.domain.EntityDoc;
import com.chenpp.admin.search.service.ElasticsearchService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author April.Chen
 * @date 2024/6/19 10:05
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Resource
    private ElasticsearchService<Entity> entityElasticsearchServiceImpl;

    @GetMapping("/query")
    public AppResponse<?> search(String keyword, @PageableDefault(sort = "uid", direction = Sort.Direction.DESC) Pageable pageable) {
        QueryBuilder query = QueryBuilders.queryStringQuery(keyword);
        //使用queryStringQuery完成单字符串查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).withPageable(pageable).build();
        SearchHits<EntityDoc> searchHits = elasticsearchTemplate.search(searchQuery, EntityDoc.class);

        return AppResponse.success(searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public AppResponse<?> addDoc(@RequestBody Entity entity) {
        Entity e = entityElasticsearchServiceImpl.save(entity);
        return AppResponse.success(e);
    }
}
