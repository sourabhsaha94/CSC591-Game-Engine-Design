

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

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
	ArrayList<Player> pList=new ArrayList<>();
	ArrayList<StaticPlatform> spList=new ArrayList<>();
	ArrayList<MovingPlatform> mpList=new ArrayList<>();
		
	Message(int id,int x,int y,int r,int g,int b){
		this.id = id;
		this.x = x;
		this.y = y;
		this.r=r;
		this.g=g;
		this.b=b;
	}
	
	Message(int id,Player P,int r,int g,int b){
		this.id = id;
		if(P!=null)
			this.pList.add(P);
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	Message(int id){
		this.id = id;
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
