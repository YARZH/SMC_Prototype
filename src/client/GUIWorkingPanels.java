package client;

import java.awt.Font;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;

import org.json.JSONObject;

import java.sql.*;
import java.util.TreeSet;
import java.util.Vector;

import server.DataProcessing;

public class GUIWorkingPanels extends GUIMainPanel {
	
	Font reg = new Font("reg", 1, 20);
	JPanel gSellPanel;
	JPanel gWHPanel;
	JPanel cPanel;
	public static JTable gSellTable = null;
	public static JTable gWHTable = null;
	public static JTable cTable = null;
	Connection conn = null;
	String branch = "";
	JComboBox<String> cbtype;
	JComboBox<String> cbname;
	JComboBox<String> cbsize;
	JComboBox<String> cbcolor;
	
	GUIWorkingPanels(String log, String branch, Connection conn) {
		super(log);
		this.branch = branch;
		setLayout(null);
		
		this.conn = conn;
	
		JTabbedPane aPanel = new JTabbedPane();
		aPanel.setFont(reg);
		aPanel.setBounds(0, 0, 1280, 720);
		aPanel.add("Продажа", panelSell());
		aPanel.add("Склад", panelSupply());
		aPanel.add("Клиенты", panelClients());
		add(aPanel);
		
		setVisible(true);	
	}
	
