

public class Player extends Thing implements EventHandler{
	
	SpawnPoint s;
	boolean collided = false;
	
	public Player(int id) {
		super(id);
		this.collisionComponent = new CollisionComponent(this);
		this.colorComponent = new ColorComponent(0,0,0);
		this.jumpComponent = new JumpComponent(this);
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
		return "Player"+","+this.id+","+this.R.x+","+this.R.y;
	}

	@Override
	public void handleEvent(Event e) {
		if(e.p.id==this.id)
		switch(e.type){
		case COLLISION:
			switch(e.id){
			case 1:
				
				if(this.motionComponent.getVy()>0){	//coming down
					this.jumpComponent.jump_flag=false;
					this.collisionComponent.direction = 2;
				}
				else if(this.motionComponent.getVy()<0){	//going up
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					this.collisionComponent.direction=0;
				}

				if(this.R.y>e.s.R.y && this.motionComponent.getVy()<0){	//going up.. hit on side
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					this.collisionComponent.direction=0;
				}
				if((this.R.y<e.s.R.getMaxY() && this.R.x<e.s.R.x) && this.motionComponent.getVy()>0){	//going down.. hit on side
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					this.collisionComponent.direction=0;
				}

				break;
			case 2:

				if(this.motionComponent.getVy()>0){	//coming down
					this.collisionComponent.direction = 2;
				}
				else if(this.motionComponent.getVy()<0){	//going up
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					this.collisionComponent.direction=0;
				}

				if(this.R.y>e.m.R.y && this.motionComponent.getVy()<0){	//going up.. hit on side
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					this.collisionComponent.direction=0;
				}
				else if((this.R.y<e.m.R.getMaxY() && this.R.x<e.m.R.x) && this.motionComponent.getVy()>0){	//going down.. hit on side
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					this.collisionComponent.direction=0;
				}

				break;
			}
			break;
		case DEATH:
			this.jumpComponent.jump_flag=false;
			this.collided=false;
			EventManager.getInstance().addEvent(new SpawnEvent(Timeline.getInstance().getTime(), this));
			//System.out.println("spawn: "+Timeline.getInstance().getTime());
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
			case 101:
				this.jumpComponent.jump_flag=true;
				this.move();
				break;
			case 666:
				Timeline.getInstance().tic_size*=50000;
				break;
			case 777:
				Timeline.getInstance().tic_size/=50000;
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
