package com.chenpp.samples.elasticsearch.controller;

import com.chenpp.samples.elasticsearch.entity.FileInfo;
import com.chenpp.samples.elasticsearch.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author April.Chen
 * @date 2024/1/22 11:23
 */
@Slf4j
@RestController
public class FileInfoController {

    @Resource
    private FileInfoService fileInfoService;

    @GetMapping("fileInfo/all")
    public Page<FileInfo> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return fileInfoService.findAll(pageRequest);
    }

    @GetMapping("fileInfo/search")
    public Page<FileInfo> search(FileInfo fileInfo, String[] fields, int page, int size) {
        return fileInfoService.search(fileInfo, fields, PageRequest.of(page, size));
    }

    @GetMapping("fileInfo/searchFile")
    public Object search(String filename, String[] keywords, String[] fields) {
        return fileInfoService.search(filename, keywords, fields);
    }

}
