package com.project.midtrans2.usermanagement.service;

import com.project.midtrans2.usermanagement.model.User;
import com.project.midtrans2.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create or Update user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID (now String since userID is an email)
    public Optional<User> getUserById(String userID) {
        return userRepository.findById(userID);
    }

    // Delete user by ID
    public void deleteUser(String userID) {
        userRepository.deleteById(userID);
    }
}
