package com.example.listingapp.endpoint;

import com.example.listingapp.entity.User;
import com.example.listingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserEndpoint {

    private final UserService userService;

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") int id) {
        User user = userService.findUserById(id);
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
        log.info("User with {} id was deleted from Database at {}", id, new Date());
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        if (userService.saveUser(user)) {
            log.info("User with {} email was saved at {} ", user.getEmail(), new Date());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (userService.updateUser(user)) {
            log.info("User was updated at {}", new Date());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}