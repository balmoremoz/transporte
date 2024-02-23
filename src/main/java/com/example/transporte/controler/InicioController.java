package com.example.transporte.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller	
public class InicioController {
	
	@GetMapping("/")
	public String inicio() {
		return "./html/index";
	}
}
