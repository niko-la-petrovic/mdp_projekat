package mdp.clientapp;

import java.awt.Dimension;
import java.math.BigInteger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mdp.dtos.SearchTerminalDto;
import mdp.test.client.TestSoapServiceService;
import mdp.util.ui.UiUtil;

public class Main {
	// TODO unnecessary throws declarations
	public static void main(String[] args) throws Exception {
		var mainFrame = new JFrame("Client App");
		setupMainFrame(mainFrame);
	}

	private static void setupMainFrame(JFrame mainFrame) {
		UiUtil.setHalfScreenSize(mainFrame);

		var terminalPanel = new JPanel();
		terminalPanel.setBorder(BorderFactory.createTitledBorder("Terminal"));
		terminalPanel.setMaximumSize(new Dimension(400, 400));

		var passageIdPanel = new JPanel();
		var passageIdLabel = new JLabel("Terminal Passage Id");
		var passageIdTextField = new JTextField(20);
		passageIdPanel.add(passageIdLabel);
		passageIdPanel.add(passageIdTextField);

		var customsOrPolicePanel = new JPanel();
		var customsOrPoliceLabel = new JLabel("Customs/Police step");
		var customsOrPoliceCheckbox = new JCheckBox();
		customsOrPolicePanel.add(customsOrPoliceLabel);
		customsOrPolicePanel.add(customsOrPoliceCheckbox);

		var terminalNamePanel = new JPanel();
		var terminalNameLabel = new JLabel("Terminal Name");
		var terminalNameTextField = new JTextField(20);
		terminalNamePanel.add(terminalNameLabel);
		terminalNamePanel.add(terminalNameTextField);

		var terminalSubmitButton = new JButton("Submit");
		terminalSubmitButton.addActionListener(e -> {
			try {
				var passageId = BigInteger.valueOf(Long.valueOf(passageIdTextField.getText()));
				var terminalName = terminalNameTextField.getText();
				var isCustomsPassage = customsOrPoliceCheckbox.isSelected();

				var getDto = new SearchTerminalDto(passageId, terminalName, isCustomsPassage);
				var locator = new TestSoapServiceService();
				var client = locator.getTestSoapService();

				var result = client.result();
				// TODO soap message

			} catch (NumberFormatException ex) {
				// TODO ex
				ex.printStackTrace();
				// TODO dialog error
			}
		});

		terminalPanel.add(passageIdPanel);
		terminalPanel.add(customsOrPolicePanel);
		terminalPanel.add(terminalNamePanel);
		terminalPanel.add(terminalSubmitButton);
		mainFrame.add(terminalPanel);
		mainFrame.setVisible(true);
	}

}
