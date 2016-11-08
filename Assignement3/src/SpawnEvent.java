
public class SpawnEvent extends Event{

	
	private static final long serialVersionUID = 4133733560141691996L;
	SpawnEvent(long time,Player p) {
		super(5);
		this.priority = EventType.SPAWN;
		this.timestamp = time;
		this.p = p;
	}

}
