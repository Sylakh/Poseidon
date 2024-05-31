package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {

	private static final Logger logger = LogManager.getLogger("BidListService");

	@Autowired
	private BidListRepository bidListRepository;

	public List<BidList> findAll() {
		logger.info("find list done");
		return bidListRepository.findAll();
	}

	public void add(BidList bid) {
		bid.setCreationDate(new Timestamp(System.currentTimeMillis()));
		bid.setCreationName("a");
		bid.setRevisionName("a");
		bidListRepository.save(bid);
		logger.info("add done");
	}

	public BidList findById(Integer id) {
		return bidListRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id:" + id));
	}

	// Méthode pour incrémenter une lettre
	public static String incrementLetter(String letter) throws Exception {
		if (letter.equals("Z")) {
			throw new Exception("too many revision"); // Exception si "Z" est atteint
		}

		int unicodeValue = letter.codePointAt(0) + 1;
		return new String(new int[] { unicodeValue }, 0, 1);
	}

	public void update(BidList bidList, Integer id) throws Exception {
		bidListRepository.findById(id).map(foundBidList -> {
			foundBidList.setBidListId(id);
			foundBidList.setAccount(bidList.getAccount());
			foundBidList.setType(bidList.getType());
			foundBidList.setBidQuantity(bidList.getBidQuantity());
			foundBidList.setRevisionDate(new Timestamp(System.currentTimeMillis()));
			try {
				foundBidList.setRevisionName(incrementLetter(foundBidList.getRevisionName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			BidList updatedBidList = bidListRepository.save(foundBidList);
			logger.info("update done");
			return updatedBidList;
		}).orElseThrow(() -> new Exception("Invalid bidlist Id:" + id));

	}

	public void delete(Integer id) {

		bidListRepository.findById(id).map(foundBidList -> {
			bidListRepository.deleteById(id);
			logger.info("delete done");
			return foundBidList;
		}).orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id:" + id));
	}
}
