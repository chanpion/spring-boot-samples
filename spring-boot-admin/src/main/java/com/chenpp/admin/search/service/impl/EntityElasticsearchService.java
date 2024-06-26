package com.chenpp.admin.search.service.impl;

import com.chenpp.admin.domain.Entity;
import com.chenpp.admin.search.domain.EntityDoc;
import com.chenpp.admin.search.repository.EntityRepository;
import com.chenpp.admin.search.service.ElasticsearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2024/6/26 16:28
 */
@Service
public class EntityElasticsearchService implements ElasticsearchService<Entity> {

    @Resource
    private EntityRepository entityRepository;

    @Override
    public Entity save(Entity entity) {
        EntityDoc entityDoc = new EntityDoc();
        BeanUtils.copyProperties(entity, entityDoc);
        entityDoc.setId(entity.getId().toString());
        EntityDoc saved = entityRepository.save(entityDoc);
        entity.setId(saved.getId());
        return entity;
    }
}
