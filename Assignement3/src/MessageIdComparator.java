import java.util.Comparator;

public class MessageIdComparator implements Comparator<Message> {

	@Override
	public int compare(Message m1, Message m2) {
		
		return Integer.compare(m1.messagePriority.p, m2.messagePriority.p);
	}

}
