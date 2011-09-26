package com.milkable.alarms.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;
import com.milkable.alarms.AlarmEvent;

public class OneTimeAlarmWidget extends Dialog {
	private String time;

	private String message;

	private int id;

	public OneTimeAlarmWidget(Shell parent, int id) {
		super(parent);
		this.id = id;
	}

	public AlarmEvent open() {
		Shell parent = getParent();
		final Shell shell = new Shell(parent, SWT.TITLE | SWT.BORDER
				| SWT.APPLICATION_MODAL);
		shell.setText("OneTimeEventDialog");

		shell.setLayout(new GridLayout(2, true));

		Label timeLabel = new Label(shell, SWT.NULL);
		timeLabel.setText("When would you like the alarm?");

		final Text timeText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		timeText.setText("in ");
		timeText.selectAll();
		timeText.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label messageLabel = new Label(shell, SWT.NULL);
		messageLabel.setText("Event");

		final Text messageText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		messageText.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Button buttonOK = new Button(shell, SWT.PUSH);
		buttonOK.setText("Ok");
		buttonOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		final Button buttonCancel = new Button(shell, SWT.PUSH);
		buttonCancel.setText("Cancel");
		shell.setDefaultButton(buttonOK);
		timeText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				try {
					onTextFieldChange(timeText.getText(),
							messageText.getText(), buttonOK, buttonCancel);
				} catch (Exception e) {
					buttonOK.setEnabled(false);
				}
			}
		});
		messageText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				try {
					onTextFieldChange(timeText.getText(),
							messageText.getText(), buttonOK, buttonCancel);
				} catch (Exception e) {
					buttonOK.setEnabled(false);
				}
			}
		});

		buttonOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				time = timeText.getText();
				message = messageText.getText();
				shell.dispose();
			}
		});

		buttonCancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				message = null;
				shell.dispose();
			}
		});

		shell.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.TRAVERSE_ESCAPE)
					event.doit = false;
			}
		});

		messageText.setText("");
		shell.pack();
		shell.open();

		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		if ((time != null) && (time.length() > 0) && (message != null)) {
			Span span = Chronic.parse(time);
			if (span != null)
				return new AlarmEvent(id, span, message);
			else
				return null;
		} else
			return null;
	}

	private void onTextFieldChange(String time, String message,
			Button buttonOK, Button buttonCancel) {
		if ((time.length() > 0) && (message.length() > 0)
				&& (Chronic.parse(time) != null))
			buttonOK.setEnabled(true);
		else
			buttonOK.setEnabled(false);
	}
}
