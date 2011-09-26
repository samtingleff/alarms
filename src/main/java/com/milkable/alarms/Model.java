package com.milkable.alarms;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mdimension.jchronic.utils.Span;

public class Model {
	private Controller ctl;

	private ScheduledThreadPoolExecutor executor;

	public Model() {
	}

	public void setController(Controller controller) {
		this.ctl = controller;
	}

	public void init() {
		executor = new ScheduledThreadPoolExecutor(2);
	}

	public void addEvent(final AlarmEvent ae) {
		Span span = ae.getTime();
		executor.schedule(new Runnable() {
			public void run() {
				ctl.event(new AppEvent<String>(Signal.Notification, ae.getMessage()));
			}
		}, span.getBegin()*1000 - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
}
