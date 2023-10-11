package com.chenpp.docker;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author April.Chen
 * @date 2023/9/27 7:45 下午
 **/
@Data
public class DockerContainerProperties {

    private String containerName;
    private String imageName;
    /**
     * 端口映射，key：主机外网端口，value：容器内网端口
     */
    private Map<Integer, Integer> portBindMap;
    /**
     * key：服务器路径，value：容器路径
     */
    private Map<String, String> pathMountMap;
    /**
     * 环境变量，格式: a=b ，例如：DOCKER_USER_NAME = "DOCKER_USER_NAME="
     */
    private List<String> envList;

    /**
     * 容器内存大小，单位：mb
     */
    private Long memory;
    /**
     * CPU数
     */
    private Long cpuCount;

    public Long getMemoryOfBytes() {
        if (memory != null) {
            return memory << 20;
        }
        return null;
    }
}
