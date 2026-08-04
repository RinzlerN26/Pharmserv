package com.pharma.pharmserv.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotBlank(message = "Username is required")
  private String userName;

  @Email(message = "Invalid email")
  @NotBlank(message = "Email is required")
  private String userEmail;

  @Column(unique = true, nullable = false)
  @NotBlank(message = "User ID is required")
  @Size(min = 4, max = 20, message = "User ID must be between 4 and 20 characters")
  private String userId;

  @NotBlank(message = "Password is required")
  private String userPass;

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

  public String getUserPass() {
    return userPass;
  }

  public void setUserPass(String userPass) {
    this.userPass = userPass;
  }
}