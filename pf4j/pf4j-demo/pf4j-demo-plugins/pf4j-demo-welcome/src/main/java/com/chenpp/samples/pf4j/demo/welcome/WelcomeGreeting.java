package com.chenpp.samples.pf4j.demo.welcome;

import com.chenpp.samples.pf4j.demo.api.Greeting;
import org.pf4j.Extension;

/**
 * @author April.Chen
 * @date 2024/4/12 11:31
 */
@Extension
public class WelcomeGreeting implements Greeting {

    @Override
    public String getGreeting() {
        return "Welcome";
    }

}
