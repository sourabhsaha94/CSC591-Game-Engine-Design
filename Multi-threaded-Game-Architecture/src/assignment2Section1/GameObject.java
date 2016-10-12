package assignment2Section1;

import java.util.LinkedList;

public abstract class GameObject {
	int x,y;
	boolean visible;
	
	LinkedList<Component> cList;
	
	GameObject(int x,int y){
		this.x=x;
		this.y=y;
		visible=false;
		cList = new LinkedList<Component>();
	}
	
	public void addComponent(Component c){
		cList.add(c);
	}
	
	public LinkedList<Component> getComponentList(){
		return cList;
	}
}
