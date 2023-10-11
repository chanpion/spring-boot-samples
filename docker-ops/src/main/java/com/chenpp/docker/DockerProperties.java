package com.chenpp.docker;

import lombok.Data;

/**
 * DOCKER_HOST Docker的地址，比如： tcp://localhost:2376 或者unix:///var/run/docker.sock
 * DOCKER_TLS_VERIFY 是否开启 TLS 验证 (http 和 https 之间切换)
 * DOCKER_CERT_PATH TLS 验证的证书路径
 * DOCKER_CONFIG 其他docker配置文件的路径 (比如 .dockercfg)
 * api.version API version版本
 * registry.url 下载源地址（docker镜像存放的地址）
 * registry.username 登陆用户名 （推送镜像到docker云仓库时需要）
 * registry.password 登陆用户密码（推送镜像到docker云仓库时需要）
 * registry.email 登陆账户的邮箱（推送镜像到docker云仓库时需要）
 *
 * @author April.Chen
 * @date 2023/9/27 2:39 下午
 **/
@Data
public class DockerProperties {

    private String host;
    private String apiVersion;
    private Boolean dockerTlsVerify;

    private String imageName;
    private String imageTag;
    private String dockerfilePath;
}
