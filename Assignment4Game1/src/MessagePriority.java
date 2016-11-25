import java.io.Serializable;

public enum MessagePriority implements Serializable{
	LOW(1),HIGH(0),EVENT(-1);
	int p;
	private MessagePriority(int p) {
		this.p=p;
	}
}
