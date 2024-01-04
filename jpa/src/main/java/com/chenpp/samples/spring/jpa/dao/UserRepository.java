package com.chenpp.samples.spring.jpa.dao;

import com.chenpp.samples.spring.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author April.Chen
 * @date 2023/10/17 6:57 下午
 **/
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
