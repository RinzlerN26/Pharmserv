package com.pharma.pharmserv.DTO.Request;

import java.time.LocalDate;

public class PharmaRequest {

    private String medicineName;
    private String companyName;
    private String dealerName;
    private Integer purchaseRate;
    private LocalDate expiryDate;

    public PharmaRequest() {
    }

    public PharmaRequest(String medicineName, String companyName,
            String dealerName, Integer purchaseRate,
            LocalDate expiryDate) {
        this.medicineName = medicineName;
        this.companyName = companyName;
        this.dealerName = dealerName;
        this.purchaseRate = purchaseRate;
        this.expiryDate = expiryDate;
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

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public Integer getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(Integer purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
