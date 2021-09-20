package com.example.listingapp.service.impl;

import com.example.listingapp.entity.User;
import com.example.listingapp.repository.UserRepository;
import com.example.listingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User getUserById(int id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public boolean deleteUserById(int id) {
        if (getUserById(id) != null) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
