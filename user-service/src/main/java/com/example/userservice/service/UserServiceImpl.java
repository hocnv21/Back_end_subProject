package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public String deleteUser(int userId) {
        userRepository.deleteById(userId);
        return "Xoa thanh cong";
    }

    @Override
    public User updateUser(User user) {
        User user1= userRepository.saveAndFlush(user);
        return user1;
    }

    @Override
    public User getUserById(int id) {
        User user= userRepository.findById(id).get();
        return user;
    }

    @Override
    public List<User> getListUser() {
        List<User> userList= userRepository.findAll();
        return userList;
    }
}
