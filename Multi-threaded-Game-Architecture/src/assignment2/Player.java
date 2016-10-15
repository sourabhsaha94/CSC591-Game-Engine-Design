package assignment2;

public class Player extends Thing{
	
	public Player(int id) {
		super(id);
		this.collisionComponent = new CollisionComponent(this);
		this.colorComponent = new ColorComponent(0,0,0);
		this.jumpComponent = new JumpComponent(this);
		this.motionComponent = new MotionComponent(this);
		this.hidComponent = new HIDComponent(this);
		// TODO Auto-generated constructor stub
	}

	
	public void setPlayerColor(int r,int g,int b){
		this.colorComponent.update(r,g,b);
	}
	
	public void setPlayerVelocity(int vx,int vy){
		this.motionComponent.setVelocity(vx, vy);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	


}
