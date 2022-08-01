package mdp.adminapp;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import mdp.util.Util;
import mdp.util.ui.UiUtil;

public class CredentialsFrame {
	static JFrame frame;
	private static JTextField usernameSearchTextField;
	private static JTable table;

	public static void setupCredentialsFrame() {
		frame = new JFrame("Credentials");
		UiUtil.setHalfScreenSize(frame);

		Container contentPane = frame.getContentPane();
		frame.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Main.mainFrame.setVisible(true);
			}
		});

		JPanel credentialsTopPanel = new JPanel();
		JButton addButton = new JButton("Add");
		addButton.addActionListener(e -> addCredentialsAction());
		credentialsTopPanel.add(addButton);

		JLabel nameSearchLabel = new JLabel("Username");
		usernameSearchTextField = new JTextField(20);
		credentialsTopPanel.add(nameSearchLabel);
		credentialsTopPanel.add(usernameSearchTextField);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(e -> {
			searchAction(usernameSearchTextField.getText());
		});
		credentialsTopPanel.add(searchButton);

		frame.add(credentialsTopPanel);

		initializeCredentialsTable();

		frame.add(new JScrollPane(table));

		getAllCredentialsAction();
	}

	private static void getAllCredentialsAction() {
		URL url;
		try {
			url = new URL(String.format("http://%s/api/credentials", Main.settings.getCredentialsServerHost()));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();

			int responseCode = connection.getResponseCode();
			if (!mdp.util.client.Util.isSuccessStatusCode(responseCode)) {
// TODO show error				
				return;
			}

			try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static JTable initializeCredentialsTable() {
		String[] tableColumnNames = { "Username" };
		Object[][] initialTableData = {};

		table = new JTable(new DefaultTableModel(initialTableData, tableColumnNames) {
			private static final long serialVersionUID = 8051783058407579702L;

			@Override
			public void fireTableCellUpdated(int row, int column) {
				super.fireTableCellUpdated(row, column);
				handleTableCellUpdated(row, column);
			}

		}) {

			private static final long serialVersionUID = 3831450219107374062L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return handleIsCellEditable(row, column);
			}

		};

		table.addMouseListener(new MouseAdapter() {
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

	protected static boolean handleIsCellEditable(int row, int column) {
		return true;
	}

	protected static void handleTableCellUpdated(int row, int column) {
		if (column == 0) {

		}
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

		JMenuItem setNewPassword = new JMenuItem("New Password");
		setNewPassword.addActionListener(evt -> handleSetNewPasswordAction(row));

		popup.add(delete);
		popup.show(e.getComponent(), e.getX(), e.getY());
	}

	private static Object handleSetNewPasswordAction(int row) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void handleDeleteAction(int row) {
		// TODO Auto-generated method stub
	}

	private static void searchAction(String text) {
		// TODO Auto-generated method stub

	}

	private static void addCredentialsAction() {
		// TODO Auto-generated method stub
	}

}
