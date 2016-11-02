package method1;



import java.awt.Rectangle;

public class DeathZone extends Thing{


	public DeathZone(int id) {
		super(id);
		this.colorComponent = new ColorComponent(0,0,0);
		this.R = new Rectangle(800,10);
	}

	public void setDeathZoneXY(int x,int y){
		
		this.R.x=x;
		this.R.y=y;
		
	}
	
	public void setDeathZoneWH(int width,int height){
		
		this.R.width=width;
		this.R.height=height;
	}
	
	private static final long serialVersionUID = 1L;

}
