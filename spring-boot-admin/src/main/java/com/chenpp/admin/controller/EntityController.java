package com.chenpp.admin.controller;

import com.chenpp.admin.domain.AppResponse;
import com.chenpp.admin.domain.Entity;
import com.chenpp.admin.service.EntityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author April.Chen
 * @date 2024/6/17 15:48
 */
@RestController
@RequestMapping("/entity")
public class EntityController {

    @Resource
    private EntityService entityService;


    @GetMapping("/get/uid")
    private AppResponse<Entity> get(String uid) {
        Entity entity = entityService.getById(uid);
        return entity == null ? AppResponse.fail("entity not found") : AppResponse.success(entity);
    }

    @GetMapping("/queryAll")
    private AppResponse<List<Entity>> queryAll() {
        List<Entity> list = entityService.list();
        return list.isEmpty() ? AppResponse.fail("entity not found") : AppResponse.success(list);
    }
}
