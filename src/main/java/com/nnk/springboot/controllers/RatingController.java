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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

import jakarta.validation.Valid;

@Controller
public class RatingController {

	private static final Logger logger = LogManager.getLogger("RatingController");

	@Autowired
	private RatingService ratingService;

	@GetMapping("/rating/list")
	public String home(Model model) {
		model.addAttribute("ratings", ratingService.findAll());
		logger.info("Get request for list of rating");
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {
		logger.info("Get request for add page");
		return "rating/add";
	}

	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		logger.info("Create a new rating in database");
		if (!result.hasErrors()) {
			ratingService.add(rating);
			model.addAttribute("ratings", ratingService.findAll());
			return "redirect:/rating/list";
		}
		return "rating/add";
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("Get request for update page");
		Rating rating = ratingService.findById(id);
		model.addAttribute("rating", rating);
		return "rating/update";
	}

	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model)
			throws Exception {
		logger.info("update a rating in database");
		if (result.hasErrors()) {
			return "rating/update";
		}
		ratingService.update(rating, id);
		model.addAttribute("ratings", ratingService.findAll());
		return "redirect:/rating/list";
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		logger.info("Delete a user in database");
		ratingService.delete(id);
		model.addAttribute("ratings", ratingService.findAll());
		return "redirect:/rating/list";
	}
}
