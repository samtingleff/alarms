package com.milkable.alarms;

public class Main {

	public static void main(String[] args) {
		System.out.println("main()");
		Controller ctl = new Controller();
		Model model = new Model();
		View view = new View();
		ctl.setModel(model);
		ctl.setView(view);
		model.setController(ctl);
		view.setController(ctl);
		
		ctl.run();
	}
}
