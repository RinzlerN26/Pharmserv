package com.pharma.pharmserv.Controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pharma.pharmserv.Entities.Pharma;
import com.pharma.pharmserv.Entities.User;
import com.pharma.pharmserv.Repositories.PharmaRepository;
import com.pharma.pharmserv.Repositories.UserRepository;

@Controller
@RequestMapping(path = "/pharma")
public class PharmaController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PharmaRepository pharmaRepository;

    @PostMapping(path = "/add-pharma-entry")
    public ResponseEntity<String> addPharmaEntry(@RequestBody Map<String, Object> pharmaDetails) {
        try {
            Optional<User> userObj = userRepository.findById((Integer) pharmaDetails.get("userId"));

            if (userObj.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found.");
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

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Entry Added Successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while adding entry: " + e.getMessage());
        }

    }

    @GetMapping(path = "/get-pharma-entries")
    public @ResponseBody Iterable<Pharma> getAllPharmaEntries() {
        return pharmaRepository.findAll();
    }

    @GetMapping("/get-pharma-entries/{userId}")
    public ResponseEntity<List<Pharma>> getPharmaEntriesByUser(@PathVariable Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User user = userOptional.get();
        List<Pharma> medicines = pharmaRepository.findByUser(user);

        return ResponseEntity.ok(medicines);
    }

}
