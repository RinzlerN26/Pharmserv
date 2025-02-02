package com.pharma.pharmserv.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.pharma.pharmserv.Entities.Pharma;

import com.pharma.pharmserv.Entities.User;

import java.util.List;

public interface PharmaRepository extends CrudRepository<Pharma, Integer> {
    List<Pharma> findByUser(User user);
}
