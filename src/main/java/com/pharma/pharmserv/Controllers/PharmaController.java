package com.pharma.pharmserv.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.pharmserv.Entities.Pharma;
import com.pharma.pharmserv.Services.PharmaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/ms/pharma")
@Tag(name = "Pharma", description = "APIs for managing pharmaceutical entries")
public class PharmaController {

    @Autowired
    private PharmaService pharmaService;

    @PostMapping(path = "/add-pharma-entry")
    @Operation(summary = "Add a new pharmaceutical entry", description = "Creates a new pharmaceutical record for a specific user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entry created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Map<String, String>> addPharmaEntry(@RequestBody Map<String, Object> pharmaDetails) {
        try {
            String addEntryResult = pharmaService.addNewPharmaEntry(pharmaDetails);

            if (addEntryResult == "User Not Found.") {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User Not Found."));
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Entry Added Successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error occurred while adding entry: " + e.getMessage()));
        }

    }

    @GetMapping(path = "/get-pharma-entries")
    @Operation(summary = "Get all pharmaceutical entries", description = "Returns all pharmaceutical entries stored in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entries retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Get pharmaceutical entries by user", description = "Returns all pharmaceutical entries belonging to a specific user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entries retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getPharmaEntriesByUser(
            @Parameter(description = "Unique user ID", example = "12") @PathVariable Integer userId) {
        try {
            List<Map<String, Object>> pharmaEntries = pharmaService.getPharmaEntriesByUser(userId);

            return ResponseEntity.ok(pharmaEntries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while adding entry: " + e.getMessage());
        }
    }

    @PatchMapping(path = "/update-pharma-entry/{userId}/{pharmaId}")
    @Operation(summary = "Update pharmaceutical entry", description = "Updates an existing pharmaceutical entry belonging to a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Entry updated successfully"),
            @ApiResponse(responseCode = "404", description = "Entry not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> updateUser(
            @Parameter(description = "User ID", example = "12") @PathVariable Integer userId,

            @Parameter(description = "Pharmaceutical Entry ID", example = "55") @PathVariable Integer pharmaId,
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
    @Operation(summary = "Delete pharmaceutical entry", description = "Deletes a pharmaceutical entry for a given user.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Entry deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Entry not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> deletePharmaEntriesByUser(
            @Parameter(description = "User ID", example = "12") @PathVariable Integer userId,
            @Parameter(description = "Pharmaceutical Entry ID", example = "55") @PathVariable Integer pharmaId) {
        try {
            pharmaService.deletePharmaEntry(userId, pharmaId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting pharmaceutical entry: " + e.getMessage());
        }
    }

}
