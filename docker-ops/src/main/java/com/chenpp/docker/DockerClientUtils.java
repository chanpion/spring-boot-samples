package com.chenpp.docker;

import com.alibaba.fastjson.JSONObject;
import com.chenpp.docker.output.FrameConsumerResultCallback;
import com.chenpp.docker.output.LogContainerTestCallback;
import com.chenpp.docker.output.OutputFrame;
import com.chenpp.docker.output.Slf4jLogConsumer;
import com.chenpp.docker.output.ToStringConsumer;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.command.CreateServiceResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerSpec;
import com.github.dockerjava.api.model.EndpointResolutionMode;
import com.github.dockerjava.api.model.EndpointSpec;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HealthCheck;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.PortConfig;
import com.github.dockerjava.api.model.PortConfigProtocol;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Service;
import com.github.dockerjava.api.model.ServiceModeConfig;
import com.github.dockerjava.api.model.ServiceReplicatedModeOptions;
import com.github.dockerjava.api.model.ServiceSpec;
import com.github.dockerjava.api.model.TaskSpec;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.github.dockerjava.core.DefaultDockerClientConfig.REGISTRY_URL;

/**
 * @author April.Chen
 * @date 2023/9/27 7:18 下午
 **/
@Slf4j
public class DockerClientUtils {

    /**
     * 连接Docker服务器
     */
    public static DockerClient connectDocker(DockerProperties dockerProperties) {
        // 配置docker CLI的一些选项
        DefaultDockerClientConfig dockerClientConfig = DefaultDockerClientConfig
                .createDefaultConfigBuilder()
                .withDockerTlsVerify(dockerProperties.getDockerTlsVerify())
                .withDockerHost(dockerProperties.getHost())
                // 与docker版本对应，参考https://docs.docker.com/engine/api/#api-version-matrix
                // 或者通过docker version指令查看api version
                .withApiVersion(dockerProperties.getApiVersion())
                //下载源地址（docker镜像存放的地址）
                .withRegistryUrl(REGISTRY_URL)
                .build();

        // 创建DockerHttpClient
        DockerHttpClient httpClient = new ApacheDockerHttpClient
                .Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(dockerClientConfig).withDockerHttpClient(httpClient).build();
        Info info = dockerClient.infoCmd().exec();
        log.info("connect to docker, info: {}", info);
        return dockerClient;
    }

    /**
     * 创建网络
     *
     * @param client      DockerClient
     * @param networkName 网络名称
     * @param driver      driver
     */
    public static void createNetwork(DockerClient client, String networkName, String driver) {
        CreateNetworkResponse networkResponse = client.createNetworkCmd()
                .withName(networkName)
                .withDriver(driver)
                .exec();
        log.info("create network response: {}", networkResponse);
    }

    /**
     * 创建容器
     *
     * @param client DockerClient
     * @return 容器id
     */
    public static String createContainer(DockerClient client, DockerContainerProperties containerProperties) {
        //映射端口8088—>80
        ExposedPort tcp80 = ExposedPort.tcp(80);
        Ports portBindings = new Ports();
        portBindings.bind(tcp80, Ports.Binding.bindPort(8088));

        // 端口绑定
        Map<Integer, Integer> portMap = Optional.ofNullable(containerProperties.getPortBindMap()).orElse(new HashMap<>());
        Iterator<Map.Entry<Integer, Integer>> iterator = portMap.entrySet().iterator();
        List<PortBinding> portBindingList = new ArrayList<>();
        List<ExposedPort> exposedPortList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            ExposedPort tcp = ExposedPort.tcp(entry.getKey());
            Ports.Binding binding = Ports.Binding.bindPort(entry.getValue());
            PortBinding ports = new PortBinding(binding, tcp);
            portBindingList.add(ports);
            exposedPortList.add(tcp);
        }

        List<Bind> binds = new ArrayList<>();
        containerProperties.getPathMountMap().forEach((k, v) -> {
            Bind bind = new Bind(k, new Volume(v));
            binds.add(bind);
        });

        HostConfig hostConfig = HostConfig.newHostConfig()
                .withBinds(binds)
                .withPortBindings(portBindingList)
                .withMemory(containerProperties.getMemoryOfBytes())
                .withCpuCount(containerProperties.getCpuCount());

