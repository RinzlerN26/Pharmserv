package com.pharma.pharmserv.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pharma.pharmserv.Services.AuthService;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userCredentials) {
        try {
            String userStringId = userCredentials.get("userStringId");
            String userPass = userCredentials.get("userPass");

            String userToken = authService.authenticate(userStringId, userPass);

            return ResponseEntity.ok(Map.of("token", userToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while logging in: " + e.getMessage());
        }
    }

}
