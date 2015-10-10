package nmt.minecraft.TeamSurvival.Enemy;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;

import nmt.minecraft.TeamSurvival.TeamSurvivalPlugin;

/**
 * A boss wave
 * @author Skyler
 *
 */
public class BossWave extends Wave {

	/*GROSS static defaults!*/
	private static final int bossCount = 5;
	
	private Mob bossMob;
	
	private Location spawnLocation;
	
	public BossWave(Location spawnLocation, Mob bossMob) {
		this.spawnLocation = spawnLocation;
		this.bossMob = bossMob;
	}
	
	@Override
	public void start() {
		//spawn bosses
		for (int i = 1; i < BossWave.bossCount; i++) {
			Entities.add(bossMob.SpawnEntity(spawnLocation));
		}
		
		Bukkit.getPluginManager().registerEvents(this, 
				TeamSurvivalPlugin.plugin);
	}
	
	@Override
	public void stop() {
		if (!Entities.isEmpty())
		for (Entity e : Entities){
			e.remove();
		}
		
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler
	@Override
	public void onEntityDeath(EntityDeathEvent e) {
		if (Entities.contains(e.getEntity())) {
			Entities.remove(e.getEntity());
		}
		
		if (Entities.isEmpty()) {
			stop();
			Bukkit.getPluginManager().callEvent(new WaveFinishEvent(this));
		}
	}
	
	@Override
	public BossWave clone(List<Location> locations) {
		BossWave wave = new BossWave(locations.get(0), bossMob);
		return wave;
	}
	
	@Override
	public boolean isComplete() {
		return (Entities.isEmpty());
	}

}
