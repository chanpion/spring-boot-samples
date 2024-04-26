package com.chenpp.samples.hive;

import com.zaxxer.hikari.util.DriverDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hive.HiveClientFactoryBean;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author April.Chen
 * @date 2024/4/26 11:51
 */
@EnableConfigurationProperties(DataSourceProperties.class)
@Configuration
public class HiveAutoConfiguration {

    @Bean("hiveDataSource")
    public DataSource hiveDataSource(DataSourceProperties dataSourceProperties) {
        DriverDataSource dataSource = new DriverDataSource(dataSourceProperties.getUrl(), dataSourceProperties.getDriverClassName(), new Properties(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        return dataSource;
    }

    @Bean
    public HiveTemplate hiveTemplate(DataSource hiveDataSource) throws Exception {
        HiveClientFactoryBean hiveClientFactoryBean = new HiveClientFactoryBean();
        hiveClientFactoryBean.setHiveDataSource(hiveDataSource);
        hiveClientFactoryBean.afterPropertiesSet();
        HiveTemplate hiveTemplate = new HiveTemplate();
        hiveTemplate.setHiveClientFactory(hiveClientFactoryBean.getObject());
        return hiveTemplate;
    }

    @Bean(name = "hiveJdbcTemplate")
    public JdbcTemplate hiveJdbcTemplate(@Qualifier("hiveDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource hiveDataSource) {
        return new JdbcTemplate(hiveDataSource);
    }
}
