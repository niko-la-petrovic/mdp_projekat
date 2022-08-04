package mdp.adminapp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.OperationNotSupportedException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.xml.rpc.ServiceException;

import mdp.exceptions.TerminalNotFoundException;
import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.TerminalRegisterServiceServiceLocator;
import mdp.register.terminals.dtos.CreateTerminalDto;
import mdp.register.terminals.dtos.GetCustomsPassageDto;
import mdp.register.terminals.dtos.GetCustomsTerminalDto;
import mdp.register.terminals.dtos.UpdateTerminalDto;
import mdp.util.ui.UiUtil;

public class TerminalFrame {
	private static final Logger logger = Logger.getLogger(TerminalFrame.class.getName());

	private static final JTextField terminalNameField = new JTextField();
	private static final JTextField terminalEntryCount = new JTextField();
	private static final JTextField terminalExitCount = new JTextField();
	private static final JComponent[] createTerminalDialogComponents = new JComponent[] { new JLabel("Terminal Name"),
			terminalNameField, new JLabel("Entry Count"), terminalEntryCount, new JLabel("Exit Count"),
			terminalExitCount };

	private static TerminalRegisterService terminalRegisterClient;
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
		GetCustomsTerminalDto result = terminalRegisterClient.createTerminal(createTerminalDto);
		nameSearchTextField.setText(result.getName());
		searchAction(nameSearchTextField.getText());
	}

	private static void getAllTerminalsAction() throws RemoteException {
		terminals = terminalRegisterClient.getTerminals();
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
				handleTableCellUpdated(row, column);
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
			handleShowContextMenu(e, row);
	}

	private static void handleShowContextMenu(MouseEvent e, int row) {
		JPopupMenu popup = new JPopupMenu("Actions");

		JMenuItem delete = new JMenuItem("Delete");
		delete.addActionListener(evt -> handleDeleteAction(row));

		JMenuItem openNotification = new JMenuItem("Open");
		openNotification.addActionListener(evt -> handleInputPassageId(row));

		popup.add(delete);
		popup.add(openNotification);
		popup.show(e.getComponent(), e.getX(), e.getY());
	}

	private static void handleInputPassageId(int row) {
		GetCustomsTerminalDto terminal = terminals[row];
		BigInteger terminalId = terminal.getId();

		ArrayList<String> passageIdList = new ArrayList<>();
		for (GetCustomsPassageDto passage : terminal.getEntries()) {
			passageIdList.add(passage.getId().toString());
		}
		for (GetCustomsPassageDto passage : terminal.getExits()) {
			passageIdList.add(passage.getId().toString());
		}

		Object[] passageIdOptions = passageIdList.toArray(new Object[passageIdList.size()]);
		if (passageIdOptions.length == 0)
			UiUtil.showErrorMessage(terminalFrame, "Terminal has no passages");

		String selection = (String) JOptionPane.showInputDialog(terminalFrame, "Select the passage ID",
				"Open Terminal Passages", JOptionPane.PLAIN_MESSAGE, null, passageIdOptions, passageIdOptions[0]);

		try {
			BigInteger selectedId = new BigInteger(selection);
		} catch (Exception e) {
			UiUtil.showErrorMessage(terminalFrame, "Selecting passage ID", "Error Selecting Passage", e.getMessage());
			logger.log(Level.SEVERE, String.format("Error selecting passage: %s", e.getMessage()));
		}

		handleSendOpenNotification(terminalId, terminalId);
	}

	private static void handleSendOpenNotification(BigInteger terminalId, BigInteger passageId) {
		String host = Main.settings.getNotificationSocketHost();
		int port = Main.settings.getNotificationSocketPort();
		try {
			Socket socket = new Socket(host, port);
			try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {
				out.writeObject(terminalId);
				out.writeObject(passageId);
				String result = (String) in.readObject();
				JOptionPane.showMessageDialog(terminalFrame, result);
			} catch (ClassNotFoundException e) {
				UiUtil.showErrorMessage(terminalFrame, "Implementation error",
						"Failed to communicate with notification server socket", e.getMessage());
				logger.log(Level.SEVERE,
						String.format("Failed to communicate with notification socket server: %s", e.getMessage()));
			}
		} catch (IOException e) {
			UiUtil.showErrorMessage(terminalFrame, "Sending open notification error", "Open Terminal Error",
					e.getMessage());
			logger.log(Level.SEVERE, String.format("Sending open notification error: %s", e.getMessage()));
		}
	}

	private static void handleDeleteAction(int row) {
		GetCustomsTerminalDto terminal = terminals[row];

		try {
			terminalRegisterClient.deleteTerminal(terminal.getId());
			removeTerminalGui(terminal.getId(), row);
		} catch (TerminalNotFoundException e) {
			UiUtil.showErrorMessage(terminalFrame, "Terminal delete error", "Terminal Delete Error",
					"Terminal with specified ID doesn't exist");
			logger.log(Level.SEVERE, "Terminal with specified ID doesn't exist");
		} catch (RemoteException e) {
			UiUtil.showErrorMessage(terminalFrame, "Terminal delete error", "Terminal Delete Error", e.getMessage());
			logger.log(Level.SEVERE, String.format("Terminal delete error: %s", e.getMessage()));
		}
	}

	private static void removeTerminalGui(BigInteger id, int row) {
		ArrayList<GetCustomsTerminalDto> tempTerminals = new ArrayList<GetCustomsTerminalDto>(Arrays.asList(terminals));
		tempTerminals.remove(row);
		terminals = tempTerminals.toArray(new GetCustomsTerminalDto[tempTerminals.size()]);
		removeTableRow(row);
	}

	private static void removeTableRow(int row) {
		((DefaultTableModel) table.getModel()).removeRow(row);
	}

	protected static boolean handleIsCellEditable(int row, int column) {
		if (column == 0)
			return false;

		return true;
	}

	protected static void handleTableCellUpdated(int row, int column) {
		String setValue = table.getModel().getValueAt(row, column).toString();
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
				UiUtil.showErrorMessage(terminalFrame, "Invalid number format for entries count");
				logger.log(Level.SEVERE, "Invalid number format for entries count");
			}
			break;
		case 3:
			try {
				dto = new UpdateTerminalDto(entryPassageCount, Integer.valueOf(setValue), terminalName, id);
			} catch (NumberFormatException e) {
				UiUtil.showErrorMessage(terminalFrame, "Invalid number format for exits count");
				logger.log(Level.SEVERE, "Invalid number format for exits count");
			}
			break;
		default:
			UiUtil.showErrorMessage(terminalFrame, "Unsupported column modified");
			logger.log(Level.SEVERE, "Unsupported column modified");
			return;
		}

		try {
			terminalRegisterClient.updateTerminal(dto);
		} catch (RemoteException e) {
			UiUtil.showErrorMessage(terminalFrame, "Update failed", "Update Error", e.getMessage());
			logger.log(Level.SEVERE, String.format("Update terminal error: %s", e.getMessage()));
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
			logger.log(Level.SEVERE, String.format("Number format error: %s", e.getMessage()));
			return;
		} catch (Exception e) {
			UiUtil.showCreationErrorDialog(terminalFrame, e);
			logger.log(Level.SEVERE, String.format("Generic exception: %s", e.getMessage()));
			return;
		}

		CreateTerminalDto createTerminalDto = new CreateTerminalDto();
		createTerminalDto.setTerminalName(terminalName);
		createTerminalDto.setEntryPassageCount(entryCount);
		createTerminalDto.setExitPassageCount(exitCount);

		try {
			createTerminal(createTerminalDto);
			clearTerminalDialog();
			UiUtil.showInfoMessage(terminalFrame, "Successfully created", "Terminal created");
		} catch (Exception e) {
			UiUtil.showCreationErrorDialog(terminalFrame, e);
			logger.log(Level.SEVERE, String.format("Failed to create terminal: %s", e.getMessage()));
		}
	}

	static TerminalRegisterService getTerminalClient()
			throws FileNotFoundException, IOException, OperationNotSupportedException, ServiceException {
		if (terminalRegisterClient == null)
			terminalRegisterClient = new TerminalRegisterServiceServiceLocator().getTerminalRegisterService();

		return terminalRegisterClient;
	}

	static void searchAction(String text) throws RemoteException {
		terminals = terminalRegisterClient.getTerminalsStartingWithName(text);
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
		terminalRegisterClient = getTerminalClient();

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
				logger.log(Level.SEVERE, String.format("Failed to conduct search: %s", e1.getMessage()));
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
