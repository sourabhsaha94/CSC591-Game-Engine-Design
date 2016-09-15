/*This class is responsible for sending message to the server
 * */

package section5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Section5ClientOut implements Runnable {
	
	private PrintWriter out;

	public Section5ClientOut(PrintWriter out){

		this.out = out;

	}

	public void run(){

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while (!Thread.interrupted()) {

				String message = in.readLine();

				out.println(message);

				out.flush();

			}

		} catch (IOException ioe) {

			// Communication is broken

		}

	}
}
