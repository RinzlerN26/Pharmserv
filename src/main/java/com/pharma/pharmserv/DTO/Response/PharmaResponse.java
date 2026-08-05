package com.pharma.pharmserv.DTO.Response;

import java.time.LocalDate;

public class PharmaResponse {

    private Integer pharmaId;
    private String medicineName;
    private String companyName;
    private Integer purchaseRate;
    private String dealerName;
    private LocalDate expiryDate;
    private Integer userId;

    public PharmaResponse() {
    }

    public PharmaResponse(Integer pharmaId,
            String medicineName,
            String companyName,
            Integer purchaseRate,
            String dealerName,
            LocalDate expiryDate,
            Integer userId) {
        this.pharmaId = pharmaId;
        this.medicineName = medicineName;
        this.companyName = companyName;
        this.purchaseRate = purchaseRate;
        this.dealerName = dealerName;
        this.expiryDate = expiryDate;
        this.userId = userId;
    }

    public Integer getPharmaId() {
        return pharmaId;
    }

    public void setPharmaId(Integer pharmaId) {
        this.pharmaId = pharmaId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
