package com.chenpp.admin.search.service;

/**
 * @author April.Chen
 * @date 2024/6/26 16:18
 */
public interface ElasticsearchService<T> {

    /**
     * 保存文档
     *
     * @param doc 文档
     * @return 保存的文档
     */
    <S extends T> S save(T doc);
}
