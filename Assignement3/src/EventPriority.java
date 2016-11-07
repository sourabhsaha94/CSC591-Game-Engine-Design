import java.io.Serializable;

public enum EventPriority implements Serializable {
	SPAWN (1),DEATH (2),COLLISION (3),HID (4);
	int p;
	private EventPriority(int p) {
		this.p=p;
	}
}
