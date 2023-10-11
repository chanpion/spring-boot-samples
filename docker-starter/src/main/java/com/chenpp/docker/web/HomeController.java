package com.chenpp.docker.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author April.Chen
 * @date 2023/9/27 5:29 下午
 **/
@RestController
public class HomeController {

    @GetMapping("/host")
    public String getHost() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        System.out.println(hostName);
        return hostName;
    }

    @GetMapping("/ip")
    public String getIp() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        System.out.println(ip);
        return ip;
    }
}
