


import java.awt.Rectangle;

public class SpawnPoint extends Thing{

	Player p;
	
	public SpawnPoint(int id) {
		super(id);
		this.colorComponent = new ColorComponent(0,0,0);
		this.R = new Rectangle(0,0,1,1);
		// TODO Auto-generated constructor stub
	}

	
	public void reset(Player player){
		player.R.x = this.R.x;
		player.R.y = this.R.y;
		player.setPlayerVelocity(0, 0);
	}
	
	public void setSpawnPoint(int x,int y){
		this.R.x =x;
		this.R.y =y;
	}
	
	private static final long serialVersionUID = 1L;

}
