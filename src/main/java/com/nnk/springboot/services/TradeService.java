package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {

	private static final Logger logger = LogManager.getLogger("TradeService");

	@Autowired
	private TradeRepository tradeRepository;

	public List<Trade> findAll() {
		logger.info("find list done");
		return tradeRepository.findAll();
	}

	public void add(Trade trade) {
		trade.setTradeDate(new Timestamp(System.currentTimeMillis()));
		trade.setRevisionName("a");
		tradeRepository.save(trade);
		logger.info("add done");
	}

	public Trade findById(int id) {
		return tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));

	}

	public void update(Trade trade, Integer id) throws Exception {
		tradeRepository.findById(id).map(foundTrade -> {
			foundTrade.setTradeId(id);
			foundTrade.setAccount(trade.getAccount());
			foundTrade.setType(trade.getType());
			foundTrade.setBuyQuantity(trade.getBuyQuantity());
			foundTrade.setRevisionDate(new Timestamp(System.currentTimeMillis()));
			try {
				foundTrade.setRevisionName(incrementLetter(foundTrade.getRevisionName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			Trade updatedTrade = tradeRepository.save(foundTrade);
			logger.info("update done");
			return updatedTrade;
		}).orElseThrow(() -> new Exception("Invalid trade Id:" + id));

	}

	// Méthode pour incrémenter une lettre
	public static String incrementLetter(String letter) throws Exception {
		if (letter.equals("Z")) {
			throw new Exception("too many revision"); // Exception si "Z" est atteint
		}

		int unicodeValue = letter.codePointAt(0) + 1;
		return new String(new int[] { unicodeValue }, 0, 1);
	}

	public void delete(Integer id) {
		tradeRepository.findById(id).map(foundTrade -> {
			tradeRepository.deleteById(id);
			logger.info("delete done");
			return foundTrade;
		}).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));

	}

}
