package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private DBUser user;

	@BeforeEach
	public void setup() {
		user = new DBUser(1, "username", "password", "fullname", "role");
	}

	@Test
	public void testFindAll() {
		List<DBUser> users = Arrays.asList(user);
		when(userRepository.findAll()).thenReturn(users);

		List<DBUser> result = userService.findAll();

		assertEquals(1, result.size());
		assertEquals(user.getUsername(), result.get(0).getUsername());
	}

	@Test
	public void testAdd() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		when(userRepository.save(any(DBUser.class))).thenReturn(user);

		userService.add(user);

		verify(userRepository, times(1)).save(any(DBUser.class));
		assertTrue(encoder.matches("password", user.getPassword()));
	}

	@Test
	public void testFindById() {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));

		DBUser result = userService.findById(1);

		assertNotNull(result);
		assertEquals(user.getUsername(), result.getUsername());
	}

	@Test
	public void testFindById_NotFound() {
		when(userRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			userService.findById(1);
		});

		assertEquals("Invalid user Id:1", exception.getMessage());
	}

	@Test
	public void testUpdate() throws Exception {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userRepository.save(any(DBUser.class))).thenReturn(user);

		DBUser updatedUser = new DBUser(1, "newUsername", "newPassword", "fullname", "role");
		userService.update(updatedUser, 1);

		verify(userRepository, times(1)).save(any(DBUser.class));
		assertEquals("newUsername", user.getUsername());
	}

	@Test
	public void testUpdate_NotFound() {
		when(userRepository.findById(1)).thenReturn(Optional.empty());

		DBUser updatedUser = new DBUser(1, "newUsername", "newPassword", "fullname", "role");

		Exception exception = assertThrows(Exception.class, () -> {
			userService.update(updatedUser, 1);
		});

		assertEquals("Invalid user Id:1", exception.getMessage());
	}

	@Test
	public void testDelete() {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		doNothing().when(userRepository).deleteById(1);

		userService.delete(1);

		verify(userRepository, times(1)).deleteById(1);
	}

	@Test
	public void testDelete_NotFound() {
		when(userRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			userService.delete(1);
		});

		assertEquals("Invalid user Id:1", exception.getMessage());
	}
}
