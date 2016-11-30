import java.io.Serializable;
public abstract class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698764155288795074L;
	
	Player p;
	Bullet s;
	Enemy m;
	DeathZone d;	
	int id;
	long timestamp=0;
	EventType type;
	int x;
	Event(int id){
		this.id = id;
	}
}