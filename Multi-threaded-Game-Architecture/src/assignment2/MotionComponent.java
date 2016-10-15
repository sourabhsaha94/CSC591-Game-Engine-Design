package assignment2;

public class MotionComponent {
	
	int vx,vy;
	
	public MotionComponent(){
		vx=0;
		vy=0;
	}
	
	public void setVelocity(int vx,int vy){
		this.vx = vx;
		this.vy = vy;
	}
	
	public int getVx(){
		return vx;
	}
	
	public int getVy(){
		return vy;
	}
	
	public void setVx(int vx){
		this.vx=vx;
	}
	
	public void setVy(int vy){
		this.vy=vy;
	}
}
