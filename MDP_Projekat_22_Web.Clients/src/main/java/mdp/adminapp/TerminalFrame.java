package mdp.adminapp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.function.Function;

import javax.naming.OperationNotSupportedException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.xml.rpc.ServiceException;

import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.TerminalRegisterServiceServiceLocator;
import mdp.register.terminals.dtos.CreateTerminalDto;
import mdp.register.terminals.dtos.GetCustomsTerminalDto;
import mdp.register.terminals.dtos.UpdateTerminalDto;
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
	private static GetCustomsTerminalDto[] terminals;
	private static JTextField nameSearchTextField;

	private static DefaultTableModel clearTableData() {
		DefaultTableModel model = (DefaultTableModel) (table.getModel());
		model.setRowCount(0);
		return model;
	}

	private static void clearTerminalDialog() {
		terminalNameField.setText("");
		terminalEntryCount.setText("");
		terminalExitCount.setText("");
	}

	private static void createTerminal(CreateTerminalDto createTerminalDto) throws Exception {
		GetCustomsTerminalDto result = client.createTerminal(createTerminalDto);
		nameSearchTextField.setText(result.getName());
		searchAction(nameSearchTextField.getText());
	}

	private static void getAllTerminalsAction() throws RemoteException {
		terminals = client.getTerminals();
		setTerminalsToTableData();
	}

	private static JTable initializeTerminalTable() {
		String[] tableColumnNames = { "Id", "Name", "Entries", "Exits" };
		Object[][] initialTableData = {};
		table = new JTable(new DefaultTableModel(initialTableData, tableColumnNames) {
			private static final long serialVersionUID = 3241446797773070710L;

			@Override
			public void fireTableCellUpdated(int row, int column) {
				super.fireTableCellUpdated(row, column);
				handleTableRowUpdated(row, column);
			}
		}) {

			private static final long serialVersionUID = -628326446532233653L;

			public boolean isCellEditable(int row, int column) {
				return handleIsCellEditable(row, column);
			};
		};

		table.addMouseListener(new MouseAdapter() {
			private JPopupMenu popupMenu;

			@Override
			public void mouseReleased(MouseEvent e) {
				if (table.getSelectedRow() != -1)
					if (e.isPopupTrigger())
						handlePopupTrigger(e);

			}
		});
		table.setFillsViewportHeight(true);

		return table;
	}

	protected static void handlePopupTrigger(MouseEvent e) {
		int row = table.rowAtPoint(e.getPoint());

		if (table.isRowSelected(row))
			handleShowContextMenu(row);
	}

	private static void handleShowContextMenu(int row) {
		GetCustomsTerminalDto terminal = terminals[row];
		// TODO
		System.out.println();

	}

	protected static boolean handleIsCellEditable(int row, int column) {
		if (column == 0)
			return false;

		return true;
	}

	protected static void handleTableRowUpdated(int row, int column) {
		String setValue = table.getModel().getValueAt(row, column).toString();
		// TODO Auto-generated method stub
		// TODO send update message via client
		// TODO refetch terminals
		GetCustomsTerminalDto terminal = terminals[row];
		int entryPassageCount = terminal.getEntries().length;
		int exitPassageCount = terminal.getExits().length;
		String terminalName = terminal.getName();
		BigInteger id = terminal.getId();
		UpdateTerminalDto dto = null;
		switch (column) {
		case 1:
			dto = new UpdateTerminalDto(entryPassageCount, exitPassageCount, setValue, id);
			break;
		case 2:
			try {
				dto = new UpdateTerminalDto(Integer.valueOf(setValue), exitPassageCount, terminalName, id);
			} catch (NumberFormatException e) {
				showErrorMessage("Invalid number format for entries count");
			}
			break;
		case 3:
			try {
				dto = new UpdateTerminalDto(entryPassageCount, Integer.valueOf(setValue), terminalName, id);
			} catch (NumberFormatException e) {
				showErrorMessage("Invalid number format for exits count");
			}
			break;
		default:
			showErrorMessage("Unsupported column modified");
			return;
		}

		try {
			client.updateTerminal(dto);
		} catch (RemoteException e) {
			showErrorMessage("Update failed", "Update Error", e.getMessage());
		}
	}

	private static Function<? super GetCustomsTerminalDto, ? extends Object[]> mapTerminalToTableDataRow() {
		return t -> new Object[] { t.getId(), t.getName(), t.getEntries().length, t.getExits().length };
	}

	private static void setTerminalsToTableData() {
		if (terminals == null || terminals.length == 0) {
			clearTableData();
			return;
		}

		Object[][] tableData = Arrays.asList(terminals).stream().map(mapTerminalToTableDataRow())
				.toArray(Object[][]::new);

		setTableData(tableData);
	}

	private static void showErrorMessage(String operation, String title, String message) {
		JOptionPane.showMessageDialog(terminalFrame, String.format("Operation: %s", message), title,
				JOptionPane.ERROR_MESSAGE);
	}

	private static void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(terminalFrame, String.format("Error: %s", message), "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private static void showCreationErrorDialog(Exception e) {
		JOptionPane.showMessageDialog(terminalFrame, String.format("Failed to create terminal: %s", e.getMessage()),
				"Create Terminal Error", JOptionPane.ERROR_MESSAGE);
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
			clearTerminalDialog();
			JOptionPane.showMessageDialog(terminalFrame, "Successfully created", "Terminal created",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			showCreationErrorDialog(e);
		}
	}

	static TerminalRegisterService getTerminalClient()
			throws FileNotFoundException, IOException, OperationNotSupportedException, ServiceException {
		if (client == null)
			client = new TerminalRegisterServiceServiceLocator().getTerminalRegisterService();

		return client;
	}

	static void searchAction(String text) throws RemoteException {
		terminals = client.getTerminalsStartingWithName(text);
		setTerminalsToTableData();
	}

	static void setTableData(Object[][] tableData) {
		DefaultTableModel model = clearTableData();

		for (Object[] objects : tableData) {
			model.addRow(objects);
		}
	}

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

		JLabel nameSearchLabel = new JLabel("Terminal Name");
		nameSearchTextField = new JTextField(20);
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

		initializeTerminalTable();

		terminalFrame.add(new JScrollPane(table));

		getAllTerminalsAction();
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

}
