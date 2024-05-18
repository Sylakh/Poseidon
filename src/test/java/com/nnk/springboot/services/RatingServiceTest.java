package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

	@Mock
	private RatingRepository ratingRepository;

	@InjectMocks
	private RatingService ratingService;

	private Rating rating;

	@BeforeEach
	public void setup() {
		rating = new Rating(1, "moodysRating", "sandpRating", "fitchRating", 10);
	}

	@Test
	public void testFindAll() {
		List<Rating> ratings = Arrays.asList(rating);
		when(ratingRepository.findAll()).thenReturn(ratings);

		List<Rating> result = ratingService.findAll();

		assertEquals(1, result.size());
		assertEquals(rating.getMoodysRating(), result.get(0).getMoodysRating());
	}

	@Test
	public void testAdd() {
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

		ratingService.add(rating);

		verify(ratingRepository, times(1)).save(any(Rating.class));
	}

	@Test
	public void testFindById() {
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

		Rating result = ratingService.findById(1);

		assertNotNull(result);
		assertEquals(rating.getMoodysRating(), result.getMoodysRating());
	}

	@Test
	public void testFindById_NotFound() {
		when(ratingRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			ratingService.findById(1);
		});

		assertEquals("Invalid rating Id:1", exception.getMessage());
	}

	@Test
	public void testUpdate() throws Exception {
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

		Rating updatedRating = new Rating(1, "newMoodysRating", "newSandpRating", "newFitchRating", 20);
		ratingService.update(updatedRating, 1);

		verify(ratingRepository, times(1)).save(any(Rating.class));
		assertEquals("newMoodysRating", rating.getMoodysRating());
	}

	@Test
	public void testUpdate_NotFound() {
		when(ratingRepository.findById(1)).thenReturn(Optional.empty());

		Rating updatedRating = new Rating(1, "newMoodysRating", "newSandpRating", "newFitchRating", 20);

		Exception exception = assertThrows(Exception.class, () -> {
			ratingService.update(updatedRating, 1);
		});

		assertEquals("Invalid rating Id:1", exception.getMessage());
	}

	@Test
	public void testDelete() {
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		doNothing().when(ratingRepository).deleteById(1);

		ratingService.delete(1);

		verify(ratingRepository, times(1)).deleteById(1);
	}

	@Test
	public void testDelete_NotFound() {
		when(ratingRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			ratingService.delete(1);
		});

		assertEquals("Invalid rating Id:1", exception.getMessage());
	}
}
