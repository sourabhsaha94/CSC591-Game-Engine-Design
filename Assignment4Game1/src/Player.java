import java.awt.Rectangle;

public class Player extends Thing implements EventHandler{
	
	SpawnPoint s;
	boolean collided = false;
	
	Bullet bullet;
	int score=0,num_bullets=13;
	public Player(int id) {
		super(id);
		this.collisionComponent = new CollisionComponent(this);
		this.colorComponent = new ColorComponent(0,0,0);
		this.fireComponent = new fireComponent(this);
		this.motionComponent = new MotionComponent(this);
		this.collided=false;
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
	
	public void setPlayerColor(int r,int g,int b){
		this.colorComponent.update(r,g,b);
	}
	
	public void setPlayerVelocity(int vx,int vy){
		this.motionComponent.setVelocity(vx, vy);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return "Player"+","+this.id+","+this.R.x+","+this.R.y+",";
	}

	@Override
	public void handleEvent(Event e) {
		if(e.p.id==this.id)
		switch(e.type){
			case COLLISION:
			e.p.collisionComponent.mPlatformList.remove(e.m);
			score+=5;
			this.fireComponent.fire_flag=false;
			break;	
			case DEATH:
			this.fireComponent.fire_flag=false;
			this.collided=true;
			//ClientEventManager.getInstance().addEvent(new SpawnEvent(Timeline.getInstance().getTime(), this));
			break;
		case HID:

			switch (e.x) {
			case 0:
				this.motionComponent.vx=0;
				this.move();
				break;
			case 1:
				this.motionComponent.vx=5;
				this.move();
				this.motionComponent.vx=0;
				this.move();
				break;
			case -1:
				this.motionComponent.vx=-5;
				this.move();
				this.motionComponent.vx=0;
				this.move();
				break;
			case 101://fire
				System.out.println("creating bullet");
				Bullet bullet = new Bullet(0);
				bullet.R = new Rectangle((int)this.R.getCenterX(), (int)this.R.getCenterY(), 10, 10);
				bullet.setBulletColor(125, 125, 125);
				bullet.setBulletVelocity(0, -5);
				bullet.collisionComponent.mPlatformList.addAll(this.collisionComponent.mPlatformList);
				bullet.move();
				bullet.p = this;
				this.bullet = bullet;
				this.fireComponent.fire_flag=true;
				this.fireComponent.fire(this.bullet);
				num_bullets--;
				if(num_bullets<0){
					ClientEventManager.getInstance().addEvent(new DeathEvent(Timeline.getInstance().getTime(), this));
				}
				break;
			case 102:
				ScriptManager.loadScript("changecolor.js");
				ScriptManager.bindArgument("color_object", this.colorComponent);
				ScriptManager.executeScript();
				break;
			default:
				break;
			}
			break;
		case SPAWN:
			this.Spawn();
			break;
		default:
			break;

		}
	}
	


}
