package assignment2Section1;

import java.util.LinkedList;

public class Player extends GameObject{

	LinkedList<Component> cList;
	
	Player(int x, int y) {
		super(x, y);
		cList = super.cList;
		cList.add(new MotionComponent(this, 0, 0));
	}
	
	
	
}
