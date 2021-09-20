package com.example.listingapp.endpoint;

import com.example.listingapp.dto.UserCreateDto;
import com.example.listingapp.dto.UserDto;
import com.example.listingapp.entity.User;
import com.example.listingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserEndpoint {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<UserDto> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserDto> userDtos = new LinkedList<>();
        for (User user : users) {
            userDtos.add(modelMapper.map(user, UserDto.class));
        }
        return userDtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") int id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable(name = "id") int id) {
        if (!userService.deleteUserById(id)) {
            return ResponseEntity.notFound().build();
        }
        log.info("User with {} id was deleted from Database", id);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity saveUser(@RequestBody @Valid UserCreateDto user) {
        if (userService.saveUser(modelMapper.map(user, User.class))) {
            log.info("User with {} email was saved", user.getEmail());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserCreateDto user
            , @RequestParam(name = "id") int id) {
        User userFromDto = modelMapper.map(user, User.class);
        userFromDto.setId(id);
        if (userService.updateUser(userFromDto)) {
            log.info("User with was updated ");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}