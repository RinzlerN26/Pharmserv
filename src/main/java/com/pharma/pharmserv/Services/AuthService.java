package com.pharma.pharmserv.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String authenticate(String userName, String userPass) {

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByUserName(userName)
                        .orElseThrow(() -> new RuntimeException("User Not Found.")));

        User user = userOptional.get();

        if (!passwordEncoder.matches(userPass, user.getUserPass())) {
            throw new RuntimeException("Invalid credentials.");
        }

        return jwtService.generateToken(userName);
    }
}
