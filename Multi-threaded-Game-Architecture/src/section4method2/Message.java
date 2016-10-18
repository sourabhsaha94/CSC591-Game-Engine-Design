package section4method2;



import java.awt.Rectangle;
import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -870309628060190905L;

	int id=0,r=0,g=0,b=0;
	Rectangle R;
	StaticPlatform sp;
	MovingPlatform mp;
	
	Message(int id,Rectangle R,int r,int g,int b){
		this.id = id;
		this.R = R;
		this.r=r;
		this.g=g;
		this.b=b;
	}
	

	void putValues(int id,Rectangle R,int r,int g,int b){
		this.id = id;
		this.R = R;
		this.r=r;
		this.g=g;
		this.b=b;
	}
	
}
