package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

	@Mock
	private RuleNameRepository ruleNameRepository;

	@InjectMocks
	private RuleNameService ruleNameService;

	private RuleName ruleName;

	@BeforeEach
	public void setup() {
		ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
	}

	@Test
	public void testFindAll() {
		List<RuleName> ruleNames = Arrays.asList(ruleName);
		when(ruleNameRepository.findAll()).thenReturn(ruleNames);

		List<RuleName> result = ruleNameService.findAll();

		assertEquals(1, result.size());
		assertEquals(ruleName.getName(), result.get(0).getName());
	}

	@Test
	public void testAdd() {
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

		ruleNameService.add(ruleName);

		verify(ruleNameRepository, times(1)).save(any(RuleName.class));
	}

	@Test
	public void testFindById() {
		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

		RuleName result = ruleNameService.findById(1);

		assertNotNull(result);
		assertEquals(ruleName.getName(), result.getName());
	}

	@Test
	public void testFindById_NotFound() {
		when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			ruleNameService.findById(1);
		});

		assertEquals("Invalid ruleName Id:1", exception.getMessage());
	}

	@Test
	public void testUpdate() throws Exception {
		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

		RuleName updatedRuleName = new RuleName(1, "newName", "newDescription", "json", "template", "sqlStr",
				"sqlPart");
		ruleNameService.update(updatedRuleName, 1);

		verify(ruleNameRepository, times(1)).save(any(RuleName.class));
		assertEquals("newName", ruleName.getName());
	}

	@Test
	public void testUpdate_NotFound() {
		when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

		RuleName updatedRuleName = new RuleName(1, "newName", "newDescription", "json", "template", "sqlStr",
				"sqlPart");

		Exception exception = assertThrows(Exception.class, () -> {
			ruleNameService.update(updatedRuleName, 1);
		});

		assertEquals("Invalid rulename Id:1", exception.getMessage());
	}

	@Test
	public void testDelete() {
		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
		doNothing().when(ruleNameRepository).deleteById(1);

		ruleNameService.delete(1);

		verify(ruleNameRepository, times(1)).deleteById(1);
	}

	@Test
	public void testDelete_NotFound() {
		when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			ruleNameService.delete(1);
		});

		assertEquals("Invalid ruleName Id:1", exception.getMessage());
	}
}
