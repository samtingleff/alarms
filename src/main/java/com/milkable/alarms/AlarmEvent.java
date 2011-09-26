package com.milkable.alarms;

import com.mdimension.jchronic.utils.Span;

public class AlarmEvent {
	private Span time;

	private String message;

	public AlarmEvent(Span time, String message) {
		this.time = time;
		this.message = message;
	}

	public Span getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}
}
