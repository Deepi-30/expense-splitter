package com.deepika.expensesplitter.service;

import com.deepika.expensesplitter.model.User;
import com.deepika.expensesplitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register user
    public User registerUser(User user) {
        // Optional: check if email exists
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if(existing.isPresent()) {
            throw new RuntimeException("User with email already exists");
        }
        return userRepository.save(user);
    }

    // Login
    public User login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

}