        CreateContainerResponse container = client.createContainerCmd(containerProperties.getImageName())
                .withName(containerProperties.getContainerName())
                //端口映射
                .withHostConfig(hostConfig)
                //对外暴露端口
                .withExposedPorts(exposedPortList)
                // 执行命令，注意命令和参数不能进行组合，必须都用逗号隔开,也就是空格全部换成这里的,分割
                // .withCmd("python", "/root/scripts/test.py", "-t", "999")
                .exec();
        log.info("create container response: {}", container);
        return container.getId();
    }

    /**
     * 启动容器
     *
     * @param client      DockerClient
     * @param containerId 容器id
     */
    public static void startContainer(DockerClient client, String containerId) {
        client.startContainerCmd(containerId).exec();
    }

    /**
     * 停止容器
     *
     * @param client      DockerClient
     * @param containerId 容器id
     */
    public static void stopContainer(DockerClient client, String containerId) {
        client.stopContainerCmd(containerId).exec();
    }

    /**
     * 停止容器
     *
     * @param client      DockerClient
     * @param containerId 容器id
     */
    public static void restartContainer(DockerClient client, String containerId) {
        client.restartContainerCmd(containerId).exec();
    }

    /**
     * 删除容器
     *
     * @param client      DockerClient
     * @param containerId 容器id
     */
    public static void removeContainer(DockerClient client, String containerId) {
        client.removeContainerCmd(containerId).exec();
    }

    public static String inspectContainer(DockerClient client, String containerId) {
        InspectContainerResponse response = client.inspectContainerCmd(containerId).exec();
        log.info("container: {}", response);
        return JSONObject.toJSONString(response);
    }

    public static void listContainers(DockerClient client, List<String> statusList) {
        //获取所有运行的容器
        List<Container> containers = client.listContainersCmd().withStatusFilter(statusList).exec();
        for (Container container : containers) {
            log.info(container.getId() + ": " + container.getNames()[0]);
        }
    }

    /**
     * 删除镜像
     *
     * @param client  DockerClient
     * @param imageId 镜像id
     */
    public static void removeImage(DockerClient client, String imageId) {
        client.removeImageCmd(imageId).exec();
    }

    /**
     * 查看镜像详细信息
     *
     * @param client  DockerClient
     * @param imageId 镜像id
     * @return 镜像详细信息
     */
    public static String inspectImage(DockerClient client, String imageId) {
        InspectImageResponse response = client.inspectImageCmd(imageId).exec();
        return JSONObject.toJSONString(response);
    }

    /**
     * 从文件加载镜像
     *
     * @param client   DockerClient
     * @param filePath 文件路径
     */
    public static void loadImage(DockerClient client, String filePath) {
        try {
            client.loadImageCmd(new FileInputStream(filePath)).exec();
        } catch (FileNotFoundException e) {
            log.info("load image error", e);
        }
    }

    /**
     * repository 镜像名称:tag名称
     **/
    public static boolean pullImage(DockerClient client, String repository) {
        PullImageCmd pullImageCmd = client.pullImageCmd(repository);
        boolean isSuccess;
        try {
            isSuccess = client.pullImageCmd(repository)
                    .start()
                    .awaitCompletion(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return isSuccess;
    }

    /**
     * 构建镜像
     *
     * @param client          DockerClient
     * @param imageProperties 镜像配置
     * @return 镜像id
     */
    public static String buildImage(DockerClient client, DockerImageProperties imageProperties) {
        ImmutableSet<String> tag = ImmutableSet.of(imageProperties.getImageName() + ":" + imageProperties.getImageTag());
        String imageId = client.buildImageCmd(new File(imageProperties.getDockerfilePath()))
                .withTags(tag)
                .start()
                .awaitImageId();
        log.info("build image id: {}", imageId);
        return imageId;
    }

    /**
     * 获取镜像列表
     *
     * @param client DockerClient
     * @return 镜像列表
     */
    public static List<Image> imageList(DockerClient client) {
        List<Image> imageList = client.listImagesCmd().withShowAll(true).exec();
        log.info("image list: {}", imageList);
        return imageList;
    }

    /**
     * 创建swarm服务
     *
     * @param dockerClient      DockerClient
     * @param serviceProperties 服务配置
     * @return 服务id
     */
    public static String createService(DockerClient dockerClient, DockerServiceProperties serviceProperties) {
        HealthCheck healthCheck = new HealthCheck()
//                .withInterval(10000L)
//                .withStartPeriod(30000L)
//                .withRetries(3)
//                .withTimeout(30000L)
                .withTest(Arrays.asList("curl", "-f", "http://localhost:80"));

        //创建服务
        ServiceSpec spec = new ServiceSpec()
                //服务名称（serviceName）
                .withName(serviceProperties.getServiceName())
                //环境变量(env）
                .withTaskTemplate(new TaskSpec()
                        .withForceUpdate(0)
                        .withContainerSpec(new ContainerSpec()
                                .withEnv(serviceProperties.getContainerProperties().getEnvList())
                                .withInit(false)
//                                .withHealthCheck(healthCheck)
                                //镜像名称
                                .withImage(serviceProperties.getContainerProperties().getImageName()))
                )
                //网络（network）
//                .withNetworks(Lists.newArrayList(
//                        new NetworkAttachmentConfig().withTarget(serviceProperties.getNetworkId())
////                .withAliases(Lists.<String>newArrayList("alias1", "alias2"))
//                ))
                //标签
//                .withLabels(ImmutableMap.of(worknumber, worknumber))
                //启动副本数量
                .withMode(new ServiceModeConfig().withReplicated(
                        new ServiceReplicatedModeOptions()
                                .withReplicas(serviceProperties.getReplicas())
                ))
                .withEndpointSpec(new EndpointSpec()
                        .withMode(EndpointResolutionMode.valueOf(serviceProperties.getEndpointMode().toUpperCase()))
                        .withPorts(serviceProperties.getContainerProperties().getPortBindMap().keySet().stream().map(k -> {
                            Integer targetPort = serviceProperties.getContainerProperties().getPortBindMap().get(k);
                            return new PortConfig()
                                    //设置主机模式（mode=host）
                                    .withPublishMode(PortConfig.PublishMode.ingress)
                                    //设置目标端口号（target port=7000）
                                    .withTargetPort(targetPort)
                                    .withPublishedPort(k)
                                    .withProtocol(PortConfigProtocol.TCP);
                        }).collect(Collectors.toList()))
                );
        //执行服务创建指令

        CreateServiceResponse response = dockerClient.createServiceCmd(spec).exec();
        log.info("create service response: {}", response);
        return response.getId();
    }

    /**
     * 查看服务信息
     *
     * @param dockerClient DockerClient
     * @param serviceId    服务id
     * @return 服务信息
     */
    public static Service inspectService(DockerClient dockerClient, String serviceId) {
        Service service = dockerClient.inspectServiceCmd(serviceId).exec();
        log.info("service {} inspect: {}", serviceId, service);
        return service;
    }

    /**
     * 删除服务
     *
     * @param dockerClient DockerClient
     * @param serviceId    服务id
     * @return 删除结果
     */
    public static boolean removeService(DockerClient dockerClient, String serviceId) {
        try {
            dockerClient.removeServiceCmd(serviceId).exec();
            Thread.sleep(5000);
            log.info("remove service: {}", serviceId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 更新服务
     *
     * @param dockerClient DockerClient
     * @param serviceId    服务id
     * @return 删除结果
     */
    public static boolean updateService(DockerClient dockerClient, String serviceId, DockerServiceProperties serviceProperties) {
        HealthCheck healthCheck = new HealthCheck()
                .withInterval(10L)
                .withStartPeriod(30L)
                .withRetries(3)
                .withTimeout(30L)
                .withTest(Arrays.asList("curl -f http://localhost:80"));
        try {
            ServiceSpec spec = new ServiceSpec()
                    .withTaskTemplate(new TaskSpec()
                            .withForceUpdate(0)
                            .withContainerSpec(new ContainerSpec()
                                    .withEnv(serviceProperties.getContainerProperties().getEnvList())
                                    .withInit(false)
                                    .withHealthCheck(healthCheck)
                                    //镜像名称
                                    .withImage(serviceProperties.getContainerProperties().getImageName()))
                    );
            dockerClient.updateServiceCmd(serviceId, spec).exec();
            log.info("update service: {}", serviceId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void getServiceLogs(DockerClient dockerClient, String serviceId) {
        try {
            final FrameConsumerResultCallback callback = new FrameConsumerResultCallback();
            callback.addConsumer(OutputFrame.OutputType.STDOUT, new Slf4jLogConsumer(log));
            callback.addConsumer(OutputFrame.OutputType.STDERR, new Slf4jLogConsumer(log));

            LogContainerTestCallback loggingCallback = dockerClient.logServiceCmd(serviceId)
                    .withStderr(true).withStdout(true)
                    .exec(new LogContainerTestCallback(true));

            boolean result = loggingCallback.awaitCompletion(60, TimeUnit.SECONDS);
            System.out.println(result);
            String logging = loggingCallback.toString();
            System.out.println(logging);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
