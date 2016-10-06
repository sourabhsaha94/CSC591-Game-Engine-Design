package assignment2;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -870309628060190905L;

	String socketAddress=null;
	int x=0;
	int y=0;
	
	Message(String s,int x,int y){
		this.socketAddress = s;
		this.x = x;
		this.y = y;
	}
	
}
