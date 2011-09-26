package com.milkable.alarms;

public class AppEvent<T> {
	private Signal signal;

	private T payload;

	public AppEvent(Signal signal, T payload) {
		this.signal = signal;
		this.payload = payload;
	}

	public Signal getSignal() {
		return signal;
	}

	public T getPayload() {
		return payload;
	}
}
