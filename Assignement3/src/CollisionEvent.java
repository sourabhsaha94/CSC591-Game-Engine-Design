
public class CollisionEvent extends Event{


	private static final long serialVersionUID = -5375591426960984485L;
	
	CollisionEvent(long time,Player p,StaticPlatform s) {
		super(1);
		this.type = EventType.COLLISION;
		this.timestamp = time;
		this.p = p;
		this.s = s;
	}
	CollisionEvent(long time,Player p,MovingPlatform m) {
		super(2);
		this.type = EventType.COLLISION;
		this.timestamp = time;
		this.p = p;
		this.m = m;
	}
	

}
