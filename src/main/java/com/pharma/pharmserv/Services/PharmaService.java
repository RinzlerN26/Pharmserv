package com.pharma.pharmserv.Services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.pharmserv.Entities.Pharma;
import com.pharma.pharmserv.Entities.User;
import com.pharma.pharmserv.Repositories.PharmaRepository;
import com.pharma.pharmserv.Repositories.UserRepository;

@Service
public class PharmaService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PharmaRepository pharmaRepository;

    public String addNewPharmaEntry(Map<String, Object> pharmaDetails) {

        Number userId = (Number) pharmaDetails.get("userId");

        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }

        Optional<User> userObj = userRepository.findById(userId.intValue());

        if (userObj.isEmpty()) {
            return "User Not Found.";
        }
        User user = userObj.get();
        Pharma newPharmaEntry = new Pharma();
        newPharmaEntry.setUser(user);
        newPharmaEntry.setMedicineName((String) pharmaDetails.get("medicineName"));
        newPharmaEntry.setCompanyName((String) pharmaDetails.get("companyName"));
        newPharmaEntry.setDealerName((String) pharmaDetails.get("dealerName"));
        newPharmaEntry.setPurchaseRate((Integer) pharmaDetails.get("purchaseRate"));
        newPharmaEntry.setExpiryDate(LocalDate.parse((String) pharmaDetails.get("expiryDate")));
        pharmaRepository.save(newPharmaEntry);
        return "Entry Added Successfully.";
    }

    public Iterable<Pharma> getPharmaEntries() {
        return pharmaRepository.findAll();
    }

    public List<Map<String, Object>> getPharmaEntriesByUser(Integer userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }

        User user = userRepository.findById(userId.intValue())
                .orElseThrow(() -> new RuntimeException("User Not Found."));
        List<Pharma> pharmaceuticals = pharmaRepository.findByUser(user);
        return pharmaceuticals.stream().map(pharma -> {
            Map<String, Object> pharmaData = new HashMap<>();
            pharmaData.put("pharmaId", pharma.getPharmaId());
            pharmaData.put("medicineName", pharma.getMedicineName());
            pharmaData.put("companyName", pharma.getCompanyName());
            pharmaData.put("purchaseRate", pharma.getPurchaseRate());
            pharmaData.put("dealerName", pharma.getDealerName());
            pharmaData.put("expiryDate", pharma.getExpiryDate());
            pharmaData.put("userId", pharma.getUserId());
            return pharmaData;
        }).collect(Collectors.toList());
    }

    public void updatePharmaEntry(Integer userId, Integer pharmaId, Map<String, Object> updatedPharmaDetails) {

        userRepository.findById(userId.intValue())
                .orElseThrow(() -> new RuntimeException("User Not Found."));

        Pharma pharma = Objects.requireNonNull(
                pharmaRepository.findById(pharmaId.intValue())
                        .orElseThrow(() -> new RuntimeException("Pharmaceutical Not Found.")),
                "Pharmaceutical must not be null");

        updatedPharmaDetails.forEach((key, value) -> {
            switch (key) {
                case "medicineName":
                    pharma.setMedicineName((String) value);
                    break;
                case "companyName":
                    pharma.setCompanyName((String) value);
                    break;
                case "purchaseRate":
                    pharma.setPurchaseRate((Integer) value);
                    break;
                case "dealerName":
                    pharma.setDealerName((String) value);
                    break;
                case "expiryDate":
                    pharma.setExpiryDate(LocalDate.parse((String) value));
                    break;
                default:
                    break;
            }
        });

        pharmaRepository.save(pharma);
        return;
    }

    public void deletePharmaEntry(Integer userId, Integer pharmaId) {
        userRepository.findById(userId.intValue())
                .orElseThrow(() -> new RuntimeException("User Not Found."));

        pharmaRepository.findById(pharmaId.intValue())
                .orElseThrow(() -> new RuntimeException("Pharmaceutical Not Found."));

        pharmaRepository.deleteById(pharmaId.intValue());
    }

}
