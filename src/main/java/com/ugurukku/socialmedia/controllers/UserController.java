package com.ugurukku.socialmedia.controllers;

import com.ugurukku.socialmedia.dao.UserDao;
import com.ugurukku.socialmedia.entities.User;
import com.ugurukku.socialmedia.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public List<User> getAll() {
        return userDao.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Integer id) {
        User user = userDao.findById(id);

        if (user == null){
            throw new UserNotFoundException("id : "+id);
        }

        return user;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User savedUser = userDao.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
