package me.crolemol.coc.arena.building;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.ResourceBuilding;
import me.crolemol.coc.arena.building.interfaces.ResourceBuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.ElixirCollectorPanel;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Gold;
import me.crolemol.coc.economy.Resource;

public class ElixirCollector extends ResourceBuilding{
	static Coc plugin = Coc.getPlugin();
	public ElixirCollector(int level){
		super(level);
	}
	
	private ElixirCollector(OfflinePlayer owner,Location loc,int level,int BuildingID, boolean isreal){
		super(owner,loc,level,BuildingID,isreal);
	}
		


	public static ElixirCollector getElixirCollector(int BuildingID,
			OfflinePlayer owner) {
		if(BuildingID == 0){
			throw new IllegalArgumentException("BuildingID cannot be 0");
		}
		if(owner == null){
			throw new IllegalArgumentException("owner cannot be null");
		}
		World world = plugin.getServer().getWorld("coc");
		ResultSet result = plugin
				.getDataBase().query("SELECT * FROM Buildings WHERE owner = '"
						+ owner.getUniqueId()
						+ "' AND BuildingID = "+ BuildingID
						+ " AND BuildingName = 'elixircollector'");
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
		return new ElixirCollector(owner,new Location(world,x,y,z),level,BuildingID,true);	}

	@Override
	public String getBuildingName() {
		return "elixircollector";
	}

		
	@Override
	public int getProduction() {
		if(getLevel() == 0){return 0;}
		specsElixirCollector[] specs2 = (specsElixirCollector[]) getBuildingSpecs();
		return specs2[getLevel()-1].getProduction();
		}

	@Override
	public int getCapacity() {
		if(getLevel() == 0){return 0;}
		specsElixirCollector[] specs2 = specsElixirCollector.values();
		return specs2[getLevel()-1].getCapacity();
	}

	public enum specsElixirCollector implements ResourceBuildingSpecs{
		lv1(new Gold(150),1,7,500,200,400,1),
		lv2(new Gold(300),5,17,1000,400,450,1),
		lv3(new Gold(700),15,30,1500,600,500,2),
		lv4(new Gold(1400),60,60,2500,800,550,2),
		lv5(new Gold(3500),120,84,10000,1000,590,3),
		lv6(new Gold(7000),360,146,20000,1300,610,3),
		lv7(new Gold(14000),720,207,30000,1600,630,4),
		lv8(new Gold(28000),1440,293,50000,1900,660,4),
		lv9(new Gold(56000),2880,415,75000,2200,680,5),
		lv10(new Gold(84000),4320,509,100000,2500,710,5),
		lv11(new Gold(168000),5760,587,150000,3000,750,7);
		Resource cost;
		int time;
		int exp;
		int capacity;
		int production;
		int health;
		int level;
		specsElixirCollector(Resource cost2,int time2,int exp2, int capacity2,int production2,int health2,int level2){
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
		return specsElixirCollector.values();
	}




	@Override
	public BuildingPanel getBuildingPanel() {
		return new ElixirCollectorPanel(this);
	}

	@Override
	public Resource getProductionType() {
		return new Elixir(0);
	}

}
