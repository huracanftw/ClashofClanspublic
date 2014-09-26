package me.crolemol.coc.army.troops.troops;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.army.troops.Soldier;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;
import me.crolemol.npc.NPCEntity;
import me.crolemol.npc.NPCFactory;
import me.crolemol.npc.NPCProfile;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class Barbarian extends Soldier{
	protected Barbarian(NPCEntity soldier) {
		super(soldier);
	}
	private int Health;
	private int level;
	private int TroopID;
	
	
	public static Soldier spawnBarbarian(Location location,OfflinePlayer owner,int level){
		NPCFactory factory = new NPCFactory(Coc.getPlugin());
		return (Soldier) factory.spawnHumanNPC(location, new NPCProfile("Barbarian"));
	}
	@Override
	public int getMaxCocHealth() {
		return barbarianspecs.values()[level-1].getHealth();
	}

	@Override
	public Resource getTrainingCost() {
		return barbarianspecs.values()[level-1].getTrainingCost();
	}

	@Override
	public Building getFavouriteBuilding() {
		return null;
	}

	@Override
	public int getMovementSpeed() {
		return 16;
	}

	@Override
	public int getHousingSpace() {
		return 1;
	}

	@Override
	public Long getTrainingTime() {
		return (long) 20;
	}
	@Override
	public int getDamagePerSecond() {
	return barbarianspecs.values()[level-1].getdamage();
	}
	public enum barbarianspecs{
		lv1(8,45,new Elixir(25)),
		lv2(11,54,new Elixir(40)),
		lv3(14,65,new Elixir(60)),
		lv4(18,78,new Elixir(80)),
		lv5(23,95,new Elixir(100)),
		lv6(26,110,new Elixir(150));
		int damage;
		int health;
		Resource trainingcost;
	barbarianspecs(int damage,int health,Resource trainingcost){
		this.damage = damage;
		this.health = health;
		this.trainingcost = trainingcost;
	}
	private int getdamage(){
		return damage;
	}
	private int getHealth(){
		return health;
	}
	private Resource getTrainingCost(){
		return trainingcost;
	}
	}
	@Override
	public int getCocHealth() {
		return Health;
	}
	@Override
	public int getTroopID() {
		return TroopID;
	}
	
}
