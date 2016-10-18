package section123;


import java.io.Serializable;

public class ColorComponent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int r,g,b;
	
	public ColorComponent(int r,int g,int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public int getR(){
		return r;
	}
	public int getG(){
		return g;
	}
	public int getB(){
		return b;
	}
	public void update(int r,int g,int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
}
