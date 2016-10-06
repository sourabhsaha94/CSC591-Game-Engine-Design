package assignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Section5ClientIn implements Runnable {

	ObjectInputStream in;
	String message;
	Thing player;
	
	public Section5ClientIn(ObjectInputStream in,Thing t){
		this.in = in;
		this.player = t;
	}
	
	@Override
	public void run() {
		
		Integer newPos=0;
		
		while(true){
		
			while(!newPos.equals(null)){
			
				try{
					newPos=(Integer)in.readObject();
					player.R.x=newPos;
				}catch(Exception e){
					break;
				}
					//System.out.println(newPos);
			}
		}
	}
}
