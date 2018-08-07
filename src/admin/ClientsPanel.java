package admin;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClientsPanel extends JFrame {
	
Font reg = new Font("reg", 1, 20);
	
	JLabel lID, lName, lPhone, lType, lDiscount;	
	JTextField tfID, tfName, tfPhone, tfDiscount;
	JComboBox<String> cbType;
	
	ClientsPanel(String title) {
		super(title);
		setSize(860, 300);
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
		
		lType = new JLabel("Тип");
		lType.setFont(reg);
		lType.setBounds(10, 60, 150, 40);
		add(lType);
		
		cbType = new JComboBox<String>();
		cbType.setFont(reg);
		cbType.setBounds(170, 60, 230, 40);
		cbType.addItem("new");
		cbType.addItem("direct");
		cbType.addItem("vip");
		add(cbType);
		
		lID = new JLabel("ID");
		lID.setFont(reg);
		lID.setBounds(10, 110, 150, 40);
		add(lID);
		
		tfID = new JTextField();
		tfID.setFont(reg);
		tfID.setBounds(170, 110, 230, 40);
		add(tfID);
						
		lPhone = new JLabel("Телефон");
		lPhone.setFont(reg);
		lPhone.setBounds(450, 10, 150, 40);
		add(lPhone);
		
		tfPhone = new JTextField();
		tfPhone.setFont(reg);
		tfPhone.setBounds(610, 10, 230, 40);
		add(tfPhone);		
		
		lDiscount = new JLabel("Скидка");
		lDiscount.setFont(reg);
		lDiscount.setBounds(450, 60, 150, 40);
		add(lDiscount);
		
		tfDiscount = new JTextField();
		tfDiscount.setFont(reg);
		tfDiscount.setBounds(610, 60, 230, 40);
		cbType.addItemListener((ai) -> {
			if (cbType.getSelectedIndex() == 0) tfDiscount.setText("0");
			if (cbType.getSelectedIndex() == 1) tfDiscount.setText("5");
			if (cbType.getSelectedIndex() == 2) tfDiscount.setText("10");
		});
		tfDiscount.setText("0");
		tfDiscount.setEditable(false);
		add(tfDiscount);
		
		setVisible(true);
	}
}
