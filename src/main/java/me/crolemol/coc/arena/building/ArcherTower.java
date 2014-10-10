package me.crolemol.coc.arena.building;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.DefensiveBuilding;
import me.crolemol.coc.arena.building.interfaces.DefensiveBuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.ArcherTowerPanel;
import me.crolemol.coc.economy.Gold;
import me.crolemol.coc.economy.Resource;

public class ArcherTower extends DefensiveBuilding{

	protected ArcherTower(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}
	public ArcherTower(int Level){
		super(Level);
	}
	
	public static ArcherTower getArcherTower(int BuildingID, OfflinePlayer owner) {
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
						+ " AND BuildingName = 'archertower'");
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
		return new ArcherTower(owner, new Location(world, x, y, z), level,
				BuildingID, true);
	}
	
	// TODO Add archertower lv13

	@Override
	public String getBuildingName() {
		return "archertower";
	}

	@Override
	public BuildingSpecs[] getBuildingSpecs() {
		return specsArchertower.values();
	}

	@Override
	public BuildingPanel getBuildingPanel() {
		return new ArcherTowerPanel(this);
	}
	
	public enum specsArchertower implements DefensiveBuildingSpecs{
		lv1(new Gold(1000),15,30,400,2),
		lv2(new Gold(2000),30,42,450,2),
		lv3(new Gold(5000),45,51,500,3),
		lv4(new Gold(20000),240,120,550,4),
		lv5(new Gold(80000),720,207,590,5),
		lv6(new Gold(180000),1440,293,610,5),
		lv7(new Gold(360000),2880,415,630,6),
		lv8(new Gold(720000),4320,509,660,7),
		lv9(new Gold(1500000),5760,589,690,8),
		lv10(new Gold(2500000),7200,657,720,8),
		lv11(new Gold(4500000),8640,720,750,9),
		lv12(new Gold(6500000),10080,777,790,10),
		lv13(new Gold(7500000),11520,831,840,10);
		Resource cost;
		int time;
		int exp;
		int health;
		int level;
		specsArchertower(Resource cost2,int time2,int exp2,int health2,int level2){
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