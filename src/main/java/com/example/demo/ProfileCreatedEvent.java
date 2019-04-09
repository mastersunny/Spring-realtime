package com.example.demo;

import org.springframework.context.ApplicationEvent;

public class ProfileCreatedEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProfileCreatedEvent(Blog source) {
		super(source);
	}
}