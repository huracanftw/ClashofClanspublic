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
import me.crolemol.coc.arena.panels.buildingpanels.GoldStoragePanel;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;

public class GoldStorage extends Storage{
	static Coc plugin = Coc.getPlugin();
	public GoldStorage(int level){
		super(level);
	}
	
	private GoldStorage(OfflinePlayer owner,Location loc,int level,int BuildingID, boolean isreal){
		super(owner,loc,level,BuildingID,isreal);
	}

	public static GoldStorage getGoldStorage(int BuildingID, OfflinePlayer owner) {
		if (BuildingID == 0) {
			throw new IllegalArgumentException("BuildingID cannot be 0");
		}
		if (owner == null) {
			throw new IllegalArgumentException("owner cannot be null");
		}
		World world = plugin.getServer().getWorld("coc");
		ResultSet result = plugin
				.getDataBase()
				.query("SELECT * FROM Buildings WHERE owner = '"
						+ owner.getUniqueId()
						+ "' AND BuildingID = "
						+ BuildingID + " AND BuildingName = 'goldstorage'");
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
		return new GoldStorage(owner, new Location(world, x, y, z), level,
				BuildingID, true);
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
	public BuildingPanel getBuildingPanel() {
		GoldStoragePanel gbp = new GoldStoragePanel(this);
		return gbp;
	}
}
