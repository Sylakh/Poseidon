package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	Integer id;

	@NotNull(message = "must not be null")
	@Column(name = "curve_id")
	Integer curveId;

	@Column(name = "as_of_date")
	Timestamp asOfDate;

	@Column(name = "term")
	Double term;

	@Column(name = "value")
	Double value;

	@Column(name = "creation_date")
	Timestamp creationDate;

}
