package notourcode;

import java.io.*;
import java.net.*;

//from Canvas - Phil posted this & EV3Server
public class PCClient {

	public static void main(String[] args) throws IOException {
		String ip = "10.0.1.1"; // BT
		if(args.length > 0)
			ip = args[0];
		Socket sock = new Socket(ip, EV3Server.port);
		System.out.println("Connected");
		InputStream in = sock.getInputStream();
		DataInputStream dIn = new DataInputStream(in);
		String str = dIn.readUTF();
		System.out.println(str);
		sock.close();
	}
}
