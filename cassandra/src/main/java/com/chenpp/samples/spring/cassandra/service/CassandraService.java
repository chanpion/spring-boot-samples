package com.chenpp.samples.spring.cassandra.service;

import com.chenpp.samples.spring.cassandra.entity.Book;
import com.chenpp.samples.spring.cassandra.repository.BookRepository;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author April.Chen
 * @date 2023/10/25 4:31 下午
 **/
@Service
public class CassandraService {
    private static final Logger LOG = LoggerFactory.getLogger(CassandraService.class);

    @Resource
    private BookRepository bookRepository;

    @Resource
    protected CassandraTemplate cassandraTemplate;

    public void ops() {
        bookRepository.saveAll(Arrays.asList(
                new Book(1L, "War and Peace", "Tolstoy"),
                new Book(2L, "Harry Potter", "Rowling, J.K."),
                new Book(3L, "Anna Karenina", "Tolstoy")
        ));

        // select only on book
        Book harryPotter = bookRepository.findBookByTitle("Harry Potter");

        // modify the selected book
        harryPotter.setTitle("Harry Potter and the Philosopher's Stone");
        bookRepository.save(harryPotter);

        //delete the book with id 1
        bookRepository.deleteById(1L);

        //get all the books
        bookRepository.findAll().forEach(System.out::println);

    }

//    public Boolean incClickCounter(UserCategoryKey key, Long cnt) {
//        try {
//            //update table
//            Update update = QueryBuilder.update("user_category");
//            //set
//            update.with(QueryBuilder.incr("click_cnt", cnt));
//            //where conditions
//            update.where(QueryBuilder.eq("user_id", key.getUserId()))
//                    .and(QueryBuilder.eq("category_id", key.getCategoryId()));
//            cassandraTemplate.getCqlOperations().execute(update);
//            return true;
//        } catch (Exception e) {
//            LOG.error("incClickCounter exception, msg:{}", e.getMessage(), e);
//        }
//        return false;
//    }

}
