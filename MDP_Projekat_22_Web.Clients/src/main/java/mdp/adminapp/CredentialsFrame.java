package mdp.adminapp;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import mdp.dtos.GetCredentialsDto;
import mdp.dtos.PostCredentialsDto;
import mdp.dtos.PutCredentialsDto;
import mdp.util.client.HttpUtil;
import mdp.util.ui.UiUtil;

public class CredentialsFrame {
private static final Logger logger = Logger.getLogger(CredentialsFrame.class.getName());

	static JFrame frame;
	private static Gson gson = new Gson();
	private static JTextField usernameSearchTextField;
	private static JTable table;
	private static GetCredentialsDto[] credentials;

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
		addButton.addActionListener(e -> handleCreateCredentialsAction());
		credentialsTopPanel.add(addButton);

		JLabel nameSearchLabel = new JLabel("Username");
		usernameSearchTextField = new JTextField(20);
		credentialsTopPanel.add(nameSearchLabel);
		credentialsTopPanel.add(usernameSearchTextField);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(e -> {
			handleSearchAction(usernameSearchTextField.getText());
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
			connection.setRequestProperty("Accept", "application/json");
			connection.connect();

			int responseCode = connection.getResponseCode();
			if (!mdp.util.client.HttpUtil.isSuccessStatusCode(responseCode)) {
				UiUtil.showErrorMessage(frame, "Fetching users", "Error Fetching Users", String.valueOf(responseCode));
				return;
			}

			String json = HttpUtil.getJson(connection);

			credentials = gson.fromJson(json, GetCredentialsDto[].class);
			setCredentialsToTableData();
		} catch (IOException e) {
			UiUtil.showErrorMessage(frame, "Fetching users", "Error Fetching Users", e.getMessage());
			logger.log(Level.SEVERE, String.format("IO Exception: %s", e.getMessage()));
		}
	}

	private static void setCredentialsToTableData() {
		if (credentials == null || credentials.length == 0) {
			clearTableData();
			return;
		}

		Object[][] tableData = Arrays.asList(credentials).stream().map(extracted()).toArray(Object[][]::new);
		setTableData(tableData);
	}

	private static Function<? super GetCredentialsDto, ? extends Object[]> extracted() {
		return c -> new Object[] { c.getUsername() };
	}

	private static DefaultTableModel clearTableData() {
		DefaultTableModel model = (DefaultTableModel) (table.getModel());
		model.setRowCount(0);
		return model;
	}

	private static void setTableData(Object[][] tableData) {
		DefaultTableModel model = clearTableData();

		for (Object[] object : tableData)
			model.addRow(object);
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
		setNewPassword.addActionListener(evt -> handleUpdatePasswordAction(row));

		popup.add(delete);
		popup.add(setNewPassword);
		popup.show(e.getComponent(), e.getX(), e.getY());
	}

	private static void handleUpdatePasswordAction(int row) {
		GetCredentialsDto userCreds = credentials[row];

		String password = UiUtil.getPassword(frame);
		if (password == null)
			return;

		URL url;
		try {
			url = new URL(String.format("http://%s/api/credentials", Main.settings.getCredentialsServerHost()));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestMethod("PUT");
			connection.setDoOutput(true);
			connection.connect();

			PutCredentialsDto dto = new PutCredentialsDto(userCreds.getUsername(), password);
			String json = gson.toJson(dto);
			HttpUtil.sendJson(connection, json);

			int responseCode = connection.getResponseCode();
			boolean successStatusCode = mdp.util.client.HttpUtil.isSuccessStatusCode(responseCode);
			if (successStatusCode) {
				UiUtil.showInfoMessage(frame,
						String.format("Successfully updated password for user '%s'", dto.getUsername()), "Success");
			} else if (responseCode == 404) {
				UiUtil.showErrorMessage(frame, "Updating password", "Error Updating Password", "User doesn't exist");
				logger.log(Level.SEVERE, String.format("HTTP 404: User doesn't exist"));
			}
		} catch (IOException e) {
			UiUtil.showErrorMessage(frame, "Updating user password", "Error Updating User Password", e.getMessage());
			logger.log(Level.SEVERE, String.format("IO Exception: %s", e.getMessage()));
		}
	}

