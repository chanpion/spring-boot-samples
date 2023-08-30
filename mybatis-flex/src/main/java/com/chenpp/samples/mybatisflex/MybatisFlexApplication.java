package com.chenpp.samples.mybatisflex;

import com.chenpp.samples.mybatisflex.mapper.AccountMapper;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.ConsoleMessageCollector;
import com.mybatisflex.core.audit.MessageCollector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2023/8/30 9:53 上午
 **/
@SpringBootApplication
@MapperScan("com.chenpp.samples.mybatisflex.mapper")
public class MybatisFlexApplication implements CommandLineRunner, ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private AccountMapper accountMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisFlexApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.accountMapper.selectOneById(1));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("onApplicationEvent");
        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        MessageCollector collector = new ConsoleMessageCollector();
        AuditManager.setMessageCollector(collector);
    }
}
