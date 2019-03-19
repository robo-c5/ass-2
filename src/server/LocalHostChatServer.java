package server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LocalHostChatServer {

	public static final int port = 1234;
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(port);
		System.out.println("Awaiting client..");
		Socket client = server.accept();
		System.out.println("CONNECTED");
		String message = "";
		Scanner in = new Scanner(System.in);
		while (true) {
			OutputStream out = client.getOutputStream();
			DataOutputStream dOut = new DataOutputStream(out);
			System.out.println("Write a message");
			message = in.nextLine();
			dOut.writeUTF(message);
			dOut.flush();
			if (message.equals("exit")) {
				break;
			}
		}
		in.close();
		server.close();
	}
}
