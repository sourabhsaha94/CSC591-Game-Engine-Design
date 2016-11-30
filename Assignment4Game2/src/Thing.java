
import java.awt.*;
import java.io.Serializable;


public abstract class Thing implements Serializable {	//class encapsulating a game object i.e. a rectangle and its velocity and position
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8768701979073409494L;
	protected Rectangle R;
	protected int id;
	int direction;
	protected CollisionComponent collisionComponent;
	protected MotionComponent motionComponent;
	protected fireComponent fireComponent;
	protected ColorComponent colorComponent;
	
	public Thing(int id){
		this.id = id;
		this.direction = 1;
		collisionComponent = null;
		motionComponent = null;
		fireComponent = null;
		colorComponent = null;
	}
}