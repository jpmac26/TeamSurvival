package nmt.minecraft.TeamSurvival.Enemy;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import nmt.minecraft.TeamSurvival.TeamSurvivalPlugin;

/**
 * A boss wave
 * @author Skyler
 *
 */
public class BossWave extends Wave {

	private Mob bossMob;
	
	private Location spawnLocation;
	
	private Entity bossEntity;
	
	public BossWave(Location spawnLocation, Mob bossMob) {
		this.spawnLocation = spawnLocation;
		this.bossMob = bossMob;
	}
	
	@Override
	public void start() {
		//spawn boss
		bossEntity = bossMob.SpawnEntity(spawnLocation);
		
		Bukkit.getPluginManager().registerEvents(this, 
				TeamSurvivalPlugin.plugin);
	}
	
	@Override
	public void stop() {
		if (bossEntity != null && !bossEntity.isDead()) {
			bossEntity.remove();
		}
	}
	
	@EventHandler
	@Override
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity().equals(bossEntity)) {
			Bukkit.getPluginManager().callEvent(new WaveFinishEvent(this));
			return;
		}
	}
	
	@Override
	public BossWave clone(List<Location> locations) {
		BossWave wave = new BossWave(locations.get(0), bossMob);
		return wave;
	}
	
	@Override
	public boolean isComplete() {
		return (bossEntity != null && bossEntity.isDead());
		//is null? - > false
		//is not null, but isn't dead? -> false
		//isn't null, and is dead? -> true
	}

}
