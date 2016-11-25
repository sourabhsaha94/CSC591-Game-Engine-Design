
public class DeathEvent extends Event{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4795739997957513935L;

	DeathEvent(long time,Player p,DeathZone d) {
		super(3);
		this.type = EventType.DEATH;
		this.timestamp = time;
		this.p = p;
		this.d = d;
	}
}
