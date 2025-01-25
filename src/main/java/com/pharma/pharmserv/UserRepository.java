package com.pharma.pharmserv;

import org.springframework.data.repository.CrudRepository;

import com.pharma.pharmserv.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
