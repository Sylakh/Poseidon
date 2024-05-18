package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import jakarta.validation.Valid;

@Service
public class BidListService {

	private static final Logger logger = LogManager.getLogger("BidListService");

	@Autowired
	private BidListRepository bidListRepository;

	public List<BidList> findAll() {
		logger.info("find list done");
		return bidListRepository.findAll();
	}

	public void add(@Valid BidList bid) {
		bid.setCreationDate(new Timestamp(System.currentTimeMillis()));
		bid.setCreationName("a");
		bid.setRevisionName("a");
		bidListRepository.save(bid);
		logger.info("add done");
	}

	public BidList findById(Integer id) {
		Optional<BidList> optionalBidList = bidListRepository.findById(id);
		if (optionalBidList.isPresent()) {
			logger.info("find by id done");
			return optionalBidList.get();
		} else {
			throw new IllegalArgumentException("Invalid BidList Id:" + id);
		}
	}

	// Méthode pour incrémenter une lettre
	public static String incrementLetter(String letter) throws Exception {
		if (letter.equals("Z")) {
			throw new Exception("too many revision"); // Exception si "Z" est atteint
		} else {
			// Convertir la lettre en son code Unicode, incrémenter, puis convertir de
			// nouveau en chaîne
			int unicodeValue = letter.codePointAt(0) + 1;
			return new String(new int[] { unicodeValue }, 0, 1);
		}
	}

	public void update(@Valid BidList bidList, Integer id) throws Exception {
		Optional<BidList> optionalBidList = bidListRepository.findById(id);
		if (optionalBidList.isPresent()) {
			BidList foundBidList = optionalBidList.get();
			foundBidList.setBidListId(id);
			foundBidList.setAccount(bidList.getAccount());
			foundBidList.setType(bidList.getType());
			foundBidList.setBidQuantity(bidList.getBidQuantity());
			foundBidList.setRevisionDate(new Timestamp(System.currentTimeMillis()));
			foundBidList.setRevisionName(incrementLetter(foundBidList.getRevisionName()));
			bidListRepository.save(foundBidList);
			logger.info("update done");
		} else {
			throw new Exception("Invalid bidlist Id:" + id);
		}

	}

	public void delete(Integer id) {
		Optional<BidList> optionalBidList = bidListRepository.findById(id);
		if (optionalBidList.isPresent()) {
			bidListRepository.deleteById(id);
			logger.info("delete done");
		} else {
			throw new IllegalArgumentException("Invalid BidList Id:" + id);
		}
	}
}
