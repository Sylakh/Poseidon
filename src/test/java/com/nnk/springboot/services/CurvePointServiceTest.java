package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

	@Mock
	private CurvePointRepository curvePointRepository;

	@InjectMocks
	private CurvePointService curvePointService;

	private CurvePoint curvePoint;

	@BeforeEach
	public void setup() {
		curvePoint = new CurvePoint(1, 10, new Timestamp(System.currentTimeMillis()), 5.0, 10.0,
				new Timestamp(System.currentTimeMillis()));
	}

	@Test
	public void testFindAll() {
		List<CurvePoint> curvePoints = Arrays.asList(curvePoint);
		when(curvePointRepository.findAll()).thenReturn(curvePoints);

		List<CurvePoint> result = (List<CurvePoint>) curvePointService.findAll();

		assertEquals(1, result.size());
		assertEquals(curvePoint.getCurveId(), result.get(0).getCurveId());
	}

	@Test
	public void testAdd() {
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

		curvePointService.add(curvePoint);

		verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
		assertNotNull(curvePoint.getCreationDate());
	}

	@Test
	public void testFindById() {
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

		CurvePoint result = curvePointService.findById(1);

		assertNotNull(result);
		assertEquals(curvePoint.getCurveId(), result.getCurveId());
	}

	@Test
	public void testFindById_NotFound() {
		when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			curvePointService.findById(1);
		});

		assertEquals("Invalid curvePoint Id:1", exception.getMessage());
	}

	@Test
	public void testUpdate() throws Exception {
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

		CurvePoint updatedCurvePoint = new CurvePoint(1, 10, new Timestamp(System.currentTimeMillis()), 5.0, 20.0,
				new Timestamp(System.currentTimeMillis()));
		curvePointService.update(updatedCurvePoint, 1);

		verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
		assertEquals(20.0, curvePoint.getValue());
	}

	@Test
	public void testUpdate_NotFound() {
		when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

		CurvePoint updatedCurvePoint = new CurvePoint(1, 10, new Timestamp(System.currentTimeMillis()), 5.0, 20.0,
				new Timestamp(System.currentTimeMillis()));

		Exception exception = assertThrows(Exception.class, () -> {
			curvePointService.update(updatedCurvePoint, 1);
		});

		assertEquals("Invalid curvepoint Id:1", exception.getMessage());
	}

	@Test
	public void testDelete() {
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		doNothing().when(curvePointRepository).deleteById(1);

		curvePointService.delete(1);

		verify(curvePointRepository, times(1)).deleteById(1);
	}

	@Test
	public void testDelete_NotFound() {
		when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			curvePointService.delete(1);
		});

		assertEquals("Invalid curvePoint Id:1", exception.getMessage());
	}
}
