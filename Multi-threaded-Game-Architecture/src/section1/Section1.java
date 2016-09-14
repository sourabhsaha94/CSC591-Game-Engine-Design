package section1;

import assignment1.Thing;
import processing.core.PApplet;
import java.awt.*;
import java.util.LinkedList;

public class Section1 extends PApplet {	//to inherit all methods provided by the Processing library


	LinkedList<Thing> ThingList;
	int direction=1; //Left 1 Up 2 Down 3 Right 4
	Thing player;
	
	int distance_from_ground;
	
	int displayx=800;
	int displayy=800;
	
	boolean jump_flag = false;
	int jump_start=0;
	int init_pos=0;
	
	boolean intersect=false;
	
	public static void main(String[] args) {

		PApplet.main("section1.Section1");	//lets the PApplet know which main class to run
	}

	public void settings(){	//initial settings --must be before all other functions
		size(displayx, displayy);
	}

	public void setup(){
		ThingList = new LinkedList<Thing>();
		ThingList.add(new Thing("r1",new Rectangle(50,600,200,50),0,0,0,0,255));	//(width,height);width means height;height means width
		ThingList.add(new Thing("r2",new Rectangle(150,500,350,50),0,0,0,255,0));
		ThingList.add(new Thing("r3",new Rectangle(500,400,150,50),0,0,255,0,0));
		player = new Thing("player",new Rectangle(50,550,50,50),0,0,255,255,0);
		
		distance_from_ground = 200;	//800-600
	}

	public void draw(){
		clear();
		
		rect(player.R.x+=player.vx,player.R.y+=player.vy,player.R.width,player.R.height);
		
		for(Thing t:ThingList){		//loop to display
			fill(t.r,t.g,t.b);
			rect(t.R.x+=t.vx,t.R.y+=t.vy,t.R.width,t.R.height);
		}
		
		distance_from_ground = (int) (displayy - player.R.getMaxY());
		
		if( !intersect && distance_from_ground>=2 && !jump_flag)
		{
			player.vy=2;
			player.vx=0;
		}
		if(distance_from_ground==2){
			player.vy=0;
		}
		
		if(player.R.x<=2||player.R.getMaxX()>=displayx-2){
			player.vx=0;
			jump_flag=false;
		}
		if(player.R.y<=2||player.R.getMaxY()>=displayy-2){
			player.vy=0;
			jump_flag=false;
		}
		
		for(Thing t:ThingList){
			if(player.R.intersects(t.R)){
				if(player.vy>0){	//coming down
					direction = 4;
				}
				if(player.vy<0){	//going up
					direction = 2;
				}
				if(player.vy<0 && player.vx>0){	//up right
					direction = 10;
				}
				if(player.vy>0 && player.vx>0){	//down right
					direction = 12;
				}
				if(player.vy<0 && player.vx<0){	//up left
					direction = 3;
				}
				if(player.vy>0 && player.vx<0){	//down left
					direction = 5;
				}
				intersect = true;
				break;
			}
				intersect=false;
		}
		
		System.out.println("vx:"+player.vx+" vy:"+player.vy+" direction:"+direction+" intersected:"+intersect);
		
		if(direction==2){
			player.vy=2;
			jump_flag=false;
			direction=0;
		}
		if(direction==4){
			player.vy=0;
			jump_flag=false;
			direction=0;
		}
		
		if(direction==12){
			player.vy=0;
			player.vx=0;	//rebound on side hit
			jump_flag=false;
			direction=0;
		}
		
		if(direction==5){
			player.vy=0;
			player.vx=0;	//rebound on side hit
			jump_flag=false;
			direction=0;
		}
		
		if(direction==10){
			player.vy=2;
			player.vx=0;
			jump_flag=false;
			direction=0;
		}
		
		if(direction==3){
			player.vy=2;
			player.vx=0;
			jump_flag=false;
			direction=0;
		}
		
		if(jump_flag){	//main jump logic
			
			if(player.vy==0){
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
		
		
	}


	public void keyPressed(){
		if (key == CODED) {
		
			if (keyCode == RIGHT) {	//move right
				if(intersect || jump_flag)
					player.vx=1;
				
			} else if (keyCode == LEFT) {	//move left
				if(intersect || jump_flag)
					player.vx=-1;
				
			}
			else if (keyCode == UP) {	//jump
				jump_flag=true;
			}
		}
		else if(key == 'r' ||key =='R'){	//reset
			player.R.x=50;
			player.R.y=550;
			player.vx=0;
			player.vy=0;
		}
	}
	
	public void keyReleased() {
		if (key == CODED) {
			if (keyCode == RIGHT) {
				player.vx=0;
			} else if (keyCode == LEFT) {
				player.vx=0;
			}
		}

	}

}
