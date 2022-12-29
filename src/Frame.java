import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Frame extends JFrame {

	private FileController fileCon = new FileController();

	private boolean editMode = false;
	private Date editTime;
	private String fileName;	// 目前選到的貼文標題
	String titleName;	// 新貼文標題

	// 初始畫面
	private JLabel dateField = new JLabel();	// 日期
	private JPanel headPanel = new JPanel();	// 標題panel

	// combo box選擇貼文
	private JComboBox<String> chooseArticle;
	String[] titles;
	private int article;	// 抓combobox事件

	private JTextArea articleField = new JTextArea();	// 文章區
	private boolean isLike = false;

	private JLabel heartLabel = new JLabel();		// 愛心
	private Icon heartEmpty;		// 空愛心
	private Icon heartFilled;		// 紅愛心
	private JButton newButton = new JButton("New Post");	// 新貼文按鈕

	// 編輯畫面
	private JButton saveButton = new JButton("Save");		// 儲存按鈕
	private JButton saveAsButton = new JButton("Save as");	// 另存內容按鈕
	private JButton importButton = new JButton("Import");	// 匯入內容按鈕
	private JButton cancelButton = new JButton("Cancel");	// 取消按鈕

	private JPanel buttonPanel = new JPanel();
	private JPanel allPanel = new JPanel();

	public Frame () {

		super("公告系統");
		fileName = "HW1 Paint GUI";

		// panels
		headPanel.setBackground(Color.yellow);
		buttonPanel.setBackground(Color.yellow);
		allPanel.setLayout(new BorderLayout());

		// 日期
		dateField.setText(String.format("%s", fileCon.readPost(fileName).getEditTime()));

		// combobox
		titles = fileCon.readPostTxt();
		chooseArticle = new JComboBox<String>(titles);
		ComboBoxListener c = new ComboBoxListener();
		chooseArticle.addActionListener(c);
		
		// title區
		headPanel.add(chooseArticle);
		headPanel.add(dateField);
		allPanel.add(headPanel, BorderLayout.NORTH);

		// post
		articleField.setEditable(false);
		articleField.setBackground(Color.yellow);
		articleField.setText(String.format("%s", fileCon.readPost(fileName).getContent()));
		allPanel.add(articleField, BorderLayout.CENTER);

		// 按鈕設定
		heartEmpty = new ImageIcon(getClass().getResource("unlike.png"));
		heartFilled = new ImageIcon(getClass().getResource("like.png"));
		likeChange();

		// JButton Listener
		newButton.addActionListener(new ButtonListener());
		saveButton.addActionListener(new ButtonListener());
		saveAsButton.addActionListener(new ButtonListener());
		importButton.addActionListener(new ButtonListener());
		cancelButton.addActionListener(new ButtonListener());

		// 按鈕區
		buttonPanel.add(heartLabel);
		buttonPanel.add(newButton);
		allPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(allPanel);

		// 是否為發布者
		if (Main.editor) {
			heartLabel.setEnabled(false);
		} else {
			newButton.setVisible(false);
			heartLabel.addMouseListener(
				new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (heartLabel.getIcon() == heartEmpty) {
							isLike = true;
							fileCon.save(articleField.getText(), isLike, fileCon.readPost(fileName).getEditTime(), fileName);
						} else {
							isLike = false;
							fileCon.save(articleField.getText(), isLike, fileCon.readPost(fileName).getEditTime(), fileName);
						}
						fileCon.readPost(fileName);
						likeChange();
					}
				});
		}
	}

	// 改變模式時改變按鈕panel
	private void EditChange() {

		buttonPanel.removeAll();

		if (editMode) {
			// 可編輯文章
			articleField.setBackground(Color.white);
			articleField.setEditable(true);
			// 新按鈕
			buttonPanel.add(saveButton);
			buttonPanel.add(saveAsButton);
			buttonPanel.add(importButton);
			buttonPanel.add(cancelButton);
		} else {
			// 不可編輯文章
			articleField.setBackground(Color.yellow);
			articleField.setEditable(false);
			// 舊按鈕
			buttonPanel.add(heartLabel);
			buttonPanel.add(newButton);
		}

		buttonPanel.revalidate();
		buttonPanel.repaint();
	}
	
	// 抓愛心變化
	private void likeChange() {
		if (fileCon.readPost(fileName).getIsLike())
			heartLabel.setIcon(heartFilled);
		else if (!fileCon.readPost(fileName).getIsLike())
			heartLabel.setIcon(heartEmpty);
	}

	// JButton Listener
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			// 新貼文
			if (event.getSource() == newButton) {
				editMode = true;
				titleName = JOptionPane.showInputDialog(null, "", "Enter file name");
				if (titleName.equals(String.format("%d", JOptionPane.CANCEL_OPTION)))
					editMode = false;
				else {
					chooseArticle.addItem(titleName);
					chooseArticle.setSelectedItem(titleName);
					chooseArticle.disable();
				}				
				articleField.setText("");
				EditChange();
				return;
			}
			// 儲存
			if (event.getSource() == saveButton) {
				editTime = new Date();
				dateField.setText(String.format("%s", editTime));
				fileCon.save(articleField.getText(), false, editTime, titleName);
				fileCon.savePostTxt(chooseArticle);
				chooseArticle.enable();
				editMode = false;
				EditChange();
				return;
			}
			// 另存
			if (event.getSource() == saveAsButton) {				
				editTime = new Date();
				dateField.setText(String.format("%s", fileCon.readPost(fileName).getEditTime()));
				fileCon.saveAs(articleField);
				fileCon.save(articleField.getText(), false, editTime, titleName);
				fileCon.savePostTxt(chooseArticle);
				chooseArticle.enable();
				editMode = false;
				EditChange();			
				return;
			}
			// 匯入
			if (event.getSource() == importButton) {
				fileCon.openFile(articleField);
				return;
			}
			// 取消
			if (event.getSource() == cancelButton) {		
				articleField.setText(String.format("%s", fileCon.readPost(fileName).getContent()));
				chooseArticle.removeItemAt(chooseArticle.getItemCount()-1);
				chooseArticle.setSelectedItem(fileName);
				chooseArticle.enable();
				editMode = false;
				EditChange();				
				return;
			}
		}
	}

	// combobox listener
	private class ComboBoxListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (!editMode)
				fileName = chooseArticle.getSelectedItem().toString();
			articleField.setText("");
			articleField.setText(String.format("%s", fileCon.readPost(fileName).getContent()));
			dateField.setText("");
			dateField.setText(String.format("%s", fileCon.readPost(fileName).getEditTime()));
			likeChange();
		}
	}

}