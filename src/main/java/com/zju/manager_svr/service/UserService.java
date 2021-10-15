package com.zju.manager_svr.service;

import java.util.List;

import com.zju.manager_svr.model.entity.User;

public interface UserService {

    public String getRole(int userType);

    public boolean isUserExist(String username);

    public boolean registerUser(User user);

    public User findUserByUsername(String username);

    public User findUserByID(Integer id);

    public List<User> findAllUser();

    public void updateUser(User user);
}
