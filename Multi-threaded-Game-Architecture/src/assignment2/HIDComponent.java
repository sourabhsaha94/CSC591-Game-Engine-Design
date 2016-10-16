package assignment2;

import java.io.Serializable;

import processing.core.PApplet;

public class HIDComponent extends PApplet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player player;

	
	HIDComponent(Player player){
		this.player = player;

	}
	
	public void keyPressed(){
		if (key == CODED) {
		
			if (keyCode == RIGHT) {	//move right
				if(player.collisionComponent.m_int || player.collisionComponent.s_int || player.jumpComponent.jump_flag)
					player.motionComponent.setVx(1);
				
				System.out.println("right");
				
			} else if (keyCode == LEFT) {	//move left
				if(player.collisionComponent.m_int || player.collisionComponent.s_int || player.jumpComponent.jump_flag)
					player.motionComponent.setVx(-1);
				
				System.out.println("left");
			}
			else if (keyCode == UP) {	//jump
				player.jumpComponent.jump_flag=true;
			}
			
			player.R.x+=player.motionComponent.getVx();
		}
		else if(key == 'r' ||key =='R'){	//reset
			player.R.x=50;
			player.R.y=550;
			player.motionComponent.setVelocity(0,0);
		}
	}
	
	public void keyReleased() {
		if (key == CODED) {
			if (keyCode == RIGHT) {
				player.motionComponent.setVx(0);
			} else if (keyCode == LEFT) {
				player.motionComponent.setVx(0);
			}
			player.R.x+=player.motionComponent.getVx();;
		}

	}
	
}
