package com.chenpp.samples.pf4j.demo.hello;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

/**
 * A very simple plugin.
 */
public class HelloPlugin extends Plugin {

    public HelloPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("HelloPlugin.start()");
    }

    @Override
    public void stop() {
        System.out.println("HelloPlugin.stop()");
    }

}
