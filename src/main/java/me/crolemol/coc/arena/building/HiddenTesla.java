package me.crolemol.coc.arena.building;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.DefensiveBuilding;
import me.crolemol.coc.arena.building.interfaces.DefensiveBuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.HiddenTeslaPanel;
import me.crolemol.coc.economy.Gold;
import me.crolemol.coc.economy.Resource;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

public class HiddenTesla extends DefensiveBuilding{

	protected HiddenTesla(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}
	public HiddenTesla(int Level){
		super(Level);
	}
	
	public static HiddenTesla getHiddenTesla(int BuildingID, OfflinePlayer owner) {
		if (BuildingID == 0) {
			throw new IllegalArgumentException("BuildingID cannot be 0");
		}
		if (owner == null) {
			throw new IllegalArgumentException("owner cannot be null");
		}
		World world = Coc.getPlugin().getServer().getWorld("coc");
		ResultSet result = Coc.getPlugin().getDataBase().query(
				"SELECT * FROM Buildings WHERE owner = '" + owner.getUniqueId()
						+ "' AND BuildingID = " + BuildingID
						+ " AND BuildingName = 'hiddentesla'");
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
		if (y == 0) {
			return null;
		}
		return new HiddenTesla(owner, new Location(world, x, y, z), level,
				BuildingID, true);
	}

	@Override
	public String getBuildingName() {
		return "hiddentesla";
	}

	@Override
	public BuildingSpecs[] getBuildingSpecs() {
		return specsHiddentesla.values();
	}

	@Override
	public BuildingPanel getBuildingPanel() {
		return new HiddenTeslaPanel(this);
	}
	
	public enum specsHiddentesla implements DefensiveBuildingSpecs{
		lv1(new Gold(1000000),1440,293,600,7),
		lv2(new Gold(1250000),4320,509,630,7),
		lv3(new Gold(1500000),7200,657,660,7),
		lv4(new Gold(2000000),8640,720,690,8),
		lv5(new Gold(2500000),11520,777,730,8),
		lv6(new Gold(3000000),14400,929,770,8),
		lv7(new Gold(3500000),17280,1018,810,9),
		lv8(new Gold(5000000),20160,1099,850,10);
		Resource cost;
		int time;
		int exp;
		int health;
		int level;
		specsHiddentesla(Resource cost2,int time2,int exp2,int health2,int level2){
			cost = cost2;
			time = time2;
			exp = exp2;
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
		public int getRange() {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public double getAttackSpeed() {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public double getDamagePerShot() {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	@Override
	public double getDamagePerShot() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void Shoot(Location loc) {
		// hier moet niks
		
	}
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getAttackSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}
}
