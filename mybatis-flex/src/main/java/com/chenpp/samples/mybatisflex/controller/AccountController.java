package com.chenpp.samples.mybatisflex.controller;

import com.chenpp.samples.mybatisflex.entity.Account;
import com.chenpp.samples.mybatisflex.mapper.AccountMapper;
import com.mybatisflex.annotation.UseDataSource;
import com.mybatisflex.core.datasource.DataSourceKey;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author April.Chen
 * @date 2023/8/30 9:57 上午
 **/
@UseDataSource("ds1")
@RestController
public class AccountController {
    @Resource
    AccountMapper accountMapper;

    @Resource
    JdbcTemplate jdbcTemplate;


    @PostMapping("/account/add")
    @Transactional
    public String add(@RequestBody Account account) {
        Integer count1 = jdbcTemplate.queryForObject("select count(*) from tb_account", Integer.class);
        System.out.println(">>>>count1: " + count1);

        DataSourceKey.use("ds2");
        int update1 = jdbcTemplate.update("INSERT INTO `flex_test`.`tb_account` ( `user_name`, `age`, `birthday`, `is_delete`) VALUES ( '王五', 18, '2023-07-04 15:00:26', 0);");
        System.out.println(">>>>>>>>>update1: " + update1);

        DataSourceKey.use("ds1");
        accountMapper.insert(account);

        DataSourceKey.use("ds2");
        accountMapper.insert(account);

        return "add ok!";
    }

    @GetMapping("/account/byId/{id}")
    Account selectId(@PathVariable("id") String id) {
        return accountMapper.selectOneById(id);
    }

    @GetMapping("/account/{id}")
    @Transactional
    public Account selectOne(@PathVariable("id") Long id) {

        Row row1 = Db.selectOneById(null, "tb_account", "id", 1);
        System.out.println(">>>>>>> row1: " + row1);

        Row row2 = Db.selectOneById(null, "tb_account", "id", 2);
        System.out.println(">>>>>>> row2: " + row2);

        Account account = new Account();
        account.setId(2L);
        account.setUserName("haha1111");
        accountMapper.update(account);

        return accountMapper.selectOneById(id);
    }


    @GetMapping("/all")
    List<Account> all() {
        return accountMapper.selectAll();
    }


    @GetMapping("/paginate")
    Page<Account> paginate(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        return accountMapper.paginate(pageNumber, pageSize, QueryWrapper.create());
    }


    @GetMapping("/ds")
    @UseDataSource("ds2222")
    public String ds() {
        return ">>>>>ds: " + DataSourceKey.get();
    }


    @GetMapping("/multids")
    @Transactional
    @UseDataSource("ds1")
    public String multids() {
        Db.selectAll("tb_account");

        DataSourceKey.use("ds2");

        Db.selectAll("tb_account");

        Db.updateById("tb_account", Row.ofKey("id", 1).set("user_name", "newUserName"));
        return "ok";
    }
}
