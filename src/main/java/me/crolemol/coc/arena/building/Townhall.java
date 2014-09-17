package me.crolemol.coc.arena.building;

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
import org.bukkit.configuration.file.FileConfiguration;

public class Townhall implements Building{
	static Coc plugin = Coc.getPlugin();
	private int level2;
	private Location loc2;
	private OfflinePlayer owner2;
	private final int BuildingID2 = 1;
	private boolean isRealBuilding;
	public Townhall(OfflinePlayer owner,int level){
		level2 = level;
		owner2 = owner;
		isRealBuilding=false;
	}
	
	private Townhall(OfflinePlayer owner,Location loc,int level, boolean isreal){
		level2 = level;
		loc2 = loc;
		owner2 = owner;
		isRealBuilding = isreal;
	}
	
	public static Townhall getTownhall(
			OfflinePlayer owner) {
		FileConfiguration dataconf = plugin.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("townhall.1.location.x");
		int y = dataconf.getInt("townhall.1.location.x");
		int z = dataconf.getInt("townhall.1.location.x");
		
		return new Townhall(owner,new Location(world,x,y,z),dataconf.getInt("townhall.1.level"),true);
	}
	

	@Override
	public int getBuildingID() {
		return BuildingID2;
	}

	@Override
	public int getLevel() {
		return level2;
	}

	@Override
	public Location getLocation() {
		return loc2;
	}

	@Override
	public OfflinePlayer getOwner() {
		return owner2;
	}

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
		public void setLevel(int level) {
			level2 = level;
			if(BuildingID2 != 0){
				plugin.getdataconffile(owner2).set(getBuildingName()+"."+BuildingID2+".level", level2);
				plugin.saveDataconf(owner2);
			}
			
		}


		@Override
		public void setLocation(Location location) {
			loc2 = location;
			if(BuildingID2 != 0){
				plugin.getdataconffile(owner2).set(getBuildingName()+"."+BuildingID2+".location.x", loc2.getBlockX());
				plugin.getdataconffile(owner2).set(getBuildingName()+"."+BuildingID2+".location.y", loc2.getBlockY());
				plugin.getdataconffile(owner2).set(getBuildingName()+"."+BuildingID2+".location.z", loc2.getBlockZ());
				plugin.saveDataconf(owner2);
			}
			
		}
		@Override
		public boolean isUpgrading() {
			if(Coc.getPlugin().getdataconffile(owner2).contains(getBuildingName()+"."+getBuildingID()+".upgrade")){
			return true;	
			}else{
				return false;
			}

		}
		
		@Override
		public BuildingPanel getBuildingPanel() {
			TownhallPanel gbp = new TownhallPanel(this);
			return gbp;
		}

		@Override
		public boolean isRealBuilding() {
			return isRealBuilding;
		}


}
