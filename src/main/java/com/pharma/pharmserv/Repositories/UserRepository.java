package com.pharma.pharmserv.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.pharma.pharmserv.Entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
