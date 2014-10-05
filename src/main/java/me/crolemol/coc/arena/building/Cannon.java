package me.crolemol.coc.arena.building;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.CannonPanel;
import me.crolemol.coc.economy.Gold;
import me.crolemol.coc.economy.Resource;

public class Cannon extends Building{

	protected Cannon(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}
	public Cannon(int Level){
		super(Level);
	}

	@Override
	public String getBuildingName() {
		return "cannon";
	}

	@Override
	public BuildingSpecs[] getBuildingSpecs() {
		return specsCannon.values();
	}

	@Override
	public BuildingPanel getBuildingPanel() {
		return new CannonPanel(this);
	}
	
	public enum specsCannon implements BuildingSpecs{
		lv1(new Gold(250),1,7,400,1),
		lv2(new Gold(1000),15,30,450,1),
		lv3(new Gold(4000),45,51,500,2),
		lv4(new Gold(16000),120,84,550,3),
		lv5(new Gold(50000),360,146,590,4),
		lv6(new Gold(100000),720,207,610,5),
		lv7(new Gold(200000),1440,293,630,6),
		lv8(new Gold(400000),2880,415,660,7),
		lv9(new Gold(800000),4320,509,690,8),
		lv10(new Gold(1600000),5760,587,750,8),
		lv11(new Gold(3200000),7200,657,900,9),
		lv12(new Gold(6400000),8640,720,1080,10);
		Resource cost;
		int time;
		int exp;
		int health;
		int level;
		specsCannon(Resource cost2,int time2,int exp2,int health2,int level2){
			cost = cost2;
			time = time2;
			exp = exp2;
			health = health2;
			level = level2;
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
			return level;
		}
	}
}
