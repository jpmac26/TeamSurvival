package nmt.minecraft.TeamSurvival.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

/**
 * A wave of enemies.<br />
 * Responsible for keeping track of how many enemies are left to spawn and providing a way to spawn one.
 * @author Franz Chavez
 * @author James Pelster
 */
public class Wave {
	private String MobTypes[] = {"Zombie", "Skeleton","CaveSpider","Endermite","Creeper","Jockey","WitherSkeleton"};
	private int MobVals[] = {0,2,4,6,7,7,8,9};
	private List<Mob> Mobs;
	private List<LivingEntity> Entities;
	private int maxSpawned;
	/**
	 * Creates a wave with the given types of mobs.
	 * @param WaveNumber is the wave number
	 * @param TotalMobCount total number of mobs in wave
	 * @note See wave implementation in google doc file
	 */
	public Wave(int WaveNumber, int TotalMobCount) {
		Mobs = new ArrayList<Mob>();
		Entities = new ArrayList<LivingEntity>();
		maxSpawned = 40;
		Random rn = new Random();
		int MobPool = (WaveNumber-1)*2;
		int RandNum = rn.nextInt() % 7;
		int UsedCount = 0;
		String MobsUsed[] = {"","",""};
		int MobDiffs[] = {0,0,0};
		
		while(MobPool > 0 || UsedCount < 3){
			RandNum = rn.nextInt() % 7;
			MobDiffs[UsedCount] = MobVals[RandNum];
			if(MobPool - MobDiffs[UsedCount] >= 0){
				/* the mob can be added */
				MobsUsed[UsedCount] = MobTypes[RandNum];
				UsedCount++;
			}
		}
		SetupMobs(MobsUsed, TotalMobCount,MobDiffs);
	}
	
	/**
	 * Creates a wave with the given types of mobs.
	 * @param WaveNumber is the wave number
	 * @param TotalMobCount total number of mobs in wave
	 * @param MaxToSpawn maximum number of mobs that can be spawned at any given time
	 * @note See wave implementation in google doc file
	 */
	public Wave(int WaveNumber, int TotalMobCount, int MaxToSpawn) {
		Mobs = new ArrayList<Mob>();
		Entities = new ArrayList<LivingEntity>();
		maxSpawned = MaxToSpawn;
		Random rn = new Random();
		int MobPool = (WaveNumber-1)*2;
		int RandNum;
		int UsedCount = 0;
		String MobsUsed[] = {"","",""};
		int MobDiffs[] = {0,0,0};
		
		while(MobPool > 0 || UsedCount < 3){
			RandNum = rn.nextInt() % 7;
			MobDiffs[UsedCount] = MobVals[RandNum];
			if(MobPool - MobDiffs[UsedCount] >= 0){
				/* the mob can be added */
				MobsUsed[UsedCount] = MobTypes[RandNum];
				UsedCount++;
			}
		}
		SetupMobs(MobsUsed, TotalMobCount,MobDiffs);
	}
	
	/**
	 * Gets the list of mobs in the wave and produces the random equipment
	 * @param MobsUsed is the array of 3 types of mobs
	 * @param MobCount is the number of mobs in the wave
	 * @return 
	 * @note calls another function 
	 */
	private void SetupMobs(String MobsUsed[], int MobCount, int MobDiffs[]){
		int count = MobCount;
		while(count > 0){
			if(count == 0)break;
			Mobs.add(new Mob(MobsUsed[0],MobDiffs[0]));
			count--;
			if(count == 0)break;
			Mobs.add(new Mob(MobsUsed[1],MobDiffs[1]));
			count--;
			if(count == 0)break;
			Mobs.add(new Mob(MobsUsed[2],MobDiffs[2]));
			count--;
		}
	}
	

	/**
	 * Gets a random mob from what's left to spawn and spawns it, returning the entity
	 * @return
	 */
	public void spawnRandomMob(Location location) {
		Random rn = new Random();
		int RandNum = rn.nextInt() % Mobs.size();
		Entities.add(Mobs.get(RandNum).SpawnEntity(location));
		Mobs.remove(RandNum);
	}
	
	/**
	 * Sets the wave to begin spawning the mobs.<br />
	 * Handles registering for events, etc.
	 */
	public void start() {
		while(Entities.size() < maxSpawned & Mobs.size() > 0) {
			//spawnRandomMob(new Location());
		}
	}
	
	/**
	 * Stops the wave, for emergency purposes.<br />
	 * Entities should be cleared, and the wave should handle de-registration
	 */
	public void stop() {
		; //TODO
	}
	
	/**
	 * Checks whether this wave is complete.<br />
	 * Complete is defined to be that all entities belonging to this wave are dead.
	 * @return
	 */
	public boolean isComplete() {
		return false; //TODO
	}
}
