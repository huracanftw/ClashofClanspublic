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
import me.crolemol.coc.arena.panels.buildingpanels.MortarPanel;
import me.crolemol.coc.economy.Gold;
import me.crolemol.coc.economy.Resource;

public class Mortar extends DefensiveBuilding{

	protected Mortar(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}
	public Mortar(int Level){
		super(Level);
	}

	public static Mortar getMortar(int BuildingID, OfflinePlayer owner) {
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
						+ " AND BuildingName = 'mortar'");
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
		return new Mortar(owner, new Location(world, x, y, z), level,
				BuildingID, true);
	}
	
	@Override
	public String getBuildingName() {
		return "mortar";
	}

	@Override
	public BuildingSpecs[] getBuildingSpecs() {
		return specsMortar.values();
	}

	@Override
	public BuildingPanel getBuildingPanel() {
		return new MortarPanel(this);
	}
	
	public enum specsMortar implements DefensiveBuildingSpecs{
		lv1(new Gold(8000),480,169,400,3),
		lv2(new Gold(32000),720,207,450,4),
		lv3(new Gold(120000),1440,293,500,5),
		lv4(new Gold(400000),2880,415,550,6),
		lv5(new Gold(800000),5760,587,590,7),
		lv6(new Gold(1600000),7200,657,610,8),
		lv7(new Gold(3200000),10080,777,640,9),
		lv8(new Gold(6400000),14400,929,670,10);
		Resource cost;
		int time;
		int exp;
		int health;
		int level;
		specsMortar(Resource cost2,int time2,int exp2,int health2,int level2){
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
