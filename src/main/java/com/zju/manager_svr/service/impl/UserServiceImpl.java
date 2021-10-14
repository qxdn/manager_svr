package com.zju.manager_svr.service.impl;

import com.zju.manager_svr.model.entity.User;
import com.zju.manager_svr.repository.UserRepository;
import com.zju.manager_svr.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

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
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
