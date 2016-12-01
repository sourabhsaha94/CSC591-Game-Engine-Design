

public class Bullet extends Thing implements EventHandler{

	SpawnPoint s;

	Player p;
	int player_score =0;
	int ai_score =0;
	
	public Bullet(int id) {
		super(id);
		this.motionComponent = new MotionComponent(this);
		this.collisionComponent = new CollisionComponent(this);
		this.colorComponent = new ColorComponent(0,0,0);
	}

	public void setPlatformColor(int r,int g,int b){
		this.colorComponent.update(r,g,b);
	}

	public void addSpawnPoint(SpawnPoint s){
		this.s =s;
	}

	public void Spawn(){
		this.setBulletVelocity(2, 6);
		s.reset(this);
	}

	public void move(){
		this.R.x+=this.motionComponent.vx;
		this.R.y+=this.motionComponent.vy;
		if(this.R.x < 0 || this.R.getMaxX() > 799){
			if(this.motionComponent.vx<0){
				ai_score+=10;
			}
			else{
				player_score+=10;
			}
			ClientEventManager.getInstance().addEvent(new DeathEvent(ClientTimeline.getInstance().getTime(), p));
		}
		if(this.R.y < 0 || this.R.getMaxY() > 799){
			
			this.motionComponent.vy=-this.motionComponent.vy;
		}
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
		if(e.id == 999){
			switch(e.type){// enemy bullet collision
			case COLLISION:
				this.motionComponent.vx=-this.motionComponent.vx;
				break;
			case DEATH:
				this.Spawn();
				break;
			default:
				break;

			}
		}
		else if(e.id == 997){//player bullet collision
			switch(e.type){
			case COLLISION:
				this.motionComponent.vx=-this.motionComponent.vx;
				break;
			case DEATH:
				this.Spawn();
				break;
			default:
				break;

			}
		}
		else if(e.id == 100){
			this.Spawn();
		}
	}

}
