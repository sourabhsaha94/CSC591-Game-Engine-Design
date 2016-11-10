import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ClientReplayManager implements EventHandler{
	
	int id;
	FileWriter fout;
	FileReader input;
	BufferedWriter bufferedWriter;
	BufferedReader bufferedReader;
	boolean start_replay=false;
	boolean stop_replay=true;
	boolean running = false;
	boolean playing =false;
	String sArray[] = new String[10];
	
	int pcount=0;
	
	Random r = new Random();
	
	private static ClientReplayManager crm;

	private ClientReplayManager(){
		this.id=r.nextInt(10);
		try {
			this.fout = new FileWriter("replays"+id+".txt");
			this.input = new FileReader("replays"+id+".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.bufferedWriter = new BufferedWriter(fout);
		this.bufferedReader = new BufferedReader(input);
		
	}

	public static ClientReplayManager getInstance(){
		if(crm == null){
			crm = new ClientReplayManager();
			ClientEventManager.getInstance().registerEvent(crm);
		}
		
		return crm;
	}
	
	public String getLine() throws IOException{
		return this.bufferedReader.readLine();
	}
	
	@Override
	public void handleEvent(Event e) {
		switch(e.type){
		case COLLISION:
			break;
		case DEATH:
			break;
		case HID:
			break;
		case PLAYBACK:
			if(!this.playing){
				this.playing = true;
			}
			else{
				this.playing = false;
			}

			break;
		case RECORD_START_STOP:
			if(!this.running)
			{
				this.start_replay=true;
				this.stop_replay=false;
			}
			else{
				this.start_replay=false;
				this.stop_replay=true;
			}
			break;
		case SPAWN:
			break;
		case SLOW:ReplayTimeline.getInstance().tic_size=10000000;
			break;
		case FAST:ReplayTimeline.getInstance().tic_size=1000;
			break;
		case NORMAL:ReplayTimeline.getInstance().tic_size=1000000;
			break;
		default:
			break;
		
		}
		
	}
	
	public Message playReplay(Message m,String s,Client c){
		this.sArray = s.split(",");
		pcount=0;
		for(int i=0;i<this.sArray.length;i++){
			if(this.sArray[i].equalsIgnoreCase("player")){
				pcount++;
				m.pList.get(Integer.parseInt(this.sArray[i+1])-1).R.x=Integer.parseInt(this.sArray[i+2]);
				m.pList.get(Integer.parseInt(this.sArray[i+1])-1).R.y=Integer.parseInt(this.sArray[i+3]);
			}
			else if(this.sArray[i].equalsIgnoreCase("movingplatform")){
				m.mpList.get(Integer.parseInt(this.sArray[i+1])).R.x=Integer.parseInt(this.sArray[i+2]);
				m.mpList.get(Integer.parseInt(this.sArray[i+1])).R.y=Integer.parseInt(this.sArray[i+3]);
			}
		}
		if(m.pList.size()>pcount){
			while((m.pList.size()-pcount)!=0){
				m.pList.remove(m.pList.size()-1);
			}
		}
		return m;
	}
	
}
