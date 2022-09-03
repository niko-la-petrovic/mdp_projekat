package mdp.util.ui;

import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

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

	public static String getUsername(JFrame frame) {
		String username = JOptionPane.showInputDialog(frame, "Enter username", "Credentials Username",
				JOptionPane.QUESTION_MESSAGE);
		return username;
	}

	public static String getPassword(JFrame frame) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter password");
		JPasswordField pass = new JPasswordField(30);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] { "OK", "Cancel" };
		int option = JOptionPane.showOptionDialog(frame, panel, "Credentials Password", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if (option != 0)
			return null;

		return new String(pass.getPassword());
	}

	public static File getSaveDirectory(JFrame frame) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Select save directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
			return fileChooser.getSelectedFile();
		else
			return null;
	}
}
