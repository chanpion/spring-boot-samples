package com.chenpp.samples.mybatisflex;

import com.chenpp.samples.mybatisflex.entity.Account;
import com.chenpp.samples.mybatisflex.mapper.AccountMapper;
import com.mybatisflex.core.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//import static com.chenpp.samples.mybatisflex.entity.table.AccountTableDef.ACCOUNT;

/**
 * @author April.Chen
 * @date 2023/8/30 10:04 上午
 **/
@SpringBootTest
public class MybatisFlexTest {
    @Autowired
    private AccountMapper accountMapper;

    @Test
    void contextLoads() {
//        QueryWrapper queryWrapper = QueryWrapper.create()
//                .select()
//                .where(ACCOUNT.AGE.eq(18));
//        Account account = accountMapper.selectOneByQuery(queryWrapper);
//        System.out.println(account);
    }

}
