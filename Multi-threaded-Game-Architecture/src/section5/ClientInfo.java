/*
 * This class stores the info of one client*/

package section5;

import java.net.Socket;

public class ClientInfo {
	
	int id = 0;
	public Socket socket = null;
	public Section5ServerOut out=null;
	public Section5ServerIn in =null;
}
