package com.chenpp.samples.pf4j.demo.api;

import org.pf4j.ExtensionPoint;

/**
 * 定义接口
 *
 * @author April.Chen
 * @date 2024/4/12 11:16
 */
public interface Greeting extends ExtensionPoint {

    /**
     * 获取欢迎语
     *
     * @return 欢迎语
     */
    String getGreeting();

}