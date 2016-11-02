import java.io.Serializable;
public abstract class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698764155288795074L;
	
	Player p;
	StaticPlatform s;
	MovingPlatform m;
	DeathZone d;	
	int id;
	long timestamp;
	EventPriority priority;
	
	Event(int id){
		this.id = id;
	}
}
