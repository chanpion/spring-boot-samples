package com.chenpp.samples.spring.cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.Statement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;

/**
 * @author April.Chen
 * @date 2023/10/25 5:43 下午
 **/
public class CassandraTest {
    private CqlSession cqlSession;

    /**
     * Initiates a connection to the session specified by the application.conf.
     */
    @Before
    public void connect() {
        InetSocketAddress address = InetSocketAddress.createUnresolved("10.58.12.60", 9042);
        cqlSession = CqlSession.builder()
                .addContactPoint(address)
                .withKeyspace("test")
                .withLocalDatacenter("dc")
                .build();
        System.out.printf("Connected session: %s%n", cqlSession.getName());
    }

    public void createSchema() {

        cqlSession.execute(
                "CREATE KEYSPACE IF NOT EXISTS simplex WITH replication "
                        + "= {'class':'SimpleStrategy', 'replication_factor':1};");

        cqlSession.execute(
                "CREATE TABLE IF NOT EXISTS simplex.songs ("
                        + "id uuid PRIMARY KEY,"
                        + "title text,"
                        + "album text,"
                        + "artist text,"
                        + "tags set<text>,"
                        + "data blob"
                        + ");");

        cqlSession.execute(
                "CREATE TABLE IF NOT EXISTS simplex.playlists ("
                        + "id uuid,"
                        + "title text,"
                        + "album text, "
                        + "artist text,"
                        + "song_id uuid,"
                        + "PRIMARY KEY (id, title, album, artist)"
                        + ");");
    }

    /**
     * Inserts data into the tables.
     */
    public void loadData() {

        cqlSession.execute(
                "INSERT INTO simplex.songs (id, title, album, artist, tags) "
                        + "VALUES ("
                        + "756716f7-2e54-4715-9f00-91dcbea6cf50,"
                        + "'La Petite Tonkinoise',"
                        + "'Bye Bye Blackbird',"
                        + "'Joséphine Baker',"
                        + "{'jazz', '2013'})"
                        + ";");

        cqlSession.execute(
                "INSERT INTO simplex.playlists (id, song_id, title, album, artist) "
                        + "VALUES ("
                        + "2cc9ccb7-6221-4ccb-8387-f22b6a1b354d,"
                        + "756716f7-2e54-4715-9f00-91dcbea6cf50,"
                        + "'La Petite Tonkinoise',"
                        + "'Bye Bye Blackbird',"
                        + "'Joséphine Baker'"
                        + ");");
    }

    /**
     * Queries and displays data.
     */
    public void querySchema() {

        ResultSet results =
                cqlSession.execute(
                        "SELECT * FROM simplex.playlists "
                                + "WHERE id = 2cc9ccb7-6221-4ccb-8387-f22b6a1b354d;");

        System.out.printf("%-30s\t%-20s\t%-20s%n", "title", "album", "artist");
        System.out.println(
                "-------------------------------+-----------------------+--------------------");

        for (Row row : results) {

            System.out.printf(
                    "%-30s\t%-20s\t%-20s%n",
                    row.getString("title"), row.getString("album"), row.getString("artist"));
        }
    }

    /**
     * Closes the session.
     */
    public void close() {
        if (cqlSession != null) {
            cqlSession.close();
        }
    }

    /**
     * 查询 键空间
     */
    @Test
    public void findKeySpace() {
        Optional<CqlIdentifier> keyspace = cqlSession.getKeyspace();
        if (keyspace.isPresent()) {
            CqlIdentifier cqlIdentifier = keyspace.get();
            System.out.println("键空间名：" + cqlIdentifier.asInternal());
        }
    }

    /**
     * 创建键空间
     */
    public void createKeySpace() {
        SimpleStatement simpleStatement = SchemaBuilder
                .createKeyspace("school")
                .ifNotExists()
                .withSimpleStrategy(1).
                        build();

        cqlSession.execute(simpleStatement);
    }

    /**
     * 删除键空间
     */
    public void dropKeySpace() {
        SimpleStatement state = SchemaBuilder.dropKeyspace("school").ifExists().build();
        cqlSession.execute(state);

    }

    /**
     * 创建表
     */
    public void createTable() {
        SimpleStatement statement = SchemaBuilder
                .createTable("school", "student")
                .withPartitionKey("id", null)
                .withColumn("address", DataTypes.TEXT)
                .withColumn("age", DataTypes.INT)
                .withColumn("education", DataTypes.mapOf(DataTypes.TEXT, DataTypes.TEXT))
                .withColumn("email", DataTypes.TEXT)
                .withColumn("gender", DataTypes.INT)
                .withColumn("interest", DataTypes.setOf(DataTypes.TEXT))
                .withColumn("phone", DataTypes.listOf(DataTypes.TEXT))
                .withColumn("name", DataTypes.TEXT)
                .build();
        ResultSet resultSet = cqlSession.execute(statement);
        System.out.println(resultSet.getExecutionInfo());

    }

    /**
     * 修改表
     */
    public void updateTable() {
        //添加字段
        SchemaBuilder.alterTable("school", "student")
                .addColumn("email", DataTypes.TEXT);
        //修改字段
        SchemaBuilder.alterTable("school", "student")
                .alterColumn("email", DataTypes.setOf(DataTypes.TEXT));
        //删除字段
        SchemaBuilder.alterTable("school", "student")
                .dropColumn("email");

    }


    /**
     * 删除表
     */
    public void dropTable() {
        SimpleStatement statement = SchemaBuilder.dropTable("school", "student").ifExists().build();
        cqlSession.execute(statement);
    }


    /**
     * 添加数据
     * 使用CQL
     */
    public void insertByCQL() {
        String insertSql = "INSERT INTO school.student (id,address,age,gender,name,interest, phone,education) VALUES (1011,'中山路21号',16,1,'李小仙',{'游泳', '跑步'},['010-88888888','13888888888'],{'小学' : '城市第一小学', '中学' : '城市第一中学'}) ;";
        cqlSession.execute(insertSql);
    }

    /**
     * 查询所有数据
     */
    public void queryAll(){
        QueryBuilder.selectFrom("school.student").all();

    }

}
