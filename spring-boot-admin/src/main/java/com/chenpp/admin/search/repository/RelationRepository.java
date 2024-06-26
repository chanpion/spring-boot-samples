package com.chenpp.admin.search.repository;

import com.chenpp.admin.search.domain.RelationDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author April.Chen
 * @date 2024/6/18 19:18
 */
public interface RelationRepository extends ElasticsearchRepository<RelationDoc, String> {
}
