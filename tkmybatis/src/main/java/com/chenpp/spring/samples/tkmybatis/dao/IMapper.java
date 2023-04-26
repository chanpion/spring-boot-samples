package com.chenpp.spring.samples.tkmybatis.dao;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.SaveMapper;

/**
 * @author April.Chen
 * @date 2023/4/26 11:52 上午
 **/
@tk.mybatis.mapper.annotation.RegisterMapper
public interface IMapper<T> extends BaseMapper<T>, ExampleMapper<T>, RowBoundsMapper<T>, SaveMapper<T>, Marker {

}