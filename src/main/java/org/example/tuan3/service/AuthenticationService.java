package org.example.tuan3.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.tuan3.dto.AuthenticationRequest;
import org.example.tuan3.dto.AuthenticationResponse;
import org.example.tuan3.entity.User;
import org.example.tuan3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

     private final String SIGNER_KEY = "DayLaMotChuoiBiMatCucKyDaiVaBaoMatDungDeKyTokenJwtChoDuAnTuan7CuaDucNguyen";

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new RuntimeException("Mật khẩu không chính xác");
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(User user) {
        // Lấy danh sách tên quyền của User (VD: "ROLE_USER")
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getUsername()) // Lưu tên
                .claim("roles", roles) // ĐÃ THÊM: Lưu luôn cả Chức vụ vào thẻ bài
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600 * 24))
                .signWith(SignatureAlgorithm.HS512, SIGNER_KEY.getBytes())
                .compact();
    }
}
