package com.pharma.pharmserv.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.pharmserv.Entities.Pharma;

import com.pharma.pharmserv.Entities.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PharmaRepository extends JpaRepository<Pharma, Integer> {
    Page<Pharma> findByUser(User user, Pageable pageable);

    Page<Pharma> findByMedicineNameContainingIgnoreCaseOrCompanyNameContainingIgnoreCaseOrDealerNameContainingIgnoreCase(
            String medicineName,
            String companyName,
            String dealerName,
            Pageable pageable);

    @Query("""
            SELECT p FROM Pharma p
            WHERE p.user = :user
            AND (
                LOWER(p.medicineName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(p.dealerName) LIKE LOWER(CONCAT('%', :search, '%'))
            )
            """)
    Page<Pharma> searchByUser(
            @Param("user") User user,
            @Param("search") String search,
            Pageable pageable);
}
