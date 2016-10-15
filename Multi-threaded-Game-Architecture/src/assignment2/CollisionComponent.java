package assignment2;

import java.util.LinkedList;

public class CollisionComponent {

	Player player;
	int direction;
	
	boolean s_int=false,m_int=false;
	
	LinkedList<StaticPlatform> sPlatformList = new LinkedList<>();
	LinkedList<MovingPlatform> mPlatformList = new LinkedList<>();
	
	CollisionComponent(Player p){
		direction =1;
		this.player= p;
	}
	
	public void addPlatforms(LinkedList<StaticPlatform> sPlatformList, LinkedList<MovingPlatform> mPlatformList){
		this.sPlatformList = sPlatformList;
		this.mPlatformList = mPlatformList;
	}
	
	public void update(int distance_from_ground,int displayx,int displayy){
		
		
		
		
		if( !m_int && !s_int && distance_from_ground>=2 && !player.jumpComponent.jump_flag)
		{
			player.motionComponent.setVy(2);
			player.motionComponent.setVx(0);;
		}
		if(distance_from_ground==2){
			player.motionComponent.setVy(0);
		}

		if(player.R.x<=2||player.R.getMaxX()>=displayx-2){	//dont cross the edges of the screen
			player.motionComponent.setVx(0);;
			player.jumpComponent.jump_flag=false;
		}
		if(player.R.y<=2||player.R.getMaxY()>=displayy-2){	//dont cross the edges of the screen
			player.motionComponent.setVy(0);;
			player.jumpComponent.jump_flag=false;
		}
		
		if(!m_int)
		for(StaticPlatform t:sPlatformList){
			
				if(player.R.intersects(t.R)){
					
					if(player.motionComponent.getVy()>0){	//coming down
						direction = 4;
					}
					else if(player.motionComponent.getVy()<0){	//going up
						direction = 2;
					}
					s_int=true;
					m_int=false;
					break;
				}
				s_int=false;
			}
	
			if(!s_int)
			for(MovingPlatform t:mPlatformList){
				if(player.R.intersects(t.R)){
					System.out.println(true+" moving");
					if(player.motionComponent.getVy()>0){	//coming down
						direction = 4;
					}
					else if(player.motionComponent.getVy()<0){	//going up
						direction = 2;
					}
					m_int=true;
					s_int=false;
					break;
				}
				m_int=false;
			}
		
		if(direction==2){	//dont go up
			player.motionComponent.setVy(2);
			player.motionComponent.setVx(-5);
			player.jumpComponent.jump_flag=false;
			direction=0;
		}
		if(direction==4){	//stop downward motion while coming down
			player.motionComponent.setVy(0);
			player.jumpComponent.jump_flag=false;
			direction=0;
		}

				
	}
}
