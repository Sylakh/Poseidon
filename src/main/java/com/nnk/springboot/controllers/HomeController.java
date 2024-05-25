package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home(Model model, HttpServletRequest request) {
		String remoteUser = request.getRemoteUser();
		model.addAttribute("remoteUser", remoteUser);
		return "home";
	}

	@GetMapping("/admin/home")
	public String adminHome(Model model) {
		return "redirect:/bidList/list";
	}

}
