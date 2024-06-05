package com.nnk.springboot.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@Component
public class SessionDestroyedListener implements HttpSessionListener {

	private static final Logger logger = LogManager.getLogger("SessionDestroyedListener");

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		logger.info("Session destroyed: " + event.getSession().getId());
	}
}
