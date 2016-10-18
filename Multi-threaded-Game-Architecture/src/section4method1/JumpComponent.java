package section4method1;



import java.io.Serializable;

public class JumpComponent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player player;
	boolean jump_flag;
	int init_pos;
	int jump_start;
	
	public JumpComponent(Player p){
		this.player = p;
		jump_flag = false;
		init_pos = 0;
		jump_start = 0;
	}
	
	public void jump(int frameCount){
		if(player.motionComponent.getVy()==0){
			player.motionComponent.setVy(-2);
			init_pos=player.R.y;
			jump_start=frameCount;
		}
		else if(frameCount-jump_start == 100){
			player.motionComponent.setVy(2);
		}
		else if(frameCount-jump_start==200){
			player.motionComponent.setVy(0);
			jump_flag=false;
		}
	}
	
}
