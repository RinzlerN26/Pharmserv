package com.pharma.pharmserv.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.pharmserv.DTO.Request.CreateUserRequest;
import com.pharma.pharmserv.Services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/ms/user")
@Tag(name = "User", description = "APIs for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/create-new-user")
    @Operation(summary = "Create a new user", description = "Creates a new user in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Map<String, String>> createNewUser(@RequestBody CreateUserRequest userDetails) {
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/get-all-users")
    @Operation(summary = "Get all users", description = "Returns all registered users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "1") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(required = false) String search) {
        try {
            return ResponseEntity.ok(
                    userService.getAllUsers(page, size, search));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching users: " + e.getMessage());
        }
    }

    @GetMapping(path = "/get-user-details/{userStringId}")
    @Operation(summary = "Get user details", description = "Returns the details of a user using the user string ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getUserDetails(
            @Parameter(description = "Unique user string ID", example = "USR10001") @PathVariable String userStringId) {
        try {
            Map<String, String> userDetailsMap = userService.getUserDetails(userStringId);
            return ResponseEntity.ok(userDetailsMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching user details: " + e.getMessage());
        }
    }

    @PatchMapping(path = "/update-user/{userId}")
    @Operation(summary = "Update user", description = "Updates an existing user's information.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> updateUser(@Parameter(description = "User ID", example = "1") @PathVariable Integer userId,
            @RequestBody Map<String, Object> userDetails) {
        try {
            userService.updateUserById(userId, userDetails);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while updating user details: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete-user/{userId}")
    @Operation(summary = "Delete user", description = "Deletes a user from the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "User ID", example = "1") @PathVariable Integer userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting user: " + e.getMessage());
        }
    }

}
