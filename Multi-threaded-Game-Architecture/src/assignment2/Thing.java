package assignment2;
import java.awt.*;
import java.io.Serializable;


public class Thing implements Serializable {	//class encapsulating a game object i.e. a rectangle and its velocity and position
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8768701979073409494L;
	public Rectangle R;
	public int id;
	public int vx,vy;
	public int r,g,b;
	
	public Thing(int id,Rectangle R,int vx,int vy,int r,int g,int b){
		this.id = id;
		this.R= R;
		this.vx = vx;
		this.vy = vy;
		this.r=r;
		this.g=g;
		this.b=b;
	}
	public Thing(int id){
		this.id = id;
	}
}
