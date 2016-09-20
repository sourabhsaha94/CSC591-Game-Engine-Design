package section5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Section5ClientIn implements Runnable {

	ObjectInputStream in;
	String message;
	
	public Section5ClientIn(ObjectInputStream in){
		this.in = in;
	}
	
	@Override
	public void run() {
		
		String message="";
		
		while(true){
		
			while(!message.equalsIgnoreCase(null)){
			
				try{
					message=(String)in.readObject();
				}catch(Exception e){
					break;
				}
					System.out.println(message);
			}
		}
	}
}
