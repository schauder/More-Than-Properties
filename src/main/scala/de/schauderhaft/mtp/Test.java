package de.schauderhaft.mtp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Test {

	public void test() {
		new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new JButton().doClick();
			}
		};
	}
}
