
//collision with death zones or sides of platforms will cause immediate re-spawn

package assignment2;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionComponent {

	Player player;
	int direction;
	Random r = new Random();
	
	boolean s_int=false,m_int=false;
	
	CopyOnWriteArrayList<StaticPlatform> sPlatformList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<MovingPlatform> mPlatformList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<DeathZone> dzList = new CopyOnWriteArrayList<>();
	
	CollisionComponent(Player p){
		direction =1;
		this.player= p;
	}
	
	public void addPlatforms(Collection<StaticPlatform> sPlatformList, Collection<MovingPlatform> mPlatformList){
		this.sPlatformList.addAll(sPlatformList);
		this.mPlatformList.addAll(mPlatformList);
	}
	
	public void addSPlatform(StaticPlatform s){
		if(!this.sPlatformList.contains(s))
			this.sPlatformList.add(s);
	}
	
	public void addMPlatform(MovingPlatform m){
		if(!this.mPlatformList.contains(m))
			this.mPlatformList.add(m);
	}
	
	public void addDeathZone(DeathZone d){
		if(!this.dzList.contains(d))
			this.dzList.add(d);
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

		for(DeathZone d:dzList){
			if(player.R.intersects(d.R)){
				
				player.jumpComponent.jump_flag=false;
				player.s.setSpawnPoint(r.nextInt(800), 200);
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
					
					if(player.R.y>t.R.y && player.motionComponent.getVy()<0){	//going up.. hit on side
						player.Spawn();
					}
					if((player.R.y<t.R.getMaxY() && player.R.x<t.R.x) && player.motionComponent.getVy()>0){	//going down.. hit on side
						player.Spawn();
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
					if((player.R.y<t.R.getMaxY() && player.R.x<t.R.x) && player.motionComponent.getVy()>0){	//going down.. hit on side
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
