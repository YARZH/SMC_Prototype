package chat;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.*;
import java.time.format.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.JSONObject;


public class ChatClient extends JFrame implements Runnable {
	
	Font reg = new Font("reg", 1, 20);
	JTextArea chatWin = new JTextArea();
	JTextArea messWin = new JTextArea();
	Socket cl = null;
	Thread listener;
	static String logn;
	
	public ChatClient(String log, String server, int port) throws IOException {		
		super(log + " - SMSys-Prototype");
		logn = log;
		setLayout(null);
		setSize(600, 400);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		setLocationRelativeTo(null);
		
		
		chatWin.setFont(reg);
		chatWin.setEditable(false);
		chatWin.setLineWrap(true);
		JScrollPane sp1 = new JScrollPane(chatWin);
		sp1.setBounds(10, 10, 575, 250);
		add(sp1);
						
		messWin.setFont(reg);
		messWin.setLineWrap(true);
		JScrollPane sp2 = new JScrollPane(messWin);
		sp2.setBounds(10, 270, 470, 85);
		add(sp2);
		
		JButton sendMes = new JButton("Send");
		sendMes.setBounds(495, 305, 90, 40);
		sendMes.setFont(reg);
		add(sendMes);
		setVisible(true);
		
		
		try {
			cl = new Socket(server, port);
		} catch (UnknownHostException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		DataOutputStream o = new DataOutputStream(new BufferedOutputStream(cl.getOutputStream()));
		
		sendMes.addActionListener((ae) -> {
			try {
				String mestxt = messWin.getText();
				o.writeUTF(toJSON(mestxt));
				o.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			messWin.setText(null);
		});
		
		listener = new Thread(this);
		listener.start();
	}
	
	public void run() {
		DataInputStream i = null;
		try {
			i = new DataInputStream(new BufferedInputStream(cl.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(true) {			
				String inp;
				inp = i.readUTF();
				chatWin.append(fromJSON(inp) + "\n");
			}	
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	}
	
	static String toJSON(String mestxt) {
		JSONObject outJSON = new JSONObject();
		outJSON.put("user", logn);
		LocalDateTime now = LocalDateTime.now();
		String date = "[" + now.format(DateTimeFormatter.ofPattern("dd'-'MM'-'yyyy HH':'mm':'ss")) + "]: ";
		outJSON.put("datetime", date);
		outJSON.put("message", mestxt);
		return outJSON.toString();		
	}
	
	static String fromJSON(String serv) {
		JSONObject chatdata = new JSONObject(serv);
		String fromserv = chatdata.getString("user") + " ";
		fromserv += chatdata.getString("datetime");
		fromserv += chatdata.getString("message");
		return fromserv;
	}
}
