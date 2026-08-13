package com.pharma.pharmserv.Services;

import org.springframework.beans.factory.annotation.Autowired;
import com.pharma.pharmserv.Entities.User;
import com.pharma.pharmserv.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(String userStringId, String userPass) {

        User user = userRepository.findByUserId(userStringId)
                .orElseThrow(() -> new RuntimeException("User Not Found."));

        if (!passwordEncoder.matches(userPass, user.getUserPass())) {
            throw new RuntimeException("Invalid credentials.");
        }

        return jwtService.generateToken(userStringId);
    }
}
