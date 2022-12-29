// 密碼登入功能：密碼為123

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	
	private static Frame frame;
	public static boolean editor;
	
	public static void main(String[]args) {	
		
		// 登入訊息
		int opt = JOptionPane.showConfirmDialog(null, "是否為發布者?", "登入", JOptionPane.YES_NO_CANCEL_OPTION);
		// 判斷是editor還是viewer
		if(opt == JOptionPane.YES_OPTION) {
			// 密碼
			String password = JOptionPane.showInputDialog("輸入密碼: ");
			if (password.equals("123")) {
				JOptionPane.showMessageDialog(null, "登入成功", "密碼正確", JOptionPane.PLAIN_MESSAGE);
				editor = true;
			} else {
				JOptionPane.showMessageDialog(null, "密碼錯誤", "錯誤", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} else if (opt == JOptionPane.NO_OPTION)
			editor = false;
		else
			System.exit(0);
		
		frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setVisible(true);		
			
	}	

}
