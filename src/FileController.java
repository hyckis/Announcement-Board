// Author: YICHIN HO

import java.io.*;
import java.nio.file.*;
import javax.swing.*;
import java.util.*;

public class FileController {
	
	private static ObjectInputStream input;
	private static ObjectOutputStream output;
	private PostSerializable p;
	
	public FileController() {
		p = new PostSerializable();
	}
	
	// 讀入post
	public PostSerializable ReadPost() {
		try {
			input = new ObjectInputStream(Files.newInputStream(Paths.get("post")));
			p = (PostSerializable) input.readObject();
			input.close();
		} catch (IOException e) {
			System.err.println("error opening file");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.err.print("error class not found");
			System.exit(1);
		}		
		return p;
	}
	
	// 匯入
	public void OpenFile(JTextArea text) {		
		File file = getOpenFile();
		Scanner write;	
		String s;		
		if (file != null) {
			try {
				write = new Scanner(file);
				s = "";
				while (write.hasNextLine()) {				
					s += write.nextLine();
					s += "\n";
				}
				text.setText(s);
				write.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 儲存
	public void Save(String content, boolean isLike, Date editTime) {	
		try {
			output = new ObjectOutputStream(Files.newOutputStream(Paths.get("post")));
			p = new PostSerializable(content, isLike, editTime);
			output.writeObject(p);
			output.close();
		} catch (IOException io) {
			System.err.println("error saving file. terminiating");
			System.exit(1);
		}
	}

	// 另存
	public void SaveAs(JTextArea text) {
		File file = getSaveFile();
		if (file != null) {
			try{				
				FileWriter write = new FileWriter(file);
				write.write(text.getText());
				write.close();
			} catch (IOException e) {
				System.err.println("error saving as file");
			}
		}		
	}
	
	// 取得開啟檔案路徑
	private File getOpenFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int opt = fileChooser.showOpenDialog(null);
		if (opt == JFileChooser.CANCEL_OPTION)
			return null;
		else
			return fileChooser.getSelectedFile();		
	}
	
	// 取得儲存檔案路徑
	private File getSaveFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int opt = fileChooser.showSaveDialog(null);
		if (opt == JFileChooser.CANCEL_OPTION)
			return null;
		else
			return fileChooser.getSelectedFile();		
	}
}
