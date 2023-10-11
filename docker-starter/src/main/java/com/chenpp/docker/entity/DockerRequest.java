package com.chenpp.docker.entity;

import com.chenpp.docker.DockerProperties;
import com.chenpp.docker.DockerServiceProperties;
import lombok.Data;

/**
 * @author April.Chen
 * @date 2023/10/8 3:59 下午
 **/
@Data
public class DockerRequest {
    private DockerProperties dockerProperties;
    private DockerServiceProperties dockerServiceProperties;
}
