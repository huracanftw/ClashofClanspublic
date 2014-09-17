package me.crolemol.coc.troops;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import me.crolemol.npc.NPCEntity;

import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.economy.Resource;

public interface Soldier{
	public int getMaxHealth();
	public int getHealth();
	public int getTroopID();
	public Resource getTrainingCost();
	public Location getLocation();
	public Building getFavouriteBuilding();
	public int getMovementSpeed();
	public int getHousingSpace();
	public Long getTrainingTime();
	public int getDamagePerSecond();
	public NPCEntity getNPC();
	public OfflinePlayer getOwner();
}
