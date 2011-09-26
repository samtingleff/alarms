package com.milkable.alarms;

public class Controller implements Runnable {
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
				AlarmEvent ae = view.showOneTimeDialog();
				model.addEvent(ae);
				break;
			case Notification:
				view.showMessage(((AppEvent<String>)event).getPayload());
				break;
			case Quit:
				System.exit(0);
				break;
			default:
				break;
		}
	}
}
