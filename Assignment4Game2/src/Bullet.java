

public class Bullet extends Thing implements EventHandler{

	SpawnPoint s;
	
	Player p;
	
	public Bullet(int id) {
		super(id);
		this.motionComponent = new MotionComponent(this);
		this.collisionComponent = new CollisionComponent(null);
		this.colorComponent = new ColorComponent(0,0,0);
	}

	public void setPlatformColor(int r,int g,int b){
		this.colorComponent.update(r,g,b);
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
	
	public void setBulletColor(int r,int g,int b){
		this.colorComponent.update(r,g,b);
	}
	
	public void setBulletVelocity(int vx,int vy){
		this.motionComponent.setVelocity(vx, vy);
	}

	
	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return "Player"+","+this.id+","+this.R.x+","+this.R.y+",";
	}

	@Override
	public void handleEvent(Event e) {
		if(e.p.id==this.id)
		switch(e.type){
		case COLLISION:
			e.p.bullet.setBulletVelocity(0, 0);
			e.p.bullet.setBulletColor(0, 0, 0);
			break;
		case SPAWN:
			this.Spawn();
			break;
		default:
			break;

		}
	}
	

}
