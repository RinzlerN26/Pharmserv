package com.pharma.pharmserv.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.pharma.pharmserv.Repositories.UserRepository;
import com.pharma.pharmserv.DTO.Request.CreateUserRequest;
import com.pharma.pharmserv.DTO.Response.UserResponse;
import com.pharma.pharmserv.Entities.User;
import com.pharma.pharmserv.Enums.Role;
import com.pharma.pharmserv.Exception.CustomServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void createNewUser(CreateUserRequest userDetails) {
        if (userRepository.existsByUserId(userDetails.getUserId())) {
            throw new CustomServiceException(HttpStatus.CONFLICT, "User ID already exists.");
        }

        if (userRepository.existsByUserEmail(userDetails.getUserEmail())) {
            throw new CustomServiceException(HttpStatus.CONFLICT, "Email already exists.");
        }
        User n = new User();
        n.setUserName(userDetails.getUserName());
        n.setUserEmail(userDetails.getUserEmail());
        n.setUserId(userDetails.getUserId());
        n.setUserPass(passwordEncoder.encode(userDetails.getUserPass()));
        n.setRole(Role.USER);
        userRepository.save(n);
    }

    public Page<UserResponse> getAllUsers(int page, int size, String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        if (search == null || search.isBlank()) {
            return userRepository.findAll(pageable)
                    .map(user -> this.convertToResponse(user));
        }

        return userRepository.search(search, pageable)
                .map(user -> this.convertToResponse(user));
    }

    public Map<String, String> getUserDetails(String userStringId) {
        User user = userRepository.findByUserId(userStringId)
                .orElseThrow(() -> new CustomServiceException(HttpStatus.NOT_FOUND, "User Not Found."));
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("userName", user.getUserName());
        userDetails.put("userEmail", user.getUserEmail());
        userDetails.put("userIntId", user.getId().toString());
        return userDetails;
    }

    public void updateUserById(Integer userId, Map<String, Object> updatedUserDetails) {
        User user = Objects.requireNonNull(userRepository.findById(userId.intValue())
                .orElseThrow(() -> new CustomServiceException(HttpStatus.NOT_FOUND, "User Not Found.")),
                "User must not be null");
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
        userRepository.findById(userId.intValue())
                .orElseThrow(() -> new CustomServiceException(HttpStatus.NOT_FOUND, "User Not Found."));

        userRepository.deleteById(userId.intValue());
        return;
    }

    private UserResponse convertToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getUserEmail(),
                user.getUserId());
    }

}
