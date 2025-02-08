package com.pharma.pharmserv.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pharma.pharmserv.Repositories.UserRepository;
import com.pharma.pharmserv.Entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void createNewUser(User userDetails) {
        User n = new User();
        n.setUserName(userDetails.getUserName());
        n.setUserEmail(userDetails.getUserEmail());
        n.setUserId(userDetails.getUserId());
        n.setUserPass(passwordEncoder.encode(userDetails.getUserPass()));
        userRepository.save(n);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

}
