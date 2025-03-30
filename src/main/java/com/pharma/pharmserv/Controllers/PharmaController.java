package com.pharma.pharmserv.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pharma.pharmserv.Entities.Pharma;
import com.pharma.pharmserv.Services.PharmaService;

@Controller
@RequestMapping(path = "/pharma")
public class PharmaController {

    @Autowired
    private PharmaService pharmaService;

    @PostMapping(path = "/add-pharma-entry")
    public ResponseEntity<String> addPharmaEntry(@RequestBody Map<String, Object> pharmaDetails) {
        try {
            String addEntryResult = pharmaService.addNewPharmaEntry(pharmaDetails);

            if (addEntryResult == "User Not Found.") {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found.");
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Entry Added Successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while adding entry: " + e.getMessage());
        }

    }

    @GetMapping(path = "/get-pharma-entries")
    public ResponseEntity<?> getAllPharmaEntries() {
        try {
            Iterable<Pharma> pharmas = pharmaService.getPharmaEntries();
            return ResponseEntity.ok(pharmas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching users: " + e.getMessage());
        }
    }

    @GetMapping("/get-pharma-entries/{userId}")
    public ResponseEntity<?> getPharmaEntriesByUser(@PathVariable Integer userId) {
        try {
            List<Map<String, Object>> pharmaEntries = pharmaService.getPharmaEntriesByUser(userId);

            return ResponseEntity.ok(pharmaEntries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while adding entry: " + e.getMessage());
        }
    }

    @PatchMapping(path = "/update-pharma-entry/{userId}/{pharmaId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @PathVariable Integer pharmaId,
            @RequestBody Map<String, Object> pharmaDetails) {
        try {
            pharmaService.updatePharmaEntry(userId, pharmaId, pharmaDetails);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while updating user details: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete-pharma-entry/{userId}/{pharmaId}")
    public ResponseEntity<?> deletePharmaEntriesByUser(@PathVariable Integer userId, @PathVariable Integer pharmaId) {
        try {
            pharmaService.deletePharmaEntry(userId, pharmaId);
            ;
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting pharmaceutical entry: " + e.getMessage());
        }
    }

}
