package com.zju.manager_svr.service.impl;

import java.util.Optional;

import com.zju.manager_svr.model.entity.User;
import com.zju.manager_svr.repository.UserRepository;
import com.zju.manager_svr.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    private String[] type = { "管理员", "主任医生", "医生" };

    @Override
    public String getRole(int userType) {
        return type[userType - 1];
    }

    @Override
    public boolean isUserExist(String username) {
        User user = userRepository.findByUsername(username);
        return null != user;
    }

    @Override
    public boolean registerUser(User user) {
        user = userRepository.save(user);
        return user.getId() != null;
    }

    @Override
    public void updateUser(User user){
        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByID(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
