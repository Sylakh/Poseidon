package com.nnk.springboot.services;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {

	private static final Logger logger = LogManager.getLogger("CurvePointService");

	@Autowired
	private CurvePointRepository curvePointRepository;

	public Object findAll() {
		logger.info("find list done");
		return curvePointRepository.findAll();
	}

	public void add(CurvePoint curvePoint) {
		curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
		curvePoint.setCurveId(curvePoint.getCurveId());
		curvePointRepository.save(curvePoint);
		logger.info("add done");
	}

	public CurvePoint findById(Integer id) {
		return curvePointRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));

	}

	public void update(CurvePoint curvePoint, Integer id) throws Exception {
		curvePointRepository.findById(id).map(foundCurvePoint -> {
			foundCurvePoint.setId(id);
			foundCurvePoint.setTerm(curvePoint.getTerm());
			foundCurvePoint.setValue(curvePoint.getValue());
			CurvePoint updatedCurvePoint = curvePointRepository.save(foundCurvePoint);
			logger.info("update done");
			return updatedCurvePoint;
		}).orElseThrow(() -> new Exception("Invalid curvepoint Id:" + id));

	}

	public void delete(Integer id) {
		curvePointRepository.findById(id).map(foundCurvePoint -> {
			curvePointRepository.deleteById(id);
			logger.info("delete done");
			return foundCurvePoint;
		}).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));

	}

}
