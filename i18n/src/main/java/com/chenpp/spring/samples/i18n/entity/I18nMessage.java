package com.chenpp.spring.samples.i18n.entity;

import lombok.Data;

/**
 * @author April.Chen
 * @date 2023/9/12 7:11 下午
 **/
@Data
public class I18nMessage {

    private String language;
    private String model;
    private String modelId;
    private String text;
    private String code;
    private String item;
    private String locale;
}
