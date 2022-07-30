package mdp.adminapp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.naming.OperationNotSupportedException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.xml.rpc.ServiceException;

import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.dtos.CreateTerminalDto;
import mdp.util.ui.UiUtil;

public class Main {

	static JFrame mainFrame;
	public static Toolkit toolkit;
	public static Dimension screenSize;

	public static void main(String[] args) {
		mainFrame = new JFrame("Admin App");
		setupMainFrame(mainFrame);
		mainFrame.setVisible(true);
	}

	private static JButton getTerminalAdministrationButton() {
		JButton terminalAdministrationButtton = new JButton("Terminals");

		terminalAdministrationButtton.addActionListener(e -> {
			if (TerminalFrame.terminalFrame == null) {
				try {
					TerminalFrame.setupTerminalFrame();
				} catch (OperationNotSupportedException | IOException | ServiceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			TerminalFrame.terminalFrame.setVisible(true);
			mainFrame.setVisible(false);
		});

		return terminalAdministrationButtton;
	}

	private static void setupMainFrame(JFrame mainFrame) {
		UiUtil.setHalfScreenSize(mainFrame);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		buttonPanel.setMaximumSize(new Dimension(400, 400));

		JButton terminalAdministrationButtton = getTerminalAdministrationButton();

		buttonPanel.add(terminalAdministrationButtton);

		mainFrame.getContentPane().add(buttonPanel);

		// TODO remove
//		terminalAdministrationButtton.doClick();
	}
}
