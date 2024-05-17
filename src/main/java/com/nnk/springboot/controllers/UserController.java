package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user/list")
	public String home(Model model) {
		model.addAttribute("users", userService.findAll());
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUser(User bid) {
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String validate(@Valid User user, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			userService.add(user);
			model.addAttribute("users", userService.findAll());
			return "redirect:/user/list";
		}
		return "user/add";
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = userService.findById(id);
		user.setPassword("");
		model.addAttribute("user", user);
		return "user/update";
	}

	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model)
			throws Exception {
		if (result.hasErrors()) {
			return "user/update";
		}
		userService.update(user, id);
		model.addAttribute("users", userService.findAll());
		return "redirect:/user/list";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		userService.delete(id);
		model.addAttribute("users", userService.findAll());
		return "redirect:/user/list";
	}
}
