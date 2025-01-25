package com.pharma.pharmserv;

import org.springframework.data.repository.CrudRepository;

import com.pharma.pharmserv.User;

@SuppressWarnings("unused")
public interface UserRepository extends CrudRepository<User, Integer> {

}
