package com.chenpp.samples.pf4j.demo.welcome;

import org.apache.commons.lang3.StringUtils;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.pf4j.RuntimeMode;

public class WelcomePlugin extends Plugin {

    public WelcomePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("WelcomePlugin.start()");
        // for testing the development mode
        if (RuntimeMode.DEVELOPMENT.equals(wrapper.getRuntimeMode())) {
            System.out.println(StringUtils.upperCase("WelcomePlugin"));
        }
    }

    @Override
    public void stop() {
        System.out.println("WelcomePlugin.stop()");
    }

}
