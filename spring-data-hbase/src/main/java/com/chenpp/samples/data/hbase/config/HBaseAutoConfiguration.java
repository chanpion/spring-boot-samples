package com.chenpp.samples.data.hbase.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

/**
 * @author April.Chen
 * @date 2024/4/26 14:11
 */
@Configuration
public class HBaseAutoConfiguration {

    @Bean
    public HbaseTemplate hbaseTemplate() {
        HBaseConfiguration conf = new HBaseConfiguration();
        conf.set("hbase.zookeeper.quorum", "yuntu-d-010057036018.te.td,yuntu-d-010057036019.te.td,yuntu-d-010057036017.te.td");
        conf.set("hbase.zookeeper.property.clientPort", "2182");
        conf.set("hbase.client.retries.number", "2");
        conf.setInt("hbase.client.operation.timeout", 1000);
        conf.set("zookeeper.recovery.retry", "2");
        conf.set("zookeeper.znode.parent", "/hbase");

        HbaseTemplate hbaseTemplate = new HbaseTemplate(conf);
        return hbaseTemplate;
    }
}
