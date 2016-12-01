

public class Enemy extends Thing{

	int score=0;
	
	public Enemy(int id) {
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
	
	public void move(Bullet bullet){
		ScriptManager.loadScript("ai_difficulty.js");
		ScriptManager.bindArgument("ai", this.motionComponent);
		ScriptManager.executeScript();
		
		if(bullet.motionComponent.vx>0){
			if(this.R.getCenterY()<bullet.R.getCenterY()){
				this.R.y+=this.motionComponent.vy;
			}
			else if(this.R.getCenterY()>bullet.R.getCenterY()){
				this.R.y-=this.motionComponent.vy;
			}
			else{
				this.R.y = this.R.y;
			}
		}
		else{
			if(this.R.getCenterY()<310){
				this.R.y+=this.motionComponent.vy;
			}
			else if(this.R.getCenterY()>390){
				this.R.y-=this.motionComponent.vy;
			}
			else{
				this.R.y = this.R.y;
			}
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString(){
		return "MovingPlatform"+","+this.id+","+this.R.x+","+this.R.y+",";
	}
}
