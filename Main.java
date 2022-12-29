
// 105403506資管3A何宜親

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

    private static Frame frame;
    public static boolean editor;

    public static void main(String[]args) {	
		
		// 使用者身分確認
		int opt = JOptionPane.showConfirmDialog(null, "是否為編輯者?", "用戶確認", JOptionPane.YES_NO_CANCEL_OPTION);
		// editor٬ viewer
		if(opt == JOptionPane.YES_OPTION) {
			// editor
			String password = JOptionPane.showInputDialog("請輸入密碼: ");
			if (password.equals("123")) {
				JOptionPane.showMessageDialog(null, "歡迎!", "確認為編輯者", JOptionPane.PLAIN_MESSAGE);
				editor = true;
			} else {
				JOptionPane.showMessageDialog(null, "請重新登入", "錯誤", JOptionPane.ERROR_MESSAGE);
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
