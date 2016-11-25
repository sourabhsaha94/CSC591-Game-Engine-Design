

public class MovingPlatform extends Thing{

	public MovingPlatform(int id) {
		super(id);
		this.colorComponent = new ColorComponent(0,0,0);
		this.motionComponent = new MotionComponent(this);
	}
	
	public void setPlatformColor(int r,int g,int b){
		this.colorComponent.update(r,g,b);
	}
	
	public void setPlatformVelocity(int vx,int vy){
		this.motionComponent.setVelocity(vx, vy);
	}
	
	public int getXVelocity(){
		return this.motionComponent.vx;
	}
	
	public int getYVelocity(){
		return this.motionComponent.vy;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString(){
		return "MovingPlatform"+","+this.id+","+this.R.x+","+this.R.y+",";
	}
}
