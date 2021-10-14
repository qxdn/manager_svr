package com.zju.manager_svr.service;

import com.zju.manager_svr.model.entity.User;

public interface UserService {
    public boolean isUserExist(String username);

    public boolean registerUser(User user);

    public User findUserByUsername(String username);
}
