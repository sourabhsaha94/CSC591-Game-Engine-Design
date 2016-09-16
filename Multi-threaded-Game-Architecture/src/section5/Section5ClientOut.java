/*This class is responsible for sending message to the server
 * */

package section5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

import assignment1.Thing;

public class Section5ClientOut implements Runnable {
	
	private ObjectOutputStream out;
	Section5Client sc;
	Thing thing;
	
	
	public Section5ClientOut(Section5Client sc,ObjectOutputStream out){

		this.out = out;	
		this.sc = sc;
		this.thing = sc.thingList.get(0);
	}

	private void sendMessageToServer(Thing thing) throws IOException{

		out.writeObject(thing);

	}

	
	public void run(){

		try {

			while (true) {
				sendMessageToServer(sc.thingList.get(0));
			}

		} catch (Exception ioe) {

			// Communication is broken

		}
	}
}
