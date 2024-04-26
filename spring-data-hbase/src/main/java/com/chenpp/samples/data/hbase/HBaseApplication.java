package com.chenpp.samples.data.hbase;

import org.apache.hadoop.hbase.client.Result;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2024/4/26 16:16
 */
@SpringBootApplication
public class HBaseApplication implements ApplicationRunner {

    @Resource
    private HbaseTemplate hbaseTemplate;

    public static void main(String[] args) {
        SpringApplication.run(HBaseApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        hbaseTemplate.get("default:zctest_indicator_entity", "107group1", new RowMapper<Object>() {
            @Override
            public Object mapRow(Result result, int i) throws Exception {
                System.out.println(result);
                return result;
            }
        });
    }
}
