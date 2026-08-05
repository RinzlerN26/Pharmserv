package com.pharma.pharmserv.DTO.Response;

public class UserResponse {
    private Integer id;
    private String userName;
    private String userEmail;
    private String userId;

    public UserResponse() {
    }

    public UserResponse(Integer id, String userName, String userEmail, String userId) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
