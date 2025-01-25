package com.pharma.pharmserv;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class User {
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String userName;

  private String userEmail;

  private String userId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return userName;
  }

  public void setName(String name) {
    this.userName = name;
  }

  public String getEmail() {
    return userEmail;
  }

  public void setEmail(String email) {
    this.userEmail = email;
  }

  public String getUserId(){
    return userId;
  }

  public void setUserId(String id){
   this.userId=id;
  }
}