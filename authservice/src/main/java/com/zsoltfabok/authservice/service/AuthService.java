package com.zsoltfabok.authservice.service;

import com.zsoltfabok.authservice.domain.User;
import com.zsoltfabok.authservice.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    public boolean register(String email, String password) {
        User user = userRepository.create(email, password);
        return user != null;
    }
}