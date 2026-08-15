package com.pharma.pharmserv.Services;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.pharma.pharmserv.Repositories.UserRepository;
import com.pharma.pharmserv.DTO.Request.AdminCreateUserRequest;
import com.pharma.pharmserv.DTO.Request.CreateUserRequest;
import com.pharma.pharmserv.DTO.Request.UserUpdateRequest;
import com.pharma.pharmserv.DTO.Response.AdminUserResponse;
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

    public Page<AdminUserResponse> getAllUsers(int page, int size, String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        if (search == null || search.isBlank()) {
            return userRepository.findAll(pageable)
                    .map(user -> this.convertToAdminResponse(user));
        }

        return userRepository.search(search, pageable)
                .map(user -> this.convertToAdminResponse(user));
    }

    public UserResponse getUserDetails(String userStringId) {
        User user = userRepository.findByUserId(userStringId)
                .orElseThrow(() -> new CustomServiceException(HttpStatus.NOT_FOUND, "User Not Found."));
        return this.convertToResponse(user);
    }

    public void updateUserById(
            Integer userId,
            UserUpdateRequest updatedUserDetails) {

        User user = userRepository.findById(userId.intValue())
                .orElseThrow(() -> new CustomServiceException(HttpStatus.NOT_FOUND, "User Not Found."));

        if (updatedUserDetails.getUserName() != null) {
            user.setUserName(
                    updatedUserDetails.getUserName());
        }

        if (updatedUserDetails.getUserEmail() != null) {
            user.setUserEmail(
                    updatedUserDetails.getUserEmail());
        }

        if (updatedUserDetails.getUserId() != null) {
            user.setUserId(
                    updatedUserDetails.getUserId());
        }

        if (updatedUserDetails.getUserPass() != null) {
            user.setUserPass(
                    passwordEncoder.encode(
                            updatedUserDetails.getUserPass()));
        }
        userRepository.save(Objects.requireNonNull(user));

    }

    public void deleteUserById(Integer userId) {
        userRepository.findById(userId.intValue())
                .orElseThrow(() -> new CustomServiceException(HttpStatus.NOT_FOUND, "User Not Found."));

        userRepository.deleteById(userId.intValue());
        return;
    }

    public void createUserAsAdmin(
            AdminCreateUserRequest userDetails) {

        if (userRepository.existsByUserId(
                userDetails.getUserId())) {

            throw new CustomServiceException(HttpStatus.CONFLICT, "User ID already exists.");
        }

        if (userRepository.existsByUserEmail(
                userDetails.getUserEmail())) {

            throw new CustomServiceException(HttpStatus.CONFLICT, "Email already exists.");
        }

        User user = new User();

        user.setUserName(userDetails.getUserName());
        user.setUserEmail(userDetails.getUserEmail());
        user.setUserId(userDetails.getUserId());

        user.setUserPass(
                passwordEncoder.encode(
                        userDetails.getUserPass()));

        user.setRole(userDetails.getRole());

        userRepository.save(user);
    }

    private UserResponse convertToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getUserEmail(),
                user.getUserId());
    }

    private AdminUserResponse convertToAdminResponse(User user) {
        return new AdminUserResponse(
                user.getId(),
                user.getUserName(),
                user.getUserEmail(),
                user.getUserId(),
                user.getRole());
    }

}
