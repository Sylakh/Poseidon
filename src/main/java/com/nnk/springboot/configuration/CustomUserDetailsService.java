package com.nnk.springboot.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.repositories.UserRepository;

/**
 * Custom service for managing user details. Implements the
 * {@link UserDetailsService} interface from Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Loads a user by its username.
	 *
	 * @param username The username of the user.
	 * @return A {@link UserDetails} object containing the user's details.
	 * @throws UsernameNotFoundException if no user is found with the provided
	 *                                   username.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<DBUser> optionalUser = userRepository.findByUsername(username);
		DBUser user = optionalUser.get();
		return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
	}

	/**
	 * Returns the list of authorities granted to a user based on their role.
	 *
	 * @param role The user's role.
	 * @return A list of {@link GrantedAuthority}.
	 */
	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}
}