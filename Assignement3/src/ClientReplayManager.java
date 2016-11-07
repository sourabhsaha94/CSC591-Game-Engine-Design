import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ClientReplayManager{
	
	int id;
	FileWriter fout;
	BufferedWriter bufferedWriter;
	boolean start_replay=false;
	boolean stop_replay=true;
	boolean running = false;
	
	private static ClientReplayManager crm;

	private ClientReplayManager(){
		this.id=1234;
		try {
			this.fout = new FileWriter("replays.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.bufferedWriter = new BufferedWriter(fout);
	}

	public static ClientReplayManager getInstance(){
		if(crm == null)
			crm = new ClientReplayManager();

		return crm;
	}
	
}
