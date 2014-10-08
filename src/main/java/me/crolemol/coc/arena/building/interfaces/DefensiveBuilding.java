package me.crolemol.coc.arena.building.interfaces;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public abstract class DefensiveBuilding extends Building{

	protected DefensiveBuilding(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}
	protected DefensiveBuilding(int Level){
		super(Level);
	}
	public abstract double getDamagePerShot();
	public abstract void Shoot(Location loc);
	public abstract int getRange();
	public abstract double getAttackSpeed();

}
