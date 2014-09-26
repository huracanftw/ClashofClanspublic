package me.crolemol.coc.arena.building;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.TownhallPanel;
import me.crolemol.coc.economy.Gold;
import me.crolemol.coc.economy.Resource;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

public class Townhall extends Building{
	static Coc plugin = Coc.getPlugin();
	public Townhall(int level){
		super(level);
	}
	
	private Townhall(OfflinePlayer owner,Location loc,int level, boolean isreal){
			super(owner,loc,level,1,isreal);
	}
	
	public static Townhall getTownhall(
			OfflinePlayer owner) {
		if(owner == null){
			throw new IllegalArgumentException("owner cannot be null");
		}
		World world = plugin.getServer().getWorld("coc");
		ResultSet result = plugin
				.getDataBase()
				.query("SELECT * FROM Buildings WHERE owner = '"
						+ owner.getUniqueId()
						+ "' AND BuildingID = "+ 1
						+ " AND BuildingName = 'townhall'");
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
			return null;
		}
		return new Townhall(owner,new Location(world,x,y,z),level,true);	}
	

	@Override
	public String getBuildingName() {
		return "townhall";
	}
		public enum spec implements BuildingSpecs{
			lv1(new Gold(0),0,0,1500),
			lv2(new Gold(1000),17,5,1600),
			lv3(new Gold(4000),103,180,1850),
			lv4(new Gold(25000),293,1440,2100),
			lv5(new Gold(150000),415,2880,2400),
			lv6(new Gold(750000),587,5760,2800),
			lv7(new Gold(1200000),720,8640,3200),
			lv8(new Gold(2000000),831,11520,3700),
			lv9(new Gold(3000000),929,14400,4200),
			lv10(new Gold(4000000),1099,20160,5000);
			
			private Resource goldprice2;
			private int gainexp2 = 0;
			private int time2 = 0;
			private int health2 = 0;
			spec(Resource goldprice, int gainexp,int time,int health){
				goldprice2 = goldprice;
				gainexp2 = gainexp;
				time2 = time;
				health2 = health;
				
			}
			public int getUpgradeTime(){
				return time2;
			}
			public int getHealth(){
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
				return 1;
			}
		}
		@Override
		public BuildingSpecs[] getBuildingSpecs() {
			
			return spec.values();
		}
			

		
		@Override
		public BuildingPanel getBuildingPanel() {
			TownhallPanel gbp = new TownhallPanel(this);
			return gbp;
		}


}
