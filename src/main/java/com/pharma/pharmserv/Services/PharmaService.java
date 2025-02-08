package com.pharma.pharmserv.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public List<Pharma> getPharmaEntriesByUser(Integer userId) {
        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found.")));
        User user = userOptional.get();
        List<Pharma> pharmaceuticals = pharmaRepository.findByUser(user);
        return pharmaceuticals;
    }

}
