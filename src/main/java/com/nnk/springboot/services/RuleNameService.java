package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

import jakarta.validation.Valid;

@Service
public class RuleNameService {

	private static final Logger logger = LogManager.getLogger("RuleNameService");

	@Autowired
	private RuleNameRepository ruleNameRepository;

	public List<RuleName> findAll() {
		logger.info("find list done");
		return ruleNameRepository.findAll();
	}

	public void add(@Valid RuleName ruleName) {
		ruleNameRepository.save(ruleName);
		logger.info("add done");
	}

	public RuleName findById(Integer id) {
		return ruleNameRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));

	}

	public void update(RuleName ruleName, Integer id) throws Exception {
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
			logger.info("update done");
		} else {
			throw new Exception("Invalid rulename Id:" + id);
		}

	}

	public void delete(Integer id) {
		Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
		if (optionalRuleName.isPresent()) {
			ruleNameRepository.deleteById(id);
			logger.info("delete done");
		} else {
			throw new IllegalArgumentException("Invalid ruleName Id:" + id);
		}

	}
}
