

public class StaticPlatform extends Thing{

	public StaticPlatform(int id) {
		super(id);
		this.collisionComponent = new CollisionComponent(null);
		this.colorComponent = new ColorComponent(0,0,0);
	}

	public void setPlatformColor(int r,int g,int b){
		this.colorComponent.update(r,g,b);
	}
	
	private static final long serialVersionUID = 1L;

}
