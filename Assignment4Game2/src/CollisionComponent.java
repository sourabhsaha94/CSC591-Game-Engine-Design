
//collision with death zones or sides of platforms will cause immediate re-spawn



import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionComponent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player player;
	Bullet cSPlatform;
	Enemy cMPlatform;
	int direction;

	Random r = new Random();

	boolean s_int=false,m_int=false;

	CopyOnWriteArrayList<Enemy> mPlatformList = new CopyOnWriteArrayList<>();

	CollisionComponent(Player p){
		direction =1;
		this.player= p;
	}
	CollisionComponent(Bullet b){
		this.cSPlatform = b;
	}
	public void addPlatforms(CopyOnWriteArrayList<Enemy> mpList){
		this.mPlatformList = mpList;
	}

	public void update(){
		if(this.mPlatformList.size()!=0){
			if(this.cSPlatform.R.intersects(this.player.R)){
				ClientEventManager.getInstance().addEvent(new CollisionEvent(ClientTimeline.getInstance().getTime(), this.player, this.cSPlatform));
			}
			else if(this.cSPlatform.R.intersects(this.mPlatformList.get(0).R)){
				ClientEventManager.getInstance().addEvent(new CollisionEvent(ClientTimeline.getInstance().getTime(),this.cSPlatform, this.mPlatformList.get(0)));
			}
		}
	}
}
