package me.crolemol.coc.arena.building;

import java.io.IOException;
import java.util.Calendar;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.ResourceBuilding;
import me.crolemol.coc.arena.building.interfaces.ResourceBuildingSpecs;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;
import me.crolemol.coc.economy.Resources;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class Goldmine implements ResourceBuilding{
	static Coc plugin = Coc.getPlugin();
	private int BuildingID2;
	private int level2;
	private Location loc2;
	private OfflinePlayer owner2;
		public Goldmine(OfflinePlayer owner,Location loc,int level,int BuildingID){
			BuildingID2 = BuildingID;
			level2 = level;
			loc2 = loc;
			owner2 = owner;
		}

		


	public static Goldmine getGoldmine(int BuildingID,
			OfflinePlayer owner) {
		FileConfiguration dataconf = plugin.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("goldmine."+BuildingID+".location.x");
		int y = dataconf.getInt("goldmine."+BuildingID+".location.y");
		int z = dataconf.getInt("goldmine."+BuildingID+".location.z");
		
		return new Goldmine(owner,new Location(world,x,y,z),dataconf.getInt("goldmine."+BuildingID+".level"),BuildingID);
	}

	@Override
	public int getBuildingID() {
		return BuildingID2;
	}

	@Override
	public int getLevel() {
		return level2;
	}

	@Override
	public Location getLocation() {
		return loc2;
	}

	@Override
	public OfflinePlayer getOwner() {
		return owner2;
	}

	@Override
	public int getProduction() {
		if(getLevel() == 0){return 0;}
		specsGoldMine[] specs2 = (specsGoldMine[]) getBuildingSpecs();
		return specs2[getLevel()-1].getProduction();}
	@Override
	public int getCapacity() {
		if(getLevel() == 0){return 0;}
		specsGoldMine[] specs2 = specsGoldMine.values();
		return specs2[getLevel()-1].getCapacity();}
	@Override
	public int getCollectable() {
		if(getLevel() == 0){return 0;}
		if(getBuildingID() == 0){return 0;}
		FileConfiguration dataconf = plugin.getdataconffile(owner2);
		specsGoldMine[] spec = (specsGoldMine[]) getBuildingSpecs();
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		Long time1 = dataconf.getLong("goldmine."+BuildingID2+".lastcollect");
		double time2 = caltime-time1;
		int gold = (int) (time2*Math.floor(spec[level2-1].getProduction()/60));
		return gold;
	}
	@Override
	public String getBuildingName() {
		return "goldmine";
	}
	@Override
	public void Collect() {
		if(getLevel() == 0){return;}
		if(getBuildingID() == 0){return;}
		int collect = getCollectable();
		FileConfiguration dataconf = plugin.getdataconffile(owner2);
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		dataconf.set("goldmine."+BuildingID2+".lastcollect", caltime);
		try {
			dataconf.save(plugin.getdatafile(owner2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Resources.giveGold(owner2, collect);

		
	}
	public enum specsGoldMine implements ResourceBuildingSpecs{
		lv1(new Elixir(50),1,7,500,200,400,1),
		lv2(new Elixir(300),5,17,1000,400,450,1),
		lv3(new Elixir(700),15,30,1500,600,500,2),
		lv4(new Elixir(1400),60,60,2500,800,550,2),
		lv5(new Elixir(3000),120,84,10000,1000,590,3),
		lv6(new Elixir(7000),360,146,20000,1300,610,3),
		lv7(new Elixir(14000),720,207,30000,1600,630,4),
		lv8(new Elixir(28000),1440,293,50000,1900,660,4),
		lv9(new Elixir(56000),2880,415,75000,2200,680,5),
		lv10(new Elixir(84000),4320,509,100000,2500,710,5),
		lv11(new Elixir(168000),5760,587,150000,3000,750,7);
		Resource cost;
		int time;
		int exp;
		int capacity;
		int production;
		int health;
		int level;
		specsGoldMine(Resource cost2,int time2,int exp2, int capacity2,int production2,int health2,int level2){
			cost = cost2;
			time = time2;
			exp = exp2;
			capacity = capacity2;
			production = production2;
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
		@Override
		public int getProduction() {
			return production;
		}
		@Override
		public int getCapacity() {
			return capacity;
		}
	}



	@Override
	public ResourceBuildingSpecs[] getBuildingSpecs() {
		return specsGoldMine.values();
	}




	@Override
	public void setLevel(int level) {
		level2 = level;
		if(BuildingID2 !=0){
			FileConfiguration dataconf = plugin.getdataconffile(owner2);
			dataconf.set("goldmine."+BuildingID2+".level", level2);
			plugin.saveDataconf(owner2);
		}
		
	}



	@Override
	public void setBuildingID(int BuildingID) {
		BuildingID2 = BuildingID;
		setLevel(level2);
		setLocation(loc2);
		
	}




	@Override
	public void setLocation(Location location) {
		loc2 = location;
		if(BuildingID2 != 0){
			FileConfiguration dataconf = plugin.getdataconffile(owner2);
			dataconf.set("goldmine."+BuildingID2+".location.x", loc2.getBlockX());
			dataconf.set("goldmine."+BuildingID2+".location.y", loc2.getBlockY());
			dataconf.set("goldmine."+BuildingID2+".location.z", loc2.getBlockZ());
			plugin.saveDataconf(owner2);
		}
		
	}




	@Override
	public void setCollectable(int collectable) {
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		if(level2 == 0){return;}
		if(BuildingID2 == 0){return;}
		Long lastcollect;
		double time = collectable / (getProduction() / 60);
		lastcollect = (long) (caltime - time);
		plugin.getdataconffile(owner2).set("goldmine."+BuildingID2+".lastcollect", lastcollect);
		
	}


}
