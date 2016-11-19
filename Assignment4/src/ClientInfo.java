/*
 * This class stores the info of one client*/



import java.net.Socket;

public class ClientInfo {
	
	int id = 0;
	public Socket socket = null;
	public ServerOut out=null;
	public ServerIn in =null;
}
