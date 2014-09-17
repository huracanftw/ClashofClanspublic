package me.crolemol.coc.arena.building;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.building.interfaces.Building;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.data.DataException;

public class RelativeBuilding implements Listener {
	static Coc plugin = Coc.getPlugin();
	private Building building;
	private static Map<String, Location> target = new HashMap<>();
	private static Map<String, Building> staticbuilding = new HashMap<>();

	public RelativeBuilding(Building building) {
		this.building = building;
	}

	public void placeRelativeBuilding(Player player){
		removeRelativeBuilding(player);
		Base base = Base.getBase(player);
		if(building.isRealBuilding() == false){
			@SuppressWarnings("deprecation")
			Location loc = player.getTargetBlock(null, 10).getLocation();
			base.placeNonRealBuilding(building,loc , player);
		}

		base.Rebuild();}

	@SuppressWarnings("deprecation")
	public void putRelativeBuilding(Player player) {

		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()
					+ "/schematics/" + building.getBuildingName() + "/"
					+ building.getBuildingName() + "_lv"
					+ building.getLevel() + ".schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		for (int xcounter = 0; xcounter < cc.getWidth(); xcounter++) {
			for (int zcounter = 0; zcounter < cc.getLength(); zcounter++) {
				for (int ycounter = 0; ycounter < cc.getHeight(); ycounter++) {
					Location loc = new Location(plugin.getServer().getWorld(
							"coc"), player.getTargetBlock(null, 10)
							.getLocation().getBlockX()+ xcounter,
							player.getTargetBlock(null, 10).getLocation().getBlockY()+ ycounter + cc.getOffset().getBlockY(), player
							.getTargetBlock(null, 10).getLocation().getBlockZ()
							+ zcounter);
					Vector vec = new Vector(xcounter, ycounter, zcounter);
					BaseBlock block = cc.getBlock(vec);
					int materialid = block.getId();
					Material material = Material.getMaterial(materialid);
					player.sendBlockChange(loc, material,
							(byte) block.getData());
				}
			}
		}

