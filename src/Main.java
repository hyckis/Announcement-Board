// �K�X�n�J�\��G�K�X��123

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	
	private static Frame frame;
	public static boolean editor;
	
	public static void main(String[]args) {	
		
		// �n�J�T��
		int opt = JOptionPane.showConfirmDialog(null, "�O�_���o����?", "�n�J", JOptionPane.YES_NO_CANCEL_OPTION);
		// �P�_�Oeditor�٬Oviewer
		if(opt == JOptionPane.YES_OPTION) {
			// �K�X
			String password = JOptionPane.showInputDialog("��J�K�X: ");
			if (password.equals("123")) {
				JOptionPane.showMessageDialog(null, "�n�J���\", "�K�X���T", JOptionPane.PLAIN_MESSAGE);
				editor = true;
			} else {
				JOptionPane.showMessageDialog(null, "�K�X���~", "���~", JOptionPane.ERROR_MESSAGE);
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
