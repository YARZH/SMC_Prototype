package admin;

import java.awt.Font;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class WorkersPanel extends JFrame {
	
	Font reg = new Font("reg", 1, 20);
	
	JLabel lName, lPhone, lAccount, lID, lPosition, lBranch, lNumber;
	JLabel lUserName, lPassword;
	JTextField tfName, tfPhone, tfAccount, tfID, tfNumber;
	JTextField tfUserName, tfPassword;
	JComboBox<String> cbPosition, cbBranch;	
	
	WorkersPanel(String title) {
		super(title);
		setSize(860, 400);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		setLocationRelativeTo(null);
		setLayout(null);
		
		lName = new JLabel("Имя");
		lName.setFont(reg);
		lName.setBounds(10, 10, 150, 40);
		add(lName);
		
		tfName = new JTextField();
		tfName.setFont(reg);
		tfName.setBounds(170, 10, 230, 40);
		add(tfName);
		
		lPhone = new JLabel("Телефон");
		lPhone.setFont(reg);
		lPhone.setBounds(10, 60, 150, 40);
		add(lPhone);
		
		tfPhone = new JTextField();
		tfPhone.setFont(reg);
		tfPhone.setBounds(170, 60, 230, 40);
		add(tfPhone);
		
		lAccount = new JLabel("Счёт");
		lAccount.setFont(reg);
		lAccount.setBounds(10, 110, 150, 40);
		add(lAccount);
		
		tfAccount = new JTextField();
		tfAccount.setFont(reg);
		tfAccount.setBounds(170, 110, 230, 40);
		add(tfAccount);
		
		lUserName = new JLabel("Логин");
		lUserName.setFont(reg);
		lUserName.setBounds(10, 160, 150, 40);
		add(lUserName);
		
		tfUserName = new JTextField();
		tfUserName.setFont(reg);
		tfUserName.setBounds(170, 160, 230, 40);
		add(tfUserName);
		
		lID = new JLabel("ID");
		lID.setFont(reg);
		lID.setBounds(10, 210, 150, 40);
		add(lID);
		
		tfID = new JTextField();
		tfID.setFont(reg);
		tfID.setBounds(170, 210, 230, 40);
		add(tfID);
		
		lPosition = new JLabel("Должность");
		lPosition.setFont(reg);
		lPosition.setBounds(450, 10, 150, 40);
		add(lPosition);
		
		cbPosition = new JComboBox<String>();
		cbPosition.setFont(reg);
		cbPosition.setBounds(610, 10, 230, 40);
		cbPosition.addItem("admin");
		cbPosition.addItem("cashier");
		cbPosition.addItem("salesman");
		cbPosition.addItem("shift manager");		
		add(cbPosition);
		
		lBranch = new JLabel("Филиал");
		lBranch.setFont(reg);
		lBranch.setBounds(450, 60, 150, 40);
		add(lBranch);
		
		cbBranch = new JComboBox<String>();
		cbBranch.setFont(reg);
		cbBranch.setBounds(610, 60, 230, 40);
		add(cbBranch);
		
		lNumber = new JLabel("Номер");
		lNumber.setFont(reg);
		lNumber.setBounds(450, 110, 150, 40);
		add(lNumber);
		
		tfNumber = new JTextField();
		tfNumber.setFont(reg);
		tfNumber.setBounds(610, 110, 230, 40);
		add(tfNumber);
		
		lPassword = new JLabel("Пароль");
		lPassword.setFont(reg);
		lPassword.setBounds(450, 160, 150, 40);
		add(lPassword);
		
		tfPassword = new JTextField();
		tfPassword.setFont(reg);
		tfPassword.setBounds(610, 160, 230, 40);
		add(tfPassword);
		
		setVisible(true);
	}	
}