package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

	private static final Logger logger = LogManager.getLogger("UserController");

	@Autowired
	private UserService userService;

	@GetMapping("/user/list")
	public String home(Model model) {
		model.addAttribute("users", userService.findAll());
		logger.info("Get request for list of user");
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUser(Model model, DBUser user) {
		model.addAttribute("dbUser", new DBUser());
		logger.info("Get request for add page");
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String validate(@Valid DBUser user, BindingResult result, Model model) {
		logger.info("Create a new user in database");
		if (!result.hasErrors()) {
			userService.add(user);
			model.addAttribute("users", userService.findAll());
			return "redirect:/user/list";
		}
		return "user/add";
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("Get request for update page");
		DBUser user = userService.findById(id);
		user.setPassword("");
		model.addAttribute("user", user);
		return "user/update";
	}

	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid DBUser user, BindingResult result, Model model)
			throws Exception {
		logger.info("update a user in database");
		if (result.hasErrors()) {
			return "user/update";
		}
		userService.update(user, id);
		model.addAttribute("users", userService.findAll());
		return "redirect:/user/list";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		logger.info("Delete a user in database");
		userService.delete(id);
		model.addAttribute("users", userService.findAll());
		return "redirect:/user/list";
	}
}
