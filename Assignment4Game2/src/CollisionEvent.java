
public class CollisionEvent extends Event{


	private static final long serialVersionUID = -5375591426960984485L;
	
		
	CollisionEvent(long time,Player p,Bullet s) {
		super(997);
		this.type = EventType.COLLISION;
		this.timestamp = time;
		this.p = p;
		this.s = s;
	}
	CollisionEvent(long time,Player p,Enemy m) {
		super(998);
		this.type = EventType.COLLISION;
		this.timestamp = time;
		this.p = p;
		this.m = m;
	}
	CollisionEvent(long time,Bullet s,Enemy m) {
		super(999);
		this.type = EventType.COLLISION;
		this.timestamp = time;
		this.s = s;
		this.m = m;
	}
	

}
