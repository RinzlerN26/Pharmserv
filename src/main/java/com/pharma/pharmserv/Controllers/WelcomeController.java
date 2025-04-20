package com.pharma.pharmserv.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping("/ms")
	public String index() {
		return "Pharmserv Microservices.";
	}

}
