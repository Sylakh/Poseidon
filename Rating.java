package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rating")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	Integer id;

	@Column(name = "moodys_rating")
	String moodysRating;

	@Column(name = "sandP_rating")
	String sandPRating;

	@Column(name = "fitch_rating")
	String fitchRating;

	@Column(name = "order_number")
	Integer orderNumber;

}
