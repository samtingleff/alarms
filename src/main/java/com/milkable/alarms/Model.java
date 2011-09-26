package com.milkable.alarms;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mdimension.jchronic.utils.Span;

public class Model {

	private List<AlarmEvent> events = new LinkedList<AlarmEvent>();

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
		events.add(ae);
		Span span = ae.getTime();
		executor.schedule(new Runnable() {
			public void run() {
				events.remove(ae);
				ctl.event(new AppEvent<AlarmEvent>(Signal.Notification, ae));
			}
		}, span.getBegin()*1000 - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
}
