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
import me.crolemol.coc.arena.panels.buildingpanels.BuildersHutPanel;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;

public class Barracks extends Building{
	static Coc plugin = Coc.getPlugin();
	
	public Barracks(int level){
		super(level);
	}
	
	private Barracks(OfflinePlayer owner,Location loc,int level,int BuildingID,boolean isreal){
			super(owner,loc,level,BuildingID,isreal);
	}
	
	public static Barracks getBarracks(int BuildingID,
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
						+ " AND BuildingName = 'barracks'");
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
		return new Barracks(owner,new Location(world,x,y,z),level,BuildingID,true);		}
	

	@Override
	public String getBuildingName() {
		return "barracks";
	}
	public enum BarracksSpecs implements BuildingSpecs{
		lv1(new Elixir(200),1,7,250,1),
		lv2(new Elixir(1000),15,30,270,1),
		lv3(new Elixir(2500),120,84,280,1),
		lv4(new Elixir(5000),240,120,290,2),
		lv5(new Elixir(10000),600,189,310,3),
		lv6(new Elixir(80000),960,240,320,4),
		lv7(new Elixir(80000),1440,293,340,5),
		lv8(new Elixir(80000),2880,415,350,6),
		lv9(new Elixir(80000),5760,587,390,7),
		lv10(new Elixir(80000),8640,720,420,8);
		Resource cost;
		int time;
		int exp;
		int health;
		int level;
		private BarracksSpecs(Resource upgradecost,int time,int exp,int health,int thlv){
			this.cost = upgradecost;
			this.exp = exp;
			this.time = time;
			this.health = health;
			this.level = thlv;
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
	@Override
	public BuildingSpecs[] getBuildingSpecs() {
		return BarracksSpecs.values();
	}



	@Override
	public BuildingPanel getBuildingPanel() {
		BuildersHutPanel bhp = new BuildersHutPanel(BuildersHut.getBuildersHut(1, getOwner()));
		return bhp;
	}
	
}
