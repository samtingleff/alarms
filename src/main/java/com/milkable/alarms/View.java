package com.milkable.alarms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import com.milkable.alarms.widgets.MessageWidget;
import com.milkable.alarms.widgets.OneTimeAlarmWidget;

public class View {
	private Controller ctl;
    Display display;
    Shell shell;
	public View() {
	}
	public void setController(Controller controller) {
		this.ctl = controller;
	}

	public void init() {
		display = new Display();
		shell = new Shell(display);
	    Image image = new Image(display, 16, 16);
	    final Tray tray = display.getSystemTray();
	    final TrayItem item = new TrayItem(tray, SWT.NONE);
	      item.setToolTipText("Alarms");
	      item.addListener(SWT.Show, new Listener() {
	        public void handleEvent(Event event) {
	        }
	      });
	      final Menu menu = new Menu(shell, SWT.POP_UP);
	      
	      // Add one-time alarm
	      MenuItem oneTimeAlarm = new MenuItem(menu, SWT.PUSH);
	      oneTimeAlarm.setText("Add one-time alarm...");
	      oneTimeAlarm.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ctl.event(new AppEvent<Object>(Signal.AddOneTimeAlarm, null));
			} });
	      // Quit menu item
	      MenuItem quit = new MenuItem(menu, SWT.PUSH);
	      quit.setText("Quit");
	      quit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ctl.event(new AppEvent<Object>(Signal.Quit, null));
			} });
	      item.addListener(SWT.MenuDetect, new Listener() {
	        public void handleEvent(Event event) {
	          menu.setVisible(true);
	        }
	      });
	      item.setImage(image);
	      while (!shell.isDisposed()) {
	          if (!display.readAndDispatch())
	            display.sleep();
	        }
	      image.dispose();
	      display.dispose();
	}

	public AlarmEvent showOneTimeDialog() {
		OneTimeAlarmWidget w = new OneTimeAlarmWidget(shell);
		AlarmEvent ae = w.open();
		return ae;
	}
	public void showMessage(final String msg) {
		display.asyncExec(new Runnable() {
			public void run() {
				try {
				MessageWidget mw = new MessageWidget(display, msg);
				mw.open();
				} catch(Exception e) {
					e.printStackTrace();
				}
			} });
	}
}
