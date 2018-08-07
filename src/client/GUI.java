package client;

import java.awt.Font;
import javax.swing.*;
import java.sql.*;

public class GUI extends JFrame {
	
	Font reg = new Font("reg", 1, 20);
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	JLabel passTry;
	public static String chatserver;
	public static int chatport;
	public static String DBServer;
	
	//Making new window
	GUI() {
			super("SMSys-Prototype");
			setSize(1280, 720);
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
			setVisible(true);					
            setLocationRelativeTo(null);			
            
	}
	
	//Making title window
	public void titleWindow() {			
		JPanel titleWin = new JPanel();
		
		titleWin.setLayout(null);

		JLabel jlab = new JLabel("SMSys-Prototype");
		jlab.setFont(new Font("fnt", 1, 90));
		jlab.setBounds(200, 250, 880, 150);
		jlab.setHorizontalAlignment(SwingConstants.CENTER);
		titleWin.add(jlab);
		
		JButton jbtn = new JButton("Enter the system");
		jbtn.setFont(new Font("fnt", 1, 20));
		jbtn.setBounds(540, 590, 200, 40);
		titleWin.add(jbtn);
		
		titleWin.setVisible(true);
		
		jbtn.addActionListener((me) -> {
				titleWin.setVisible(false);
				loginWindow();
			});
		
		getContentPane().add(titleWin);		
	}
	
	//Making login-password window
	public void loginWindow() {
		JPanel logWin = new JPanel();
		
		logWin.setLayout(null);
		
		JLabel logLab = new JLabel("Login");
		logLab.setFont(reg);
		logLab.setBounds(440, 250, 200, 50);
		logWin.add(logLab);
		
		JLabel passLab = new JLabel("Password");
		passLab.setFont(reg);
		passLab.setBounds(440, 310, 200, 50);
		logWin.add(passLab);
		
		JTextField logText = new JTextField();
		logText.setFont(reg);
		logText.setBounds(640, 250, 200, 50);
		logWin.add(logText);
		
		JPasswordField passText = new JPasswordField();
		passText.setFont(reg);
		passText.setBounds(640, 310, 200, 50);
		logWin.add(passText);
		
		JButton logBtn = new JButton("Login");
		logBtn.setFont(reg);
		logBtn.setBounds(540, 590, 200, 50);
		logWin.add(logBtn);
		
		passTry = new JLabel("");
		passTry.setFont(reg);
		passTry.setBounds(850, 310, 300, 50);
		logWin.add(passTry);
		
		getContentPane().add(logWin);
		
		logWin.setVisible(true);
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {						
			conn = DriverManager.getConnection("jdbc:mysql://"+DBServer, "worker", "v@hb[efyf,k@");
		} catch (SQLException e) {						
			e.printStackTrace();
		}
		
		//Обработка нажатия Enter в поле Password
		passText.addActionListener((ae) -> {
			auth(logText, passText);
		});
		
		//Обработка нажатия кнопки Login
		logBtn.addActionListener((ae) -> {
			auth(logText, passText);
		});
	}
	
	public static void main(String[] args) {
		chatserver = args[0];
		chatport = Integer.parseInt(args[1]);
		DBServer = args[2];
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI mainGUI = new GUI();
				mainGUI.titleWindow();
			}
		});		
	}
	
	public void auth(JTextField logText, JPasswordField passText) {
		final String log = logText.getText();
		try {
			final char[] pass = passText.getPassword();
			String pass1 = new String(pass);
			if(pass.length == 0) passTry.setText("Enter the password");
			else {				
				try {
					stmt = conn.createStatement();
					String qury = "SELECT * FROM smcprototype.workers WHERE (UserName) like '" + log + "'";
					rs = stmt.executeQuery(qury);
					if(!rs.next()) passTry.setText("Invalid username or password");
					else {							
						if(rs.getString("Password").equals(pass1)) {
							String branch = rs.getString("Store");
							setVisible(false);
							new GUIWorkingPanels(log, branch, conn);
						}
						else passTry.setText("Invalid username or password");
					}						
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					if(rs!=null) {
						try {
							rs.close();
						} catch (SQLException e) {
							rs = null;
							e.printStackTrace();
						}
					}
					if(stmt!=null) {
						try {
							stmt.close();
						} catch (SQLException e) {
							stmt = null;								
						}
					}
				}
			}
		} catch(NullPointerException npe) {
			passTry.setText("Enter the password");
		}	
	}
}
