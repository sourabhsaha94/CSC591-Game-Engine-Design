package section5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Section5ClientIn implements Runnable {

	BufferedReader in;
	String message;
	
	public Section5ClientIn(BufferedReader in){
		this.in = in;
	}
	
	@Override
	public void run() {
		
		try {
			while((message=in.readLine())!=null){
				System.out.println(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
