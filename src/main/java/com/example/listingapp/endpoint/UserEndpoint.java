package com.example.listingapp.endpoint;


import com.example.listingapp.entity.User;
import com.example.listingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable(name = "id") int id) {
        if (!userService.deleteUserById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        if (userService.saveUser(user)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (user != null) {
            User fromDB = getUserById(user.getId()).getBody();
            if (fromDB != null) {
                userService.saveUser(user);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}