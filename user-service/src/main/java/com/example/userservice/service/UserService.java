package com.example.userservice.service;

import com.example.userservice.entity.User;

import java.util.List;

public interface UserService {

    //create
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user of given userId
    User getUser(String userId);
}