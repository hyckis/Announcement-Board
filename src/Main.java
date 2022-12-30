// Author: YICHIN HO

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	private static Frame frame;
	public static boolean editor;

	public static void main(String[]args) {

		// 登入訊息
		int opt = JOptionPane.showConfirmDialog(null, "是否為發布者?", "登入", JOptionPane.YES_NO_CANCEL_OPTION);
		// 判斷是editor還是viewer
		if(opt == JOptionPane.YES_OPTION)
			editor = true;
		else if (opt == JOptionPane.NO_OPTION)
			editor = false;
		else
			System.exit(0);

		frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setVisible(true);

	}

}
