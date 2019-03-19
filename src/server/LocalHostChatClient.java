package server;

import java.io.*;
import java.net.*;

public class LocalHostChatClient {
	
	static String message;
	static String ip = "localhost";
	static Socket sock;

	public static void main(String[] args) throws IOException {
		sock = new Socket(ip, LocalHostChatServer.port);
		System.out.println("Connected");
		while (true) {
			getNewMessage();
			System.out.println(message);
			if (message.equals("exit")) {
            	break;
            }
        }
		sock.close();
	}

	private static void getNewMessage() {
		try {
			InputStream in = sock.getInputStream();
			DataInputStream dIn = new DataInputStream(in);
	        message = dIn.readUTF();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
    }
}
