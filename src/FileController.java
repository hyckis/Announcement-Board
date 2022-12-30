// Author: YICHIN HO

import java.io.*;
import java.nio.file.*;
import javax.swing.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileController {

	private static ObjectInputStream input;
	private static ObjectOutputStream output;
	private PostSerializable p = new PostSerializable();
	private String[] title;
	private List<String> titleList = new ArrayList<>();

	public FileController() {}

	// 讀入post
	public PostSerializable readPost(String fileName) {
		try {
			input = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
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

	// 讀入post.txt
	public String[] readPostTxt() {
		try {
			File filename = new File("posts.txt");
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader b = new BufferedReader(reader);
			String s;
			while((s = b.readLine()) != null)
				titleList.add(s);
			title = new String[titleList.size()];
			for (int i=0; i<titleList.size(); i++) 
				title[i] = titleList.get(i);
			return title;
		} catch (IOException e) {
			return null;
		}
	}

	// 匯入
	public void openFile(JTextArea text) {
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

	// 將標題存入posts.txt
	public void savePostTxt(JComboBox comboBox) {
		File file = new File("posts.txt");
		try {
			FileWriter write = new FileWriter(file);
			for (int i=0; i<comboBox.getItemCount(); i++) {
				write.write(comboBox.getItemAt(i).toString());
				write.write("\n");
			}
			write.close();
		} catch (IOException io) {
			System.err.println("error saving file. terminiating");
		}
	}

	// 儲存
	public void save(String content, boolean isLike, Date editTime, String fileName) {
		try {
			output = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
			p = new PostSerializable(content, isLike, editTime);
			output.writeObject(p);
			output.close();
		} catch (IOException io) {
			System.err.println("error saving file. terminiating");
			System.exit(1);
		}
	}

	// 另存
	public void saveAs(JTextArea text) {
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
