package nmt.minecraft.TeamSurvival.Enemy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import nmt.minecraft.TeamSurvival.TeamSurvivalPlugin;

/**
 * A wave of enemies.<br />
 * Responsible for keeping track of how many enemies are left to spawn and providing a way to spawn one.
 * @author Franz Chavez
 * @author James Pelster
 */
public class Wave implements Listener {
	private String MobTypes[] = {"Zombie","Skeleton","Cave_Spider","Endermite","Creeper","Jockey","Wither_Skeleton"};
	private int MobVals[] = {0,4,3,2,7,7,8,6};
	private List<Mob> Mobs;
	private List<LivingEntity> Entities;
	private int maxSpawned, waveN;
	private boolean started;
	private List<Location> MobSpawnPoints = new ArrayList<Location>();
	
	/**
	 * Creates a wave with the given types of mobs.
	 * @param WaveNumber is the wave number
	 * @param spawnPoints is the list of Mob spawn points for the map
	 * @param TotalMobCount total number of mobs in wave
	 * @note See wave implementation in google doc file
	 */
	public Wave(int WaveNumber, List<Location> spawnPoints, int TotalMobCount) {
		this();
		try {
			maxSpawned = 40; //#magicNumber
			waveN = WaveNumber;
			MobSpawnPoints = spawnPoints;
			Random rn = new Random();
			int MobPool = (WaveNumber-1)*2;
			int RandNum;
			int UsedCount = 0;
			String MobsUsed[] = new String[3];
			int MobDiffs[] = new int[3];
			
			while(MobPool >= 0 & UsedCount < 3) {
				RandNum = rn.nextInt(7); //#magicNumber -> should be MobVals.size
				MobDiffs[UsedCount] = MobVals[RandNum];
				if(MobPool - MobDiffs[UsedCount] >= 0){
					/* the mob can be added */
					MobsUsed[UsedCount] = MobTypes[RandNum];
					UsedCount++;
				}
			}
			SetupMobs(MobsUsed, TotalMobCount, MobDiffs, WaveNumber);
		}
		catch (Exception e) {
			TeamSurvivalPlugin.plugin.getLogger().info("Error: " + e + "\r\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a wave with the given types of mobs.
	 * @param WaveNumber is the wave number
	 * @param spawnPoints is the list of Mob spawn points for the map
	 * @param TotalMobCount total number of mobs in wave
	 * @param MaxToSpawn maximum number of mobs that can be spawned at any given time
	 * @note See wave implementation in google doc file
	 */
	public Wave(int WaveNumber, List<Location> spawnPoints, int TotalMobCount, int MaxToSpawn) {
		this();
		maxSpawned = MaxToSpawn;
		MobSpawnPoints = spawnPoints;
		Random rn = new Random();
		int MobPool = (WaveNumber-1)*2;
		int RandNum;
		int UsedCount = 0;
		String MobsUsed[] = new String[3];
		int MobDiffs[] = new int[3];
		
		while(MobPool >= 0 & UsedCount < 3){
			RandNum = rn.nextInt(7);
			MobDiffs[UsedCount] = MobVals[RandNum];
			if(MobPool - MobDiffs[UsedCount] >= 0){
				/* the mob can be added */
				MobsUsed[UsedCount] = MobTypes[RandNum];
				UsedCount++;
			}
		}
		SetupMobs(MobsUsed, TotalMobCount, MobDiffs, WaveNumber);
	}
	
	/**
	 * Sets up a basic wave with no specifications, for construction in a factory method (see {@link #clone})
	 */
	protected Wave() {
		Mobs = new LinkedList<Mob>();
		Entities = new LinkedList<LivingEntity>();
		started = false;
		Bukkit.getPluginManager().registerEvents(this, TeamSurvivalPlugin.plugin);
	}
	
	/**
	 * Gets the list of mobs in the wave and produces the random equipment
	 * @param MobsUsed is the array of 3 types of mobs
	 * @param MobCount is the number of mobs in the wave
	 * @return 
	 * @note calls another function 
	 */
	private void SetupMobs(String MobsUsed[], int MobCount, int MobDiffs[], int waveNum){
		int count = MobCount;
		while(count > 0){
			if(count == 0)break;
			Mobs.add(new Mob(MobsUsed[0],MobDiffs[0], waveNum));
			count--;
			if(count == 0)break;
			Mobs.add(new Mob(MobsUsed[1],MobDiffs[1], waveNum));
			count--;
			if(count == 0)break;
			Mobs.add(new Mob(MobsUsed[2],MobDiffs[2], waveNum));
			count--;
		}
	}
	

	/**
	 * Gets a random mob from what's left to spawn and spawns it, returning the entity
	 * @return
	 */
	public void spawnRandomMob(Location location) {
		Random rn = new Random();
		int RandNum = rn.nextInt(Mobs.size());
		Entities.add(Mobs.get(RandNum).SpawnEntity(location));
		Mobs.remove(RandNum);
	}
	
	/**
	 * Sets the wave to begin spawning the mobs.<br />
	 * Handles registering for events, etc.
	 */
	public void start() {
		started = true;
		while(started == true && Entities.size() < maxSpawned && Mobs.size() > 0) {
			Random rn = new Random();
			int RandPoint = rn.nextInt(MobSpawnPoints.size());
			spawnRandomMob(MobSpawnPoints.get(RandPoint));
		}
	}
	
	/**
	 * Stops the wave, for emergency purposes.<br />
	 * Entities should be cleared, and the wave should handle de-registration.
	 */
	public void stop() {
		started = false;
		clearWave();
		HandlerList.unregisterAll(this);;
	}
	
	/**
	 * Tells the wave to check  if it needs to spawn more mobs.<br />
	 * It will also check if the wave is complete or has been force-stopped.
	 */
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		try {
			if(started == true) {
				if(Entities.contains(event.getEntity())){
						Entities.remove(event.getEntity());
						//Remove entity drops so we don't clutter up the battlefield
						event.getDrops().clear();
						//Spawn a new mob to replace the one that just died
						if (Mobs.size() > 0) {
							TeamSurvivalPlugin.plugin.getLogger().info("Spawning new mob to replace dead one.\n Remaining: " + ((Integer)(Mobs.size() - 1)).toString() + ".\r\n");
							Random rn = new Random();
							int RandPoint = rn.nextInt(MobSpawnPoints.size());
							spawnRandomMob(MobSpawnPoints.get(RandPoint));
						}
						
						if(isComplete() == true) {
							Bukkit.getPluginManager().callEvent(new WaveFinishEvent(this));
							HandlerList.unregisterAll(this);
						}
						
						//break;
					}
				}
			//}
		} catch (Exception e) {
			TeamSurvivalPlugin.plugin.getLogger().info("Error: " + e + "\r\n");
			e.printStackTrace();
		}
	}
	
	private void clearWave() {
		Mobs.clear();
		for(LivingEntity ent : Entities) {
			if(ent.getPassenger() != null) {
				ent.getPassenger().getLocation().setY(0);
				ent.getPassenger().remove();
			}
			ent.getLocation().setY(0);
			ent.remove();
		}
		Entities.clear();
	}
	
	/**
	 * Checks whether this wave is complete.<br />
	 * Complete is defined to be that all entities belonging to this wave are dead.
	 * @return
	 */
	public boolean isComplete() {
		return Entities.isEmpty();
	}
	/**
	 * Returns a clone of the wave. 
	 * @param needs the list of the locations of the new 
	 */
	public Wave clone(List<Location> m){
		Wave NW = new Wave();

		NW.maxSpawned = maxSpawned;
		NW.waveN = this.waveN;
		for (Mob mob : Mobs) {
			NW.Mobs.add(mob);
		}
		
		NW.MobSpawnPoints = m;
		
		return NW;
	}
}
