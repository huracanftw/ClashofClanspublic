package me.crolemol.coc.arena.building;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.Storage;
import me.crolemol.coc.arena.building.interfaces.StorageBuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.DarkElixirStoragePanel;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;

public class DarkElixirStorage implements Storage{
	static Coc plugin = Coc.getPlugin();
	private int BuildingID2;
	private int level2;
	private Location loc2;
	private OfflinePlayer owner2;
	private boolean isRealBuilding;
		public DarkElixirStorage(OfflinePlayer owner,int level){
			level2 = level;
			owner2 = owner;
			isRealBuilding=false;
		}
		
		private DarkElixirStorage(OfflinePlayer owner,Location loc,int level,int BuildingID, boolean isreal){
			BuildingID2 = BuildingID;
			level2 = level;
			loc2 = loc;
			owner2 = owner;
			isRealBuilding = isreal;
		}

	

	public static DarkElixirStorage getElixirStorage(int BuildingID,
			OfflinePlayer owner) {
		FileConfiguration dataconf = plugin.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("darkelixirstorage."+BuildingID+".location.x");
		int y = dataconf.getInt("darkelixirstorage."+BuildingID+".location.y");
		int z = dataconf.getInt("darkelixirstorage."+BuildingID+".location.z");
		
		return new DarkElixirStorage(owner,new Location(world,x,y,z),dataconf.getInt("darkelixirstorage."+BuildingID+".level"),BuildingID,true);
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
	public int getCapacity() {
		if(getLevel() == 0){return 0;}
		specsDarkElixirStorage[] specs2 = specsDarkElixirStorage.values();
		return specs2[getLevel()-1].getCapacity();
		}
	
	@Override
	public String getBuildingName() {
		return "darkelixirstorage";
	}

	public enum specsDarkElixirStorage implements StorageBuildingSpecs{
		lv1(new Elixir(600000),1440,293,10000,2000,7),
		lv2(new Elixir(1200000),2880,415,20000,2200,7),
		lv3(new Elixir(1800000),4320,509,40000,2400,8),
		lv4(new Elixir(2400000),5760,587,80000,2600,8),
		lv5(new Elixir(3000000),7200,657,150000,2900,9),
		lv6(new Elixir(3600000),8640,720,200000,3200,9);
		Resource cost;
		int time;
		int exp;
		int capacity;
		int health;
		int level;
		specsDarkElixirStorage(Resource cost2,int time2,int exp2, int capacity2,int health2,int level2){
			cost = cost2;
			time = time2;
			exp = exp2;
			capacity = capacity2;
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
		public int getCapacity() {
			return capacity;
		}
	}



	@Override
	public StorageBuildingSpecs[] getBuildingSpecs() {
		return specsDarkElixirStorage.values();
	}




	@Override
	public void setLevel(int level) {
		level2 = level;
		if(isRealBuilding() == true){
			FileConfiguration dataconf = plugin.getdataconffile(owner2);
			dataconf.set(getBuildingName()+"."+BuildingID2+".level", level2);
			plugin.saveDataconf(owner2);
		}
		
	}







	@Override
	public void setLocation(Location location) {
		loc2 = location;
		if(isRealBuilding() == true){
			FileConfiguration dataconf = plugin.getdataconffile(owner2);
			dataconf.set(getBuildingName()+"."+BuildingID2+".location.x", loc2.getBlockX());
			dataconf.set(getBuildingName()+"."+BuildingID2+".location.y", loc2.getBlockY());
			dataconf.set(getBuildingName()+"."+BuildingID2+".location.z", loc2.getBlockZ());
			plugin.saveDataconf(owner2);
		}
		
	}






	@Override
	public BuildingPanel getBuildingPanel() {
		DarkElixirStoragePanel esp = new DarkElixirStoragePanel(this);
		return esp;
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
