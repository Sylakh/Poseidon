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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

import jakarta.validation.Valid;

@Controller
public class BidListController {

	private static final Logger logger = LogManager.getLogger("BidListController");

	@Autowired
	private BidListService bidListService;

	@GetMapping("/bidList/list")
	public String home(Model model) {
		model.addAttribute("bidLists", bidListService.findAll());
		logger.info("Get request for list of Bidlist");
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(BidList bid) {
		logger.info("Get request for add page");
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		logger.info("Create a new bid in database");
		if (!result.hasErrors()) {
			bidListService.add(bid);
			model.addAttribute("bidLists", bidListService.findAll());
			return "redirect:/bidList/list";
		}
		return "bidList/add";
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("Get request for update page");
		BidList bid = bidListService.findById(id);
		model.addAttribute("bidList", bid);
		return "bidList/update";
	}

	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model)
			throws Exception {
		logger.info("update a bidlist in database");
		if (result.hasErrors()) {
			return "bidList/update";
		}
		bidListService.update(bidList, id);
		model.addAttribute("bidLists", bidListService.findAll());
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		logger.info("Delete a user in database");
		bidListService.delete(id);
		model.addAttribute("bidLists", bidListService.findAll());
		return "redirect:/bidList/list";
	}
}
