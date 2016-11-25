

import java.io.Serializable;

public class MotionComponent implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Thing t;
	int vx,vy;
	int border=800;
	public MotionComponent(Thing t){
		vx=0;
		vy=0;
		this.t=t;
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
	
	/*public void update(){
		if(t.R.getMaxX()==border || t.R.x==0){
			vx=-vx;
			t.direction = -t.direction;
		}
	}*/
}
