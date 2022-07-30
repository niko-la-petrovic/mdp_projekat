package mdp.adminapp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;

import javax.naming.OperationNotSupportedException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.xml.rpc.ServiceException;

import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.TerminalRegisterServiceServiceLocator;
import mdp.register.terminals.dtos.CreateTerminalDto;
import mdp.register.terminals.dtos.GetCustomsTerminalDto;
import mdp.util.ui.UiUtil;

public class TerminalFrame {
	private static final JTextField terminalNameField = new JTextField();
	private static final JTextField terminalEntryCount = new JTextField();
	private static final JTextField terminalExitCount = new JTextField();
	private static final JComponent[] createTerminalDialogComponents = new JComponent[] { new JLabel("Terminal Name"),
			terminalNameField, new JLabel("Entry Count"), terminalEntryCount, new JLabel("Exit Count"),
			terminalExitCount };

	private static TerminalRegisterService client;
	private static JTable table;

	static JFrame terminalFrame;

	static void setupTerminalFrame()
			throws FileNotFoundException, OperationNotSupportedException, IOException, ServiceException {
		client = getTerminalClient();

		terminalFrame = new JFrame("Terminal Administration");
		terminalFrame.setLayout(new BoxLayout(terminalFrame.getContentPane(), BoxLayout.Y_AXIS));
		UiUtil.setHalfScreenSize(terminalFrame);
		terminalFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Main.mainFrame.setVisible(true);
			}
		});

		JPanel terminalTopPanel = new JPanel();
		JButton addButton = new JButton("Add");
		addButton.addActionListener(e -> addTerminalAction());
		terminalTopPanel.add(addButton);
		// TODO move
		// var deleteButton = new JButton("Delete");
		// TODO move
		// var editButton = new JButton("Edit");

		// terminalTopPanel.add(deleteButton);
		// terminalTopPanel.add(editButton);

		JLabel nameSearchLabel = new JLabel("Terminal Name");
		JTextField nameSearchTextField = new JTextField(20);
		terminalTopPanel.add(nameSearchLabel);
		terminalTopPanel.add(nameSearchTextField);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(e -> {
			try {
				searchAction(nameSearchTextField.getText());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		terminalTopPanel.add(searchButton);

		terminalFrame.add(terminalTopPanel);

		String[] tableColumnNames = { "Id", "Name", "Entries", "Exits" };
		Object[][] initialTableData = {};
		table = new JTable(initialTableData, tableColumnNames);
		table.setFillsViewportHeight(true);

		terminalFrame.add(new JScrollPane(table));
	}

	static void searchAction(String text) throws RemoteException {

		GetCustomsTerminalDto[] terminals = client.getTerminalsStartingWithName(text);
		Object[][] tableData = (Object[][]) Arrays.asList(terminals).stream()
				.map(t -> new Object[] { t.getId(), t.getName(), t.getEntries().length, t.getExits().length })
				.toArray();

		updateTableData(tableData);
	}

	static void updateTableData(Object[][] tableData) {
		for (int i = 0; i < tableData.length; i++) {
			Object[] objects = tableData[i];
			for (int j = 0; j < objects.length; j++) {
				Object object = objects[j];
				table.getModel().setValueAt(object, i, j);
			}
		}
	}

	static TerminalRegisterService getTerminalClient()
			throws FileNotFoundException, IOException, OperationNotSupportedException, ServiceException {
		if (client == null)
			client = new TerminalRegisterServiceServiceLocator().getTerminalRegisterService();

		return client;
	}

	static void addTerminalAction() {
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

		CreateTerminalDto createTerminalDto = new CreateTerminalDto();
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
		GetCustomsTerminalDto result = client.createTerminal(createTerminalDto);
		System.out.println();
	}

}
