package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public void add(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public User findById(int id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new IllegalArgumentException("Invalid user Id:" + id);
		}
	}

	public void update(User user, int id) throws Exception {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User updateUser = optionalUser.get();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			updateUser.setUsername(user.getUsername());
			updateUser.setPassword(encoder.encode(user.getPassword()));
			updateUser.setRole(user.getRole());
			updateUser.setId(id);
			userRepository.save(updateUser);
		} else {
			throw new Exception("Invalid user Id:" + id);
		}
	}

	public void delete(int id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			userRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Invalid user Id:" + id);
		}
	}
}
