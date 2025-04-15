package com.pharma.pharmserv.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pharma.pharmserv.Entities.User;
import com.pharma.pharmserv.Services.UserService;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/create-new-user")
    public ResponseEntity<Map<String, String>> createNewUser(@RequestBody User userDetails) {
        // userDetails gets converted to User object
        // Spring Boot binds JSON to the getter/setter names,
        try {
            userService.createNewUser(userDetails);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User Created Successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error occurred while creating the user: " + e.getMessage()));
        }
    }

    @GetMapping(path = "/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        try {
            Iterable<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching users: " + e.getMessage());
        }
    }

    @GetMapping(path = "/get-user-details/{userStringId}")
    public ResponseEntity<?> getUserDetails(@PathVariable String userStringId) {
        try {
            Map<String, String> userDetailsMap = userService.getUserDetails(userStringId);
            return ResponseEntity.ok(userDetailsMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching user details: " + e.getMessage());
        }
    }

    @PatchMapping(path = "/update-user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody Map<String, Object> userDetails) {
        try {
            userService.updateUserById(userId, userDetails);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while updating user details: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting user: " + e.getMessage());
        }
    }

}
