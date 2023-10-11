package com.chenpp.docker.web;

import com.chenpp.docker.DockerClientUtils;
import com.chenpp.docker.entity.DockerRequest;
import com.github.dockerjava.api.DockerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author April.Chen
 * @date 2023/10/8 1:36 下午
 **/
@Slf4j
@RestController
@RequestMapping("/docker")
public class DockerController {


    @PostMapping("/service/create")
    public Object createService(@RequestBody DockerRequest dockerRequest) {
        DockerClient dockerClient = DockerClientUtils.connectDocker(dockerRequest.getDockerProperties());
        String result = DockerClientUtils.createService(dockerClient, dockerRequest.getDockerServiceProperties());
        log.info("create service: {}", result);
        return result;
    }
}
