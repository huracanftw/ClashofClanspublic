package me.crolemol.coc.arena.building;

import java.io.IOException;
import java.util.Calendar;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.ResourceBuilding;
import me.crolemol.coc.arena.building.interfaces.ResourceBuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.DarkElixirDrillPanel;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;
import me.crolemol.coc.economy.Resources;

public class DarkElixirDrill implements ResourceBuilding{
	static Coc plugin = Coc.getPlugin();
	private int BuildingID2;
	private int level2;
	private Location loc2;
	private OfflinePlayer owner2;
	private boolean isRealBuilding;
	
		public DarkElixirDrill(OfflinePlayer owner,int level){
			level2 = level;
			owner2 = owner;
			isRealBuilding = false;
		}
		private DarkElixirDrill(OfflinePlayer owner,Location loc,int level,int BuildingID,boolean isreal){
			BuildingID2 = BuildingID;
			level2 = level;
			loc2 = loc;
			owner2 = owner;
			isRealBuilding = isreal;
		}

		


	public static DarkElixirDrill getDarkElixirDrill(int BuildingID,
			OfflinePlayer owner) {
		if(BuildingID == 0){
			throw new IllegalArgumentException("BuildingID cannot be 0");
		}
		if(owner == null){
			throw new IllegalArgumentException("owner cannot be null");
		}
		FileConfiguration dataconf = plugin.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("darkelixirdrill."+BuildingID+".location.x");
		int y = dataconf.getInt("darkelixirdrill."+BuildingID+".location.y");
		int z = dataconf.getInt("darkelixirdrill."+BuildingID+".location.z");
		
		if(y == 0){
			return null;
		}
		
		return new DarkElixirDrill(owner,new Location(world,x,y,z),dataconf.getInt("darkelixirdrill."+BuildingID+".level"),BuildingID,true);
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
	public String getBuildingName() {
		return "darkelixirdrill";
	}

	@Override
	public void setLevel(int level) {
		level2 = level;
		if(isRealBuilding == true){
			FileConfiguration dataconf = plugin.getdataconffile(owner2);
			dataconf.set(getBuildingName()+"."+BuildingID2+".level", level2);
			plugin.saveDataconf(owner2);
		}
		
	}




	@Override
	public void setLocation(Location location) {
		loc2 = location;
		if(isRealBuilding == true){
			FileConfiguration dataconf = plugin.getdataconffile(owner2);
			dataconf.set(getBuildingName()+"."+BuildingID2+".location.x", loc2.getBlockX());
			dataconf.set(getBuildingName()+"."+BuildingID2+".location.y", loc2.getBlockY());
			dataconf.set(getBuildingName()+"."+BuildingID2+".location.z", loc2.getBlockZ());
			plugin.saveDataconf(owner2);
			}
		}

		
	@Override
	public int getProduction() {
		if(getLevel() == 0){return 0;}
		specsDarkElixirDrill[] specs2 = (specsDarkElixirDrill[]) getBuildingSpecs();
		return specs2[getLevel()-1].getProduction();
		}

	@Override
	public int getCapacity() {
		if(getLevel() == 0){return 0;}
		specsDarkElixirDrill[] specs2 = specsDarkElixirDrill.values();
		return specs2[getLevel()-1].getCapacity();
	}

	@Override
	public int getCollectable() {
		if(getLevel() == 0){return 0;}
		if(isRealBuilding() == false){return 0;}
		FileConfiguration dataconf = plugin.getdataconffile(owner2);
		specsDarkElixirDrill[] spec = (specsDarkElixirDrill[]) getBuildingSpecs();
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		Long time1 = dataconf.getLong(getBuildingName()+"."+BuildingID2+".lastcollect");
		double time2 = caltime-time1;
		int elixir = (int) (time2*Math.floor(spec[level2-1].getProduction()/60));
		return elixir;
	}

	@Override
	public void Collect() {
		if(getLevel() == 0){return;}
		if(isRealBuilding() == false){return;}
		int collect = getCollectable();
		FileConfiguration dataconf = plugin.getdataconffile(owner2);
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		dataconf.set(getBuildingName()+"."+BuildingID2+".lastcollect", caltime);
		try {
			dataconf.save(plugin.getdatafile(owner2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Resources.giveDarkElixir(owner2, collect);
		
	}

	@Override
	public void setCollectable(int collectable) {
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		if(isRealBuilding() == true){
		Long lastcollect;
		double time = collectable / (getProduction() / 60);
		lastcollect = (long) (caltime - time);
		plugin.getdataconffile(owner2).set(getBuildingName()+"."+BuildingID2+".lastcollect", lastcollect);
		}
	}

	public enum specsDarkElixirDrill implements ResourceBuildingSpecs{
		lv1(new Elixir(1000000),1140,293,120,20,400,8),
		lv2(new Elixir(1500000),2880,415,240,30,480,8),
		lv3(new Elixir(2000000),4320,509,450,45,580,8),
		lv4(new Elixir(3000000),5760,587,720,60,690,9),
		lv5(new Elixir(4000000),8640,720,1120,80,830,9),
		lv6(new Elixir(5000000),11520,831,1600,100,1000,9);
		Resource cost;
		int time;
		int exp;
		int capacity;
		int production;
		int health;
		int level;
		specsDarkElixirDrill(Resource cost2,int time2,int exp2, int capacity2,int production2,int health2,int level2){
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
		return specsDarkElixirDrill.values();
	}




	@Override
	public BuildingPanel getBuildingPanel() {
		return new DarkElixirDrillPanel(this);
	}
	
	@Override
	public boolean isUpgrading() {
		if(Coc.getPlugin().getdataconffile(owner2).contains(getBuildingName()+"."+getBuildingID()+".upgrade")){
		return true;	
		}else{
			return false;
		}

	}




	@Override
	public boolean isRealBuilding() {
		return isRealBuilding;
	}

}
