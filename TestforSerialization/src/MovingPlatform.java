

public class MovingPlatform extends Thing{

	public MovingPlatform(int id) {
		super(id);
		this.colorComponent = new ColorComponent(0,255,0);
		this.motionComponent = new MotionComponent(this);
		this.collisionComponent = new CollisionComponent(null);
	}
	
	public void setPlatformXY(int x,int y){
		this.R.x = x;
		this.R.y = y;
	}
	
	public void setPlatformWH(int w,int h){
		this.R.width = w;
		this.R.height = h;
	}
	
	public void setPlatformColor(int r,int g,int b){
		this.colorComponent.update(r,g,b);
	}
	
	public void setPlatformVelocity(int vx,int vy){
		this.motionComponent.setVelocity(vx, vy);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
