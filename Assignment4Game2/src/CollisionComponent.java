
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

	public void addPlatforms(CopyOnWriteArrayList<Enemy> mpList){
		this.mPlatformList = mpList;
	}

	public void update(Bullet bullet){
		for(Enemy e:mPlatformList){
			if(bullet.R.intersects(e.R)){
				System.out.println("hit enemy "+ e.id);
				ClientEventManager.getInstance().addEvent(new CollisionEvent(ClientTimeline.getInstance().getTime(), bullet.p, e));
				break;
			}
		}
	}
	
	public void update(){
		for(Enemy e:mPlatformList){
			if(player.R.intersects(e.R)){
				ClientEventManager.getInstance().addEvent(new DeathEvent(ClientTimeline.getInstance().getTime(),player));
				break;
			}
		}
	}
}
