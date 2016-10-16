import java.util.Random;

public class ThingFactory {

	Random r = new Random();
	
	//for player r is between 0-100
	//for static platform r is between 1000-2000
	//for moving platform r is between 3000-4000
	//for death zone r is between 500-600
	//for spawn point r is between 700-800
	
	/*public Player getPlayer(){
		return new Player(this.r.nextInt(100));
	}
	
	public StaticPlatform getSP(int x,int y){
		return new StaticPlatform(this.r.nextInt(1000)+1000,x,y);
	}
	
	public MovingPlatform getMP(int x,int y){
		return new MovingPlatform(this.r.nextInt(1000)+3000,x,y);

	}
	public DeathZone getDZ(int x,int y,int w,int h){
		return new DeathZone(this.r.nextInt(100)+500,x,y,w,h);

	}
	public SpawnPoint getSpawnP(){
		return new SpawnPoint(this.r.nextInt(100)+700);
	}*/
}
