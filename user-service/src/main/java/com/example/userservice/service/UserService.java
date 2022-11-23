package com.example.userservice.service;
import com.example.userservice.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User addUser(User user);

    public String deleteUser(int userId);

    public User updateUser(User user);

    public User getUserById(int id);

    public List<User> getListUser();
}
