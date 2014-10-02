package me.crolemol.coc.arena.building;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.BuildingType;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.ArmyCampPanel;
import me.crolemol.coc.army.troops.SoldierType;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;

public class ArmyCamp extends Building {
	static Coc plugin = Coc.getPlugin();

	public ArmyCamp(int level) {
		super(level);
	}

	private ArmyCamp(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}

	public static ArmyCamp getArmyCamp(int BuildingID, OfflinePlayer owner) {
		if (BuildingID == 0) {
			throw new IllegalArgumentException("BuildingID cannot be 0");
		}
		if (owner == null) {
			throw new IllegalArgumentException("owner cannot be null");
		}
		World world = plugin.getServer().getWorld("coc");
		ResultSet result = plugin.getDataBase().query(
				"SELECT * FROM Buildings WHERE owner = '" + owner.getUniqueId()
						+ "' AND BuildingID = " + BuildingID
						+ " AND BuildingName = 'armycamp'");
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
		return new ArmyCamp(owner, new Location(world, x, y, z), level,
				BuildingID, true);
	}

	@Override
	public String getBuildingName() {
		return "armycamp";
	}

	public enum ArmyCampSpec implements BuildingSpecs {
		lv1(new Elixir(250), 17, 5, 400, 1, 20), lv2(new Elixir(2500), 60, 60,
				500, 2, 30), lv3(new Elixir(10000), 103, 180, 600, 3, 35), lv4(
				new Elixir(10000), 169, 480, 700, 4, 40), lv5(
				new Elixir(250000), 293, 1440, 800, 5, 45), lv6(new Elixir(
				750000), 509, 4320, 1000, 6, 50), lv7(new Elixir(2250000), 657,
				7200, 1200, 9, 55), lv8(new Elixir(6750000), 929, 14400, 1400,
				10, 60);

		private Resource goldprice2;
		private int gainexp2;
		private int time2;
		private int health2;
		private int troopcapacity;
		private int minthlevel;

		ArmyCampSpec(Resource goldprice, int gainexp, int time, int health,
				int minthlevel, int troopcapacity) {
			goldprice2 = goldprice;
			gainexp2 = gainexp;
			time2 = time;
			health2 = health;
			this.minthlevel = minthlevel;
			this.troopcapacity = troopcapacity;

		}

		public int getUpgradeTime() {
			return time2;
		}

		public int getHealth() {
			return health2;
		}

		@Override
		public Resource getUpgradePrice() {
			return goldprice2;
		}

		@Override
		public int getGainExpOnUpgrade() {
			return gainexp2;
		}

		@Override
		public int getMinTownhallLevel() {
			return minthlevel;
		}

		public int getTroopcapacity() {
			return troopcapacity;
		}
	}

	@Override
	public ArmyCampSpec[] getBuildingSpecs() {

		return ArmyCampSpec.values();
	}

	@Override
	public BuildingPanel getBuildingPanel() {
		ArmyCampPanel acp = new ArmyCampPanel(this);
		return acp;
	}

	public int getFreeHousingSpace() {
		ResultSet result = plugin.getDataBase().query(
				"SELECT * FROM Troops WHERE owner = '"
						+ super.getOwner().getUniqueId()+"'");
		int finalhousingspace = 0;
		try {
			Bukkit.getServer().getLogger().log(Level.INFO, SoldierType.getSoldierType(result.getString("SoldierType"))+"");
			int housingspacecount = SoldierType.getSoldierType(
					result.getString("SoldierType")).getHousingSpace();
			while (result.next()) {
				int housingspace = SoldierType.getSoldierType(
						result.getString("SoldierType")).getHousingSpace();
				housingspacecount += housingspace;
			}
			finalhousingspace = getTotalHousingSpace() - housingspacecount;
			return finalhousingspace;
		} catch (SQLException e) {
			e.printStackTrace();
			return finalhousingspace;
		}
	}
	public int getTotalHousingSpace(){
		Base base = Base.getBase(super.getOwner());
		int space = 0;
		int armycamps = base.getAmountofBuilding(BuildingType.ArmyCamp.getName());
		if(armycamps == 0){
			return space;
		}
		for(int counter = 1;counter<=armycamps;counter++){
			ArmyCamp armycamp = ArmyCamp.getArmyCamp(counter, super.getOwner());
			space += ArmyCampSpec.values()[armycamp.getLevel()-1].getTroopcapacity();
		}
		return space;
	}
}
