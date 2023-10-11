package com.chenpp.docker;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.Service;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author April.Chen
 * @date 2023/9/28 10:14 上午
 **/
public class DockerTest {

    private DockerProperties dockerProperties;

    private DockerClient dockerClient;

    private String imageName = "containous/whoami";
    private String serviceName = "service-whoami";

    @Before
    public void init() {
        dockerProperties = new DockerProperties();
//        dockerProperties.setHost("tcp://10.57.16.102:2375");
        dockerProperties.setHost("tcp://10.58.12.60:2375");
        dockerClient = DockerClientUtils.connectDocker(dockerProperties);
        Info info = dockerClient.infoCmd().exec();
        System.out.println("connect to docker, info: " + info);
    }

    @Test
    public void testCreateService() {
        DockerContainerProperties containerProperties = new DockerContainerProperties();
        containerProperties.setContainerName("service-demo");
        containerProperties.setCpuCount(1L);
        containerProperties.setMemory(128L);
        containerProperties.setImageName(imageName);
        containerProperties.setEnvList(new ArrayList<>());



        Map<Integer, Integer> portMap = new HashMap<>();
        portMap.put(18091, 80);
        containerProperties.setPortBindMap(portMap);
        DockerServiceProperties serviceProperties = new DockerServiceProperties();
        serviceProperties.setReplicas(1);
        serviceProperties.setContainerProperties(containerProperties);
        serviceProperties.setServiceName(serviceName);
        serviceProperties.setEndpointMode("vip");
        serviceProperties.setEndpointTargetPort(18091);
        String serviceId = DockerClientUtils.createService(dockerClient, serviceProperties);
        System.out.println(serviceId);
    }

    @Test
    public void testListService() {
        List<Service> serviceList = dockerClient.listServicesCmd().exec();
        serviceList.forEach(service -> System.out.println(JSONObject.toJSONString(service)));
    }

    @Test
    public void testRemoveService() {
        DockerClientUtils.removeService(dockerClient, serviceName);
    }

    @Test
    public void testInspectService() {
        Service service = DockerClientUtils.inspectService(dockerClient, serviceName);
        System.out.println(JSONObject.toJSONString(service));
    }

    @Test
    public void testCreteNetwork() {
        //设置network
        String networkId = dockerClient.createNetworkCmd().withName("networkName")
                .withDriver("overlay")
                .withIpam(new Network.Ipam()
                        .withDriver("default"))
                .exec().getId();
        System.out.println(networkId);
    }

    @Test
    public void testSize() {
        System.out.println(StandardCharsets.UTF_8.name());
        long size = 1 << 20;
        System.out.println(size);
    }


    @Test
    public void testListImages() {
        List<Image> images = DockerClientUtils.imageList(dockerClient);
        images.forEach(System.out::println);
    }

    @Test
    public void testService() {
        testListService();
        testInspectService();
        testRemoveService();
        testCreateService();
        testInspectService();
    }

    @Test
    public void testGetServiceLogs(){
        DockerClientUtils.getServiceLogs(dockerClient,"service-nginx-v3");
    }

    @Test
    public void testStats(){

        dockerClient.statsCmd("");
    }
}
