package com.chenpp.samples.pf4j.demo;

import com.chenpp.samples.pf4j.demo.api.Greeting;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author April.Chen
 * @date 2024/4/12 13:51
 */
public class Greetings {
    @Autowired
    private List<Greeting> greetings;

    public void printGreetings() {
        System.out.println(String.format("Found %d extensions for extension point '%s'", greetings.size(), Greeting.class.getName()));
        for (Greeting greeting : greetings) {
            System.out.println(">>> " + greeting.getGreeting());
        }
    }
}
