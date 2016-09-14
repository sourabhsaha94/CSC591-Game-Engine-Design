package assignment1;
import java.awt.*;
import java.io.Serializable;


public class Thing implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8768701979073409494L;
	public Rectangle R;
	public String name;
	public int vx,vy;
	public int r,g,b;
	
	public Thing(String name,Rectangle R,int vx,int vy,int r,int g,int b){
		this.name = name;
		this.R= R;
		this.vx = vx;
		this.vy = vy;
		this.r=r;
		this.g=g;
		this.b=b;
	}
	public Thing(String name){
		this.name = name;
	}
}
