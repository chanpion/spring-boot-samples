package com.chenpp.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenpp.admin.domain.Entity;
import com.chenpp.admin.mapper.EntityMapper;
import com.chenpp.admin.service.EntityService;
import org.springframework.stereotype.Service;

/**
 * @author April.Chen
 * @date 2024/6/25 18:33
 */
@Service
public class EntityServiceImpl extends ServiceImpl<EntityMapper, Entity> implements EntityService {
}
