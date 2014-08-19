package me.crolemol.coc;

import java.io.File;
import java.io.IOException;

import me.crolemol.coc.arena.Buildingspecs;
import me.crolemol.coc.arena.InteractStick;
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.RelativeBuilding;
import me.crolemol.coc.arena.panels.BuildingPanels;
import me.crolemol.coc.arena.panels.BuildingShop;

import org.bukkit.OfflinePlayer;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Coc extends JavaPlugin {
	protected static Coc plugin;
	private static FileConfiguration mineConf;
	private static File mineFile;
	private File generalFile;
	protected FileConfiguration generalConf;
	protected  File UUIDFile;
	protected  FileConfiguration UUIDConf;

	@Override
	public void onEnable() {
		plugin = this;
		UUIDFile = new File(this.getDataFolder() + "/data/ArenaUUIDS.yml");
		UUIDConf = YamlConfiguration.loadConfiguration(UUIDFile);
		if (!(this.getServer().getWorlds().contains(this.getServer().getWorld(
				"coc")))) {
			WorldCreator wc = new WorldCreator("coc");
			wc.generator(new Nullchunkgenerator());
			wc.generateStructures(false);
			wc.type(WorldType.FLAT);
			wc.createWorld();
			plugin.getServer().createWorld(wc);
		}
		saveresources();
		generalFile = new File(plugin.getDataFolder() + "/data", "general.yml");
		generalConf = YamlConfiguration.loadConfiguration(getGeneralFile());
		generalConf.addDefault("max x", 2000);
		generalConf.addDefault("nextground.x", 0);
		generalConf.addDefault("nextground.y", 64);
		generalConf.addDefault("nextground.z", 0);
		generalConf.options().copyDefaults(true);
		try {
			generalConf.save(getGeneralFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		UUIDConf.addDefault("UUIDCounter", 0);
		UUIDConf.options().copyDefaults(true);
		try {
			UUIDConf.save(UUIDFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getCommand("coc").setExecutor(new CocCommandExecutor());
		this.getServer().getPluginManager().registerEvents(new Eventlistener(), this);
		this.getServer().getPluginManager().registerEvents(new BuildingPanels(), this);
		this.getServer().getPluginManager().registerEvents(new BuildingShop(), this);
		this.getServer().getPluginManager().registerEvents(new RelativeBuilding(new Goldmine(null, null, 0, 0)), this);
		this.getServer().getPluginManager().registerEvents(new InteractStick(), this);

	}

	@Override
	public void onDisable() {

	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new Nullchunkgenerator();
	}


	public static FileConfiguration getdataconffile(OfflinePlayer player) {
		mineFile = new File(plugin.getDataFolder() + "/data/players/",
				player.getName() + ".yml");
		mineConf = YamlConfiguration.loadConfiguration(mineFile);
		return mineConf;

	}

	public static File getdatafile(OfflinePlayer player) {
		mineFile = new File(plugin.getDataFolder() + "/data/players",
				player.getName() + ".yml");
		return mineFile;

	}

	public FileConfiguration getgeneraldataconf() {
		generalConf = YamlConfiguration.loadConfiguration(getGeneralFile());
		return generalConf;
	}

	public File getGeneralFile() {
		return generalFile;
	}

	public static Coc getPlugin() {
		return plugin;
	}
	public FileConfiguration getUUIDConf(){
		return UUIDConf;
	}
	public File getUUIDFile(){
		return UUIDFile;
	}
	private void saveresources() {
		File ground = new File(this.getDataFolder()
				+ "schematics/ground.schematic");
		if (!ground.exists()) {
			this.saveResource("schematics/ground.schematic", false);
		}
		for(Buildingspecs building : Buildingspecs.values()){
			for(int level=1;level<=building.getMaxLevel();level++){
				File buildingfile = new File(this.getDataFolder()	+ "schematics/"+building.getName()+"/"+building.getName()+"_lv"+level+".schematic");
				if (!buildingfile.exists()) {
					this.saveResource("schematics/"+building.getName()+"/"+building.getName()+"_lv"+level+".schematic",
							false);
				}
			}
		}
		
	}
}
