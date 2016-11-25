

import java.io.Serializable;

public class JumpComponent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player player;
	boolean jump_flag;
	int init_pos;
	long jump_start;
	int jump_interval=3000;
	public JumpComponent(Player p){
		this.player = p;
		jump_flag = false;
		init_pos = 0;
		jump_start = 0;
	}
	
	public void setJumpHeight(int jump_time_in_millis){
		this.jump_interval = jump_time_in_millis;
	}
	
	public void jump(){
		player.collided=false;
		if(player.motionComponent.getVy()==0){
			init_pos=player.R.y;
			jump_start=System.currentTimeMillis();
			player.motionComponent.setVy(-1);
			System.out.println("y: "+player.R.y);
		}
		else if((System.currentTimeMillis()-jump_start)>jump_interval){
			System.out.println("stop jump");
			jump_flag=false;
		}
		
	}
	
}