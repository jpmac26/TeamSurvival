package nmt.minecraft.TeamSurvival.Enemy;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

/**
 * A wave of enemies.<br />
 * Responsible for keeping track of how many enemies are left to spawn and providing a way to spawn one.
 * @author Skyler
 * @author Franz Chavez
 * @author James Pelster
 */
public class Wave {
	private String MobTypes[] = {"Zombie", "Skeleton","CaveSpider","Endermite","Creeper","Jockey","WitherSkeleton"};
	private int MobVals[] = {0,2,4,6,7,7,8,9};
	/**
	 * Creates a wave with the given types of mobs.
	 * @param WaveNumber is the wave number
	 * @param MobCount number of mobs
	 * @note See wave implementation in google doc file
	 */
	public Wave(int WaveNumber, int MobCount) {
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
		SetupMobs(MobsUsed, MobCount,MobDiffs);
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
		Mob Mobs[] = new Mob[MobCount];
		while(count > 0){
			if(count == 0)break;
			Mobs[MobCount-count] = new Mob(MobsUsed[0],MobDiffs[0]);
			count--;
			if(count == 0)break;
			Mobs[MobCount-count] = new Mob(MobsUsed[1],MobDiffs[1]);
			count--;
			if(count == 0)break;
			Mobs[MobCount-count] = new Mob(MobsUsed[2],MobDiffs[2]);
			count--;
		}
	}
	

	/**
	 * Gets a random mob from what's left to spawn and spawns it, returning the entity
	 * @return
	 */
	public LivingEntity spawnRandomMob(Location location) {
		return null; //TODO
	}
}
