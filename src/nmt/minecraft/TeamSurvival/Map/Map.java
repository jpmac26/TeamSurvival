package nmt.minecraft.TeamSurvival.Map;

import java.util.Collection;

import org.bukkit.Location;

/**
 * This Class represents a single map within Team Survival.<br />
 * Each map will contain a Shop Location and a Collection of Locations<br>
 * for players to spawn in.
 * @author William
 *
 */
public class Map {
	/**
	 * This is the Location of the Shop within Team Survival
	 */
	private Location ShopLocation;
	
	/**
	 * This collection contains all the initial spawn locations for the Map.
	 */
	private Collection<Location> ArenaLocations;
	
	private Map() {
		
	}
	
	/**
	 * This method sets the Shop Location for the Map.
	 * @param location The Location of the Shop.
	 */
	public void setShopLocation(Location location) {
		this.ShopLocation = location;
	}
	
	/**
	 * This adds a single location to this Map's Arena Locations.
	 * @param location The location to add.
	 * @return True if the location was successfully added.
	 */
	public boolean addArenaLocation(Location location) {
		return this.ArenaLocations.add(location);
	}
	
	/**
	 * This method adds multiple locations to this Map's Arena Locations.
	 * @param locations The Collection of Locations to add.
	 * @return True if all the locations could be added.
	 */
	public boolean addArenaLocation(Collection<Location> locations) {
		return this.ArenaLocations.addAll(locations);
	}
	
	/**
	 * This method returns the Arena Locations of this Map.
	 * @return A collection of all the Arena Locations.
	 */
	public Collection<Location> getArenaLocations()	{
		return this.ArenaLocations;
	}
	
	/**
	 * This method returns the Shop Location of this Map.
	 * @return
	 */
	public Location getShopLocation() {
		return this.ShopLocation;
	}
}
