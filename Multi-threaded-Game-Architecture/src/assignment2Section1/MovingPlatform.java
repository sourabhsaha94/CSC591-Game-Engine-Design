package assignment2Section1;

import java.util.LinkedList;

public class MovingPlatform extends GameObject {

	LinkedList<Component> cList;

	MovingPlatform(int x, int y) {
		super(x, y);
		cList = super.cList;
		cList.add(new MotionComponent(this, 0, 0));
	}

}