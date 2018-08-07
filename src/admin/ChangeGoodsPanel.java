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

public class ChangeGoodsPanel extends GoodsPanel {
	
	Font reg = new Font("reg", 1, 20);
	JLabel lmistake;
	String prState = "";
	long pQty;
	
	ChangeGoodsPanel(String title, Connection conn) {
		super(title);
		
		addBranch(conn);
		addType(conn);
		addColor(conn);
		
		super.cbType.setEditable(true);
		super.cbColor.setEditable(true);
		
		int selRow = GUIWorkingPanels.gTable.getSelectedRow();
		pQty = 	(long) GUIWorkingPanels.gTable.getValueAt(selRow, 6);
		super.tfID.setText("" + GUIWorkingPanels.gTable.getValueAt(selRow, 0));
		super.cbBranch.setSelectedItem((String) GUIWorkingPanels.gTable.getValueAt(selRow, 1));
		super.cbType.setSelectedItem((String) GUIWorkingPanels.gTable.getValueAt(selRow, 2));
		super.tfName.setText((String) GUIWorkingPanels.gTable.getValueAt(selRow, 3));
		super.cbSize.setSelectedItem((String) GUIWorkingPanels.gTable.getValueAt(selRow, 4));
		super.cbColor.setSelectedItem((String) GUIWorkingPanels.gTable.getValueAt(selRow, 5));		
		super.tfQuantity.setText("" + pQty);
		
		prState = "idGoods = " + Integer.parseInt(super.tfID.getText()) + ", Store = " + super.cbBranch.getSelectedItem()
				+ ", Type = " + super.cbType.getSelectedItem() + ", Name = " + super.tfName.getText() + ", Size = "
				+ super.cbSize.getSelectedItem() + ", Color = " + super.cbColor.getSelectedItem()
				+ ", Quantity = " + Integer.parseInt(super.tfQuantity.getText());
				
		super.tfID.setEditable(false);
				
		lmistake = new JLabel();
		lmistake.setFont(reg);
		lmistake.setBounds(450, 160, 390, 40);
		add(lmistake);
				
		JButton changeG = new JButton("Изменить");
		changeG.setFont(reg);
		changeG.setBounds(355, 260, 150, 40);
		add(changeG);
		
		setVisible(true);
		
		changeG.addActionListener((ae) -> {
			changeGood(conn);
			this.dispose();
		});		
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
		if(super.tfName.getText().isEmpty()) lmistake.setText("Не введено наименование");
		else if(super.tfQuantity.getText().isEmpty()) lmistake.setText("Не введено количество");
		else if(super.tfID.getText().isEmpty()) lmistake.setText("Не введено ID");
		else
		try {
			Statement stmt = conn.createStatement();
			
			String state = "idGoods = " + Integer.parseInt(super.tfID.getText()) + ", Store = " + super.cbBranch.getSelectedItem()
			+ ", Type = " + super.cbType.getSelectedItem() + ", Name = " + super.tfName.getText() + ", Size = "
			+ super.cbSize.getSelectedItem() + ", Color = " + super.cbColor.getSelectedItem()
			+ ", Quantity = " + Integer.parseInt(super.tfQuantity.getText());
			long qty = Integer.parseInt(super.tfQuantity.getText());
			
			String qury = "UPDATE smcprototype.goods SET idGoods = " + Integer.parseInt(super.tfID.getText())
					+ ", Store = '" + super.cbBranch.getSelectedItem() + "', Type = '" + super.cbType.getSelectedItem()
					+ "', Name = '" + super.tfName.getText() + "', Size = '" + super.cbSize.getSelectedItem()
					+ "', Color = '" + super.cbColor.getSelectedItem() + "', Quantity = " + Integer.parseInt(super.tfQuantity.getText())					
					+ " WHERE idGoods='" + GUIWorkingPanels.gTable.getValueAt(GUIWorkingPanels.gTable.getSelectedRow(), 0) + "'";
			int ins = stmt.executeUpdate(qury);
			GUIWorkingPanels.gTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.goods"));
			
			qury = "INSERT into smcprototype.log_goods (idGood,Branch,Name,action,prevState,actState,prevQty,actQty) VALUES ("
					+ Integer.parseInt(super.tfID.getText()) + ",'" + super.cbBranch.getSelectedItem() + "','" + super.tfName.getText()
					+  "','Change','" + prState + "','" + state + "'," + pQty + "," + qty + ")";
			ins = stmt.executeUpdate(qury);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
}
