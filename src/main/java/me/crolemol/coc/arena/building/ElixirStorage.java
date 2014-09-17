package me.crolemol.coc.arena.building;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.Storage;
import me.crolemol.coc.arena.building.interfaces.StorageBuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.ElixirStoragePanel;
import me.crolemol.coc.economy.Gold;
import me.crolemol.coc.economy.Resource;

public class ElixirStorage implements Storage{
	static Coc plugin = Coc.getPlugin();
	private int BuildingID2;
	private int level2;
	private Location loc2;
	private OfflinePlayer owner2;
	private boolean isRealBuilding;
	public ElixirStorage(OfflinePlayer owner,int level){
		level2 = level;
		owner2 = owner;
		isRealBuilding=false;
	}
	
	private ElixirStorage(OfflinePlayer owner,Location loc,int level,int BuildingID, boolean isreal){
		BuildingID2 = BuildingID;
		level2 = level;
		loc2 = loc;
		owner2 = owner;
		isRealBuilding = isreal;
	}

		


	public static ElixirStorage getElixirStorage(int BuildingID,
			OfflinePlayer owner) {
		FileConfiguration dataconf = plugin.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("elixirstorage."+BuildingID+".location.x");
		int y = dataconf.getInt("elixirstorage."+BuildingID+".location.y");
		int z = dataconf.getInt("elixirstorage."+BuildingID+".location.z");
		
		return new ElixirStorage(owner,new Location(world,x,y,z),dataconf.getInt("elixirstorage."+BuildingID+".level"),BuildingID,true);
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
		specsElixirStorage[] specs2 = specsElixirStorage.values();
		return specs2[getLevel()-1].getCapacity();
		}
	
	@Override
	public String getBuildingName() {
		return "elixirstorage";
	}

	public enum specsElixirStorage implements StorageBuildingSpecs{
		lv1(new Gold(300),1,30,1500,400,1),
		lv2(new Gold(750),30,42,3000,600,1),
		lv3(new Gold(1500),60,60,6000,800,2),
		lv4(new Gold(3000),120,84,12000,1000,2),
		lv5(new Gold(6000),180,103,25000,1200,3),
		lv6(new Gold(12000),240,120,50000,1500,3),
		lv7(new Gold(25000),360,146,100000,1650,4),
		lv8(new Gold(50000),480,169,250000,1740,4),
		lv9(new Gold(100000),720,207,500000,1820,5),
		lv10(new Gold(250000),1440,293,1000000,1920,5),
		lv11(new Gold(500000),2880,415,2000000,2016,7);
		Resource cost;
		int time;
		int exp;
		int capacity;
		int health;
		int level;
		specsElixirStorage(Resource cost2,int time2,int exp2, int capacity2,int health2,int level2){
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
		return specsElixirStorage.values();
	}




	@Override
	public void setLevel(int level) {
		level2 = level;
		if(isRealBuilding()== true){
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
		ElixirStoragePanel esp = new ElixirStoragePanel(this);
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
