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
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class CurveController {

	private static final Logger logger = LogManager.getLogger("CurveController");

	@Autowired
	private CurvePointService curvePointService;

	@RequestMapping("/curvePoint/list")
	public String home(Model model, HttpServletRequest request) {
		String remoteUser = request.getRemoteUser();
		model.addAttribute("remoteUser", remoteUser);
		model.addAttribute("curvePoints", curvePointService.findAll());
		logger.info("Get request for list of curvePoint");
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint bid) {
		logger.info("Get request for add page");
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		logger.info("Create a new curvepoint in database");
		if (!result.hasErrors()) {
			curvePointService.add(curvePoint);
			model.addAttribute("curvePoints", curvePointService.findAll());
			return "redirect:/curvePoint/list";
		}
		return "curvePoint/add";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("Get request for update page");
		CurvePoint curvePoint = curvePointService.findById(id);
		model.addAttribute("curvePoint", curvePoint);
		return "curvePoint/update";
	}

	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) throws Exception {
		logger.info("update a curvepoint in database");
		if (result.hasErrors()) {
			return "curvePoint/update";
		}
		curvePointService.update(curvePoint, id);
		model.addAttribute("curvePoints", curvePointService.findAll());
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		logger.info("Delete a user in database");
		curvePointService.delete(id);
		model.addAttribute("curvePoints", curvePointService.findAll());
		return "redirect:/curvePoint/list";
	}
}
