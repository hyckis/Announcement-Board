import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Frame extends JFrame {
		
	private FileController fileCon;

	private boolean editMode = false;
	private Date editTime;
	
	// ��l�e��
	private JLabel headField;	// ���D
	private JLabel dateField;	// ���
	private JPanel headPanel;
	
	private JTextArea articleField;	// �峹��
	private boolean isLike = false;
	
	private JLabel heartLabel;		// �R��
	private Icon heartEmpty;		// �ŷR��
	private Icon heartFilled;		// ���R��
	private JButton editButton;		// �s����s
	private JButton newButton;		// �s�K����s
	
	// �s��e��
	private JButton saveButton;		// �x�s���s
	private JButton saveAsButton;	// �t�s���e���s
	private JButton importButton;	// �פJ���e���s
	private JButton cancelButton;	// �������s
	
	private JPanel buttonPanel;
	private JPanel allPanel;

	public Frame () {
		
		super("���i�t��");
		
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
		headField.setText("�iJA�U��");
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
		
		// ���s�]�w
		heartEmpty = new ImageIcon(getClass().getResource("unlike.png"));
		heartFilled = new ImageIcon(getClass().getResource("like.png"));
		heartLabel = new JLabel();
		LikeChange();
		editButton = new JButton("�s��");
		newButton = new JButton("���s�K��");
		// JButton Listener
		editButton.addActionListener(new ButtonListener());
		newButton.addActionListener(new ButtonListener());
		buttonPanel.add(heartLabel);
		buttonPanel.add(editButton);
		buttonPanel.add(newButton);
		allPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		// �s��ɥX�{�����s
		saveButton = new JButton("�x�s");	
		saveAsButton = new JButton("�t�s���e");
		importButton = new JButton("�פJ���e");
		cancelButton = new JButton("����");	
		// button listener
		saveButton.addActionListener(new ButtonListener());
		saveAsButton.addActionListener(new ButtonListener());
		importButton.addActionListener(new ButtonListener());
		cancelButton.addActionListener(new ButtonListener());
		
		add(allPanel);
		
		// �O�_���o����
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
			// �i�s��峹
			articleField.setBackground(Color.white);
			articleField.setEditable(true);
			// �s���s
			buttonPanel.add(saveButton);
			buttonPanel.add(saveAsButton);
			buttonPanel.add(importButton);
			buttonPanel.add(cancelButton);
		} else {
			// ���i�s��峹
			articleField.setBackground(Color.yellow);
			articleField.setEditable(false);			
			// �«��s
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