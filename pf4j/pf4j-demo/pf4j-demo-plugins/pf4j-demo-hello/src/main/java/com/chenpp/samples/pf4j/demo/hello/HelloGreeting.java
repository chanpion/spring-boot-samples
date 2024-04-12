package com.chenpp.samples.pf4j.demo.hello;

import com.chenpp.samples.pf4j.demo.api.Greeting;
import org.pf4j.Extension;

/**
 * @author April.Chen
 * @date 2024/4/12 11:27
 */
@Extension(ordinal = 1)
public class HelloGreeting implements Greeting {

    @Override
    public String getGreeting() {
        return "Hello";
    }

}
