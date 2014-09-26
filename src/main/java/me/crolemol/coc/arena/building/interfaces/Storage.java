package me.crolemol.coc.arena.building.interfaces;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;


public abstract class Storage extends Building{
	protected Storage(int level) {
		super(level);
	}
	protected Storage(OfflinePlayer owner,Location loc,int level,int BuildingID,boolean isreal){
		super(owner,loc,level,BuildingID,isreal);
	}
	public abstract int getCapacity();

}
