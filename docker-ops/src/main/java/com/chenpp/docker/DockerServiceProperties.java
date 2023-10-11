package com.chenpp.docker;

import lombok.Data;

/**
 * @author April.Chen
 * @date 2023/9/28 10:32 上午
 **/
@Data
public class DockerServiceProperties {

    private String serviceName;
    /**
     * service副本数，对应容器启动个数
     */
    private int replicas;
    private String endpointMode;
    private int endpointTargetPort;
    private String networkId;
    private DockerContainerProperties containerProperties;
}
