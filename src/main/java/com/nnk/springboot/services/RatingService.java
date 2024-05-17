package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import jakarta.validation.Valid;

@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	public List<Rating> findAll() {
		return ratingRepository.findAll();
	}

	public void add(@Valid Rating rating) {
		ratingRepository.save(rating);
	}

	public Rating findById(Integer id) {
		Optional<Rating> optionalRating = ratingRepository.findById(id);
		if (optionalRating.isPresent()) {
			return optionalRating.get();
		} else {
			throw new IllegalArgumentException("Invalid rating Id:" + id);
		}
	}

	public void update(@Valid Rating rating, Integer id) {
		Optional<Rating> optionalRating = ratingRepository.findById(id);
		if (optionalRating.isPresent()) {
			Rating foundRating = optionalRating.get();
			foundRating.setId(id);
			foundRating.setMoodysRating(rating.getMoodysRating());
			foundRating.setSandpRating(rating.getSandpRating());
			foundRating.setFitchRating(rating.getFitchRating());
			foundRating.setOrderNumber(rating.getOrderNumber());

			ratingRepository.save(foundRating);
		}

	}

	public void delete(Integer id) {
		Optional<Rating> optionalRating = ratingRepository.findById(id);
		if (optionalRating.isPresent()) {
			ratingRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Invalid rating Id:" + id);
		}

	}

}
