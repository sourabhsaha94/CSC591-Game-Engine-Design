
public class HIDEvent extends Event{

	HIDEvent(long time,Player p,int x) {
		super(4);
		this.priority = EventPriority.HID;
		this.timestamp = time;
		this.p = p;
		this.x = x;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3665461831488755652L;

}
