package com.pharma.pharmserv.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pharma.pharmserv.Services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/ms/auth")
@Tag(name = "Auth", description = "Auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/login")
    @Operation(summary = "Authenticate user", description = "Authenticates a user using userStringId and password and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User login credentials", required = true, content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = """
                    {
                      "userStringId": "admin",
                      "userPass": "password123"
                    }
                    """))) @RequestBody Map<String, String> userCredentials) {
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
