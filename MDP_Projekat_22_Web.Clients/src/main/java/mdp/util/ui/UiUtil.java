package mdp.util.ui;

import java.awt.Toolkit;

import javax.swing.JFrame;

import mdp.adminapp.Main;

public class UiUtil {

	public static void setHalfScreenSize(JFrame frame) {
		if (Main.toolkit == null)
			Main.toolkit = Toolkit.getDefaultToolkit();
		if (Main.screenSize == null)
			Main.screenSize = Main.toolkit.getScreenSize();
		frame.setSize(Main.screenSize.width / 2, Main.screenSize.height / 2);
	}

}
