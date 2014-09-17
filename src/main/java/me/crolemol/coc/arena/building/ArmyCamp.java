package me.crolemol.coc.arena.building;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.ArmyCampPanel;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;

public class ArmyCamp implements Building{
	static Coc plugin = Coc.getPlugin();
	private int level2;
	private Location loc2;
	private OfflinePlayer owner2;
	private int BuildingID2;
	private boolean isRealBuilding;
	
	public ArmyCamp(OfflinePlayer owner,int level){
		level2 = level;
		owner2 = owner;
		isRealBuilding = false;
	}
	private ArmyCamp(OfflinePlayer owner,Location loc,int level,int BuildingID,boolean isreal){
		level2 = level;
		loc2 = loc;
		owner2 = owner;
		BuildingID2 = BuildingID;
		isRealBuilding = isreal;
	}
	
	public static ArmyCamp getArmyCamp(int BuildingID,
			OfflinePlayer owner) {
		if(BuildingID == 0){
			throw new IllegalArgumentException("BuildingID cannot be 0");
		}
		if(owner == null){
			throw new IllegalArgumentException("owner cannot be null");
		}
		FileConfiguration dataconf = plugin.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("armycamp."+BuildingID+".location.x");
		int y = dataconf.getInt("armycamp."+BuildingID+".location.y");
		int z = dataconf.getInt("armycamp."+BuildingID+".location.z");
		
		return new ArmyCamp(owner,new Location(world,x,y,z),dataconf.getInt("armycamp."+BuildingID+".level"),BuildingID,true);
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
		return "armycamp";
	}
		public enum ArmyCampSpec implements BuildingSpecs{
			lv1(new Elixir(250),17,5,400,1,20),
			lv2(new Elixir(2500),60,60,500,2,30),
			lv3(new Elixir(10000),103,180,600,3,35),
			lv4(new Elixir(10000),169,480,700,4,40),
			lv5(new Elixir(250000),293,1440,800,5,45),
			lv6(new Elixir(750000),509,4320,1000,6,50),
			lv7(new Elixir(2250000),657,7200,1200,9,55),
			lv8(new Elixir(6750000),929,14400,1400,10,60);
			
			private Resource goldprice2;
			private int gainexp2;
			private int time2;
			private int health2;
			private int troopcapacity;
			private int minthlevel;
			ArmyCampSpec(Resource goldprice, int gainexp,int time,int health, int minthlevel,int troopcapacity){
				goldprice2 = goldprice;
				gainexp2 = gainexp;
				time2 = time;
				health2 = health;
				this.minthlevel = minthlevel;
				this.troopcapacity = troopcapacity;
				
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
				return minthlevel;
			}
			public int getTroopcapacity(){
				return troopcapacity;
			}
		}
		@Override
		public ArmyCampSpec[] getBuildingSpecs() {
			
			return ArmyCampSpec.values();
		}

		@Override
		public void setLevel(int level) {
			level2 = level;
			if(isRealBuilding == true){
				plugin.getdataconffile(owner2).set(getBuildingName()+"."+BuildingID2+".level", level2);
				plugin.saveDataconf(owner2);
			}
			
		}


		@Override
		public void setLocation(Location location) {
			loc2 = location;
			if(isRealBuilding == true){
				plugin.getdataconffile(owner2).set(getBuildingName()+"."+BuildingID2+".location.x", loc2.getBlockX());
				plugin.getdataconffile(owner2).set(getBuildingName()+"."+BuildingID2+".location.y", loc2.getBlockY());
				plugin.getdataconffile(owner2).set(getBuildingName()+"."+BuildingID2+".location.z", loc2.getBlockZ());
				plugin.saveDataconf(owner2);
			}
			
		}
		@Override
		public BuildingPanel getBuildingPanel() {
			ArmyCampPanel acp = new ArmyCampPanel(this);
			return acp;
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
		public boolean isRealBuilding() {
			return isRealBuilding;
		}


}
