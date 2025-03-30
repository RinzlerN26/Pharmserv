package com.pharma.pharmserv.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.pharma.pharmserv.Entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUserId(String userStringId);
}
