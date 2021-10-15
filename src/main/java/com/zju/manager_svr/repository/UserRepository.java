package com.zju.manager_svr.repository;

import java.util.Optional;

import com.zju.manager_svr.model.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    Optional<User> findById(Integer id);
}
