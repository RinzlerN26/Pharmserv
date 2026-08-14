package com.pharma.pharmserv.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pharma.pharmserv.Entities.User;
import com.pharma.pharmserv.Enums.Role;
import com.pharma.pharmserv.Repositories.UserRepository;

@Component
public class AdminBootstrapService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.bootstrap.enabled}")
    private boolean bootstrapEnabled;

    @Value("${admin.bootstrap.user-id}")
    private String adminUserId;

    @Value("${admin.bootstrap.username}")
    private String adminUsername;

    @Value("${admin.bootstrap.email}")
    private String adminEmail;

    @Value("${admin.bootstrap.password}")
    private String adminPassword;

    public AdminBootstrapService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (!bootstrapEnabled) {
            return;
        }

        if (userRepository.existsByRole(Role.ADMIN)) {
            return;
        }

        User admin = new User();

        admin.setUserName(adminUsername);
        admin.setUserEmail(adminEmail);
        admin.setUserId(adminUserId);
        admin.setUserPass(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);

        System.out.println("Initial ADMIN account created successfully.");
    }
}
