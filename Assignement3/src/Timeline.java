
public class Timeline implements Runnable{

	long anchor;
	long tic_size;
	long origin;
	int id;
	private static Timeline time;
	
	private Timeline(){
		this.id=2345;
		this.tic_size = 1000;
		this.origin=System.nanoTime();
	}
	
	public static Timeline getInstance(){
		if(time==null)
			time = new Timeline();
		
		return time;
	}
	
	@Override
	public void run() {
		while(true){
			anchor = System.currentTimeMillis();
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
