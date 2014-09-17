package me.crolemol.coc.arena.building;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.Storage;
import me.crolemol.coc.arena.building.interfaces.StorageBuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.GoldStoragePanel;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;

public class GoldStorage implements Storage{
	static Coc plugin = Coc.getPlugin();
	private int BuildingID2;
	private int level2;
	private Location loc2;
	private OfflinePlayer owner2;
	private boolean isRealBuilding;
	public GoldStorage(OfflinePlayer owner,int level){
		level2 = level;
		owner2 = owner;
		isRealBuilding=false;
	}
	
	private GoldStorage(OfflinePlayer owner,Location loc,int level,int BuildingID, boolean isreal){
		BuildingID2 = BuildingID;
		level2 = level;
		loc2 = loc;
		owner2 = owner;
		isRealBuilding = isreal;
	}
	public static GoldStorage getGoldStorage(int BuildingID,
			OfflinePlayer owner) {
		FileConfiguration dataconf = plugin.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("goldstorage."+BuildingID+".location.x");
		int y = dataconf.getInt("goldstorage."+BuildingID+".location.y");
		int z = dataconf.getInt("goldstorage."+BuildingID+".location.z");
		
		return new GoldStorage(owner,new Location(world,x,y,z),dataconf.getInt("goldstorage."+BuildingID+".level"),BuildingID,true);
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
		return "goldstorage";
	}

	@Override
	public int getCapacity() {
		return 0;
	}
	public enum specsGoldStorage implements StorageBuildingSpecs{
		lv1(1500,400,new Elixir(300),1,30,1),
		lv2(3000,600,new Elixir(750),30,42,2),
		lv3(6000,800,new Elixir(1500),60,60,2),
		lv4(12000,1000,new Elixir(3000),120,84,3),
		lv5(25000,1200,new Elixir(6000),180,103,3),
		lv6(50000,1500,new Elixir(12000),240,120,3),
		lv7(100000,1650,new Elixir(25000),360,146,4),
		lv8(250000,1740,new Elixir(50000),480,169,4),
		lv9(500000,1820,new Elixir(100000),720,207,5),
		lv10(1000000,1920,new Elixir(250000),1440,293,6),
		lv11(2000000,2016,new Elixir(500000),2880,415,7);
		int capacity;
		int health;
		Resource cost;
		int exp;
		int time;
		int minthlv;
		specsGoldStorage(int capacity,int health,Resource cost,int time,int exp,int minthlv){
			this.capacity = capacity;
			this.health = health;
			this.cost = cost;
			this.time = time;
			this.minthlv = minthlv;
			this.exp = exp;
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
			return minthlv;
		}

		@Override
		public int getCapacity() {
			return capacity;
		}
	}

	@Override
	public StorageBuildingSpecs[] getBuildingSpecs() {
		return specsGoldStorage.values();
	}


	@Override
	public void setLevel(int level) {
		level2 = level;
		if(isRealBuilding()==true){
			plugin.getdataconffile(owner2).set("goldstorage."+BuildingID2+".level", level2);
			plugin.saveDataconf(owner2);
		}
		
	}



	@Override
	public void setLocation(Location location) {
		loc2 = location;
		if(isRealBuilding()==true){
			plugin.getdataconffile(owner2).set("goldstorage."+BuildingID2+".location.x", loc2.getBlockX());
			plugin.getdataconffile(owner2).set("goldstorage."+BuildingID2+".location.y", loc2.getBlockY());
			plugin.getdataconffile(owner2).set("goldstorage."+BuildingID2+".location.z", loc2.getBlockZ());
			plugin.saveDataconf(owner2);
		}
		
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
	public BuildingPanel getBuildingPanel() {
		GoldStoragePanel gbp = new GoldStoragePanel(this);
		return gbp;
	}

	@Override
	public boolean isRealBuilding() {
		return isRealBuilding;
	}

}
