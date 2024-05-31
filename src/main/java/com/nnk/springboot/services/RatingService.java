package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {

	private static final Logger logger = LogManager.getLogger("RatingService");

	@Autowired
	private RatingRepository ratingRepository;

	public List<Rating> findAll() {
		logger.info("find list done");
		return ratingRepository.findAll();
	}

	public void add(Rating rating) {
		ratingRepository.save(rating);
		logger.info("add done");
	}

	public Rating findById(Integer id) {
		return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));

	}

	public void update(Rating rating, Integer id) throws Exception {
		ratingRepository.findById(id).map(foundRating -> {
			foundRating.setId(id);
			foundRating.setMoodysRating(rating.getMoodysRating());
			foundRating.setSandpRating(rating.getSandpRating());
			foundRating.setFitchRating(rating.getFitchRating());
			foundRating.setOrderNumber(rating.getOrderNumber());
			Rating updatedRating = ratingRepository.save(foundRating);
			logger.info("update done");
			return updatedRating;
		}).orElseThrow(() -> new Exception("Invalid rating Id:" + id));

	}

	public void delete(Integer id) {
		ratingRepository.findById(id).map(foundRating -> {
			ratingRepository.deleteById(id);
			logger.info("delete done");
			return foundRating;
		}).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));

	}

}
