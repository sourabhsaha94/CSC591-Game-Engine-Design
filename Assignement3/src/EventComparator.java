import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

	@Override
	public int compare(Event o1, Event o2) {
		return Integer.compare(o1.priority.p, o2.priority.p);
	}

}
