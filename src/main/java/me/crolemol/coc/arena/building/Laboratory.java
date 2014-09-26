package me.crolemol.coc.arena.building;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.LaboratoryPanel;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;

public class Laboratory extends Building{
	public Laboratory(int Level){
		super(Level);
	}

	protected Laboratory(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}

	@Override
	public String getBuildingName() {
		return "laboratory";
	}
	public enum LaboratorySpecs implements BuildingSpecs{
		lv1(250,new Elixir(25000),30,42,3),
		lv2(270,new Elixir(50000),300,134,4),
		lv3(280,new Elixir(90000),720,207,5),
		lv4(290,new Elixir(270000),1440,293,6),
		lv5(310,new Elixir(500000),2880,415,7),
		lv6(330,new Elixir(1000000),5760,587,8),
		lv7(350,new Elixir(2500000),7200,657,9),
		lv8(370,new Elixir(4000000),8640,720,10);
		int health;
		Resource cost;
		int time;
		int exp;
		int thlv;
		LaboratorySpecs(int health,Resource cost,int time,int exp,int thlv){
			this.health = health;
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
	}
	@Override
	public LaboratorySpecs[] getBuildingSpecs() {
		return LaboratorySpecs.values();
	}

	@Override
	public BuildingPanel getBuildingPanel() {
		return new LaboratoryPanel(this);
	}

}
