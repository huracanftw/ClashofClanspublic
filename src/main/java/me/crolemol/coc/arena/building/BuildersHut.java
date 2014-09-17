package me.crolemol.coc.arena.building;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.panels.buildingpanels.BuildersHutPanel;
import me.crolemol.coc.economy.Gem;
import me.crolemol.coc.economy.Resource;

public class BuildersHut implements Building{
	static Coc plugin = Coc.getPlugin();
	private int level2;
	private Location loc2;
	private OfflinePlayer owner2;
	private int BuildingID2;
	private boolean isRealBuilding;
	
	public BuildersHut(OfflinePlayer owner,int level){
		level2 = level;
		owner2 = owner;
		isRealBuilding = false;
	}
	
	private BuildersHut(OfflinePlayer owner,Location loc,int level,int BuildingID,boolean isreal){
		level2 = level;
		loc2 = loc;
		owner2 = owner;
		BuildingID2 = BuildingID;
		isRealBuilding = isreal;
	}
	
	public static BuildersHut getBuildersHut(int BuildingID,
			OfflinePlayer owner) {
		if(BuildingID == 0){
			throw new IllegalArgumentException("BuildingID cannot be 0");
		}
		if(owner == null){
			throw new IllegalArgumentException("owner cannot be null");
		}
		FileConfiguration dataconf = plugin.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("buildershut."+BuildingID+".location.x");
		int y = dataconf.getInt("buildershut."+BuildingID+".location.y");
		int z = dataconf.getInt("buildershut."+BuildingID+".location.z");
		
		return new BuildersHut(owner,new Location(world,x,y,z),dataconf.getInt("armycamp."+BuildingID+".level"),BuildingID,true);
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

	@Override
	public void setLevel(int level) {
		level2 = level;
		if(isRealBuilding() == true){
			FileConfiguration dataconf = plugin.getdataconffile(owner2);
			dataconf.set("buildershut."+BuildingID2+".level", level2);
			plugin.saveDataconf(owner2);
		}
		
	}

	@Override
	public void setLocation(Location location) {
		loc2 = location;
		if(isRealBuilding() == true){
			FileConfiguration dataconf = plugin.getdataconffile(owner2);
			dataconf.set("buildershut."+BuildingID2+".location.x", loc2.getBlockX());
			dataconf.set("buildershut."+BuildingID2+".location.y", loc2.getBlockY());
			dataconf.set("buildershut."+BuildingID2+".location.z", loc2.getBlockZ());
			plugin.saveDataconf(owner2);
		}
		
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
