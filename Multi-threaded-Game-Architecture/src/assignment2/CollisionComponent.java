package assignment2;

import java.util.LinkedList;

public class CollisionComponent {

	Player player;
	int direction;
	
	boolean s_int=false,m_int=false;
	
	LinkedList<StaticPlatform> sPlatformList = new LinkedList<>();
	LinkedList<MovingPlatform> mPlatformList = new LinkedList<>();
	LinkedList<DeathZone> dzones = new LinkedList<>();
	
	CollisionComponent(Player p){
		direction =1;
		this.player= p;
	}
	
	public void addPlatforms(LinkedList<StaticPlatform> sPlatformList, LinkedList<MovingPlatform> mPlatformList){
		this.sPlatformList = sPlatformList;
		this.mPlatformList = mPlatformList;
	}
	
	public void addDeathZone(DeathZone d){
		dzones.add(d);
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

		for(DeathZone d:dzones){
			if(player.R.intersects(d.R)){
				player.Spawn();
			}
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
					
					if(player.motionComponent.getVy()>0){	//coming down
						direction = 4;
					}
					else if(player.motionComponent.getVy()<0){	//going up
						direction = 2;
					}
					
					if(player.R.y>t.R.y && player.motionComponent.getVy()<0){	//going up.. hit on side
						player.Spawn();
					}
					if((player.R.y<t.R.getMaxY() && player.R.x<t.R.x) && player.motionComponent.getVy()>0){	//going up.. hit on side
						player.Spawn();
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