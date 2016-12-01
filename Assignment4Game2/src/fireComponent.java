

import java.io.Serializable;

public class fireComponent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player player;
	boolean fire_flag;
	int init_pos;
	long jump_start;
	int jump_interval=3000;
	public fireComponent(Player p){
		this.player = p;
		fire_flag = false;
		init_pos = 0;
		jump_start = 0;
	}
	
	public void fire(Bullet bullet){
		if(bullet.R.y<=0){
			this.fire_flag=false;
		}
		else{
			bullet.move();
		}
	}
	
}
