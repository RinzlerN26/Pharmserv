package com.pharma.pharmserv.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserUpdateRequest {

    private String userName;

    @Email(message = "Invalid email")
    private String userEmail;

    @Size(min = 4, max = 20, message = "User ID must be between 4 and 20 characters")
    private String userId;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String userPass;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}