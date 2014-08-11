package me.crolemol.coc;

import java.io.File;
import java.io.IOException;

import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.Building;
import me.crolemol.coc.arena.panels.BuildingPanels;
import me.crolemol.coc.arena.panels.BuildingShop;

import org.bukkit.OfflinePlayer;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Coc extends JavaPlugin {
	protected static Coc plugin;
	private static FileConfiguration mineConf;
	private static File mineFile;
	protected File generalFile;
	protected FileConfiguration generalConf;

	@Override
	public void onEnable() {
		plugin = this;
		Base.UUIDFile = new File(this.getDataFolder() + "/data/ArenaUUIDS.yml");
		Base.UUIDConf = YamlConfiguration.loadConfiguration(Base.UUIDFile);
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
		generalConf = YamlConfiguration.loadConfiguration(generalFile);
		generalConf.addDefault("max x", 2000);
		generalConf.addDefault("nextground.x", 0);
		generalConf.addDefault("nextground.y", 64);
		generalConf.addDefault("nextground.z", 0);
		generalConf.options().copyDefaults(true);
		try {
			generalConf.save(generalFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Base.UUIDConf.addDefault("UUIDCounter", 0);
		Base.UUIDConf.options().copyDefaults(true);
		try {
			Base.UUIDConf.save(Base.UUIDFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getCommand("coc").setExecutor(new CocCommandExecutor());
		this.getServer().getPluginManager()
				.registerEvents(new Eventlistener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new BuildingPanels(), this);
		this.getServer().getPluginManager()
				.registerEvents(new BuildingShop(), this);
		this.getServer().getPluginManager()
				.registerEvents(new Building(), this);

	}

	@Override
	public void onDisable() {

	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new Nullchunkgenerator();
	}

	public static FileConfiguration getdataconffile(Player player) {
		mineFile = new File(getPlugin().getDataFolder() + "/data/players/",
				player.getName() + ".yml");
		mineConf = YamlConfiguration.loadConfiguration(mineFile);
		return mineConf;

	}

	public static FileConfiguration getdataconffile(OfflinePlayer player) {
		mineFile = new File(plugin.getDataFolder() + "/data/players/",
				player.getName() + ".yml");
		mineConf = YamlConfiguration.loadConfiguration(mineFile);
		return mineConf;

	}

	public static File getdatafile(Player player) {
		mineFile = new File(plugin.getDataFolder() + "/data/players",
				player.getName() + ".yml");
		return mineFile;

	}

	public FileConfiguration getgeneraldataconf() {
		generalFile = new File(getPlugin().getDataFolder() + "/data", "general.yml");
		generalConf = YamlConfiguration.loadConfiguration(generalFile);
		return generalConf;
	}

	public static Coc getPlugin() {
		return plugin;
	}

	private void saveresources() {
		File ground = new File(this.getDataFolder()
				+ "schematics/ground.schematic");
		File townhall_lv1 = new File(this.getDataFolder()
				+ "schematics/townhall/townhall_lv1.schematic");
		File townhall_lv2 = new File(this.getDataFolder()
				+ "schematics/townhall/townhall_lv2.schematic");
		File townhall_lv3 = new File(this.getDataFolder()
				+ "schematics/townhall/townhall_lv3.schematic");
		File townhall_lv4 = new File(this.getDataFolder()
				+ "schematics/townhall/townhall_lv4.schematic");
		File townhall_lv5 = new File(this.getDataFolder()
				+ "schematics/townhall/townhall_lv5.schematic");
		File townhall_lv6 = new File(this.getDataFolder()
				+ "schematics/townhall/townhall_lv6.schematic");
		File goldmine_lv1 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv1.schematic");
		File goldmine_lv2 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv2.schematic");
		File goldmine_lv3 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv3.schematic");
		File goldmine_lv4 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv4.schematic");
		File goldmine_lv5 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv5.schematic");
		File goldmine_lv6 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv6.schematic");
		File goldmine_lv7 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv7.schematic");
		File goldmine_lv8 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv8.schematic");
		File goldmine_lv9 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv9.schematic");
		File goldmine_lv10 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv9.schematic");
		File goldmine_lv11 = new File(this.getDataFolder()
				+ "schematics/goldmine/goldmine_lv9.schematic");

		if (!ground.exists()) {
			this.saveResource("schematics/ground.schematic", false);
		}
		if (!townhall_lv1.exists()) {
			this.saveResource("schematics/townhall/townhall_lv1.schematic",
					false);
		}
		if (!townhall_lv2.exists()) {
			this.saveResource("schematics/townhall/townhall_lv2.schematic",
					false);
		}
		if (!townhall_lv3.exists()) {
			this.saveResource("schematics/townhall/townhall_lv3.schematic",
					false);
		}
		if (!townhall_lv4.exists()) {
			this.saveResource("schematics/townhall/townhall_lv4.schematic",
					false);
		}
		if (!townhall_lv5.exists()) {
			this.saveResource("schematics/townhall/townhall_lv5.schematic",
					false);
		}
		if (!townhall_lv6.exists()) {
			this.saveResource("schematics/townhall/townhall_lv6.schematic",
					false);
		}

		if (!goldmine_lv1.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv1.schematic",
					false);
		}
		if (!goldmine_lv2.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv2.schematic",
					false);
		}
		if (!goldmine_lv3.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv3.schematic",
					false);
		}
		if (!goldmine_lv4.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv4.schematic",
					false);
		}
		if (!goldmine_lv5.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv5.schematic",
					false);
		}
		if (!goldmine_lv6.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv6.schematic",
					false);
		}
		if (!goldmine_lv7.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv7.schematic",
					false);
		}
		if (!goldmine_lv8.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv8.schematic",
					false);
		}
		if (!goldmine_lv9.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv9.schematic",
					false);
		}
		if (!goldmine_lv10.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv10.schematic",
					false);
		}
		if (!goldmine_lv11.exists()) {
			this.saveResource("schematics/goldmine/goldmine_lv11.schematic",
					false);
		}
	}
}
