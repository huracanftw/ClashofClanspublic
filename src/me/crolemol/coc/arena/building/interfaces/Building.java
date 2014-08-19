package me.crolemol.coc.arena.building.interfaces;

import me.crolemol.coc.Coc;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public interface Building{
	Coc plugin = Coc.getPlugin();
	

	
	public int getBuildingID();
	public int getLevel();
	public Location getLocation();
	public Player getOwner();
	public String getBuildingName();
}
