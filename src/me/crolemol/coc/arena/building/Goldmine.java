package me.crolemol.coc.arena.building;

import java.io.IOException;
import java.util.Calendar;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.ResourceBuilding;
import me.crolemol.coc.arena.panels.Specs.specsGoldMine;
import me.crolemol.coc.economy.Resources;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Goldmine implements ResourceBuilding{
	static Coc plugin = Coc.getPlugin();
	private int BuildingID2;
	private int level2;
	private Location loc2;
	private Player owner2;
		public Goldmine(Player owner,Location loc,int level,int BuildingID){
			BuildingID2 = BuildingID;
			level2 = level;
			loc2 = loc;
			owner2 = owner;
		}

		


	public static Goldmine getGoldmine(int BuildingID,
			Player owner) {
		FileConfiguration dataconf = Coc.getdataconffile(owner);
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
	public Player getOwner() {
		return owner2;
	}

	@Override
	public int getProduction() {
		specsGoldMine[] specs2 = specsGoldMine.values();
		return specs2[getLevel()-1].getProduction();}
	@Override
	public int getCapacity() {
		specsGoldMine[] specs2 = specsGoldMine.values();
		return specs2[getLevel()-1].getCapacity();}
	@Override
	public int getCollectable() {
		FileConfiguration dataconf = Coc.getdataconffile(owner2);
		specsGoldMine[] spec = specsGoldMine.values();
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
		int collect = getCollectable();
		FileConfiguration dataconf = Coc.getdataconffile(owner2);
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		dataconf.set("goldmine."+BuildingID2+".lastcollect", caltime);
		try {
			dataconf.save(Coc.getdatafile(owner2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Resources.giveGold(owner2, collect);

		
	}


}
