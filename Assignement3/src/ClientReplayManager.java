import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

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
	ArrayList<Player> playerList = new ArrayList<>();;
	ArrayList<MovingPlatform> mPlatformList = new ArrayList<>();

	
	
	private static ClientReplayManager crm;

	private ClientReplayManager(){
		this.id=1234;
		try {
			this.fout = new FileWriter("replays.txt");
			this.input = new FileReader("replays.txt");
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
	
	public void convertFile(){
		
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
		default:
			break;
		
		}
		
	}
	
}
