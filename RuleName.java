package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rulename")
public class RuleName {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	Integer id;

	@Column(name = "name")
	String name;

	@Column(name = "description")
	String description;

	@Column(name = "json")
	String json;

	@Column(name = "template")
	String template;

	@Column(name = "sql_str")
	String sqlStr;

	@Column(name = "sql_part")
	String sqlPart;
}
