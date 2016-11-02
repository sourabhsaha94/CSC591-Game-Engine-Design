import java.util.Comparator;

public class MessageIdComparator implements Comparator<Message> {

	@Override
	public int compare(Message m1, Message m2) {
		
		return m2.messagePriority.compareTo(m1.messagePriority);
	}

}
