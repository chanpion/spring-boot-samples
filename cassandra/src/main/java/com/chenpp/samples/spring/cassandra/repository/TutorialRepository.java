package com.chenpp.samples.spring.cassandra.repository;

import com.chenpp.samples.spring.cassandra.entity.Tutorial;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author April.Chen
 * @date 2023/10/25 4:43 下午
 **/
public interface TutorialRepository extends CassandraRepository<Tutorial, UUID> {
    @AllowFiltering
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContaining(String title);
}
