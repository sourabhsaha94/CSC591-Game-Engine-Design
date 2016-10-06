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
	int r=0;
	int g=0;
	int b=0;
	
	Message(String s,int x,int y,int r,int g,int b){
		this.socketAddress = s;
		this.x = x;
		this.y = y;
		this.r=r;
		this.g=g;
		this.b=b;
	}
	
}
