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
import me.crolemol.coc.arena.panels.buildingpanels.BarbarianKingAltarPanel;
import me.crolemol.coc.economy.DarkElixir;
import me.crolemol.coc.economy.Resource;

public class BarbarianKingAltar extends Building{

	static Coc plugin = Coc.getPlugin();
	
	public BarbarianKingAltar(int level){
			super(level);
	}
	private BarbarianKingAltar(OfflinePlayer owner,Location loc,int level,int BuildingID,boolean isreal){
	super(owner,loc,level,BuildingID,isreal);
	}
	
	public static BarbarianKingAltar getBarbarianKingAltar(int BuildingID,
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
						+ " AND BuildingName = 'barbariankingaltar'");
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
		return new BarbarianKingAltar(owner,new Location(world,x,y,z),level,BuildingID,true);	}

	@Override
	public String getBuildingName() {
		return "barbariankingaltar";
	}
		public enum BarbarianKingAltarSpecs implements BuildingSpecs{
			lv1(new DarkElixir(10000),0,0,250,7);
			
			private Resource darkelixirprice;
			private int gainexp2;
			private int time2;
			private int health2;
			private int minthlevel;
			BarbarianKingAltarSpecs(Resource darkelixirprice, int gainexp,int time,int health, int minthlevel){
				this.darkelixirprice = darkelixirprice;
				gainexp2 = gainexp;
				time2 = time;
				health2 = health;
				this.minthlevel = minthlevel;
				
			}
			public int getUpgradeTime(){
				return time2;
			}
			public int getHealth(){
				return health2;
			}
			@Override
			public Resource getUpgradePrice() {
				return darkelixirprice;
			}
			@Override
			public int getGainExpOnUpgrade() {
				return gainexp2;
			}
			@Override
			public int getMinTownhallLevel() {
				return minthlevel;
			}
		}
		@Override
		public BarbarianKingAltarSpecs[] getBuildingSpecs() {
			
			return BarbarianKingAltarSpecs.values();
		}

		@Override
		public BuildingPanel getBuildingPanel() {
			BarbarianKingAltarPanel acp = new BarbarianKingAltarPanel(this);
			return acp;
		}


}
