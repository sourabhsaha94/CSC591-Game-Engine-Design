

import java.io.Serializable;

public class fireComponent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player player;
	boolean jump_flag;
	int init_pos;
	long jump_start;
	int jump_interval=3000;
	public fireComponent(Player p){
		this.player = p;
		jump_flag = false;
		init_pos = 0;
		jump_start = 0;
	}
	
	public void setJumpHeight(int jump_time_in_millis){
		this.jump_interval = jump_time_in_millis;
	}
	
	public void fire(){
	
	}
	
}
