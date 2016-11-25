

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
	MessagePriority messagePriority = MessagePriority.LOW;
	Player player;
	ArrayList<Enemy> mpList=new ArrayList<>();
		
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
		this.player = P;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	Message(int id){
		this.id = id;
	}

	void putValues(int id,int x,int y){
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public String toString(){
		String s="";
		
		s+=player.toString();
		
		for(int i=0;i<mpList.size();i++){
			s+=mpList.get(i).toString();
		}
		
		s+="\n";
		return s;
	}
}
