package me.crolemol.coc.arena.building;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.WizardTowerPanel;
import me.crolemol.coc.economy.Gold;
import me.crolemol.coc.economy.Resource;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

public class WizardTower extends Building{

	protected WizardTower(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}
	public WizardTower(int Level){
		super(Level);
	}
	
	public static WizardTower getWizardTower(int BuildingID, OfflinePlayer owner) {
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
						+ " AND BuildingName = 'wizardtower'");
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
		return new WizardTower(owner, new Location(world, x, y, z), level,
				BuildingID, true);
	}

	@Override
	public String getBuildingName() {
		return "wizardtower";
	}

	@Override
	public BuildingSpecs[] getBuildingSpecs() {
		return specsWizardTower.values();
	}

	@Override
	public BuildingPanel getBuildingPanel() {
		return new WizardTowerPanel(this);
	}
	
	public enum specsWizardTower implements BuildingSpecs{
		lv1(new Gold(180000),720,207,620,5),
		lv2(new Gold(360000),1440,293,660,5),
		lv3(new Gold(720000),2880,415,690,6),
		lv4(new Gold(1280000),4320,509,720,7),
		lv5(new Gold(1960000),5760,587,760,8),
		lv6(new Gold(2680000),7200,720,800,8),
		lv7(new Gold(5360000),10080,777,840,9),
		lv8(new Gold(6480000),14400,929,880,10);
		Resource cost;
		int time;
		int exp;
		int health;
		int level;
		specsWizardTower(Resource cost2,int time2,int exp2,int health2,int level2){
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
	}
}
