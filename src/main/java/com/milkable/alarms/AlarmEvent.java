package com.milkable.alarms;

import com.mdimension.jchronic.utils.Span;

public class AlarmEvent {
	private int id;

	private Span time;

	private String message;

	public AlarmEvent(int id, Span time, String message) {
		this.id = id;
		this.time = time;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public Span getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}
}
