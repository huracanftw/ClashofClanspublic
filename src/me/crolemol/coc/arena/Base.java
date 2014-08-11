package me.crolemol.coc.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.crolemol.coc.Coc;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;

public class Base {
	private static Coc plugin = Coc.getPlugin();
	public static File UUIDFile;
	public static FileConfiguration UUIDConf;

	public Boolean isinownbase(Player player) throws DataException, IOException {
		FileConfiguration dataconf = Coc.getdataconffile(player);
		@SuppressWarnings("deprecation")
		CuboidClipboard cc = CuboidClipboard.loadSchematic(new File(plugin
				.getDataFolder() + "/schematics/ground.schematic"));
		if (plugin.getServer().getOnlinePlayers().contains(player)) {
			if (player.getLocation().getBlockX() < dataconf.getInt("spawn.x")
					+ cc.getLength() / 2
					&& player.getLocation().getBlockX() > dataconf
							.getInt("spawn.x") - cc.getLength() / 2) {
				if (player.getLocation().getBlockZ() < dataconf
						.getInt("spawn.z") + cc.getLength() / 2
						&& player.getLocation().getBlockZ() > dataconf
								.getInt("spawn.z") - cc.getLength() / 2) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public List<String> containsbuildings(Player player) {
		List<String> contains = new ArrayList<String>();
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(player);
		for (Buildingspecs specs : Buildingspecs.values()) {
			if (dataconf.contains(specs.getName())) {
				contains.add(specs.getName());
			}
		}
		return contains;
	}

	public int getAmountofBuilding(String Buildingname, Player player) {
		FileConfiguration dataconf = Coc.getdataconffile(player);
		if (!dataconf.contains(Buildingname)) {
			return 0;
		}
		int numberofbuilding = 0;
		for (int counter = 1; dataconf.contains(Buildingname + "." + counter); counter++) {
			numberofbuilding = counter;
		}
		return numberofbuilding;
	}

	public enum Buildingspecs {
		// length is z axis
		// width is x axis
		townhall("townhall", 12, 10), goldmine("goldmine", 8, 8);
		private int returnlength;
		private int returnwidth;
		private String returnname;

		Buildingspecs(String name, int width, int length) {
			returnname = name;
			returnlength = length;
			returnwidth = width;
		}

		public int getLength() {
			return returnlength;
		}

		public int getWidth() {
			return returnwidth;
		}

		public String getName() {
			return returnname;
		}
	}

	public Player playerisInWhoseBase(Player player) throws DataException,
			IOException {
		if (plugin.getServer().getOnlinePlayers().contains(player)) {
			int UUID = getArenaUUID(player.getLocation());
			String name = UUIDConf.getString("" + UUID);
			@SuppressWarnings("deprecation")
			Player returnplayer = plugin.getServer().getPlayer(name);
			return returnplayer;
		} else {
			return null;
		}
	}

	public void Rebuild(Player BaseOwner) {
		Base arena = new Base();
		List<String> contains = arena.containsbuildings(BaseOwner);
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(BaseOwner);
		World world = Coc.getPlugin().getServer().getWorld("coc");
		File ground = new File(plugin.getDataFolder()
				+ "/schematics/ground.schematic");
		try {
			@SuppressWarnings("deprecation")
			CuboidClipboard cc = CuboidClipboard.loadSchematic(ground);
			pasteschematic(
					world,
					ground,
					new Vector(dataconf.getInt("spawn.x") - cc.getWidth() / 2,
							dataconf.getInt("spawn.y"), dataconf
									.getInt("spawn.z") - cc.getLength() / 2));
		} catch (MaxChangedBlocksException | DataException | IOException e) {
			e.printStackTrace();
		}
		for (Buildingspecs building : Buildingspecs.values()) {
			if (contains.contains(building.getName())) {
				for (int counter = 1; counter <= arena.getAmountofBuilding(
						building.getName(), BaseOwner); counter++) {
					File schematic = new File(Coc.getPlugin().getDataFolder()
							+ "/schematics/"
							+ building.getName()
							+ "/"
							+ building.getName()
							+ "_lv"
							+ dataconf.getInt(building.getName() + "."
									+ counter + ".level") + ".schematic");
					Vector vec = new Vector(dataconf.getInt(building.getName()
							+ "." + counter + ".location.x"),
							dataconf.getInt(building.getName() + "." + counter
									+ ".location.y"), dataconf.getInt(building
									.getName() + "." + counter + ".location.z"));
					try {
						pasteschematic(world, schematic, vec);
					} catch (MaxChangedBlocksException | DataException
							| IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public Integer getArenaUUID(Location loc) throws DataException, IOException {
		@SuppressWarnings("deprecation")
		CuboidClipboard cc = CuboidClipboard.loadSchematic(new File(plugin
				.getDataFolder() + "/schematics/ground.schematic"));

		int max_x = plugin.getgeneraldataconf().getInt("max x")
				/ cc.getLength();
		int x_edge = cc.getLength();
		int z_edge = cc.getLength();
		int UUID = 1;
		double max_columns = Math.ceil(UUIDConf.getInt("UUIDCounter") / max_x);
		if (loc.getX() >= 0 || loc.getZ() >= 0) {
			for (int column = 0; column <= max_columns; column++) {
				for (int row = 1; row <= max_x; row++) {
					x_edge = row * cc.getLength();
					if (loc.getBlockX() < x_edge && loc.getBlockZ() < z_edge) {
						return UUID;
					} else {
						UUID++;
					}
				}
			}
		}
		return null;

	}

	public Location getArenaSpawn(Player player) {
		FileConfiguration dataconf = Coc.getdataconffile(player);
		Location loc = new Location(plugin.getServer().getWorld("coc"),
				dataconf.getInt("spawn.x"), dataconf.getInt("spawn.y"),
				dataconf.getInt("spawn.z"));
		return loc;
	}

	@SuppressWarnings("deprecation")
	public Location getArenaSpawn(int ArenaUUID) {
		OfflinePlayer player;
		if (plugin
				.getServer()
				.getOnlinePlayers()
				.contains(
						plugin.getServer().getPlayer(
								UUIDConf.getString("" + ArenaUUID)))) {
			Player player2 = plugin.getServer().getPlayer(
					UUIDConf.getString("" + ArenaUUID));
			player = player2;
		} else {
			OfflinePlayer player2 = plugin.getServer().getOfflinePlayer(
					UUIDConf.getString("" + ArenaUUID));
			player = player2;
		}
		FileConfiguration dataconf = Coc.getdataconffile(player);
		Location loc = new Location(plugin.getServer().getWorld("coc"),
				dataconf.getInt("spawn.x"), dataconf.getInt("spawn.y"),
				dataconf.getInt("spawn.z"));
		return loc;
	}

	public void giveplayerArenaUUID(Player player) {
		int uuid = UUIDConf.getInt("UUIDCounter");
		uuid++;
		UUIDConf.set(uuid + "", player.getName());
		UUIDConf.set("UUIDCounter", uuid);
		try {
			UUIDConf.save(UUIDFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void pasteschematic(World world, File file,
			com.sk89q.worldedit.Vector origin) throws DataException,
			IOException, MaxChangedBlocksException {
		EditSession es = new EditSession(new BukkitWorld(world), 999999999);
		@SuppressWarnings("deprecation")
		CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
		cc.paste(es, origin, false);
	}
}
