package me.crolemol.coc.army.troops;

import java.util.UUID;
import java.util.logging.Level;

import net.minecraft.server.v1_7_R4.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.crolemol.npc.NPCEntity;
import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.BuildingType;
import me.crolemol.coc.arena.building.ArmyCamp;
import me.crolemol.coc.arena.building.ArmyCamp.ArmyCampSpec;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.economy.Resource;

public abstract class Soldier{
	public Soldier(NPCEntity entity,Player owner,int Level){
		this.owner = owner;
		this.level = Level;
		this.entity = entity;
		this.health = getMaxCocHealth();
	}
	NPCEntity entity;
	OfflinePlayer owner;
	int level;
	double health;
	public abstract double getMaxCocHealth();
	public double getCocHealth(){
		return health;
	}
	public abstract Resource getTrainingCost();
	public Location getLocation(){
		return this.getNPC().getBukkitEntity().getLocation();
	}
	public abstract Building getFavouriteBuilding();
	public abstract int getMovementSpeed();
	public abstract int getHousingSpace();
	public abstract int getTrainingTime();
	public abstract double getDamagePerAttack();
	public abstract double getAttackSpeed();
	public OfflinePlayer getOwner(){
		return owner;
	}
	public NPCEntity getNPC(){
		return this.entity;
	}
	public int getLevel(){
		return level;
	}
	public abstract double getRange();
	private static EntityPlayer getEntityPlayer(Location loc, UUID uuid){
		for(Entity e : loc.getWorld().getEntities()){
			if(e instanceof EntityPlayer){
			if(e.getLocation().distance(loc) > 100){
				if(e.getUniqueId().equals(uuid)){
					return (EntityPlayer) e;
				}
			}
		}
		}
		return null;
	}
	public void addToArmyCamps(){
		Base base = Base.getBase(getOwner());
		int armycampscounter = base.getAmountofBuilding(BuildingType.ArmyCamp.getName());
		int housingspacecounter = 0;
		for(int counter=1;armycampscounter >= counter;counter++){
			ArmyCamp armycamp = ArmyCamp.getArmyCamp(counter, getOwner());
			housingspacecounter += ArmyCampSpec.values()[armycamp.getLevel()-1].getTroopcapacity();
			int troophousing = armycamp.getTotalHousingSpace() - armycamp.getFreeHousingSpace();
			if(troophousing < housingspacecounter){
				addToArmyCamp(armycamp);
				break;
			}
		}
	}
	private void addToArmyCamp(ArmyCamp armycamp){
		if(owner.getUniqueId() != armycamp.getOwner().getUniqueId()){
			throw new IllegalArgumentException("the soldier and the armycamp don't have the same owner");
		}
		boolean bol = getNPC().pathfindTo(armycamp.getLocation().add(0, 1, 0));
		Bukkit.getServer().getLogger().log(Level.INFO, bol+"");
	}
}
