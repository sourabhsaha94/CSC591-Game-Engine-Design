/*
 * This class is responsible for instantiating a client
 *  and its input and output streams*/

package section5;


import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Section5Client {

	public static void main(String argv[]) throws Exception {
	
		BufferedReader in =null;
		PrintWriter out = null;
	
		Socket socket = new Socket("localhost",9000);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		System.out.println("connected to server..");
		
		Section5ClientOut sender = new Section5ClientOut(out);
		Thread t_sender = new Thread(sender);
		t_sender.setDaemon(true);
		t_sender.start();
		
		String message;
		while((message=in.readLine())!=null){
			System.out.println(message);
		}
	}

}
