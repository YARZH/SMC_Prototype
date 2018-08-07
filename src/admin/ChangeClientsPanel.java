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

public class ChangeClientsPanel extends ClientsPanel {
	
	Font reg = new Font("reg", 1, 20);
	JLabel lmistake;
	String prState = "";

	ChangeClientsPanel(String title, Connection conn) {
		super(title);
		
		int selRow = GUIWorkingPanels.cTable.getSelectedRow();
		
		super.tfID.setText("" + GUIWorkingPanels.cTable.getValueAt(selRow, 0));
		super.tfName.setText((String) GUIWorkingPanels.cTable.getValueAt(selRow, 1));
		super.tfPhone.setText((String) GUIWorkingPanels.cTable.getValueAt(selRow, 2));		
		super.cbType.setSelectedItem((String) GUIWorkingPanels.cTable.getValueAt(selRow, 3));
		super.tfDiscount.setText("" + GUIWorkingPanels.cTable.getValueAt(selRow, 4));
				
		super.tfID.setEditable(false);
		
		prState = "idClients = " + Integer.parseInt(super.tfID.getText()) + ", Name = " + super.tfName.getText() + ", Phone = "
				+ super.tfPhone.getText() + ", Type = " + super.cbType.getSelectedItem() 
				+ ", Discount = " + Double.parseDouble(super.tfDiscount.getText());
				
		lmistake = new JLabel();
		lmistake.setFont(reg);
		lmistake.setBounds(450, 110, 390, 40);
		add(lmistake);
				
		JButton changeC = new JButton("Изменить");
		changeC.setFont(reg);
		changeC.setBounds(355, 210, 150, 40);
		add(changeC);
		
		setVisible(true);
		
		changeC.addActionListener((ae) -> {
			changeClient(conn);
			this.dispose();
			//Добавить запись в лог
		});		
	}
	
	void changeClient(Connection conn) {
		if(super.tfName.getText().isEmpty()) lmistake.setText("Не введено имя");
		else if(super.tfPhone.getText().isEmpty()) lmistake.setText("Не введен телефон");
		else if(super.tfID.getText().isEmpty()) lmistake.setText("Не введено ID");
		else
		try {
			Statement stmt = conn.createStatement();
			
			String state = "idClients = " + Integer.parseInt(super.tfID.getText()) + ", Name = " + super.tfName.getText() + ", Phone = "
					+ super.tfPhone.getText() + ", Type = " + super.cbType.getSelectedItem() 
					+ ", Discount = " + Double.parseDouble(super.tfDiscount.getText());
			
			String qury = "UPDATE smcprototype.clients SET idClients = " + Integer.parseInt(super.tfID.getText())
					+ ", Name = '" + super.tfName.getText() + "', Phone = '" + super.tfPhone.getText()
					+ "', Type = '" + super.cbType.getSelectedItem() + "', Discount = " + Double.parseDouble(super.tfDiscount.getText())					
					+ " WHERE idClients='" + GUIWorkingPanels.cTable.getValueAt(GUIWorkingPanels.cTable.getSelectedRow(), 0) + "'";
			int ins = stmt.executeUpdate(qury);
			GUIWorkingPanels.cTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.clients"));
			
			qury = "INSERT into smcprototype.log_clients (idClient,action,prevState,actState) VALUES ("
					+ Integer.parseInt(super.tfID.getText()) + ",'Change','" + prState + "','" + state + "')";
			ins = stmt.executeUpdate(qury);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
}
