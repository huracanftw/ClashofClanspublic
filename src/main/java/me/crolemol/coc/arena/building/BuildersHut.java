package me.crolemol.coc.arena.building;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.BuildersHutPanel;
import me.crolemol.coc.economy.Gem;
import me.crolemol.coc.economy.Resource;

public class BuildersHut extends Building{
	static Coc plugin = Coc.getPlugin();

	
	public BuildersHut(int level){
		super(level);
	}
	
	private BuildersHut(OfflinePlayer owner,Location loc,int level,int BuildingID,boolean isreal){
		super(owner,loc,level,BuildingID,isreal);
	}
	
	public static BuildersHut getBuildersHut(int BuildingID,
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
						+ " AND BuildingName = 'buildershut'");
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
		return new BuildersHut(owner,new Location(world,x,y,z),level,BuildingID,true);	}
	


	@Override
	public String getBuildingName() {
		return "buildershut";
	}
	private enum Specs implements BuildingSpecs{
		lv1(new Gem(50));
		Resource cost;
		Specs(Resource cost2){
			cost = cost2;
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
			return 250;
		}

		@Override
		public int getMinTownhallLevel() {
			return 1;
		}
	}

	@Override
	public BuildingSpecs[] getBuildingSpecs() {
		return Specs.values();
	}


	public static Gem getNextGemCost(OfflinePlayer player){
		int counter = 0;
		Base base = Base.getBase(player);
		switch(base.getAmountofBuilding("buildershut") +1){
		case 1:counter = 0;
		case 2:counter = 250;
		case 3:counter = 500;
		case 4:counter = 1000;
		case 5:counter = 2000;
			
		}
		return new Gem(counter);
	}

	@Override
	public BuildingPanel getBuildingPanel() {
		BuildersHutPanel bhp = new BuildersHutPanel(this);
		return bhp;
	}
	
}
