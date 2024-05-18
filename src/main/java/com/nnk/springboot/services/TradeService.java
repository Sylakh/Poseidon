package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import jakarta.validation.Valid;

@Service
public class TradeService {

	private static final Logger logger = LogManager.getLogger("TradeService");

	@Autowired
	private TradeRepository tradeRepository;

	public List<Trade> findAll() {
		logger.info("find list done");
		return tradeRepository.findAll();
	}

	public void add(@Valid Trade trade) {
		trade.setTradeDate(new Timestamp(System.currentTimeMillis()));
		trade.setRevisionName("a");
		tradeRepository.save(trade);
		logger.info("add done");
	}

	public Trade findById(int id) {
		Optional<Trade> optionalTrade = tradeRepository.findById(id);
		if (optionalTrade.isPresent()) {
			logger.info("find by id done");
			return optionalTrade.get();
		} else {
			throw new IllegalArgumentException("Invalid trade Id:" + id);
		}
	}

	public void update(@Valid Trade trade, Integer id) throws Exception {
		Optional<Trade> optionalTrade = tradeRepository.findById(id);
		if (optionalTrade.isPresent()) {
			Trade foundTrade = optionalTrade.get();
			foundTrade.setTradeId(id);
			foundTrade.setAccount(trade.getAccount());
			foundTrade.setType(trade.getType());
			foundTrade.setBuyQuantity(trade.getBuyQuantity());
			foundTrade.setRevisionDate(new Timestamp(System.currentTimeMillis()));
			foundTrade.setRevisionName(incrementLetter(foundTrade.getRevisionName()));
			tradeRepository.save(foundTrade);
			logger.info("update done");
		} else {
			throw new Exception("Invalid trade Id:" + id);
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

	public void delete(Integer id) {
		Optional<Trade> optionalTrade = tradeRepository.findById(id);
		if (optionalTrade.isPresent()) {
			tradeRepository.deleteById(id);
			logger.info("delete done");
		} else {
			throw new IllegalArgumentException("Invalid trade Id:" + id);
		}
	}

}
