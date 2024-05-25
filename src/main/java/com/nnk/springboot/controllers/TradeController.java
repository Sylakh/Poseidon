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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class TradeController {

	private static final Logger logger = LogManager.getLogger("TradeController");

	@Autowired
	private TradeService tradeService;

	@GetMapping("/trade/list")
	public String home(Model model, HttpServletRequest request) {
		model.addAttribute("trades", tradeService.findAll());
		String remoteUser = request.getRemoteUser();
		// Authentication authentication =
		// SecurityContextHolder.getContext().getAuthentication();
		// String remoteUser = authentication.getName();
		model.addAttribute("remoteUser", remoteUser);
		logger.info("Get request for list of trade");
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addUser(Trade bid) {
		logger.info("Get request for add page");
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		logger.info("Create a new trade in database");
		if (!result.hasErrors()) {
			tradeService.add(trade);
			model.addAttribute("trades", tradeService.findAll());
			return "redirect:/trade/list";
		}
		return "trade/add";
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("Get request for update page");
		Trade trade = tradeService.findById(id);
		model.addAttribute("trade", trade);
		return "trade/update";
	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model)
			throws Exception {
		logger.info("update a trade in database");
		if (result.hasErrors()) {
			return "trade/update";
		}
		tradeService.update(trade, id);
		model.addAttribute("trades", tradeService.findAll());
		return "redirect:/trade/list";
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		logger.info("Delete a user in database");
		tradeService.delete(id);
		model.addAttribute("trades", tradeService.findAll());
		return "redirect:/trade/list";
	}
}
