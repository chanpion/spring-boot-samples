package com.chenpp.samples.spring.jpa.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * @author April.Chen
 * @date 2023/10/18 11:20 上午
 **/
public class TableUpperCasePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    private static final long serialVersionUID = 5541563555251935294L;

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        // 将表名全部转换成大写
        String tableName = name.getText().toUpperCase();
        return Identifier.toIdentifier(tableName);
    }
}
