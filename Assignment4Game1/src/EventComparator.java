import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

	@Override
	public int compare(Event o1, Event o2) {
		return Long.compare(o1.timestamp,o2.timestamp);
	}

}
