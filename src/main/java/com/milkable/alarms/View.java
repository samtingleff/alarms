package com.milkable.alarms;

import java.util.LinkedList;
import java.util.List;

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

	private List<SubMenuItem> activeAlarmItems = new LinkedList<SubMenuItem>();

	private Controller ctl;

	private Display display;

	private Shell shell;

	private Menu menu;

	private Menu submenu;

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
		menu = new Menu(shell, SWT.POP_UP);

		// Add one-time alarm
		MenuItem oneTimeAlarm = new MenuItem(menu, SWT.PUSH);
		oneTimeAlarm.setText("Add one-time alarm...");
		oneTimeAlarm.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ctl.event(new AppEvent<Object>(Signal.AddOneTimeAlarm, null));
			}
		});

		// Active alarms
		final MenuItem alarmMenuItem = new MenuItem(menu, SWT.CASCADE);
		alarmMenuItem.setText("&Active alarms");
		submenu = new Menu(shell, SWT.DROP_DOWN);
		alarmMenuItem.setMenu(submenu);

		// Quit menu item
		MenuItem quit = new MenuItem(menu, SWT.PUSH);
		quit.setText("Quit");
		quit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ctl.event(new AppEvent<Object>(Signal.Quit, null));
			}
		});
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

	public AlarmEvent showOneTimeDialog(int id) {
		OneTimeAlarmWidget w = new OneTimeAlarmWidget(shell, id);
		AlarmEvent ae = w.open();
		return ae;
	}

	public void addEvent(AlarmEvent ae) {
		final MenuItem childItem = new MenuItem(submenu, SWT.PUSH);
		childItem.setText(ae.getTime().toString());
		activeAlarmItems.add(new SubMenuItem(childItem, ae.getId()));
	}

	public void removeEvent(AlarmEvent ae) {
		for (final SubMenuItem item : activeAlarmItems) {
			if (item.id == ae.getId()) {
				display.asyncExec(new Runnable() {
					public void run() {
						item.item.dispose();
					}
				});
			}
		}
	}

	public void showMessage(final String msg) {
		display.syncExec(new Runnable() {
			public void run() {
				try {
					MessageWidget mw = new MessageWidget(display, msg);
					mw.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static class SubMenuItem {
		public MenuItem item;

		public int id;

		public SubMenuItem(MenuItem item, int id) {
			this.item = item;
			this.id = id;
		}
	}
}