		target.put(player.getName(), player.getTargetBlock(null, 10)
				.getLocation());
		staticbuilding.put(player.getName(), building);
	}

	@SuppressWarnings("deprecation")
	public void removeRelativeBuilding(Player player) {
		if (!target.containsKey(player.getPlayer().getName())) {
			return;
		}
		if (!staticbuilding.containsKey(player.getPlayer().getName())) {
			return;
		}
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()
					+ "/schematics/" + building.getBuildingName() + "/"
					+ building.getBuildingName() + "_lv"
					+ building.getLevel() + ".schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		for (int xcounter = 0; xcounter < cc.getWidth(); xcounter++) {
			for (int zcounter = 0; zcounter < cc.getLength(); zcounter++) {
				for (int ycounter = 0; ycounter < cc.getHeight(); ycounter++) {
					Location loc = new Location(plugin.getServer().getWorld(
							"coc"), target.get(player.getName()).getBlockX()
							+ xcounter, target.get(player.getName())
							.getBlockY()
							+ ycounter
							+ cc.getOffset().getBlockY(), target.get(
							player.getName()).getBlockZ()
							+ zcounter);
					Block block = loc.getBlock();
					Material material = block.getType();
					player.sendBlockChange(loc, material, loc.getBlock()
							.getData());
				}
			}
		}
		target.remove(player.getName());
		staticbuilding.remove(player.getName());
	}

	@SuppressWarnings("deprecation")
	private void moveRelativeBuilding(Player player) {
		if (!target.containsKey(player.getPlayer().getName())) {
			return;
		}
		if (!target.get(player.getName()).equals(
				player.getTargetBlock(null, 10))) {
			CuboidClipboard cc = null;
			try {
				cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()
		+ "/schematics/"+ building.getBuildingName()+ "/"+ building.getBuildingName()
						+ "_lv"
						+ building.getLevel() + ".schematic"));
			} catch (DataException | IOException e) {
				e.printStackTrace();
			}
			for (int xcounter = 0; xcounter < cc.getWidth(); xcounter++) {
				for (int zcounter = 0; zcounter < cc.getLength(); zcounter++) {
					for (int ycounter = 0; ycounter < cc.getHeight(); ycounter++) {
						Location loc = new Location(plugin.getServer()
								.getWorld("coc"), target.get(player.getName())
								.getBlockX() + xcounter, target.get(
								player.getName()).getBlockY()
								+ ycounter + cc.getOffset().getBlockY(),
								target.get(player.getName()).getBlockZ()
										+ zcounter);
						Block block = loc.getBlock();
						block.setData(block.getData());
						Material material = block.getType();
						player.sendBlockChange(loc, material, loc.getBlock()
								.getData());
					}
				}
				for (int xcounter2 = 0; xcounter2 < cc.getWidth(); xcounter2++) {
					for (int zcounter2 = 0; zcounter2 < cc.getLength(); zcounter2++) {
						for (int ycounter2 = 0; ycounter2 < cc.getHeight(); ycounter2++) {
							Location loc2 = new Location(plugin.getServer()
									.getWorld("coc"), player.getTargetBlock(null, 10).getLocation()
									.getBlockX()+ xcounter2, player.getTargetBlock(null, 10).getLocation()
									.getBlockY()+ ycounter2+ cc.getOffset().getBlockY()
									, player.getTargetBlock(null, 10)
									.getLocation().getBlockZ()
									+ zcounter2);
							Vector vec = new Vector(xcounter2, ycounter2,
									zcounter2);
							BaseBlock block = cc.getBlock(vec);
							int materialid = block.getId();
							Material material = Material
									.getMaterial(materialid);
							player.sendBlockChange(loc2, material,
									(byte) block.getData());
						}
					}
				}
			}
			target.put(player.getName(), player.getTargetBlock(null, 10)
					.getLocation());

		}
	}

	@EventHandler
	private void onplayermove(PlayerMoveEvent event) {
		if (!event.getPlayer().getWorld()
				.equals(plugin.getServer().getWorld("coc"))) {
			return;
		}
		if (!target.containsKey(event.getPlayer().getName())) {
			return;
		}
		if (!staticbuilding.containsKey(event.getPlayer().getName())) {
			return;
		}
		RelativeBuilding rb = new RelativeBuilding(
				getNewBuilding(event.getPlayer()));
		rb.moveRelativeBuilding(event.getPlayer());
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Base base = Base.getBase(event.getPlayer());
		if (!event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc"))) {
			return;
		}
		if (!target.containsKey(event.getPlayer().getName())) {
			return;
		}
		if (!staticbuilding.containsKey(event.getPlayer().getName())) {
			return;
		}
		RelativeBuilding rb = new RelativeBuilding(getNewBuilding(event.getPlayer()));
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()
					+ "/schematics/ground.schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		CuboidClipboard cc2 = null;
		try {
			cc2 = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()
					+ "/schematics/" + rb.building.getBuildingName() + "/"
					+ rb.building.getBuildingName() + "_lv"
					+ rb.building.getLevel() + ".schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				|| event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (event.getPlayer().getTargetBlock(null, 10).getLocation()
					.getBlockY() == plugin.getdataconffile(event.getPlayer())
					.getInt("spawn.y") - 1) {
				Location loc1 = new Location(plugin.getServer().getWorld("coc"), base.getArenaSpawn().getBlockX()+ cc.getWidth()
								/ 2 - 10, base.getArenaSpawn().getBlockY(),
						base.getArenaSpawn().getBlockZ() + cc.getLength() / 2
								- 10);
				Location loc2 = new Location(
						plugin.getServer().getWorld("coc"), base
								.getArenaSpawn().getBlockX()
								- cc.getWidth()
								/ 2 + 10, base.getArenaSpawn().getBlockY(),
						base.getArenaSpawn().getBlockZ() - cc.getLength() / 2
								+ 10);
				if (checkIfInArea(loc1, loc2,
						event.getPlayer().getTargetBlock(null, 10)
								.getLocation()) == true) {
					if (checkIfInArea(loc1, loc2,
							event.getPlayer().getTargetBlock(null, 10)
									.getLocation()
									.add(cc2.getWidth() - 1, 0, 0))) {
						if (checkIfInArea(
								loc1,
								loc2,
								event.getPlayer().getTargetBlock(null, 10)
										.getLocation()
										.add(0, 0, cc2.getLength() - 1))) {
							if (checkIfInArea(
									loc1,
									loc2,
									event.getPlayer()
											.getTargetBlock(null, 10)
											.getLocation()
											.add(cc2.getWidth() - 1, 0,
													cc2.getLength() - 1))) {
								rb.placeRelativeBuilding(event.getPlayer());
								base.Rebuild();
							} else {
								event.getPlayer()
										.sendMessage(
												ChatColor.RED
														+ "[ClashofClans] your building is not in your base!");
							}

						} else {
							event.getPlayer()
									.sendMessage(
											ChatColor.RED
													+ "[ClashofClans] your building is not in your base!");
						}
					} else {
						event.getPlayer().sendMessage(ChatColor.RED
												+ "[ClashofClans] your building is not in your base!");
					}
				} else {
					event.getPlayer().sendMessage(
									ChatColor.RED+ "[ClashofClans] your building is not in your base!");
				}
			} else {
				event.getPlayer().sendMessage(
								ChatColor.RED+ "[ClashofClans] your building is not on the ground!");
			}
		}
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)
				|| event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			rb.removeRelativeBuilding(event.getPlayer());
		}
	}

	private boolean checkIfInArea(Location corner1, Location corner2,
			Location PlayerPos) {
		if (PlayerPos.getBlockX() >= corner2.getBlockX()
				&& PlayerPos.getBlockX() <= corner1.getBlockX()) {
			if (PlayerPos.getBlockZ() >= corner2.getBlockZ()
					&& PlayerPos.getBlockZ() <= corner1.getBlockZ()) {
				return true;
			}
		}
		return false;
	}

	private Building getNewBuilding(Player player) {
		Building building = staticbuilding.get(player.getName());
		if (building instanceof Goldmine) {
			return new Goldmine(building.getOwner(),building.getLevel());
		} else if (building instanceof Townhall) {
			return new Townhall(building.getOwner(),building.getLevel());
		} else if (building instanceof GoldStorage) {
			return new GoldStorage(building.getOwner(),building.getLevel());
		}else if (building instanceof BuildersHut){
			return new BuildersHut(player,building.getLevel());
		}else if(building instanceof ElixirCollector){
			return new ElixirCollector(player,building.getLevel());
		}else if(building instanceof ElixirStorage){
			return new ElixirStorage(player,building.getLevel());
		}else if(building instanceof DarkElixirStorage){
			return new DarkElixirStorage(player,building.getLevel());
		}else if(building instanceof DarkElixirDrill){
			return new DarkElixirDrill(player,building.getLevel());
		}else if(building instanceof ArmyCamp){
			return new ArmyCamp(player,building.getLevel());
		}
		return null;
	}
}