	private static void handleDeleteAction(int row) {
		GetCredentialsDto userCreds = credentials[row];
		URL url;
		try {
			url = new URL(String.format("http://%s/api/credentials?username=%s",
					Main.settings.getCredentialsServerHost(),
					URLEncoder.encode(userCreds.getUsername(), java.nio.charset.StandardCharsets.UTF_8.toString())));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");

			connection.connect();
			int responseCode = connection.getResponseCode();
			if (responseCode == 404) {
				UiUtil.showErrorMessage(frame, "Deleting user", "Error Deleting User", "User not found");
				logger.log(Level.SEVERE, String.format("HTTP 404: User doesn't exist"));

				return;
			} else if (!mdp.util.client.HttpUtil.isSuccessStatusCode(responseCode)) {
				UiUtil.showErrorMessage(frame, "Deleting user", "Error Deleting User", String.valueOf(responseCode));
				logger.log(Level.SEVERE, String.format("Error deleting user: HTTP %s", responseCode));

				return;
			}

			UiUtil.showInfoMessage(frame, String.format("Successfully deleted user '%s'", userCreds.getUsername()),
					"Delete User Success");
			removeCredentialsFromGui(userCreds.getUsername(), row);
		} catch (IOException e) {
			UiUtil.showErrorMessage(frame, "Deleting user", "Error Deleting User", e.getMessage());
			logger.log(Level.SEVERE, String.format("Error deleting user - IO Exception: %s", e.getMessage()));
		}
	}

	private static void removeCredentialsFromGui(String username, int row) {
		ArrayList<GetCredentialsDto> tempCredentials = new ArrayList<>(Arrays.asList(credentials));
		tempCredentials.remove(row);
		credentials = tempCredentials.toArray(new GetCredentialsDto[tempCredentials.size()]);
		removeTableRow(row);
	}

	private static void removeTableRow(int row) {
		((DefaultTableModel) table.getModel()).removeRow(row);
	}

	private static void handleSearchAction(String text) {
		URL url;
		try {
			url = new URL(String.format("http://%s/api/credentials/search?username=%s",
					Main.settings.getCredentialsServerHost(),
					URLEncoder.encode(text, java.nio.charset.StandardCharsets.UTF_8.toString())));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.connect();

			int responseCode = connection.getResponseCode();
			if (!mdp.util.client.HttpUtil.isSuccessStatusCode(responseCode)) {
				UiUtil.showErrorMessage(frame, "Fetching users", "Error Fetching Users", String.valueOf(responseCode));
				return;
			}

			String json = HttpUtil.getJson(connection);
			credentials = gson.fromJson(json, GetCredentialsDto[].class);
			setCredentialsToTableData();
		} catch (IOException e) {
			UiUtil.showErrorMessage(frame, "Fetching users", "Error Fetching Users", e.getMessage());
			logger.log(Level.SEVERE, String.format("Error fetching users - IO Exception: %s", e.getMessage()));
		}
	}

	private static void handleCreateCredentialsAction() {
		String username = UiUtil.getUsername(frame);
		if (username == null || username.equals(""))
			return;

		String password = UiUtil.getPassword(frame);
		if (password == null)
			return;
		PostCredentialsDto dto = new PostCredentialsDto(username, password);

		URL url;
		try {
			url = new URL(String.format("http://%s/api/credentials", Main.settings.getCredentialsServerHost()));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.addRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			connection.connect();
			String json = gson.toJson(dto);
			HttpUtil.sendJson(connection, json);

			int responseCode = connection.getResponseCode();
			boolean successStatusCode = mdp.util.client.HttpUtil.isSuccessStatusCode(responseCode);
			if (successStatusCode) {
				UiUtil.showInfoMessage(frame, String.format("Successfully created user %s", username), "Success");
				usernameSearchTextField.setText(username);
				handleSearchAction(username);
			} else if (responseCode == 409){
				UiUtil.showErrorMessage(frame, "Creating user", "Error Creating User", "Username already in use");
				logger.log(Level.SEVERE, String.format("Username is already in use"));
			}
		} catch (IOException e) {
			UiUtil.showErrorMessage(frame, "Creating user", "Failed to create user", e.getMessage());
			logger.log(Level.SEVERE, String.format("Error creating user - IO Exception: %s", e.getMessage()));
		}
	}

}
