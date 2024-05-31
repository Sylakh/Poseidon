package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserService {

	private static final Logger logger = LogManager.getLogger("UserService");

	@Autowired
	private UserRepository userRepository;

	public List<DBUser> findAll() {
		logger.info("find list done");
		return userRepository.findAll();
	}

	public void add(DBUser user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		logger.info("add done");
	}

	public DBUser findById(int id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

	}

	public void update(DBUser user, int id) throws Exception {
		userRepository.findById(id).map(foundUser -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			foundUser.setUsername(user.getUsername());
			foundUser.setPassword(encoder.encode(user.getPassword()));
			foundUser.setRole(user.getRole());
			foundUser.setId(id);
			userRepository.save(foundUser);
			logger.info("update done");
			return foundUser;
		}).orElseThrow(() -> new Exception("Invalid user Id:" + id));

	}

	public void delete(int id) {
		Optional<DBUser> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			userRepository.deleteById(id);
			logger.info("delete done");
		} else {
			throw new IllegalArgumentException("Invalid user Id:" + id);
		}
	}
}
