package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "bidlist")
public class BidList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bidlist_id")
	Integer BidListId;

	@NotBlank(message = "account is mandatory")
	@Column(name = "account")
	String account;

	@NotBlank(message = "type is mandatory")
	@Column(name = "type")
	String type;

	@Column(name = "bid_quantity")
	Double bidQuantity;

	@Column(name = "ask_quantity")
	Double askQuantity;

	@Column(name = "bid")
	Double bid;

	@Column(name = "ask")
	Double ask;

	@Column(name = "benchmark")
	String benchmark;

	@Column(name = "bidlist_date")
	Timestamp bidListDate;

	@Column(name = "commentary")
	String commentary;

	@Column(name = "security")
	String security;

	@Column(name = "status")
	String status;

	@Column(name = "trader")
	String trader;

	@Column(name = "book")
	String book;

	@Column(name = "creation_name")
	String creationName;

	@Column(name = "creation_date")
	Timestamp creationDate;

	@Column(name = "revision_name")
	String revisionName;

	@Column(name = "revision_date")
	Timestamp revisionDate;

	@Column(name = "deal_name")
	String dealName;

	@Column(name = "deal_type")
	String dealType;

	@Column(name = "sourcelist_id")
	String sourceListId;

	@Column(name = "side")
	String side;

}
