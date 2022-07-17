package mdp.adminapp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mdp.util.ui.UiUtil;

public class Main {

	private static JFrame mainFrame;
	private static JFrame terminalFrame;
	public static Toolkit toolkit;
	public static Dimension screenSize;

	public static void main(String[] args) {
		mainFrame = new JFrame("Admin App");
		setupMainFrame(mainFrame);
	}

	private static void setupMainFrame(JFrame mainFrame) {
		UiUtil.setHalfScreenSize(mainFrame);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

		var buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		buttonPanel.setMaximumSize(new Dimension(400, 400));

		var terminalAdministrationButtton = getTerminalAdministrationButton();

		buttonPanel.add(terminalAdministrationButtton);

		mainFrame.getContentPane().add(buttonPanel);

		// TODO move to main
		mainFrame.setVisible(true);
		// TODO remove
		terminalAdministrationButtton.doClick();
	}

	private static JButton getTerminalAdministrationButton() {
		var terminalAdministrationButtton = new JButton("Terminals");

		terminalAdministrationButtton.addActionListener(e -> {
			if (terminalFrame == null) {
				setupTerminalFrame();
			}
			mainFrame.setVisible(false);
			terminalFrame.setVisible(true);
		});

		return terminalAdministrationButtton;
	}

	private static void setupTerminalFrame() {
		terminalFrame = new JFrame("Terminal Administration");
		UiUtil.setHalfScreenSize(terminalFrame);
		terminalFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});

		terminalFrame.setLayout(new FlowLayout());
		var addButton = new JButton("Add");
		var deleteButton = new JButton("Delete");
		var editButton = new JButton("Edit");
		var searchButton = new JButton("Search");

		terminalFrame.add(addButton);
		terminalFrame.add(deleteButton);
		terminalFrame.add(editButton);
		terminalFrame.add(searchButton);

		var nameSearchTextField = new JTextField(20);
		terminalFrame.add(nameSearchTextField);
	}
}
