import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	private static Frame frame;
	public static boolean editor;

	public static void main(String[]args) {

		// �n�J�T��
		int opt = JOptionPane.showConfirmDialog(null, "�O�_���o����?", "�n�J", JOptionPane.YES_NO_CANCEL_OPTION);
		// �P�_�Oeditor�٬Oviewer
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
