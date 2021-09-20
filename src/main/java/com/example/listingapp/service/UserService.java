package com.example.listingapp.service;


import com.example.listingapp.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    boolean saveUser(User user);

    User getUserById(int id);

    boolean deleteUserById(int id);

}
