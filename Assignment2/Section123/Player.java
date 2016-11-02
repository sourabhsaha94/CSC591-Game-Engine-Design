

public class Player extends Thing{
	
	SpawnPoint s;
	
	public Player(int id) {
		super(id);
		this.collisionComponent = new CollisionComponent(this);
		this.colorComponent = new ColorComponent(0,0,0);
		this.jumpComponent = new JumpComponent(this);
		this.motionComponent = new MotionComponent(this);
		// TODO Auto-generated constructor stub
	}

	public void addSpawnPoint(SpawnPoint s){
		this.s =s;
	}
	
	public void Spawn(){
		s.reset(this);
	}
	
	public void move(){
		this.R.x+=this.motionComponent.vx;
		this.R.y+=this.motionComponent.vy;
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
