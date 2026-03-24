package org.example.tuan3.service;

import org.example.tuan3.dto.UserCreationRequest;
import org.example.tuan3.entity.Role;
import org.example.tuan3.entity.User;
import org.example.tuan3.repository.RoleRepository;
import org.example.tuan3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder; // Gọi cái "máy băm" đã tạo ở Bước 1

    @Autowired
    private RoleRepository roleRepository; // Bạn cần tạo Repository cho Role nếu chưa có

    public User createUser(UserCreationRequest request) {
        User user = new User();
        user.setId(request.getId());
        user.setUsername(request.getUsername());

        // DÒNG 4: HASH PASSWORD BẰNG BCRYPT
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Gán quyền mặc định là ROLE_USER (Dòng 2)
        Role roleUser = roleRepository.findById("ROLE_USER").orElse(null);
        if (roleUser != null) {
            user.getRoles().add(roleUser);
        }

        return userRepository.save(user);
    }
}
