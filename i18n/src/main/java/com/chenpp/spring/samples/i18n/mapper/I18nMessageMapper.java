package com.chenpp.spring.samples.i18n.mapper;

import com.chenpp.spring.samples.i18n.entity.I18nMessage;

import java.util.List;

/**
 * @author April.Chen
 * @date 2023/9/12 7:04 下午
 **/
public interface I18nMessageMapper {

    /**
     * selectAll
     *
     * @return list
     */
    List<I18nMessage> selectAll();
}
