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

public class AddGoodsPanel extends GoodsPanel {
	
	Font reg = new Font("reg", 1, 20);
	JLabel lmistake;

	AddGoodsPanel(String title, Connection conn) {
		super(title);		

		super.tfID.setEditable(false);
				
		addBranch(conn);
		addType(conn);
		addColor(conn);
		
		super.cbBranch.setEditable(true);
		super.cbType.setEditable(true);
		super.cbColor.setEditable(true);
		
		lmistake = new JLabel();
		lmistake.setFont(reg);
		lmistake.setBounds(450, 160, 390, 40);
		add(lmistake);
				
		JButton generateID = new JButton("Сгенерировать ID");
		generateID.setFont(reg);
		generateID.setBounds(100, 210, 210, 40);
		add(generateID);
		
		JButton addG = new JButton("Добавить");
		addG.setFont(reg);
		addG.setBounds(270, 260, 150, 40);
		add(addG);
		
		JButton clear = new JButton("Сброс");
		clear.setFont(reg);
		clear.setBounds(440, 260, 150, 40);
		add(clear);
		
		setVisible(true);
		
		generateID.addActionListener((ae) -> {
			genID(conn);
		});
		
		addG.addActionListener((ae) -> {
			addGood(conn);
			//Добавить запись в лог
		});
		
		clear.addActionListener((ae) -> {
			cl();
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
	
	void genID(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idGoods FROM smcprototype.goods");
			TreeSet<Integer> ss = new TreeSet<Integer>();
			while(rs.next()) ss.add(rs.getInt(1));
			super.tfID.setText("" + (ss.last()+1)); 
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	void addGood(Connection conn) {
		if(super.tfName.getText().isEmpty()) lmistake.setText("Не введено наименование");
		else if(super.tfQuantity.getText().isEmpty()) lmistake.setText("Не введено количество");
		else if(super.tfID.getText().isEmpty()) lmistake.setText("Не введено ID");
		else
		try {
			Statement stmt = conn.createStatement();
			
			String state = "idGoods = " + Integer.parseInt(super.tfID.getText()) + ", Store = "	+ super.cbBranch.getSelectedItem()
					+ ", Type = " + cbType.getSelectedItem() + ", Name = " + super.tfName.getText() + ", Size = " + cbSize.getSelectedItem() 
					+ ", Color = " + cbSize.getSelectedItem() + ", Quantity = " + Integer.parseInt(super.tfQuantity.getText());
			
			String qury = "INSERT into smcprototype.goods (idGoods,Store,Type,Name,Size,Color,Quantity)"
					+ "VALUES (" + Integer.parseInt(super.tfID.getText()) + ",\"" + super.cbBranch.getSelectedItem() 
					+ "\",\"" + super.cbType.getSelectedItem() + "\",\"" + super.tfName.getText()
					+ "\",\"" + super.cbSize.getSelectedItem() + "\",\"" + super.cbColor.getSelectedItem()
					+ "\",\"" + super.tfQuantity.getText() + "\")";
			int ins = stmt.executeUpdate(qury);
			GUIWorkingPanels.gTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.goods"));
			
			qury = "INSERT into smcprototype.log_goods (idGood,Branch,Name,action,actState,actQty) VALUES ("
					+ Integer.parseInt(super.tfID.getText()) + ",'" + super.cbBranch.getSelectedItem() + "','"
					+ super.tfName.getText() + "','Add','" + state + "'," + Integer.parseInt(super.tfQuantity.getText()) + ")";
			ins = stmt.executeUpdate(qury);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	void cl() {
		super.tfName.setText("");
		super.tfQuantity.setText("");
		super.tfID.setText("");
	}

}
