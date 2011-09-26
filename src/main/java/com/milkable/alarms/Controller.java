package com.milkable.alarms;

import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Runnable {
	private AtomicInteger ai = new AtomicInteger();

	private Model model;

	private View view;

	public void setModel(Model model) {
		this.model = model;
	}

	public void setView(View view) {
		this.view = view;
	}

	public void run() {
		model.init();
		view.init();
	}

	public void event(AppEvent event) {
		switch (event.getSignal()) {
		case AddOneTimeAlarm:
			AlarmEvent ae = view.showOneTimeDialog(ai.incrementAndGet());
			if (ae != null) {
				model.addEvent(ae);
				view.addEvent(ae);
			}
			break;
		case Notification:
			view.showMessage(((AppEvent<AlarmEvent>) event).getPayload().getMessage());
			view.removeEvent(((AppEvent<AlarmEvent>) event).getPayload());
			break;
		case Quit:
			System.exit(0);
			break;
		default:
			break;
		}
	}
}
