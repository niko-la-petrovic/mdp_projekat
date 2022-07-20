package mdp.adminapp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.dtos.CreateTerminalDto;
import mdp.util.ui.UiUtil;

public class Main {

	private static JFrame mainFrame;
	private static JFrame terminalFrame;
	public static Toolkit toolkit;
	public static Dimension screenSize;

	private static final JTextField terminalNameField = new JTextField();
	private static final JTextField terminalEntryCount = new JTextField();
	private static final JTextField terminalExitCount = new JTextField();
	private static final JComponent[] createTerminalDialogComponents = new JComponent[] { new JLabel("Terminal Name"),
			terminalNameField, new JLabel("Entry Count"), terminalEntryCount, new JLabel("Exit Count"),
			terminalExitCount };

	private static TerminalRegisterService client;

	public static void main(String[] args) {
		mainFrame = new JFrame("Admin App");
		setupMainFrame(mainFrame);
	}

	private static void addTerminalAction() {
		int result = JOptionPane.showConfirmDialog(terminalFrame, createTerminalDialogComponents, "Create Terminal",
				JOptionPane.PLAIN_MESSAGE);

		if (result != JOptionPane.OK_OPTION)
			return;

		String terminalName;
		int entryCount;
		int exitCount;
		try {
			terminalName = terminalNameField.getText();
			if (terminalName == null || terminalName.length() < 1)
				throw new Exception("Terminal name cannot be empty.");
			entryCount = Integer.valueOf(terminalEntryCount.getText());
			exitCount = Integer.valueOf(terminalExitCount.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(terminalFrame, "Number format error: " + e.getMessage(),
					"Failed to create terminal", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception e) {
			showCreationErrorDialog(e);
			return;
		}

		// TODO change dto type
		var createTerminalDto = new CreateTerminalDto();
		createTerminalDto.setTerminalName(terminalName);
		createTerminalDto.setEntryPassageCount(entryCount);
		createTerminalDto.setExitPassageCount(exitCount);

		try {
			createTerminal(createTerminalDto);
			terminalNameField.setText("");
			terminalEntryCount.setText("");
			terminalExitCount.setText("");
		} catch (Exception e) {
			showCreationErrorDialog(e);
		}
	}

	private static void showCreationErrorDialog(Exception e) {
		JOptionPane.showMessageDialog(terminalFrame, e.getMessage(), "Failed to create terminal",
				JOptionPane.ERROR_MESSAGE);
	}

	private static void createTerminal(CreateTerminalDto createTerminalDto) throws Exception {
		// TODO use remote service
		var client = getTerminalClient();
		var result = client.createTerminal(createTerminalDto);
		System.out.println();
	}

	private static TerminalRegisterService getTerminalClient() throws FileNotFoundException, IOException {
		if (client == null)
			client = new TerminalRegisterService();

		return client;
	}

	private static JButton getTerminalAdministrationButton() {
		var terminalAdministrationButtton = new JButton("Terminals");

		terminalAdministrationButtton.addActionListener(e -> {
			if (terminalFrame == null) {
				setupTerminalFrame();
			}
			mainFrame.setVisible(false);
			terminalFrame.setVisible(true);
			// TODO remove
			terminalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		});

		return terminalAdministrationButtton;
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

	private static void setupTerminalFrame() {
		terminalFrame = new JFrame("Terminal Administration");
		terminalFrame.setLayout(new BoxLayout(terminalFrame.getContentPane(), BoxLayout.Y_AXIS));
		UiUtil.setHalfScreenSize(terminalFrame);
		terminalFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});

		var terminalTopPanel = new JPanel();
		var addButton = new JButton("Add");
		addButton.addActionListener(e -> addTerminalAction());
		// TODO move
//		var deleteButton = new JButton("Delete");
		// TODO move
//		var editButton = new JButton("Edit");
		var searchButton = new JButton("Search");

		terminalTopPanel.add(addButton);
//		terminalTopPanel.add(deleteButton);
//		terminalTopPanel.add(editButton);

		var nameSearchLabel = new JLabel("Terminal Name");
		var nameSearchTextField = new JTextField(20);
		terminalTopPanel.add(nameSearchLabel);
		terminalTopPanel.add(nameSearchTextField);
		terminalTopPanel.add(searchButton);

		terminalFrame.add(terminalTopPanel);

		String[] tableColumnNames = { "Id", "Name", "Entries", "Exits" };
		Object[][] initialTableData = {};
		var table = new JTable(initialTableData, tableColumnNames);
		table.setFillsViewportHeight(true);

		terminalFrame.add(new JScrollPane(table));
	}
}
