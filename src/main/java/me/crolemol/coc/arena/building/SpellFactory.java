package me.crolemol.coc.arena.building;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;

public class SpellFactory extends Building{

	protected SpellFactory(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}
	public SpellFactory(int Level) {
		super(Level);
	}
	@Override
	public String getBuildingName() {
		return "spellfactory";
	}
	public enum SpellFactorySpecs implements BuildingSpecs{
		lv1(200,1,new Elixir(200000),1440,293,5),
		lv2(300,2,new Elixir(400000),2880,415,6),
		lv3(400,3,new Elixir(800000),5760,587,7),
		lv4(500,4,new Elixir(1600000),7200,657,9),
		lv5(600,5,new Elixir(3200000),8640,720,10);
		Resource cost;
		int health;
		int spellcapacity;
		int time;
		int exp;
		int thlv;
		SpellFactorySpecs(int health,int spellcapacity,Resource cost,int time,int exp,int thlv){
			this.health = health;
			this.spellcapacity = spellcapacity;
			this.cost = cost;
			this.time = time;
			this.exp = exp;
			this.thlv = thlv;
		}

		@Override
		public Resource getUpgradePrice() {
			return cost;
		}

		@Override
		public int getGainExpOnUpgrade() {
			return exp;
		}

		@Override
		public int getUpgradeTime() {
			return time;
		}

		@Override
		public int getHealth() {
			return health;
		}

		@Override
		public int getMinTownhallLevel() {
			return thlv;
		}
		public int getSpellCapacity(){
			return spellcapacity;
		}
		public int getBoostCost(){
			return 10;
		}
	}
	@Override
	public SpellFactorySpecs[] getBuildingSpecs() {
		return SpellFactorySpecs.values();
	}
	@Override
	public BuildingPanel getBuildingPanel() {
		// TODO Auto-generated method stub
		return null;
	}

}
