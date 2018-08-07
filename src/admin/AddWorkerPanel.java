package admin;

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
import admin.GUIWorkingPanels;
import server.DataProcessing;

public class AddWorkerPanel extends WorkersPanel {
	
	Font reg = new Font("reg", 1, 20);
	JLabel lmistake;
	
	AddWorkerPanel(String title, Connection conn) {
		super(title);
		
		super.tfID.setEditable(false);		
		
		addBranch(conn);
		
		super.cbBranch.setEditable(true);
		
		lmistake = new JLabel();
		lmistake.setFont(reg);
		lmistake.setBounds(450, 260, 390, 40);
		add(lmistake);
				
		JButton generateID = new JButton("Сгенерировать ID");
		generateID.setFont(reg);
		generateID.setBounds(100, 260, 210, 40);
		add(generateID);
		
		JButton addW = new JButton("Добавить");
		addW.setFont(reg);
		addW.setBounds(270, 310, 150, 40);
		add(addW);
		
		JButton clear = new JButton("Сброс");
		clear.setFont(reg);
		clear.setBounds(440, 310, 150, 40);
		add(clear);
		
		setVisible(true);
		
		generateID.addActionListener((ae) -> {
			genID(conn);
		});
		
		addW.addActionListener((ae) -> {			
			addWorker(conn);
			//Добавить запись в лог
		});
		
		clear.addActionListener((ae) -> {
			cl();
		});
	}
	
	void addBranch(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Store FROM smcprototype.workers");
			TreeSet<String> ss = new TreeSet<String>();
			while(rs.next())
				if(rs.getString(1)==null) ss.add("------");
				else ss.add(rs.getString(1));
			Vector<String> v = new Vector<String>(ss);
			super.cbBranch.setModel(new DefaultComboBoxModel<String>(v));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	void genID(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idWorkers FROM smcprototype.workers");
			TreeSet<Integer> ss = new TreeSet<Integer>();
			while(rs.next()) ss.add(rs.getInt(1));
			super.tfID.setText("" + (ss.last()+1)); 
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	void addWorker(Connection conn) {
		if(super.tfName.getText().isEmpty()) lmistake.setText("Не введено имя");
		else if(super.tfPhone.getText().isEmpty()) lmistake.setText("Не введен телефон");
		else if(super.tfAccount.getText().isEmpty()) lmistake.setText("Не введен счёт");
		else if(super.tfID.getText().isEmpty()) lmistake.setText("Не введено ID");
		else if(super.tfNumber.getText().isEmpty()) lmistake.setText("Не введен номер");
		else if(super.tfUserName.getText().isEmpty()) lmistake.setText("Не введен логин");
		else if(super.tfPassword.getText().isEmpty()) lmistake.setText("Не введен пароль");
		else
		try {
			Statement stmt = conn.createStatement();
			
			String state = "idWorkers = " + Integer.parseInt(super.tfID.getText()) + ", Name = " + super.tfName.getText() + ", Store = "
			+ super.cbBranch.getSelectedItem() + ", Position = " + super.cbPosition.getSelectedItem() + ", Phone = " + super.tfPhone.getText() 
					+ ", Account = " + super.tfAccount.getText() + ", Number = " + Integer.parseInt(super.tfNumber.getText());					
			
			String qury = "INSERT into smcprototype.workers (idWorkers,Name,Store,Position,"
					+ "Phone,Account,Number,UserName,Password) VALUES (" + Integer.parseInt(super.tfID.getText())
					+ ",\"" + super.tfName.getText() + "\",\"" + super.cbBranch.getSelectedItem()
					+ "\",\"" + super.cbPosition.getSelectedItem() + "\",\"" + super.tfPhone.getText()
					+ "\",\"" + super.tfAccount.getText() + "\"," + Integer.parseInt(super.tfNumber.getText())
					+ ",\"" + super.tfUserName.getText() + "\",\"" + super.tfPassword.getText() + "\")";
			int ins = stmt.executeUpdate(qury);
			GUIWorkingPanels.wTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.workers"));
			
			qury = "INSERT into smcprototype.log_Workers (idWorker,action,actState) VALUES ("
					+ Integer.parseInt(super.tfID.getText()) + ",'Add','" + state + "')";
			ins = stmt.executeUpdate(qury);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	void cl() {
		super.tfName.setText("");
		super.tfPhone.setText("");
		super.tfAccount.setText("");
		super.tfID.setText("");
		super.tfNumber.setText("");
		super.tfUserName.setText("");
		super.tfPassword.setText("");		
	}	
}
