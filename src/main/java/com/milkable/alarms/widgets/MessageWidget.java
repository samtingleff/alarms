package com.milkable.alarms.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageWidget {
	private Display parent;

	private String message;

	public MessageWidget(Display parent, String message) {
		this.parent = parent;
		this.message = message;
	}

	public void open() {
		try {
			Shell shell = new Shell(parent);
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION
					| SWT.YES | SWT.NO);
			messageBox.setMessage(message);
			int rc = messageBox.open();
			shell.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
