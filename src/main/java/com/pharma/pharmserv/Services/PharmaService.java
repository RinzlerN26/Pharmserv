package com.pharma.pharmserv.Services;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pharma.pharmserv.DTO.Request.PharmaRequest;
import com.pharma.pharmserv.DTO.Response.PharmaResponse;
import com.pharma.pharmserv.Entities.Pharma;
import com.pharma.pharmserv.Entities.User;
import com.pharma.pharmserv.Exception.CustomServiceException;
import com.pharma.pharmserv.Repositories.PharmaRepository;
import com.pharma.pharmserv.Repositories.UserRepository;

@Service
public class PharmaService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PharmaRepository pharmaRepository;

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getName() == null) {

            throw new CustomServiceException(
                    HttpStatus.UNAUTHORIZED,
                    "User is not authenticated.");
        }

        String userStringId = authentication.getName();

        return userRepository.findByUserId(userStringId)
                .orElseThrow(() -> new CustomServiceException(
                        HttpStatus.NOT_FOUND,
                        "User Not Found."));
    }

    public String addNewPharmaEntry(PharmaRequest request) {
        User user = getAuthenticatedUser();

        Pharma newPharmaEntry = new Pharma();
        newPharmaEntry.setUser(user);
        newPharmaEntry.setMedicineName(request.getMedicineName());
        newPharmaEntry.setCompanyName(request.getCompanyName());
        newPharmaEntry.setDealerName(request.getDealerName());
        newPharmaEntry.setPurchaseRate(request.getPurchaseRate());
        newPharmaEntry.setExpiryDate(request.getExpiryDate());
        pharmaRepository.save(newPharmaEntry);
        return "Entry Added Successfully.";
    }

    public Page<PharmaResponse> getPharmaEntriesByUser(
            int page,
            int size,
            String search) {

        User user = getAuthenticatedUser();

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Pharma> pharmaPage;

        if (search == null || search.isBlank()) {
            pharmaPage = pharmaRepository.findByUser(user, pageable);
        } else {
            pharmaPage = pharmaRepository.searchByUser(user, search, pageable);
        }

        return pharmaPage.map(pharma -> this.convertToResponse(pharma));
    }

    public void updatePharmaEntry(Integer pharmaId, Map<String, Object> updatedPharmaDetails) {

        User user = getAuthenticatedUser();

        Pharma pharma = Objects.requireNonNull(
                pharmaRepository.findById(pharmaId.intValue())
                        .orElseThrow(
                                () -> new CustomServiceException(HttpStatus.NOT_FOUND, "Pharmaceutical Not Found.")),
                "Pharmaceutical must not be null");

        if (!pharma.getUserId().equals(user.getId())) {
            throw new CustomServiceException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to modify this pharmaceutical entry.");
        }

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

    public void deletePharmaEntry(Integer pharmaId) {
        User user = getAuthenticatedUser();

        Pharma pharma = pharmaRepository
                .findById(pharmaId.intValue())
                .orElseThrow(() -> new CustomServiceException(
                        HttpStatus.NOT_FOUND,
                        "Pharmaceutical Not Found."));

        if (!pharma.getUserId().equals(user.getId())) {
            throw new CustomServiceException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to delete this pharmaceutical entry.");
        }
        pharmaRepository.deleteById(pharmaId.intValue());
    }

    public Page<PharmaResponse> getAllPharmaEntries(int page, int size, String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Pharma> pharmaPage;

        if (search == null || search.trim().isEmpty()) {
            pharmaPage = pharmaRepository.findAll(pageable);
        } else {
            pharmaPage = pharmaRepository.search(search, pageable);
        }

        return pharmaPage.map(pharma -> this.convertToResponse(pharma));
    }

    private PharmaResponse convertToResponse(Pharma pharma) {
        return new PharmaResponse(
                pharma.getPharmaId(),
                pharma.getMedicineName(),
                pharma.getCompanyName(),
                pharma.getPurchaseRate(),
                pharma.getDealerName(),
                pharma.getExpiryDate(),
                pharma.getUserId());
    }

}
