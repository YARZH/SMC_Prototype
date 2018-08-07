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

import server.DataProcessing;

public class ChangeWorkerPanel extends WorkersPanel {
	
	Font reg = new Font("reg", 1, 20);
	JLabel lmistake;
	String prState = "";
	
	ChangeWorkerPanel(String title, Connection conn) {
		super(title);
		
		addBranch(conn);
		
		int selRow = GUIWorkingPanels.wTable.getSelectedRow();
		
		super.tfID.setText("" + GUIWorkingPanels.wTable.getValueAt(selRow, 0));
		super.tfName.setText((String) GUIWorkingPanels.wTable.getValueAt(selRow, 1));
		super.cbBranch.setSelectedItem((String) GUIWorkingPanels.wTable.getValueAt(selRow, 2));
		super.cbPosition.setSelectedItem((String) GUIWorkingPanels.wTable.getValueAt(selRow, 3));
		super.tfPhone.setText((String) GUIWorkingPanels.wTable.getValueAt(selRow, 4));
		super.tfAccount.setText((String) GUIWorkingPanels.wTable.getValueAt(selRow, 5));
		super.tfNumber.setText("" + GUIWorkingPanels.wTable.getValueAt(selRow, 6));
		super.tfUserName.setText((String) GUIWorkingPanels.wTable.getValueAt(selRow, 7));
		super.tfPassword.setText((String) GUIWorkingPanels.wTable.getValueAt(selRow, 8));
		
		super.tfID.setEditable(false);
		super.tfNumber.setEditable(false);
		
		prState = "idWorkers = " + Integer.parseInt(super.tfID.getText()) + ", Name = " + super.tfName.getText() + ", Store = "
				+ super.cbBranch.getSelectedItem() + ", Position = " + super.cbPosition.getSelectedItem() + ", Phone = " + super.tfPhone.getText() 
				+ ", Account = " + super.tfAccount.getText() + ", Number = " + Integer.parseInt(super.tfNumber.getText());	
		
		lmistake = new JLabel();
		lmistake.setFont(reg);
		lmistake.setBounds(450, 260, 390, 40);
		add(lmistake);
				
		JButton changeW = new JButton("Изменить");
		changeW.setFont(reg);
		changeW.setBounds(355, 310, 150, 40);
		add(changeW);
		
		setVisible(true);
		
		changeW.addActionListener((ae) -> {
			changeWorker(conn);
			this.dispose();
			//Добавить запись в лог
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
	
	void changeWorker(Connection conn) {
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
			
			String qury = "UPDATE smcprototype.workers SET idWorkers = " + Integer.parseInt(super.tfID.getText()) + ", "
					+ "Name = '" + super.tfName.getText() + "', Store = '" + super.cbBranch.getSelectedItem()
					+ "', Position = '" + super.cbPosition.getSelectedItem() + "', Phone = '" + super.tfPhone.getText()
					+ "', Account = '" + super.tfAccount.getText() + "', Number = " + Integer.parseInt(super.tfNumber.getText())
					+ ", UserName = '" + super.tfUserName.getText() + "', Password = '" + super.tfPassword.getText() + "'"
					+ "WHERE idWorkers='" + GUIWorkingPanels.wTable.getValueAt(GUIWorkingPanels.wTable.getSelectedRow(), 0) + "'";
			int ins = stmt.executeUpdate(qury);
			GUIWorkingPanels.wTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.workers"));
			
			qury = "INSERT into smcprototype.log_workers (idWorker,action,prevState,actState) VALUES ("
					+ Integer.parseInt(super.tfID.getText()) + ",'Change','" + prState + "','" + state + "')";
			ins = stmt.executeUpdate(qury);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}	
}
