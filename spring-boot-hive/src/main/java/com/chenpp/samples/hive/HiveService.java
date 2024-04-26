package com.chenpp.samples.hive;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/4/26 10:53
 */

@Slf4j
@Service
public class HiveService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HiveTemplate hiveTemplate;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public void execute(String query) {
        try {
            HiveConf hiveConf = new HiveConf();
            hiveConf.addResource(new Path(System.getenv("HIVE_CONF_DIR"), "hive-site.xml"));
            hiveConf.set("hive.execution.engine", "tez");

            Class.forName("org.apache.hive.jdbc.HiveDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = ((Connection) connection).createStatement();

            statement.execute(query);

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                // 处理结果集
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void query(String query) {
        List<String> list = hiveTemplate.query("SELECT * FROM  default.person LIMIT 10");
        list.forEach(System.out::println);
    }

    public void query() {
        List<String> list = hiveTemplate.query("SELECT * FROM   default.person LIMIT 10");
        list.forEach(System.out::println);
        List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM   default.person LIMIT 10");
        results.forEach(System.out::println);
    }

    public void crud() {
        StringBuffer sql = new StringBuffer("create table IF NOT EXISTS ");
        sql.append("HIVE_TEST");
        sql.append("(KEY INT, VALUE STRING)");
        sql.append("PARTITIONED BY (CTIME DATE)"); // 分区存储
        sql.append("ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' "); // 定义分隔符
        sql.append("STORED AS TEXTFILE"); // 作为文本存储

        log.info(sql.toString());
        jdbcTemplate.execute(sql.toString());

        jdbcTemplate.execute("insert into hive_test(key, value) values('Neo','Chen')");
        jdbcTemplate.execute("DROP TABLE IF EXISTS hive_test");
    }
}