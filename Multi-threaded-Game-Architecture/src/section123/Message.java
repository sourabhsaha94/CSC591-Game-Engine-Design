package section123;


import java.io.Serializable;
import java.util.LinkedList;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -870309628060190905L;

	int id=0;
	int x=0;
	int y=0;
	int r=0;
	int g=0;
	int b=0;
	LinkedList<StaticPlatform> sp;
	LinkedList<MovingPlatform> mp;
	LinkedList<DeathZone> dz;
	
	Message(int id,int x,int y,int r,int g,int b){
		this.id = id;
		this.x = x;
		this.y = y;
		this.r=r;
		this.g=g;
		this.b=b;
	}
	

	void putValues(int id,int x,int y,int r,int g,int b){
		this.id = id;
		this.x = x;
		this.y = y;
		this.r=r;
		this.g=g;
		this.b=b;
	}
	
}
