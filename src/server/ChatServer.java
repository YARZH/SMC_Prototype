package server;

import java.net.*;
import java.io.*;

public class ChatServer {
	
	public ChatServer(int port) throws IOException {
		ServerSocket chatserver = new ServerSocket(port);
		while (true) {
			Socket chatclient = chatserver.accept();
			System.out.println("Accepted from " + chatclient.getInetAddress());
			ChatHandler c = new ChatHandler(chatclient);
			c.start();
		}
	}

	public static void main(String[] args) throws IOException {
		new ChatServer(Integer.parseInt(args[0]));
	}

}