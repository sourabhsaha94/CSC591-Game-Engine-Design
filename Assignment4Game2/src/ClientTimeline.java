
public class ClientTimeline implements Runnable{

	long anchor;
	long anchor_millis;
	long tic_size,tic_size_millis;
	long origin,origin_millis;
	int id;
	private static ClientTimeline time;
	
	private ClientTimeline(){
		this.id=3456;
		this.tic_size = 100000;
		this.tic_size_millis = 1000;
		this.origin=System.nanoTime();
		this.origin_millis= System.currentTimeMillis();
	}
	
	public static ClientTimeline getInstance(){
		if(time==null)
			time = new ClientTimeline();
		
		return time;
	}
	
	@Override
	public void run() {
		while(true){
			anchor = System.nanoTime();
			anchor_millis = System.currentTimeMillis();
		}
		
	}
	
	public boolean rightTime(){
		if((anchor-origin)>tic_size){
			origin=anchor;
			return true;
		}
		else return false;
	}
	
	public boolean rightTimeMillis(){
		if((anchor_millis-origin_millis)>tic_size_millis){
			origin_millis=anchor_millis;
			return true;
		}
		else return false;
	}
	
	public long getTime(){
		return origin;
	}
	
	public long getTimeInMillis(){
		return origin_millis;
	}

}
