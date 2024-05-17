package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

import jakarta.validation.Valid;

@Service
public class RuleNameService {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	public List<RuleName> findAll() {
		return ruleNameRepository.findAll();
	}

	public void add(@Valid RuleName ruleName) {
		ruleNameRepository.save(ruleName);
	}

	public RuleName findById(Integer id) {
		Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
		if (optionalRuleName.isPresent()) {
			return optionalRuleName.get();
		} else {
			throw new IllegalArgumentException("Invalid ruleName Id:" + id);
		}
	}

	public void update(@Valid RuleName ruleName, Integer id) {
		Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
		if (optionalRuleName.isPresent()) {
			RuleName foundRuleName = optionalRuleName.get();
			foundRuleName.setId(id);
			foundRuleName.setName(ruleName.getName());
			foundRuleName.setDescription(ruleName.getDescription());
			foundRuleName.setJson(ruleName.getJson());
			foundRuleName.setTemplate(ruleName.getTemplate());
			foundRuleName.setSqlStr(ruleName.getSqlStr());
			foundRuleName.setSqlPart(ruleName.getSqlPart());

			ruleNameRepository.save(foundRuleName);
		}

	}

	public void delete(Integer id) {
		Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
		if (optionalRuleName.isPresent()) {
			ruleNameRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Invalid ruleName Id:" + id);
		}

	}
}
