package admin;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import chat.*;

public class GUIMainPanel extends GUI {
	
	Font reg = new Font("reg", 1, 20);
	
	GUIMainPanel(String log) {
		super();
		setTitle(log + " - SMSys-Prototype");
		setLayout(null);
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.setBounds(1190, 0, 90, 40);
		exitBtn.setFont(reg);
		add(exitBtn);
		
		JButton chatBtn = new JButton("Chat");
		chatBtn.setBounds(1190, 645, 90, 40);
		chatBtn.setFont(reg);
		add(chatBtn);
		
		setVisible(true);
		
		//Exit button activity
		exitBtn.addActionListener((ae) -> {
			exitConfirmation();
		});
		
		//Chat button activity
		chatBtn.addActionListener((ae) -> {			
			try {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							new ChatClient(log, GUI.chatserver, GUI.chatport);
						} catch (Exception e) {							
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}			
		});
		
	}
	
	// Exit confirmation window
	void exitConfirmation() {
		JFrame exitConf = new JFrame("SMSys-Prototype");
		exitConf.setSize(200, 150);
		exitConf.setResizable(false);
		exitConf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		exitConf.setLayout(null);					
		exitConf.setLocationRelativeTo(null);
        
        JButton yesBtn = new JButton("Yes");
        yesBtn.setBounds(10, 30, 80, 50);
        yesBtn.setFont(reg);
        exitConf.add(yesBtn);
        
        JButton noBtn = new JButton("No");
        noBtn.setBounds(105, 30, 80, 50);
        noBtn.setFont(reg);
        exitConf.add(noBtn);
        
        exitConf.setVisible(true);
        
        yesBtn.addActionListener((ae) -> {
        	try {
        		System.exit(0);
        	} catch(SecurityException ie) {}
        });
        
        noBtn.addActionListener((ae) -> {
        	exitConf.dispose();
        });
	}
}
