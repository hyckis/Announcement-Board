// Author: YICHIN HO

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Frame extends JFrame {
		
	private FileController fileCon;

	private boolean editMode = false;
	private Date editTime;
	
	// 初始畫面
	private JLabel headField;	// 標題
	private JLabel dateField;	// 日期
	private JPanel headPanel;
	
	private JTextArea articleField;	// 文章區
	private boolean isLike = false;
	
	private JLabel heartLabel;		// 愛心
	private Icon heartEmpty;		// 空愛心
	private Icon heartFilled;		// 紅愛心
	private JButton editButton;		// 編輯按鈕
	private JButton newButton;		// 新貼文按鈕
	
	// 編輯畫面
	private JButton saveButton;		// 儲存按鈕
	private JButton saveAsButton;	// 另存內容按鈕
	private JButton importButton;	// 匯入內容按鈕
	private JButton cancelButton;	// 取消按鈕
	
	private JPanel buttonPanel;
	private JPanel allPanel;

	public Frame () {
		
		super("公告系統");
		
		fileCon = new FileController();
		
		// panels
		headPanel = new JPanel();
		headPanel.setBackground(Color.yellow);
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.yellow);
		allPanel = new JPanel();
		allPanel.setLayout(new BorderLayout());
		
		// title
		headField = new JLabel();
		headField.setText("進JA助教");
		dateField = new JLabel();
		dateField.setText(String.format("%s", fileCon.ReadPost().getEditTime()));
		headPanel.add(headField);
		headPanel.add(dateField);
		allPanel.add(headPanel, BorderLayout.NORTH);
		
		// post
		articleField = new JTextArea();
		articleField.setEditable(false);
		articleField.setBackground(Color.yellow);
		articleField.setText(String.format("%s", fileCon.ReadPost().getContent()));
		allPanel.add(articleField, BorderLayout.CENTER);
		
		// 按鈕設定
		heartEmpty = new ImageIcon(getClass().getResource("unlike.png"));
		heartFilled = new ImageIcon(getClass().getResource("like.png"));
		heartLabel = new JLabel();
		LikeChange();
		editButton = new JButton("編輯");
		newButton = new JButton("全新貼文");
		// JButton Listener
		editButton.addActionListener(new ButtonListener());
		newButton.addActionListener(new ButtonListener());
		buttonPanel.add(heartLabel);
		buttonPanel.add(editButton);
		buttonPanel.add(newButton);
		allPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		// 編輯時出現的按鈕
		saveButton = new JButton("儲存");	
		saveAsButton = new JButton("另存內容");
		importButton = new JButton("匯入內容");
		cancelButton = new JButton("取消");	
		// button listener
		saveButton.addActionListener(new ButtonListener());
		saveAsButton.addActionListener(new ButtonListener());
		importButton.addActionListener(new ButtonListener());
		cancelButton.addActionListener(new ButtonListener());
		
		add(allPanel);
		
		// 是否為發布者
		if (Main.editor) {
			heartLabel.setEnabled(false);
		} else {
			editButton.setVisible(false);
			newButton.setVisible(false);
			heartLabel.addMouseListener(
				new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (heartLabel.getIcon() == heartEmpty) {
							isLike = true;
							fileCon.Save(articleField.getText(), isLike, editTime);
						} else {
							isLike = false;
							fileCon.Save(articleField.getText(), isLike, editTime);
						}
						fileCon.ReadPost();
						LikeChange();
					}
				});
		}				
	}
	
	// edit method
	private void Edit() {
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
			buttonPanel.add(editButton);
			buttonPanel.add(newButton);
		}
	}
	
	private void EditChange() {
		buttonPanel.removeAll();
		Edit();
		buttonPanel.revalidate();
		buttonPanel.repaint();
	}
	
	private void LikeChange() {
		if (fileCon.ReadPost().getIsLike())
			heartLabel.setIcon(heartFilled);
		else if (!fileCon.ReadPost().getIsLike())
			heartLabel.setIcon(heartEmpty);
	}
	
	// JButton Listener
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			
			if (event.getSource() == editButton) {
				editMode = true;
				EditChange();
				return;
			}
			if (event.getSource() == newButton) {
				editMode = true;
				articleField.setText("");
				EditChange();
				return;
			}
			if (event.getSource() == saveButton) {
				editTime = new Date();
				dateField.setText(String.format("%s", editTime));
				fileCon.Save(articleField.getText(), isLike, editTime);
				editMode = false;
				EditChange();
				return;
			}
			if (event.getSource() == saveAsButton) {
				editMode = false;
				editTime = new Date();
				dateField.setText(String.format("%s", editTime));
				fileCon.SaveAs(articleField);
				EditChange();
				return;
			}
			if (event.getSource() == importButton) {
				fileCon.OpenFile(articleField);
				return;
			}
			if (event.getSource() == cancelButton) {
				articleField.setText(String.format("%s", fileCon.ReadPost().getContent()));
				editMode = false;
				EditChange();
				return;
			}
		}
	}

}
