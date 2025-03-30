package com.pharma.pharmserv.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public Map<String, String> getUserDetails(String userStringId) {
        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByUserId(userStringId)
                        .orElseThrow(() -> new RuntimeException("User Not Found.")));

        User user = userOptional.get();
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("userName", user.getUserName());
        userDetails.put("userEmail", user.getUserEmail());
        userDetails.put("userIntId", user.getId().toString());
        return userDetails;
    }

    public void updateUserById(Integer userId, Map<String, Object> updatedUserDetails) {
        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found.")));
        User user = userOptional.get();
        updatedUserDetails.forEach((key, value) -> {
            switch (key) {
                case "userName":
                    user.setUserName((String) value);
                    break;
                case "userEmail":
                    user.setUserEmail((String) value);
                    break;
                case "userPass":
                    user.setUserPass(passwordEncoder.encode((String) value));
                    break;
                case "userId":
                    user.setUserId((String) value);
                    break;
                default:
                    break;
            }
        });
        userRepository.save(user);
    }

    public void deleteUserById(Integer userId) {
        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found.")));
        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
        }
        return;
    }

}
