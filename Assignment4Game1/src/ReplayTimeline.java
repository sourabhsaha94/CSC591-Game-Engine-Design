
public class ReplayTimeline implements Runnable{

	long anchor;
	long tic_size;
	long origin;
	int id;
	private static ReplayTimeline time;
	
	private ReplayTimeline(){
		this.id=3456;
		this.tic_size = 1000000;
		this.origin=Timeline.getInstance().origin;
	}
	
	public static ReplayTimeline getInstance(){
		if(time==null)
			time = new ReplayTimeline();
		
		return time;
	}
	
	@Override
	public void run() {
		while(true){
			anchor = System.nanoTime();
		}
		
	}
	
	public boolean rightTime(){
		if((anchor-origin)>tic_size){
			origin=anchor;
			return true;
		}
		else return false;
	}
	
	public long getTime(){
		return origin;
	}

}
