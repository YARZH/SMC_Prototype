package client;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;

import server.DataProcessing;

public class AddClientsPanel extends ClientsPanel{
	
	Font reg = new Font("reg", 1, 20);
	JLabel lmistake;

	AddClientsPanel(String title, Connection conn) {
		super(title);
		
		super.tfID.setEditable(false);
		
		lmistake = new JLabel();
		lmistake.setFont(reg);
		lmistake.setBounds(450, 110, 390, 40);
		add(lmistake);
				
		JButton generateID = new JButton("Сгенерировать ID");
		generateID.setFont(reg);
		generateID.setBounds(100, 160, 210, 40);
		add(generateID);
		
		JButton addC = new JButton("Добавить");
		addC.setFont(reg);
		addC.setBounds(270, 210, 150, 40);
		add(addC);
		
		JButton clear = new JButton("Сброс");
		clear.setFont(reg);
		clear.setBounds(440, 210, 150, 40);
		add(clear);
		
		setVisible(true);
		
		generateID.addActionListener((ae) -> {
			genID(conn);
		});
		
		addC.addActionListener((ae) -> {
			addClient(conn);
			//Добавить запись в лог
		});
		
		clear.addActionListener((ae) -> {
			cl();
		});
	}
	
	void genID(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idClients FROM smcprototype.clients");
			TreeSet<Integer> ss = new TreeSet<Integer>();
			while(rs.next()) ss.add(rs.getInt(1));
			super.tfID.setText("" + (ss.last()+1)); 
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	void addClient(Connection conn) {
		if(super.tfName.getText().isEmpty()) lmistake.setText("Не введено имя");
		else if(super.tfPhone.getText().isEmpty()) lmistake.setText("Не введен телефон");
		else if(super.tfID.getText().isEmpty()) lmistake.setText("Не введено ID");
		else
		try {
			Statement stmt = conn.createStatement();
			
			String state = "idClients = " + Integer.parseInt(super.tfID.getText()) + ", Name = " + super.tfName.getText() + ", Phone = "
					+ super.tfPhone.getText() + ", Type = " + super.cbType.getSelectedItem()
					+ ", Discount = " + Integer.parseInt(super.tfDiscount.getText());
			
			String qury = "INSERT into smcprototype.clients (idClients,Name,Phone,Type,Discount)"
					+ "VALUES (" + Integer.parseInt(super.tfID.getText()) + ",\"" + super.tfName.getText()
					+ "\",\"" + super.tfPhone.getText() + "\",\"" + super.cbType.getSelectedItem() 
					+ "\",\"" + Double.parseDouble(super.tfDiscount.getText()) + "\")";
			int ins = stmt.executeUpdate(qury);
			GUIWorkingPanels.cTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.clients"));
			
			qury = "INSERT into smcprototype.log_clients (idClient,action,actState) VALUES ("
					+ Integer.parseInt(super.tfID.getText()) + ",'Add','" + state + "')";
			ins = stmt.executeUpdate(qury);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	void cl() {
		super.tfName.setText("");
		super.tfPhone.setText("");
		super.tfID.setText("");
	}
}
