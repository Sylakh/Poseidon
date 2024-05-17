package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import jakarta.validation.Valid;

@Service
public class CurvePointService {

	@Autowired
	private CurvePointRepository curvePointRepository;

	public Object findAll() {
		return curvePointRepository.findAll();
	}

	public void add(@Valid CurvePoint curvePoint) {
		curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
		curvePointRepository.save(curvePoint);
	}

	public CurvePoint findById(Integer id) {
		Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
		if (optionalCurvePoint.isPresent()) {
			return optionalCurvePoint.get();
		} else {
			throw new IllegalArgumentException("Invalid curvePoint Id:" + id);
		}
	}

	public void update(@Valid CurvePoint curvePoint, Integer id) {
		Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
		if (optionalCurvePoint.isPresent()) {
			CurvePoint foundCurvePoint = optionalCurvePoint.get();
			foundCurvePoint.setId(id);
			foundCurvePoint.setTerm(curvePoint.getTerm());
			foundCurvePoint.setValue(curvePoint.getValue());

			curvePointRepository.save(foundCurvePoint);
		}
	}

	public void delete(Integer id) {
		Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
		if (optionalCurvePoint.isPresent()) {
			curvePointRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Invalid curvePoint Id:" + id);
		}

	}

}
