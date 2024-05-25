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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/user/*").hasRole("ADMIN");

			auth.anyRequest().authenticated();
		}).sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1)
				.expiredUrl("/login?session-expired=true").maxSessionsPreventsLogin(true))
				.formLogin(form -> form.defaultSuccessUrl("/", true)).logout(logout -> logout.logoutUrl("/app-logout") // URL
																														// de
						// déconnexion
						// par défaut
						.logoutSuccessUrl("/login?logout") // Redirection après déconnexion
						.permitAll())
				.exceptionHandling((exception) -> exception.accessDeniedPage("/app/error"));

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
			throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}
	/**
	 * @Bean HttpSessionEventPublisher httpSessionEventPublisher() { return new
	 *       HttpSessionEventPublisher(); }
	 */
}
