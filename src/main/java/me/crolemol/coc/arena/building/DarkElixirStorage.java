package me.crolemol.coc.arena.building;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.Storage;
import me.crolemol.coc.arena.building.interfaces.StorageBuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.DarkElixirStoragePanel;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;

public class DarkElixirStorage extends Storage{
	static Coc plugin = Coc.getPlugin();
		public DarkElixirStorage(int level){
			super(level);
		}
		
		private DarkElixirStorage(OfflinePlayer owner,Location loc,int level,int BuildingID, boolean isreal){
			super(owner,loc,level,BuildingID,isreal);
		}

	

	public static DarkElixirStorage getDarkElixirStorage(int BuildingID,
			OfflinePlayer owner) {
		if(BuildingID == 0){
			throw new IllegalArgumentException("BuildingID cannot be 0");
		}
		if(owner == null){
			throw new IllegalArgumentException("owner cannot be null");
		}
		World world = plugin.getServer().getWorld("coc");
		ResultSet result = plugin
				.getDataBase()
				.query("SELECT * FROM Buildings WHERE owner = '"
						+ owner.getUniqueId()
						+ "' AND BuildingID = "+ BuildingID
						+ " AND BuildingName = 'darkelixirstorage'");
		int x = 0;
		int y = 0;
		int z = 0;
		int level = 0;
		try {
			x = result.getInt("Location_x");
			y = result.getInt("Location_y");
			z = result.getInt("Location_z");
			level = result.getInt("Level");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(y == 0){
			return null;
		}
		return new DarkElixirStorage(owner,new Location(world,x,y,z),level,BuildingID,true);	}
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
	public BuildingPanel getBuildingPanel() {
		DarkElixirStoragePanel esp = new DarkElixirStoragePanel(this);
		return esp;
	}
	
}
