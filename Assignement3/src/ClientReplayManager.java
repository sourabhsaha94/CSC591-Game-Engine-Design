import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientReplayManager{
	
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
		if(crm == null)
			crm = new ClientReplayManager();

		return crm;
	}
	
	public String getLine() throws IOException{
		return this.bufferedReader.readLine();
	}
	
	public void convertFile(){
		
	}
	
}
