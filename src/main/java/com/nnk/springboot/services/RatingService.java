package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import jakarta.validation.Valid;

@Service
public class RatingService {

	private static final Logger logger = LogManager.getLogger("RatingService");

	@Autowired
	private RatingRepository ratingRepository;

	public List<Rating> findAll() {
		logger.info("find list done");
		return ratingRepository.findAll();
	}

	public void add(@Valid Rating rating) {
		ratingRepository.save(rating);
		logger.info("add done");
	}

	public Rating findById(Integer id) {
		Optional<Rating> optionalRating = ratingRepository.findById(id);
		if (optionalRating.isPresent()) {
			logger.info("find by id done");
			return optionalRating.get();
		} else {
			throw new IllegalArgumentException("Invalid rating Id:" + id);
		}
	}

	public void update(@Valid Rating rating, Integer id) throws Exception {
		Optional<Rating> optionalRating = ratingRepository.findById(id);
		if (optionalRating.isPresent()) {
			Rating foundRating = optionalRating.get();
			foundRating.setId(id);
			foundRating.setMoodysRating(rating.getMoodysRating());
			foundRating.setSandpRating(rating.getSandpRating());
			foundRating.setFitchRating(rating.getFitchRating());
			foundRating.setOrderNumber(rating.getOrderNumber());
			ratingRepository.save(foundRating);
			logger.info("update done");
		} else {
			throw new Exception("Invalid rating Id:" + id);
		}

	}

	public void delete(Integer id) {
		Optional<Rating> optionalRating = ratingRepository.findById(id);
		if (optionalRating.isPresent()) {
			ratingRepository.deleteById(id);
			logger.info("delete done");
		} else {
			throw new IllegalArgumentException("Invalid rating Id:" + id);
		}

	}

}
