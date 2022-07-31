package mdp.util.ui;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mdp.adminapp.Main;

public class UiUtil {

	public static void setHalfScreenSize(JFrame frame) {
		if (Main.toolkit == null)
			Main.toolkit = Toolkit.getDefaultToolkit();
		if (Main.screenSize == null)
			Main.screenSize = Main.toolkit.getScreenSize();
		frame.setSize(Main.screenSize.width / 2, Main.screenSize.height / 2);
	}

	public static void showErrorMessage(JFrame frame, String operation, String title, String message) {
		JOptionPane.showMessageDialog(frame, String.format("Operation: %s", message), title, JOptionPane.ERROR_MESSAGE);
	}

	public static void showErrorMessage(JFrame frame, String message) {
		JOptionPane.showMessageDialog(frame, String.format("Error: %s", message), "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void showCreationErrorDialog(JFrame frame, Exception e) {
		JOptionPane.showMessageDialog(frame, String.format("Failed to create terminal: %s", e.getMessage()),
				"Create Terminal Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void showInfoMessage(JFrame frame, String message, String title) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
