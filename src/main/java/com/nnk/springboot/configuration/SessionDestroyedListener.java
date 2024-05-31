package com.nnk.springboot.configuration;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@Component
public class SessionDestroyedListener implements HttpSessionListener {

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("Session destroyed: " + event.getSession().getId());
	}
}