	//Panel for adding, changing, deleting workers
	JPanel panelSell() {
		gSellPanel = new JPanel();
		gSellPanel.setBounds(0, 40, 1280, 680);
		gSellPanel.setLayout(null);
				
		try {
			gSellTable = new JTable(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' GROUP BY Type,Name,Size,Color"));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		JScrollPane sp1 = new JScrollPane(gSellTable);
		sp1.setBounds(10, 10, 1255, 480);
		gSellPanel.add(sp1);
		
		JLabel ltype = new JLabel("Тип");
		ltype.setFont(reg);
		ltype.setBounds(10, 500, 310, 40);
		gSellPanel.add(ltype);
		
		cbtype = new JComboBox<String>();
		cbtype.setFont(reg);
		cbtype.setBounds(10, 550, 310, 40);
		gSellPanel.add(cbtype);
		
		JLabel lname = new JLabel("Наименование");
		lname.setFont(reg);
		lname.setBounds(325, 500, 310, 40);
		gSellPanel.add(lname);
		
		cbname = new JComboBox<String>();
		cbname.setFont(reg);
		cbname.setBounds(325, 550, 310, 40);
		gSellPanel.add(cbname);
		
		JLabel lsize = new JLabel("Размер");
		lsize.setFont(reg);
		lsize.setBounds(640, 500, 310, 40);
		gSellPanel.add(lsize);
		
		cbsize = new JComboBox<String>();
		cbsize.setFont(reg);
		cbsize.setBounds(640, 550, 310, 40);
		gSellPanel.add(cbsize);
		
		JLabel lcolor = new JLabel("Цвет");
		lcolor.setFont(reg);
		lcolor.setBounds(955, 500, 310, 40);
		gSellPanel.add(lcolor);
		
		cbcolor = new JComboBox<String>();
		cbcolor.setFont(reg);
		cbcolor.setBounds(955, 550, 310, 40);
		gSellPanel.add(cbcolor);
		
		JButton gChange = new JButton("Продажа");
		gChange.setBounds(540, 599, 200, 38);
		gChange.setFont(reg);
		gSellPanel.add(gChange);
		
		getContentPane().add(gSellPanel);
		gSellPanel.setVisible(true);
		
		addType(conn);
		addName(conn);
		addSize(conn);
		addColor(conn);
		
		cbtype.addItemListener((al) -> {
			changeSellTable(conn);
		});
		
		cbname.addItemListener((al) -> {
			changeSellTable(conn);
		});
		
		cbsize.addItemListener((al) -> {
			changeSellTable(conn);
		});
		
		cbcolor.addItemListener((al) -> {
			changeSellTable(conn);
		});
		
		gChange.addActionListener((ae) -> {
			new SellPanel("Продажа", conn);
		});
		
		return gSellPanel;
	}
	
	//Panel for adding, changing, deleting goods
	JPanel panelSupply() {		
		gWHPanel = new JPanel();
		gWHPanel.setBounds(0, 40, 1280, 680);
		gWHPanel.setLayout(null);
				
		try {
			gWHTable = new JTable(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' GROUP BY Type,Name,Size,Color"));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		JScrollPane sp1 = new JScrollPane(gWHTable);
		sp1.setBounds(10, 10, 1255, 580);
		gWHPanel.add(sp1);
		
		JButton gAdd = new JButton("Добавить");
		gAdd.setBounds(330, 599, 200, 38);
		gAdd.setFont(reg);
		gWHPanel.add(gAdd);
		
		JButton gChange = new JButton("Изменить");
		gChange.setBounds(540, 599, 200, 38);
		gChange.setFont(reg);
		gWHPanel.add(gChange);
		
		JButton gRemove = new JButton("Удалить");
		gRemove.setBounds(750, 599, 200, 38);
		gRemove.setFont(reg);
		gWHPanel.add(gRemove);
		
		getContentPane().add(gWHPanel);
		gWHPanel.setVisible(true);
		
		gAdd.addActionListener((ae) -> {
			new AddGoodsPanel("Добавление товара", branch, conn);			
		});
		
		gChange.addActionListener((ae) -> {
			new ChangeGoodsPanel("Изменение товара", conn);
		});
		
		gRemove.addActionListener((ae) -> {
			try {
				int selRow = gWHTable.getSelectedRow();
				long pID = (long) gWHTable.getValueAt(selRow, 0);
				long pQty = (long) gWHTable.getValueAt(selRow, 6);
				String branch = (String) gWHTable.getValueAt(selRow, 1);
				String name = (String) gWHTable.getValueAt(selRow, 3);
				String prState = "idGoods = " + pID + ", Store = " + gWHTable.getValueAt(selRow, 1) + ", Type = "
						+ gWHTable.getValueAt(selRow, 2) + ", Name = " + gWHTable.getValueAt(selRow, 3) + ", Size = " + gWHTable.getValueAt(selRow, 4) 
						+ ", Color = " + gWHTable.getValueAt(selRow, 5) + ", Quantity = " + pQty;
				
				Statement stmt = conn.createStatement();
				int ins = stmt.executeUpdate("DELETE FROM smcprototype.goods WHERE idGoods='" + gWHTable.getValueAt(gWHTable.getSelectedRow(), 0) + "'");
				GUIWorkingPanels.gWHTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "'"));
				
				String qury = "INSERT into smcprototype.log_goods (idGood,Branch,Name,action,prevState,prevQty) VALUES ("
						+ pID + ",'" + branch + "','" + name + "','Delete','" + prState + "'," + pQty + ")";
				ins = stmt.executeUpdate(qury);
			} catch (SQLException e) {			
				e.printStackTrace();
			}			
		});
		
		return gWHPanel;
	}
	
	//Panel for adding, changing, deleting clients
		JPanel panelClients() {		
			cPanel = new JPanel();
			cPanel.setBounds(0, 40, 1280, 680);
			cPanel.setLayout(null);			
			
			try {
				cTable = new JTable(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.clients"));
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			
			JScrollPane sp1 = new JScrollPane(cTable);
			sp1.setBounds(10, 10, 1255, 580);
			cPanel.add(sp1);
			
			JButton cAdd = new JButton("Добавить");
			cAdd.setBounds(330, 599, 200, 38);
			cAdd.setFont(reg);
			cPanel.add(cAdd);
			
			JButton cChange = new JButton("Изменить");
			cChange.setBounds(540, 599, 200, 38);
			cChange.setFont(reg);
			cPanel.add(cChange);
			
			JButton cRemove = new JButton("Удалить");
			cRemove.setBounds(750, 599, 200, 38);
			cRemove.setFont(reg);
			cPanel.add(cRemove);
			
			getContentPane().add(cPanel);
			cPanel.setVisible(true);
			
			cAdd.addActionListener((ae) -> {
				new AddClientsPanel("Добавление клиента", conn);			
			});
			
			cChange.addActionListener((ae) -> {
				new ChangeClientsPanel("Изменение клиента", conn);
			});
			
			cRemove.addActionListener((ae) -> {
				try {
					int selRow = cTable.getSelectedRow();
					long pID = (long) cTable.getValueAt(selRow, 0);
					String prState = "idClients = " + pID + ", Name = " + cTable.getValueAt(selRow, 1) + ", Phone = "
							+ cTable.getValueAt(selRow, 2) + ", Type = " + cTable.getValueAt(selRow, 3)
							+ ", Discount = " + cTable.getValueAt(selRow, 3);
					
					Statement stmt = conn.createStatement();
					int ins = stmt.executeUpdate("DELETE FROM smcprototype.clients WHERE idClients='" + pID + "'");
					GUIWorkingPanels.cTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.clients"));
					
					String qury = "INSERT into smcprototype.log_clients (idClient,action,prevState) VALUES ("
							+ pID + ",'Delete','" + prState + "')";
					ins = stmt.executeUpdate(qury);
				} catch (SQLException e) {			
					e.printStackTrace();
				}			
			});
			
			return cPanel;
		}
		
		void addType(Connection conn) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT Type FROM smcprototype.goods WHERE Store='" + branch + "'");
				TreeSet<String> ss = new TreeSet<String>();
				while(rs.next())
					ss.add(rs.getString(1));
				Vector<String> v = new Vector<String>(ss);
				v.addElement("All");
				cbtype.setModel(new DefaultComboBoxModel<String>(v));
				cbtype.setSelectedItem("All");
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}
		
		void addName(Connection conn) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT Name FROM smcprototype.goods WHERE Store='" + branch + "'");
				TreeSet<String> ss = new TreeSet<String>();
				while(rs.next())
					ss.add(rs.getString(1));
				Vector<String> v = new Vector<String>(ss);
				v.addElement("All");
				cbname.setModel(new DefaultComboBoxModel<String>(v));
				cbname.setSelectedItem("All");
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}
		
		void addSize(Connection conn) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT Size FROM smcprototype.goods WHERE Store='" + branch + "'");
				TreeSet<String> ss = new TreeSet<String>();
				while(rs.next())
					ss.add(rs.getString(1));
				Vector<String> v = new Vector<String>(ss);
				v.addElement("All");
				cbsize.setModel(new DefaultComboBoxModel<String>(v));
				cbsize.setSelectedItem("All");
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}
		
		void addColor(Connection conn) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT Color FROM smcprototype.goods WHERE Store='" + branch + "'");
				TreeSet<String> ss = new TreeSet<String>();
				while(rs.next())
					ss.add(rs.getString(1));
				Vector<String> v = new Vector<String>(ss);
				v.addElement("All");
				cbcolor.setModel(new DefaultComboBoxModel<String>(v));
				cbcolor.setSelectedItem("All");
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}
		
