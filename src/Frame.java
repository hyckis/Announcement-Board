import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Frame extends JFrame {

	private FileController fileCon = new FileController();

	private boolean editMode = false;
	private Date editTime;
	private String fileName;	// �ثe��쪺�K����D
	String titleName;	// �s�K����D

	// ��l�e��
	private JLabel dateField = new JLabel();	// ���
	private JPanel headPanel = new JPanel();	// ���Dpanel

	// combo box��ܶK��
	private JComboBox<String> chooseArticle;
	String[] titles;
	private int article;	// ��combobox�ƥ�

	private JTextArea articleField = new JTextArea();	// �峹��
	private boolean isLike = false;

	private JLabel heartLabel = new JLabel();		// �R��
	private Icon heartEmpty;		// �ŷR��
	private Icon heartFilled;		// ���R��
	private JButton newButton = new JButton("New Post");	// �s�K����s

	// �s��e��
	private JButton saveButton = new JButton("Save");		// �x�s���s
	private JButton saveAsButton = new JButton("Save as");	// �t�s���e���s
	private JButton importButton = new JButton("Import");	// �פJ���e���s
	private JButton cancelButton = new JButton("Cancel");	// �������s

	private JPanel buttonPanel = new JPanel();
	private JPanel allPanel = new JPanel();

	public Frame () {

		super("���i�t��");
		fileName = "HW1 Paint GUI";

		// panels
		headPanel.setBackground(Color.yellow);
		buttonPanel.setBackground(Color.yellow);
		allPanel.setLayout(new BorderLayout());

		// ���
		dateField.setText(String.format("%s", fileCon.readPost(fileName).getEditTime()));

		// combobox
		titles = fileCon.readPostTxt();
		chooseArticle = new JComboBox<String>(titles);
		ComboBoxListener c = new ComboBoxListener();
		chooseArticle.addActionListener(c);
		
		// title��
		headPanel.add(chooseArticle);
		headPanel.add(dateField);
		allPanel.add(headPanel, BorderLayout.NORTH);

		// post
		articleField.setEditable(false);
		articleField.setBackground(Color.yellow);
		articleField.setText(String.format("%s", fileCon.readPost(fileName).getContent()));
		allPanel.add(articleField, BorderLayout.CENTER);

		// ���s�]�w
		heartEmpty = new ImageIcon(getClass().getResource("unlike.png"));
		heartFilled = new ImageIcon(getClass().getResource("like.png"));
		likeChange();

		// JButton Listener
		newButton.addActionListener(new ButtonListener());
		saveButton.addActionListener(new ButtonListener());
		saveAsButton.addActionListener(new ButtonListener());
		importButton.addActionListener(new ButtonListener());
		cancelButton.addActionListener(new ButtonListener());

		// ���s��
		buttonPanel.add(heartLabel);
		buttonPanel.add(newButton);
		allPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(allPanel);

		// �O�_���o����
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

	// ���ܼҦ��ɧ��ܫ��spanel
	private void EditChange() {

		buttonPanel.removeAll();

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
			buttonPanel.add(newButton);
		}

		buttonPanel.revalidate();
		buttonPanel.repaint();
	}
	
	// ��R���ܤ�
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
			// �s�K��
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
			// �x�s
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
			// �t�s
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
			// �פJ
			if (event.getSource() == importButton) {
				fileCon.openFile(articleField);
				return;
			}
			// ����
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