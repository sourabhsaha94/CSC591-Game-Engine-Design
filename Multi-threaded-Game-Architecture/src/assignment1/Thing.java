package assignment1;
import java.awt.*;
import java.io.Serializable;


public class Thing implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8768701979073409494L;
	Rectangle R;
	String name;
	int vx,vy;
	int r,g,b;
	
	Thing(String name,Rectangle R,int vx,int vy,int r,int g,int b){
		this.name = name;
		this.R= R;
		this.vx = vx;
		this.vy = vy;
		this.r=r;
		this.g=g;
		this.b=b;
	}
	Thing(String name){
		this.name = name;
	}
}
