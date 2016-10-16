

public class StaticPlatform extends Thing{

	public StaticPlatform(int id) {
		super(id);
		this.collisionComponent = new CollisionComponent(null);
		this.colorComponent = new ColorComponent(255,0,0);
		
	}

	public void setPlatformColor(int r,int g,int b){
		this.colorComponent.update(r,g,b);
	}
	
	public void setPlatformXY(int x,int y){
		this.R.x = x;
		this.R.y = y;
	}
	
	public void setPlatformWH(int w,int h){
		this.R.width = w;
		this.R.height = h;
	}
	
	private static final long serialVersionUID = 1L;

}
