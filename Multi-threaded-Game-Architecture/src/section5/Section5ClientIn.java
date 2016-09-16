package section5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;
import java.util.Vector;

import assignment1.Thing;

public class Section5ClientIn implements Runnable {

	ObjectInputStream in;
	Section5Client sc;
	
	public Section5ClientIn(Section5Client sc,ObjectInputStream in){
		this.in = in;
		this.sc = sc;
	} 
	
	public void addToList(Thing thing){
		
		if(sc.thingList.contains(thing)){
			sc.thingList.remove(thing);
		}
		
		sc.thingList.add(thing);
	}

	
	@Override
	public void run() {
		boolean check=true;
		
		while(check){
			Thing thing = null;

			while(check){
				try{
					thing = (Thing)in.readObject();
				}
				catch(Exception e){
					break;
				}
				addToList(thing);
			}
		}


		Thread.currentThread().interrupt();
	}
}
