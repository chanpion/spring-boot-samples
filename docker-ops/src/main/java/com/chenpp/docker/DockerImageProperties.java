package com.chenpp.docker;

import lombok.Data;

/**
 * @author April.Chen
 * @date 2023/9/27 7:39 下午
 **/
@Data
public class DockerImageProperties {
    private String imageName;
    private String imageTag;
    private String dockerfilePath;
}
