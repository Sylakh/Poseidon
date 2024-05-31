package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for setting up Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * Configures the security filter chain.
	 *
	 * @param http the {@link HttpSecurity} to modify.
	 * @return the {@link SecurityFilterChain}.
	 * @throws Exception if an error occurs when configuring the filter chain.
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/user/**").hasRole("ADMIN");
			auth.requestMatchers("/app/secure/article-details").hasRole("ADMIN"); // hidden door?

			auth.anyRequest().authenticated();
		}).sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1)
				.expiredUrl("/login?session-expired=true").maxSessionsPreventsLogin(false))
				.formLogin(form -> form.defaultSuccessUrl("/", true))
				.logout(logout -> logout.logoutUrl("/app-logout").logoutSuccessUrl("/login?logout")
						.invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll())
				.exceptionHandling((exception) -> exception.accessDeniedPage("/app/error"));

		return http.build();
	}

	/**
	 * Creates a {@link PasswordEncoder} bean that uses BCrypt hashing algorithm.
	 *
	 * @return a {@link BCryptPasswordEncoder} instance.
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configures the {@link AuthenticationManager} with the custom user details
	 * service and password encoder.
	 *
	 * @param http                  the {@link HttpSecurity} to configure.
	 * @param bCryptPasswordEncoder the {@link BCryptPasswordEncoder} to use.
	 * @return the configured {@link AuthenticationManager}.
	 * @throws Exception if an error occurs when building the authentication
	 *                   manager.
	 */
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
			throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}

}
