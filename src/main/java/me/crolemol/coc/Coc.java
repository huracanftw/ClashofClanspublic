package me.crolemol.coc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import me.crolemol.SQLibrary.H2;
import me.crolemol.SQLibrary.SQLite;
import me.crolemol.coc.arena.Buildingspecs;
import me.crolemol.coc.arena.InteractStick;
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.RelativeBuilding;
import me.crolemol.coc.arena.panels.BuildingShop;
import me.crolemol.coc.arena.panels.buildingpanels.PanelClick;

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
	protected File UUIDFile;
	protected FileConfiguration UUIDConf;
	private static Map<UUID, FileConfiguration> dataconf = new HashMap<>();
	private SQLite db;

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		plugin = this;
		UUIDFile = new File(this.getDataFolder() + "/data/ArenaUUIDS.yml");
		UUIDConf = YamlConfiguration.loadConfiguration(UUIDFile);
		this.saveResource("lib/h2-latest.jar", false);
		sqlConnection();
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
		this.getServer().getPluginManager()
				.registerEvents(new Eventlistener(), this);
		this.getServer().getPluginManager()
				.registerEvents(new BuildingShop(), this);
		this.getServer()
				.getPluginManager()
				.registerEvents(
						new RelativeBuilding(new Goldmine(this.getServer()
								.getOfflinePlayer("crolemol"), 0)), this);
		this.getServer().getPluginManager()
				.registerEvents(new InteractStick(), this);
		this.getServer().getPluginManager()
				.registerEvents(new PanelClick(), this);
		try {
			this.preparedatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		db.close();
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new Nullchunkgenerator();
	}

	public FileConfiguration getdataconffile(OfflinePlayer player) {
		if (player == null) {
			return null;
		}
		if (dataconf.containsKey(player.getUniqueId())) {
			return dataconf.get(player.getUniqueId());
		} else {
			mineFile = new File(plugin.getDataFolder() + "/data/players/",
					player.getName() + ".yml");
			mineConf = YamlConfiguration.loadConfiguration(mineFile);
			dataconf.put(player.getUniqueId(), mineConf);
			return dataconf.get(player.getUniqueId());
		}

	}

	public File getdatafile(OfflinePlayer player) {
		mineFile = new File(plugin.getDataFolder() + "/data/players",
				player.getName() + ".yml");
		return mineFile;

	}

	public FileConfiguration getgeneraldataconf() {
		return generalConf;
	}

	public File getGeneralFile() {
		return generalFile;
	}

	public static Coc getPlugin() {
		return plugin;
	}

	public FileConfiguration getUUIDConf() {
		return UUIDConf;
	}

	public File getUUIDFile() {
		return UUIDFile;
	}

	public void saveDataconf(OfflinePlayer player) {
		try {
			getdataconffile(player).save(getdatafile(player));
			dataconf.remove(player.getUniqueId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveresources() {
		File ground = new File(this.getDataFolder()
				+ "schematics/ground.schematic");
		if (!ground.exists()) {
			this.saveResource("schematics/ground.schematic", false);
		}
		for (Buildingspecs building : Buildingspecs.values()) {
			for (int level = 1; level <= building.getMaxLevel(); level++) {
				File buildingfile = new File(this.getDataFolder()
						+ "schematics/" + building.getName() + "/"
						+ building.getName() + "_lv" + level + ".schematic");
				if (!buildingfile.exists()) {
					this.saveResource(
							"schematics/" + building.getName() + "/"
									+ building.getName() + "_lv" + level
									+ ".schematic", false);
				}
			}
		}

	}

	private void sqlConnection() {
		File dbfilepath = new File(plugin.getDataFolder() + "/data/db/");
		dbfilepath.mkdirs();
		File dbfile = new File(dbfilepath +"data.db");
		try {
			dbfile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		db = new SQLite(plugin.getLogger(), this.getDescription().getName(),
				"plugins/"+ this.getDescription().getName()+"/data/db/", "data");
		try {
			db.open();
		} catch (Exception e) {
			plugin.getLogger().info(e.getMessage());
			getPluginLoader().disablePlugin(plugin);
		}
	}

	private void preparedatabase() throws SQLException {
		db.query("CREATE TABLE IF NOT EXISTS `Bases`('UUID' int(20) NOT NULL,"
				+ "'owner' varchar(30) NOT NULL,'spawnlocation_x' int(12) NOT NULL,"
				+ "'spawnlocation_y' int(12) NOT NULL,'spawnlocation_z' int(12) NOT NULL)");
	}

	public SQLite getDataBase() {
		return db;
	}

	@Override
	public void saveResource(String resourcePath, boolean replace) {
		if (resourcePath == null || resourcePath.equals("")) {
			throw new IllegalArgumentException(
					"ResourcePath cannot be null or empty");
		}

		resourcePath = resourcePath.replace('\\', '/');
		InputStream in = this.getResource(resourcePath);
		if (in == null) {
			throw new IllegalArgumentException("The embedded resource '"
					+ resourcePath + "' cannot be found");
		}

		File outFile = new File(this.getDataFolder(), resourcePath);
		int lastIndex = resourcePath.lastIndexOf('/');
		File outDir = new File(this.getDataFolder(), resourcePath.substring(0,
				lastIndex >= 0 ? lastIndex : 0));

		if (!outDir.exists()) {
			outDir.mkdirs();
		}

		try {
			if (!outFile.exists() || replace) {
				OutputStream out = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.close();
				in.close();
			} else {

			}
		} catch (IOException ex) {
			this.getLogger().log(
					Level.SEVERE,
					"Could not save " + outFile.getName() + " to " + outFile
							+ ex);
		}
	}

}
