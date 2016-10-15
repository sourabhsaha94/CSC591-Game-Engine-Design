package assignment2;

public class ColorComponent {
	
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
