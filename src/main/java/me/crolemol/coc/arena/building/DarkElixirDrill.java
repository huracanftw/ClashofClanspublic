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
import me.crolemol.coc.arena.panels.buildingpanels.DarkElixirDrillPanel;
import me.crolemol.coc.economy.DarkElixir;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;

public class DarkElixirDrill extends ResourceBuilding{
	static Coc plugin = Coc.getPlugin();
	
		public DarkElixirDrill(int level){
			super(level);
		}
		private DarkElixirDrill(OfflinePlayer owner,Location loc,int level,int BuildingID,boolean isreal){
			super(owner,loc,level,BuildingID,isreal);
		}

		


	public static DarkElixirDrill getDarkElixirDrill(int BuildingID,
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
						+ " AND BuildingName = 'darkelixirdrill'");
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
		return new DarkElixirDrill(owner,new Location(world,x,y,z),level,BuildingID,true);	}


	@Override
	public String getBuildingName() {
		return "darkelixirdrill";
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
	public Resource getProductionType() {
		return new DarkElixir(0);
	}

}
