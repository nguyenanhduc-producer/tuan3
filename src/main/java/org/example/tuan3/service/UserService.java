package org.example.tuan3.service;

import org.example.tuan3.entity.User;
import org.example.tuan3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User createUser(User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            throw new IllegalArgumentException("ID không được để trống!");
        }
        return userRepository.save(user);
    }
}
