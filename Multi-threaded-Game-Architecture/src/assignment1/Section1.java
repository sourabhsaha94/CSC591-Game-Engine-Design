package assignment1;
import processing.core.PApplet;
import java.awt.*;
import java.util.ArrayList;

public class Section1 extends PApplet {	//to inherit all methods provided by the Processing library


	ArrayList<Thing> ThingList;
	int direction=0; //Left 1 Up 2 Down 3 Right 4
	Thing player;
	
	int displayx=800;
	int displayy=800;
	
	boolean jump_flag = false;
	int jump_start=0;
	int init_pos=0;
	
	public static void main(String[] args) {

		PApplet.main("assignment1.Section1");	//lets the PApplet know which main class to run
	}

	public void settings(){	//initial settings --must be before all other functions
		size(displayx, displayy);
	}

	public void setup(){
		ThingList = new ArrayList<Thing>();
		ThingList.add(new Thing("r1",new Rectangle(100,displayy-(200+50),50,50),0,0,0,0,255));	//(width,height);width means height;height means width
		ThingList.add(new Thing("r2",new Rectangle(400,displayy-(200+50),50,50),0,0,0,255,0));
		ThingList.add(new Thing("r3",new Rectangle(600,displayy-(200+50),50,50),0,0,255,0,0));
		player = ThingList.get(0);
	}

	public void draw(){
		clear();
		
		
		for(Thing t:ThingList){		//loop to display
			fill(t.r,t.g,t.b);
			rect(t.R.x+=t.vx,t.R.y+=t.vy,t.R.width,t.R.height);
		}
		
		for(Thing t:ThingList){		//loop to check collision
			
			if(t.R.equals(player.R))
				continue;
			else{
				if(player.R.intersects(t.R)){
					direction = checkCollision();
					break;
				}	
			}
		}
		
		
		if(jump_flag){
			if(direction==2){	//collision while coming down
				player.vy=0;
				jump_flag=false;
			}
			else if(direction==3){	//collision while going up
				player.vx=(-player.vx);
			}
			else if(player.vy==0){
				player.vy=-2;
				init_pos=player.R.y;
				jump_start=frameCount;
			}
			else if(frameCount-jump_start == 100){
				player.vy=2;
			}
			else if(frameCount-jump_start==200){
				player.vy=0;
				jump_flag=false;
			}
		}
		
		beginShape();
		fill(255,255,255);
		rect(0,displayy-200,displayx,5);
		endShape();
		System.out.println(direction);
	}

	private void stay_on_ground() {
		if(player.R.y!=displayy-(200+50) && !jump_flag){
			player.vy=2;
		}
		if(player.R.y==displayy-(200+50) && !jump_flag){
			player.vy=0;
		}
	}

	private int checkCollision() {
		if(keyCode==RIGHT){
			player.vx=0;
			player.vy=0;
			return 4;
		}
		else if(player.vy>0 || keyCode==DOWN){
			player.vx=0;
			player.vy=0;
			return 2;
		}
		else if(keyCode==LEFT){
			player.vx=0;
			player.vy=0;
			return 1;
		}
		else if(player.vy<0 || keyCode==UP){
			player.vx=0;
			player.vy=0;
			return 3;
		}
		else
			return 0;
	}

	public void keyPressed(){
		if (key == CODED) {
			if (keyCode == RIGHT) {	//move right
				
				if(direction==4)
					ThingList.get(0).vx=0;
				else
					ThingList.get(0).vx=1;
				
			} else if (keyCode == LEFT) {	//move left
				if(direction==1)
					ThingList.get(0).vx=0;
				else
					ThingList.get(0).vx=-1;
			}
			else if (keyCode == UP) {	//jump
				jump_flag=true;
			}
		}
	}
	public void keyReleased() {
		if (key == CODED) {
			if (keyCode == RIGHT) {
				ThingList.get(0).vx=0;
			} else if (keyCode == LEFT) {
				ThingList.get(0).vx=0;
			}
		}

	}

}
