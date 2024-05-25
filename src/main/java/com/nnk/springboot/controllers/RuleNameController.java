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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class RuleNameController {

	private static final Logger logger = LogManager.getLogger("RuleNameController");

	@Autowired
	private RuleNameService ruleNameService;

	@GetMapping("/ruleName/list")
	public String home(Model model, HttpServletRequest request) {
		String remoteUser = request.getRemoteUser();
		model.addAttribute("remoteUser", remoteUser);
		model.addAttribute("ruleNames", ruleNameService.findAll());
		logger.info("Get request for list of rulename");
		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName bid) {
		logger.info("Get request for add page");
		return "ruleName/add";
	}

	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		logger.info("Create a new rulename in database");
		if (!result.hasErrors()) {
			ruleNameService.add(ruleName);
			model.addAttribute("ruleNames", ruleNameService.findAll());
			return "redirect:/ruleName/list";
		}
		return "ruleName/add";
	}

	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("Get request for update page");
		RuleName ruleName = ruleNameService.findById(id);
		model.addAttribute("ruleName", ruleName);
		return "ruleName/update";
	}

	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) throws Exception {
		logger.info("update a rulename in database");
		if (result.hasErrors()) {
			return "trade/update";
		}
		ruleNameService.update(ruleName, id);
		model.addAttribute("ruleNames", ruleNameService.findAll());
		return "redirect:/ruleName/list";
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		logger.info("Delete a user in database");
		ruleNameService.delete(id);
		model.addAttribute("ruleNames", ruleNameService.findAll());
		return "redirect:/ruleName/list";
	}
}
