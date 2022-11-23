package com.example.userservice.controller;

import java.util.List;

import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    @CachePut(value = "user", key = "#User.id")
    public User addUser(@RequestBody User User) {
        userService.addUser(User);
        return User;
    }

    @DeleteMapping("/{UserId}")
    @CacheEvict(value = "user", key = "#UserId")
    public String deleteUser(@PathVariable int UserId) {
        userService.deleteUser(UserId);
        return "xoá thành công id" + UserId;
    }

    @PostMapping("/update")
    public User updateUser(@RequestBody User User) {
        User User2 = userService.updateUser(User);
        return User2;
    }

    @GetMapping("/{id}")
    @Cacheable(value = "user", key = "#id")
    public User getUserById(@PathVariable int id) {
        User User = userService.getUserById(id);
        return User;
    }

    @GetMapping("")
    public List<User> getListUser() {
        List<User> dsUser = userService.getListUser();
        return dsUser;
    }
}
