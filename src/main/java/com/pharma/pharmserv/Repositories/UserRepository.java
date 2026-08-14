package com.pharma.pharmserv.Repositories;

import org.springframework.data.repository.query.Param;

import com.pharma.pharmserv.Entities.User;
import com.pharma.pharmserv.Enums.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
        Optional<User> findByUserId(String userStringId);

        @Query("""
                            SELECT u
                            FROM User u
                            WHERE
                                LOWER(u.userName) LIKE LOWER(CONCAT('%', :search, '%'))
                                OR LOWER(u.userId) LIKE LOWER(CONCAT('%', :search, '%'))
                                OR LOWER(u.userEmail) LIKE LOWER(CONCAT('%', :search, '%'))
                        """)
        Page<User> search(
                        @Param("search") String search,
                        Pageable pageable);

        boolean existsByUserId(String userId);

        boolean existsByUserEmail(String userEmail);

        boolean existsByRole(Role role);
}