		void changeSellTable(Connection conn) {
			String qry = qury();
			try {
				gSellTable.setModel(DataProcessing.showTable(conn, qry));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String qury() {
			String type = (String) cbtype.getSelectedItem();
			String name = (String) cbname.getSelectedItem();
			String size = (String) cbsize.getSelectedItem();
			String color = (String) cbcolor.getSelectedItem();
			String qry = "";
			if (type.equals("All")&&name.equals("All")&&size.equals("All")&&color.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "'";
			else if (type.equals("All")&&name.equals("All")&&size.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Color='" + color + "'";
			else if (type.equals("All")&&name.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch
				+ "' AND Size='" + size + "' AND Color='" + color + "'";
			else if (type.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Name='" + name
				+ "' AND Size='" + size + "' AND Color='" + color + "'";
			else if (name.equals("All")&&size.equals("All")&&color.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Type='" + type + "'";
			else if (name.equals("All")&&size.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Type='" + type
				+ "' AND Color='" + color + "'";
			else if (name.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Type='" + type
				+ "' AND Size='" + size + "' AND Color='" + color + "'";
			else if (size.equals("All")&&color.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Name='" + name
				+ "' AND Type='" + type + "'";
			else if (size.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Name='" + name
				+ "' AND Type='" + type + "' AND Color='" + color + "'";
			else if (color.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Name='" + name
				+ "' AND Size='" + size + "' AND Type='" + type + "'";
			else if (type.equals("All")&&size.equals("All")&&color.equals("All")) 
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Name='" + name	+ "'";
			else if (type.equals("All")&&name.equals("All")&&color.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Size='" + size + "'";
			else if (name.equals("All")&&color.equals("All"))
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Type='" + type
				+ "' AND Size='" + size + "'";
			else
				qry = "SELECT * FROM smcprototype.goods WHERE Store='" + branch + "' AND Type='" + type
				+ "' AND Name='" + name	+ "' AND Size='" + size + "' AND Color='" + color + "'";
			return qry;			
		}
}
