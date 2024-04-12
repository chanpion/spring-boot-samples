package com.chenpp.samples;

import com.chenpp.samples.pf4j.demo.api.Greeting;
import org.pf4j.Extension;

@Extension
public class WhazzupGreeting implements Greeting {

    @Override
    public String getGreeting() {
        return "Whazzup";
    }

}
