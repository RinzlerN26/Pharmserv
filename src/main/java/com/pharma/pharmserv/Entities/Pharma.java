package com.pharma.pharmserv.Entities;

import java.time.LocalDate;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

//Migration Commands
// SELECT TABLE_NAME, CONSTRAINT_NAME, DELETE_RULE
// FROM information_schema.REFERENTIAL_CONSTRAINTS
// WHERE TABLE_NAME = 'pharma';
// ALTER TABLE pharma
// DROP FOREIGN KEY 'ENTER CONSTRAINT_NAME HERE';

// ALTER TABLE pharma
// ADD CONSTRAINT 'ENTER CONSTRAINT_NAME HERE'
// FOREIGN KEY (user_id)
// REFERENCES user(id) ON DELETE CASCADE;

@Entity
public class Pharma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Medicine name is required")
    private String medicineName;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotNull(message = "Purchase rate is required")
    @Positive(message = "Purchase rate must be greater than 0")
    private Integer purchaseRate;

    @NotBlank(message = "Dealer name is required")
    private String dealerName;

    @NotNull(message = "Expiry date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Integer getUserId() {
        return user.getId();
    }

    public Integer getPharmaId() {
        return id;
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
