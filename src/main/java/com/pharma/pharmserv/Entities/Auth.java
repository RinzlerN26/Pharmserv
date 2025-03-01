package com.pharma.pharmserv.Entities;

public class Auth {

    private String userName;
    private String userPass;

    public Auth() {
    }

    public Auth(String userName, String userPass) {
        this.userName = userName;
        this.userPass = userPass;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return userPass;
    }

    public void setPassword(String userPass) {
        this.userPass = userPass;
    }
}
