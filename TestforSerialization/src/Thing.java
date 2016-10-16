
import java.awt.*;
import java.io.Serializable;


public abstract class Thing implements Serializable {	//class encapsulating a game object i.e. a rectangle and its velocity and position
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8768701979073409494L;
	protected Rectangle R;
	protected int id;
	
	protected CollisionComponent collisionComponent;
	protected HIDComponent hidComponent;
	protected MotionComponent motionComponent;
	protected JumpComponent jumpComponent;
	protected ColorComponent colorComponent;
	
	public Thing(int id){
		this.id = id;
		collisionComponent = null;
		hidComponent = null;
		motionComponent = null;
		jumpComponent = null;
		colorComponent = null;
	}
}
