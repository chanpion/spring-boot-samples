package com.chenpp.samples.pf4j.demo;

import com.chenpp.samples.pf4j.demo.api.Greeting;
import org.apache.commons.lang.StringUtils;
import org.pf4j.PluginManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author April.Chen
 * @date 2024/4/12 13:52
 */
@SpringBootApplication
public class SpringBoot implements CommandLineRunner {
    @Resource
    private Greetings greetings;

    @Resource
    private PluginManager pluginManager;

    public static void main(String[] args) {
        // print logo
        printLogo();
        SpringApplication.run(SpringBoot.class, args);
    }

    private static void printLogo() {
        System.out.println(StringUtils.repeat("#", 40));
        System.out.println(StringUtils.center("PF4J-SPRING", 40));
        System.out.println(StringUtils.repeat("#", 40));
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("greeting: ");
        greetings.printGreetings();
        List<Greeting> greetingList = pluginManager.getExtensions(Greeting.class);
        System.out.println("greetings.size() = " + greetingList.size());
        greetingList.forEach(Greeting::getGreeting);
    }
}
