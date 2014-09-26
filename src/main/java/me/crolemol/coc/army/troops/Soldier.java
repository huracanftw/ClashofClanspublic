package me.crolemol.coc.army.troops;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import me.crolemol.npc.NPCEntity;
import me.crolemol.npc.NPCNetworkManager;
import me.crolemol.npc.NPCProfile;
import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.economy.Resource;

public abstract class Soldier extends NPCEntity{
	protected Soldier(NPCEntity soldier){
		super(Coc.getPlugin().getServer().getWorld("coc"),new NPCProfile(soldier.getProfile()) , new NPCNetworkManager());
			}
	OfflinePlayer owner;
	int TroopID;
	public abstract int getMaxCocHealth();
	public abstract int getCocHealth();
	public int getTroopID(){
		return TroopID;
	}
	public abstract Resource getTrainingCost();
	public Location getLocation(){
		return this.getBukkitEntity().getLocation();
	}
	public abstract Building getFavouriteBuilding();
	public abstract int getMovementSpeed();
	public abstract int getHousingSpace();
	public abstract Long getTrainingTime();
	public abstract int getDamagePerSecond();
	public OfflinePlayer getOwner(){
		return owner;
	}
}
