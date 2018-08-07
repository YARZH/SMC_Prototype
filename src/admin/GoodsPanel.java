package admin;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GoodsPanel extends JFrame {
	
	Font reg = new Font("reg", 1, 20);
	
	JLabel lID, lBranch, lType, lName, lSize, lColor, lQuantity;	
	JTextField tfID, tfName, tfQuantity;
	JComboBox<String> cbBranch, cbType, cbSize, cbColor;	
	
	GoodsPanel(String title) {
		super(title);
		setSize(860, 350);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		setLocationRelativeTo(null);
		setLayout(null);
		
		lBranch = new JLabel("Филиал");
		lBranch.setFont(reg);
		lBranch.setBounds(10, 10, 150, 40);
		add(lBranch);
		
		cbBranch = new JComboBox<String>();
		cbBranch.setFont(reg);
		cbBranch.setBounds(170, 10, 230, 40);
		add(cbBranch);
		
		lName = new JLabel("Наименование");
		lName.setFont(reg);
		lName.setBounds(10, 60, 150, 40);
		add(lName);
		
		tfName = new JTextField();
		tfName.setFont(reg);
		tfName.setBounds(170, 60, 230, 40);
		add(tfName);
		
		lColor = new JLabel("Цвет");
		lColor.setFont(reg);
		lColor.setBounds(10, 110, 150, 40);
		add(lColor);
		
		cbColor = new JComboBox<String>();
		cbColor.setFont(reg);
		cbColor.setBounds(170, 110, 230, 40);
		add(cbColor);
		
		lID = new JLabel("ID");
		lID.setFont(reg);
		lID.setBounds(10, 160, 150, 40);
		add(lID);
		
		tfID = new JTextField();
		tfID.setFont(reg);
		tfID.setBounds(170, 160, 230, 40);
		add(tfID);
						
		lType = new JLabel("Тип");
		lType.setFont(reg);
		lType.setBounds(450, 10, 150, 40);
		add(lType);
		
		cbType = new JComboBox<String>();
		cbType.setFont(reg);
		cbType.setBounds(610, 10, 230, 40);
		add(cbType);
		
		
		lSize = new JLabel("Размер");
		lSize.setFont(reg);
		lSize.setBounds(450, 60, 150, 40);
		add(lSize);
		
		cbSize = new JComboBox<String>();
		cbSize.setFont(reg);
		cbSize.setBounds(610, 60, 230, 40);
		cbSize.addItem("XS");
		cbSize.addItem("S");
		cbSize.addItem("M");
		cbSize.addItem("L");
		cbSize.addItem("XL");
		cbSize.addItem("XXL");
		cbSize.addItem("XXXL");
		add(cbSize);
		
		lQuantity = new JLabel("Количество");
		lQuantity.setFont(reg);
		lQuantity.setBounds(450, 110, 150, 40);
		add(lQuantity);
		
		tfQuantity = new JTextField();
		tfQuantity.setFont(reg);
		tfQuantity.setBounds(610, 110, 230, 40);
		add(tfQuantity);
		
		setVisible(true);
	}	
}
