package assignment2Section1;

public class MotionComponent extends Component {

	int vx,vy;
	
	MotionComponent(GameObject g, int vx,int vy) {
		super(g);
		this.vx=vx;
		this.vy=vy;
	}

	void update(){
		g.x+=vx;
		g.y+=vy;
	}
	
	void setVelocity(int vx,int vy){
		this.vx=vx;
		this.vy=vy;
	}
	
	int getVelocityX(){
		return vx;
	}
	
	int getVelocityY(){
		return vy;
	}
	
}
