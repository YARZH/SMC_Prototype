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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import server.DataProcessing;

public class SellPanel extends GoodsPanel {
	
	Font reg = new Font("reg", 1, 20);
	
	String prState = "";
	long pQty;
	JTextField tfSellQty;
	JComboBox<String> cbClient;
	
	SellPanel(String title, Connection conn) {
		super(title);
		
		addBranch(conn);
		addType(conn);
		addColor(conn);
		
		super.cbBranch.setEnabled(false);
		super.cbType.setEnabled(false);
		super.tfName.setEnabled(false);
		super.cbSize.setEnabled(false);
		super.cbColor.setEnabled(false);
		super.tfQuantity.setEnabled(false);
		super.tfID.setEnabled(false);
		
		int selRow = GUIWorkingPanels.gSellTable.getSelectedRow();
		pQty = 	(long) GUIWorkingPanels.gSellTable.getValueAt(selRow, 6);
		super.tfID.setText("" + GUIWorkingPanels.gSellTable.getValueAt(selRow, 0));
		super.cbBranch.setSelectedItem((String) GUIWorkingPanels.gSellTable.getValueAt(selRow, 1));
		super.cbType.setSelectedItem((String) GUIWorkingPanels.gSellTable.getValueAt(selRow, 2));
		super.tfName.setText((String) GUIWorkingPanels.gSellTable.getValueAt(selRow, 3));
		super.cbSize.setSelectedItem((String) GUIWorkingPanels.gSellTable.getValueAt(selRow, 4));
		super.cbColor.setSelectedItem((String) GUIWorkingPanels.gSellTable.getValueAt(selRow, 5));		
		super.tfQuantity.setText("" + pQty);
		
		prState = "idGoods = " + Integer.parseInt(super.tfID.getText()) + ", Store = " + super.cbBranch.getSelectedItem()
				+ ", Type = " + super.cbType.getSelectedItem() + ", Name = " + super.tfName.getText() + ", Size = "
				+ super.cbSize.getSelectedItem() + ", Color = " + super.cbColor.getSelectedItem()
				+ ", Quantity = " + Integer.parseInt(super.tfQuantity.getText());		
				
		JLabel lclient = new JLabel("Клиент");
		lclient.setFont(reg);
		lclient.setBounds(450, 160, 150, 40);
		add(lclient);
		
		cbClient = new JComboBox<String>();
		cbClient.setFont(reg);
		cbClient.setBounds(610, 160, 150, 40);
		add(cbClient);
		
		addClients(conn);
		
		JLabel lSellQty = new JLabel("Количество");
		lSellQty.setFont(reg);
		lSellQty.setBounds(10, 210, 150, 40);
		add(lSellQty);
		
		tfSellQty = new JTextField();
		tfSellQty.setFont(reg);
		tfSellQty.setBounds(170, 210, 230, 40);
		add(tfSellQty);
				
		JButton changeG = new JButton("Продать");
		changeG.setFont(reg);
		changeG.setBounds(355, 260, 150, 40);
		add(changeG);
		
		setVisible(true);
		
		changeG.addActionListener((ae) -> {
			changeGood(conn);
			this.dispose();
		});		
	}
	
	void addClients(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Name FROM smcprototype.clients");
			TreeSet<String> ss = new TreeSet<String>();
			while(rs.next())
				ss.add(rs.getString(1));
			Vector<String> v = new Vector<String>(ss);
			cbClient.setModel(new DefaultComboBoxModel<String>(v));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}

	void addBranch(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Store FROM smcprototype.goods");
			TreeSet<String> ss = new TreeSet<String>();
			while(rs.next())
				ss.add(rs.getString(1));
			Vector<String> v = new Vector<String>(ss);
			super.cbBranch.setModel(new DefaultComboBoxModel<String>(v));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	void addType(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Type FROM smcprototype.goods");
			TreeSet<String> ss = new TreeSet<String>();
			while(rs.next())
				ss.add(rs.getString(1));
			Vector<String> v = new Vector<String>(ss);
			super.cbType.setModel(new DefaultComboBoxModel<String>(v));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	void addColor(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Color FROM smcprototype.goods");
			TreeSet<String> ss = new TreeSet<String>();
			while(rs.next())
				ss.add(rs.getString(1));
			Vector<String> v = new Vector<String>(ss);
			super.cbColor.setModel(new DefaultComboBoxModel<String>(v));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	void changeGood(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			
			String state = "idGoods = " + Integer.parseInt(super.tfID.getText()) + ", Store = " + super.cbBranch.getSelectedItem()
			+ ", Type = " + super.cbType.getSelectedItem() + ", Name = " + super.tfName.getText() + ", Size = "
			+ super.cbSize.getSelectedItem() + ", Color = " + super.cbColor.getSelectedItem()
			+ ", Quantity = " + (Integer.parseInt(super.tfQuantity.getText()) - Integer.parseInt(tfSellQty.getText()))
			+ ", Client = " + cbClient.getSelectedItem();
			long qty = Integer.parseInt(super.tfQuantity.getText()) - Integer.parseInt(tfSellQty.getText());
			
			String qury = "UPDATE smcprototype.goods SET idGoods = " + Integer.parseInt(super.tfID.getText())
					+ ", Store = '" + super.cbBranch.getSelectedItem() + "', Type = '" + super.cbType.getSelectedItem()
					+ "', Name = '" + super.tfName.getText() + "', Size = '" + super.cbSize.getSelectedItem()
					+ "', Color = '" + super.cbColor.getSelectedItem() + "', Quantity = " + (Integer.parseInt(super.tfQuantity.getText()) - Integer.parseInt(tfSellQty.getText()))					
					+ " WHERE idGoods='" + GUIWorkingPanels.gSellTable.getValueAt(GUIWorkingPanels.gSellTable.getSelectedRow(), 0) + "'";
			int ins = stmt.executeUpdate(qury);
			GUIWorkingPanels.gSellTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.goods WHERE Store='" + super.cbBranch.getSelectedItem() + "'"));
			
			qury = "INSERT into smcprototype.log_goods (idGood,Branch,Name,action,prevState,actState,prevQty,actQty) VALUES ("
					+ Integer.parseInt(super.tfID.getText()) + ",'" + super.cbBranch.getSelectedItem() + "','" + super.tfName.getText()
					+  "','Sell','" + prState + "','" + state + "'," + pQty + "," + qty + ")";
			ins = stmt.executeUpdate(qury);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
}
