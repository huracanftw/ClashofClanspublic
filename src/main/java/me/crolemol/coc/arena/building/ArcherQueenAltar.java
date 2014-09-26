package me.crolemol.coc.arena.building;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.ArcherQueenAltarPanel;
import me.crolemol.coc.economy.DarkElixir;
import me.crolemol.coc.economy.Resource;

public class ArcherQueenAltar extends Building{
	public ArcherQueenAltar(int Level) {
		super(Level);
	}
	protected ArcherQueenAltar(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}
	public static ArcherQueenAltar getArcherQueenAltar(int BuildingID,
			OfflinePlayer owner) {
		if(BuildingID == 0){
			throw new IllegalArgumentException("BuildingID cannot be 0");
		}
		if(owner == null){
			throw new IllegalArgumentException("owner cannot be null");
		}
		World world = Coc.getPlugin().getServer().getWorld("coc");
		ResultSet result = Coc.getPlugin()
				.getDataBase().query("SELECT * FROM Buildings WHERE owner = '"
						+ owner.getUniqueId()
						+ "' AND BuildingID = "+ BuildingID
						+ " AND BuildingName = 'archerqueenaltar'");
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
		return new ArcherQueenAltar(owner,new Location(world,x,y,z),level,BuildingID,true);	}
	
	public enum ArcherQueenAltarSpecs implements BuildingSpecs{
		lv1(new DarkElixir(40000),250,9);
		Resource cost;
		int health;
		int thlv;
		ArcherQueenAltarSpecs(Resource cost,int health,int thlv){
			this.cost = cost;
			this.health = health;
			this.thlv = thlv;
		}

		@Override
		public Resource getUpgradePrice() {
			return cost;
		}

		@Override
		public int getGainExpOnUpgrade() {
			return 0;
		}

		@Override
		public int getUpgradeTime() {
			return 0;
		}

		@Override
		public int getHealth() {
			return health;
		}

		@Override
		public int getMinTownhallLevel() {
			return thlv;
		}
	}
	@Override
	public String getBuildingName() {
		return "archerqueenaltar";
	}

	@Override
	public BuildingSpecs[] getBuildingSpecs() {
		return ArcherQueenAltarSpecs.values();
	}

	@Override
	public BuildingPanel getBuildingPanel() {
		return new ArcherQueenAltarPanel(this);
	}

}
