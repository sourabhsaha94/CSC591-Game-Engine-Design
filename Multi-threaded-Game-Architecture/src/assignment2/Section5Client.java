/*
 * This class is responsible for instantiating a client
 *  and its input and output streams*/

package assignment2;


import java.awt.Rectangle;
import java.io.*;
import processing.core.PApplet;
import java.net.*;
import java.util.Scanner;




public class Section5Client extends PApplet{

	Thing player;
	
	public void settings(){
		size(800, 800);
	}
	
	public void setup(){
		
		
		player = new Thing("player",new Rectangle(50,550,50,50),0,0,255,255,0);
		ObjectInputStream in =null;
		ObjectOutputStream out = null;
	
		Socket socket = null;
		try {
			socket = new Socket("localhost",9000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(socket.getLocalSocketAddress()+" connected to server..");	//print unique client identifier
		
		Section5ClientOut sender = new Section5ClientOut(out,player);	//start sender thread
		Thread t_sender = new Thread(sender);
		t_sender.setDaemon(true);
		t_sender.start();
		
		Section5ClientIn recieve = new Section5ClientIn(in,player);	//start receiver thread
		Thread t_recieve = new Thread(recieve);
		t_recieve.setDaemon(true);
		t_recieve.start();

	}
	
	public void draw(){
		clear();
		fill(255,0,0);
		rect(player.R.x+=player.vx,player.R.y+=player.vy,player.R.width,player.R.height);
		System.out.println("Current value of x: "+player.R.x);
	}
	
	public static void main(String argv[]){
	
		//Section5Client c = new Section5Client();
		PApplet.main("assignment2.Section5Client");		
	}

}
