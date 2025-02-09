package com.pharma.pharmserv.Entities;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;

//Migration Commands
// SELECT TABLE_NAME, CONSTRAINT_NAME, DELETE_RULE
// FROM information_schema.REFERENTIAL_CONSTRAINTS
// WHERE TABLE_NAME = 'pharma';
// ALTER TABLE pharma
// DROP FOREIGN KEY FK7yttn8j8yp3ntvrhauu0ivxc5;

// ALTER TABLE pharma
// ADD CONSTRAINT FK7yttn8j8yp3ntvrhauu0ivxc5
// FOREIGN KEY (user_id)
// REFERENCES user(id) ON DELETE CASCADE;

@Entity
public class Pharma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String medicineName;
    private String companyName;
    private Integer purchaseRate;
    private String dealerName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Integer getUserId() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(Integer purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
