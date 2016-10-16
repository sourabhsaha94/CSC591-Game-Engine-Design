package assignment2;
import java.io.Serializable;

public class InitialMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	StaticPlatform s;
	
	public InitialMessage(StaticPlatform s) {
		this.s =s;
	}
}
