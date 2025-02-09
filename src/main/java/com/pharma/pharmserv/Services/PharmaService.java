package com.pharma.pharmserv.Services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Optional<User> userObj = userRepository.findById((Integer) pharmaDetails.get("userId"));

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
        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found.")));
        User user = userOptional.get();
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
        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found.")));
        if (userOptional.isPresent()) {
            Optional<Pharma> pharmaOptional = Optional
                    .ofNullable(pharmaRepository.findById(pharmaId)
                            .orElseThrow(() -> new RuntimeException("Pharmaceutical Not Found.")));
            Pharma pharma = pharmaOptional.get();
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
        }
        return;
    }

    public void deletePharmaEntry(Integer userId, Integer pharmaId) {
        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found.")));
        if (userOptional.isPresent()) {
            Optional<Pharma> pharmaOptional = Optional
                    .ofNullable(pharmaRepository.findById(pharmaId)
                            .orElseThrow(() -> new RuntimeException("Pharmaceutical Not Found.")));
            if (pharmaOptional.isPresent()) {
                pharmaRepository.deleteById(pharmaId);
            }
        }
        return;
    }

}
