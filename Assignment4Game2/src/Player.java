public class Player extends Thing implements EventHandler{
	
	SpawnPoint s;
	boolean collided = false;
	int velocity;
	Bullet bullet;
	int score=0;
	
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
		if(e.id == 4)
		switch(e.type){
		case COLLISION:
			break;	
		case DEATH:
			break;
		case HID:
			switch (e.x) {
			case 0:
				this.motionComponent.vy=0;
				this.move();
				break;
			case 1:
				ScriptManager.loadScript("player_difficulty.js");
				ScriptManager.bindArgument("player", this.motionComponent);
				ScriptManager.executeScript();
				//System.out.println("Outside script velocity = "+velocity);
				this.motionComponent.vy=-this.motionComponent.vy;
				this.move();
				this.motionComponent.vy=0;
				this.move();
				break;
			case -1:
				ScriptManager.loadScript("player_difficulty.js");
				ScriptManager.bindArgument("player", this.motionComponent);
				ScriptManager.executeScript();
				//System.out.println("Outside script velocity = "+velocity);
				this.motionComponent.vy=this.motionComponent.vy;
				this.move();
				this.motionComponent.vy=0;
				this.move();
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
