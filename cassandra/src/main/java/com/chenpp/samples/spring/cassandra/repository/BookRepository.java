package com.chenpp.samples.spring.cassandra.repository;

import com.chenpp.samples.spring.cassandra.entity.Book;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * @author April.Chen
 * @date 2023/10/25 4:28 下午
 **/
public interface BookRepository extends CassandraRepository<Book, Long> {

    /**
     * find book by title
     *
     * @param title title
     * @return Book
     */
    @AllowFiltering
    Book findBookByTitle(String title);
}
