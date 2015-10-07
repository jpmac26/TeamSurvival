package nmt.minecraft.TeamSurvival.Map;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nmt.minecraft.TeamSurvival.TeamSurvivalPlugin;

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
	 * Location of the Shop Button
	 */
	private Location shopButtonLocation;
	
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
	
	public Location getShopButtonLocation() {
		return this.shopButtonLocation;
	}
	
	/**
	 * This static method prints out all the yml configuration files<br />
	 * that could possibly be loaded into a map.
	 */
	public static List<String> listConfigs() {
		File resourceFolder = TeamSurvivalPlugin.plugin.getDataFolder();
		File[] resourceFiles = resourceFolder.listFiles();
		Pattern ymlPattern = Pattern.compile("*.yml");
		
		List<String> configFilenames = new LinkedList<String>();
		for (File file : resourceFiles) {
			//Get the Filename
			String fileName = file.getName();
			//Search filename for the .yml extension
			Matcher isYml = ymlPattern.matcher(fileName);
			if (isYml.find()) {
				//Remove .yml extension
				String cleanString = (fileName.split("."))[0];
				configFilenames.add(cleanString);
			}
		}
		return configFilenames;
	}
}
