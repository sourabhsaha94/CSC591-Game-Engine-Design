import processing.core.PApplet;

public class UsingProcessing extends PApplet {	//to inherit all methods provided by the Processing library
	
	int xr1,yr1,xr2,yr2,xr3,yr3;
	
	public static void main(String[] args) {
		PApplet.main("UsingProcessing");	//lets the PApplet know which main class to run
	}
	public void settings(){	//initial settings --must be before all other functions
		size(1000, 1000);
	}
	public void setup(){
		xr1=100;
		xr2=700;
		xr3=700;
		yr1=100;
		yr2=100;
		yr3=700;
 }
	public void draw(){
		
		fill(255,0,0);
		rect(xr1,yr1,200,100);
		fill(0,255,0);
		rect(xr2,yr2,50,50);
		fill(0,0,255);
		rect(xr3,yr3,100,100);
	}
}
