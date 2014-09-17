package me.crolemol.coc.arena.building.interfaces;

import me.crolemol.coc.Coc;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;


public interface Building{
	Coc plugin = Coc.getPlugin();
	

	
	public int getBuildingID();
	public int getLevel();
	public Location getLocation();
	public OfflinePlayer getOwner();
	public String getBuildingName();
	public BuildingSpecs[] getBuildingSpecs();
	public void setLevel(int level);
	public void setLocation(Location location);
	public BuildingPanel getBuildingPanel();
	public boolean isUpgrading();
	public boolean isRealBuilding();
}
