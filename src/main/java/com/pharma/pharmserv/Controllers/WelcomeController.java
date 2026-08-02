package com.pharma.pharmserv.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Application", description = "General application endpoints")
public class WelcomeController {

	@GetMapping("/ms")
	@Operation(summary = "Application status", description = "Returns a welcome message indicating the API is running.")
	public String index() {
		return "Pharmserv Microservices.";
	}

}
