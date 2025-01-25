package com.pharma.pharmserv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/create-new-user")
    public ResponseEntity<String> createNewUser(@RequestBody User userDetails) {
        // userDetails gets converted to User object
        try {
            User n = new User();
            n.setName(userDetails.getName());
            n.setEmail(userDetails.getEmail());
            n.setUserId(userDetails.getUserId());
            userRepository.save(n);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Created Successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating the user: " + e.getMessage());
        }
    }

    @GetMapping(path = "/get-all-users")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
