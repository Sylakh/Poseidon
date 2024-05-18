package com.nnk.springboot.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import jakarta.validation.Valid;

@Service
public class CurvePointService {

	private static final Logger logger = LogManager.getLogger("CurvePointService");

	@Autowired
	private CurvePointRepository curvePointRepository;

	public Object findAll() {
		logger.info("find list done");
		return curvePointRepository.findAll();
	}

	public void add(@Valid CurvePoint curvePoint) {
		curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
		curvePointRepository.save(curvePoint);
		logger.info("add done");
	}

	public CurvePoint findById(Integer id) {
		Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
		if (optionalCurvePoint.isPresent()) {
			logger.info("find by id done");
			return optionalCurvePoint.get();
		} else {
			throw new IllegalArgumentException("Invalid curvePoint Id:" + id);
		}
	}

	public void update(@Valid CurvePoint curvePoint, Integer id) throws Exception {
		Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
		if (optionalCurvePoint.isPresent()) {
			CurvePoint foundCurvePoint = optionalCurvePoint.get();
			foundCurvePoint.setId(id);
			foundCurvePoint.setTerm(curvePoint.getTerm());
			foundCurvePoint.setValue(curvePoint.getValue());
			curvePointRepository.save(foundCurvePoint);
			logger.info("update done");
		} else {
			throw new Exception("Invalid curvepoint Id:" + id);
		}
	}

	public void delete(Integer id) {
		Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
		if (optionalCurvePoint.isPresent()) {
			curvePointRepository.deleteById(id);
			logger.info("delete done");
		} else {
			throw new IllegalArgumentException("Invalid curvePoint Id:" + id);
		}

	}

}
