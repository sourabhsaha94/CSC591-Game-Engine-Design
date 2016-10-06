/*This class is responsible for sending message to the server
 * */

package assignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Section5ClientOut implements Runnable {
	
	private ObjectOutputStream out;
	Thing player;

	public Section5ClientOut(ObjectOutputStream out, Thing t){

		this.out = out;
		this.player = t;

	}

	public void run(){

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while(!Thread.interrupted()) {

				Integer oldPos = player.R.x;//in.readLine();
				out.writeObject(oldPos);

			}

		} catch (IOException ioe) {

			// Communication is broken

		}

	}
}
