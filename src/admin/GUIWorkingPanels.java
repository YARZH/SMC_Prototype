package admin;

import java.awt.Font;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;

import org.json.JSONObject;

import java.sql.*;
import server.DataProcessing;

public class GUIWorkingPanels extends GUIMainPanel {
	
	Font reg = new Font("reg", 1, 20);
	JPanel wPanel;
	JPanel gPanel;
	JPanel cPanel;
	JPanel rPanel;
	public static JTable wTable = null;
	public static JTable gTable = null;
	public static JTable cTable = null;
	Connection conn = null;
	
	GUIWorkingPanels(String log, Connection conn) {
		super(log);
		setLayout(null);
		
		this.conn = conn;
	
		JTabbedPane aPanel = new JTabbedPane();
		aPanel.setFont(reg);
		aPanel.setBounds(0, 0, 1280, 720);
		aPanel.add("Рабочие", panelWorkers());
		aPanel.add("Товары", panelGoods());
		aPanel.add("Клиенты", panelClients());
		aPanel.add("Отчёты", panelReports());
		add(aPanel);
		
		setVisible(true);	
	}
	
	//Panel for adding, changing, deleting workers
	JPanel panelWorkers() {
		wPanel = new JPanel();
		wPanel.setBounds(0, 40, 1280, 680);
		wPanel.setLayout(null);
		
		try {
			wTable = new JTable(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.workers"));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
				
		JScrollPane sp1 = new JScrollPane(wTable);
		sp1.setBounds(10, 10, 1255, 580);
		wPanel.add(sp1);
		
		JButton wAdd = new JButton("Добавить");
		wAdd.setBounds(330, 599, 200, 38);
		wAdd.setFont(reg);
		wPanel.add(wAdd);
		
		JButton wChange = new JButton("Изменить");
		wChange.setBounds(540, 599, 200, 38);
		wChange.setFont(reg);
		wPanel.add(wChange);
		
		JButton wRemove = new JButton("Удалить");
		wRemove.setBounds(750, 599, 200, 38);
		wRemove.setFont(reg);
		wPanel.add(wRemove);
		
		getContentPane().add(wPanel);
		wPanel.setVisible(true);
		
		wAdd.addActionListener((ae) -> {
			new AddWorkerPanel("Добавление работника", conn);			
		});
		
		wChange.addActionListener((ae) -> {
			new ChangeWorkerPanel("Изменение работника", conn);
		});
		
		wRemove.addActionListener((ae) -> {
			try {
				int selRow = wTable.getSelectedRow();
				long pID = (long) wTable.getValueAt(selRow, 0);
				String prState = "idWorkers = " + pID + ", Name = " + wTable.getValueAt(selRow, 1) + ", Store = "
						+ wTable.getValueAt(selRow, 2) + ", Position = " + wTable.getValueAt(selRow, 3) + ", Phone = " + wTable.getValueAt(selRow, 4) 
						+ ", Account = " + wTable.getValueAt(selRow, 5) + ", Number = " + wTable.getValueAt(selRow, 6);
				
				Statement stmt = conn.createStatement();
				int ins = stmt.executeUpdate("DELETE FROM smcprototype.workers WHERE idWorkers='" + pID + "'");
				GUIWorkingPanels.wTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.workers"));
											
				String qury = "INSERT into smcprototype.log_workers (idWorker,action,prevState) VALUES ("
						+ pID + ",'Delete','" + prState + "')";
				ins = stmt.executeUpdate(qury);
			} catch (SQLException e) {			
				e.printStackTrace();
			}			
		});
		
		return wPanel;
	}
	
	//Panel for adding, changing, deleting goods
	JPanel panelGoods() {		
		gPanel = new JPanel();
		gPanel.setBounds(0, 40, 1280, 680);
		gPanel.setLayout(null);
				
		try {
			gTable = new JTable(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.goods"));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		JScrollPane sp1 = new JScrollPane(gTable);
		sp1.setBounds(10, 10, 1255, 580);
		gPanel.add(sp1);
		
		JButton gAdd = new JButton("Добавить");
		gAdd.setBounds(330, 599, 200, 38);
		gAdd.setFont(reg);
		gPanel.add(gAdd);
		
		JButton gChange = new JButton("Изменить");
		gChange.setBounds(540, 599, 200, 38);
		gChange.setFont(reg);
		gPanel.add(gChange);
		
		JButton gRemove = new JButton("Удалить");
		gRemove.setBounds(750, 599, 200, 38);
		gRemove.setFont(reg);
		gPanel.add(gRemove);
		
		getContentPane().add(gPanel);
		gPanel.setVisible(true);
		
		gAdd.addActionListener((ae) -> {
			new AddGoodsPanel("Добавление товара", conn);			
		});
		
		gChange.addActionListener((ae) -> {
			new ChangeGoodsPanel("Изменение товара", conn);
		});
		
		gRemove.addActionListener((ae) -> {
			try {
				int selRow = gTable.getSelectedRow();
				long pID = (long) gTable.getValueAt(selRow, 0);
				long pQty = (long) gTable.getValueAt(selRow, 6);
				String branch = (String) gTable.getValueAt(selRow, 1);
				String name = (String) gTable.getValueAt(selRow, 3);
				String prState = "idGoods = " + pID + ", Store = " + gTable.getValueAt(selRow, 1) + ", Type = "
						+ gTable.getValueAt(selRow, 2) + ", Name = " + gTable.getValueAt(selRow, 3) + ", Size = " + gTable.getValueAt(selRow, 4) 
						+ ", Color = " + gTable.getValueAt(selRow, 5) + ", Quantity = " + pQty;
				
				Statement stmt = conn.createStatement();
				int ins = stmt.executeUpdate("DELETE FROM smcprototype.goods WHERE idGoods='" + gTable.getValueAt(gTable.getSelectedRow(), 0) + "'");
				GUIWorkingPanels.gTable.setModel(DataProcessing.showTable(conn, "SELECT * FROM smcprototype.goods"));
				
				String qury = "INSERT into smcprototype.log_goods (idGood,Branch,Name,action,prevState,prevQty) VALUES ("
						+ pID + ",'" + branch + "','" + name + "','Delete','" + prState + "'," + pQty + ")";
				ins = stmt.executeUpdate(qury);
			} catch (SQLException e) {			
				e.printStackTrace();
			}			
		});
		
		return gPanel;
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
	
	//Panel for reports creating
	JPanel panelReports() {
		rPanel = new JPanel();
		rPanel.setBounds(0, 40, 1280, 680);
		rPanel.setLayout(null);
		
		JLabel rl1 = new JLabel("Продажи:");
		rl1.setFont(reg);
		rl1.setBounds(10, 10, 200, 40);
		rPanel.add(rl1);
		
		JLabel rl2 = new JLabel("по филиалам");
		rl2.setFont(reg);
		rl2.setBounds(30, 50, 300, 40);
		rPanel.add(rl2);
		
		JButton rFilRep = new JButton("Отчёт");
		rFilRep.setBounds(650, 50, 100, 40);
		rFilRep.setFont(reg);
		rPanel.add(rFilRep);
		
		JLabel rl3 = new JLabel("по продукту/категории");
		rl3.setFont(reg);
		rl3.setBounds(30, 100, 300, 40);
		rPanel.add(rl3);
		
//		JComboBox<String> rCBPrRep = new JComboBox<String>();
//		rCBPrRep.setFont(reg);
//		rCBPrRep.setBounds(340, 100, 300, 40);
//		rPanel.add(rCBPrRep);
		
		JButton rPrRep = new JButton("Отчёт");
		rPrRep.setBounds(650, 100, 100, 40);
		rPrRep.setFont(reg);
		rPanel.add(rPrRep);
		
		JLabel rl4 = new JLabel("Список клиентов VIP");
		rl4.setFont(reg);
		rl4.setBounds(10, 150, 300, 40);
		rPanel.add(rl4);
		
		JButton rVipRep = new JButton("Отчёт");
		rVipRep.setBounds(650, 150, 100, 40);
		rVipRep.setFont(reg);
		rPanel.add(rVipRep);
		
		getContentPane().add(rPanel);
		rPanel.setVisible(true);
		
		rFilRep.addActionListener((ae) -> {
			rep("Продажи по филиалам","SELECT Branch, SUM(diff) AS Sales FROM smcprototype.log_goods WHERE diff>0 AND action='Sell' GROUP BY Branch","branch","sales","BranchSales.dat");
		});
		
		rPrRep.addActionListener((ae) -> {
			rep("Продажи по продукту","SELECT Name, SUM(diff) AS Sales FROM smcprototype.log_goods WHERE diff>0 AND action='Sell' GROUP BY Name","name","sales","ProductSales.dat");
		});
		
		rVipRep.addActionListener((ae) -> {
			rep("Клиенты VIP","SELECT Name,Phone FROM smcprototype.clients WHERE Type = 'vip'","name","phone","ClientsVIP.dat");
		});
		
		return rPanel;
	}
	
	void rep(String title, String query, String str1, String str2, String fileName) {
		JFrame vr = new JFrame(title);
		vr.setSize(800, 600);
		vr.setResizable(false);
		vr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		vr.setLocationRelativeTo(null);
		vr.setLayout(null);
		
		JTextArea ta = new JTextArea();
		ta.setFont(reg);
		ta.setEditable(false);
		ta.setLineWrap(true);
		JScrollPane sp = new JScrollPane(ta);		
		sp.setBounds(10, 10, 775, 510);
		vr.add(sp);
		
		JButton ok = new JButton("OK");
		ok.setFont(reg);
		ok.setBounds(240, 520, 150, 40);
		vr.add(ok);
		
		JButton toFile = new JButton("В файл");
		toFile.setFont(reg);
		toFile.setBounds(410, 520, 150, 40);
		vr.add(toFile);		
        
        vr.setVisible(true);
        
        JSONObject jso = new JSONObject();
        
        try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ta.append(str1 + "\t" + str2 + "\r\n");
			while(rs.next()) {
				jso.append(str1, rs.getString(1));
				jso.append(str2, rs.getString(2));
				ta.append(rs.getString(1) + "\t" + rs.getString(2) + "\r\n");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
        //ta.setText(jso.toString());
        
        toFile.addActionListener((ae) -> {
        	try (DataOutputStream da = new DataOutputStream(new FileOutputStream(fileName))) {
        		System.out.println(jso.toString());
        		da.writeUTF(jso.toString());
        	} catch (FileNotFoundException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
        });
        
        ok.addActionListener((ae) -> {
        	vr.dispose();
        });
	}
}
