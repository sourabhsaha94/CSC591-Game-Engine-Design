package section5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import assignment1.Thing;

public class Section5ClientIn implements Runnable {

	ObjectInputStream in;
	Thing thing;
	
	public Section5ClientIn(ObjectInputStream in){
		this.in = in;
	}
	
	@Override
	public void run() {
		
		try {
			while((thing=(Thing)in.readObject())!=null){
				//System.out.println(thing);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
