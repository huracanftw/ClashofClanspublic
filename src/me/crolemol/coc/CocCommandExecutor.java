package me.crolemol.coc;


import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.InteractStick;
import me.crolemol.coc.scoreboard.ScoreboardApi;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CocCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Coc plugin = Coc.getPlugin();
		if (cmd.getName().equals("coc")) {
			if (sender instanceof Player) {
				if (args.length == 1 && args[0].equals("home")) {
					Player player = (Player) sender;
					Coc.getPlugin();
					FileConfiguration dataconf = plugin.getdataconffile(player);
					Coc.getPlugin();
					if (plugin.getdatafile(player).exists() == true) {
						Location tploc = new Location(Coc.plugin.getServer()
								.getWorld("coc"), dataconf.getInt("spawn.x"),
								dataconf.getInt("spawn.y"),
								dataconf.getInt("spawn.z"));
						player.teleport(tploc);
						player.getInventory().clear();
						ItemStack book = new ItemStack(Material.BOOK);
						ItemMeta bookMeta = book.getItemMeta();
						bookMeta.setDisplayName("Shop");
						book.setItemMeta(bookMeta);
						player.getInventory().setItem(8, book);
						InteractStick.getInteractStick(player);
						ScoreboardApi sb = new ScoreboardApi();
						sb.setCurrencyBoard(player);
						return true;
					} else {
						Base.newBase(player);
						return true;
				}

			}else if(args[0].equals("building")){
				((Player) sender).getInventory().clear();
				ItemStack offset = new ItemStack(Material.STICK);
				ItemMeta offsetmeta = offset.getItemMeta();
				offsetmeta.setDisplayName("SurfaceStick");
				offset.setItemMeta(offsetmeta);
				ItemStack wand = new ItemStack(Material.WOOD_AXE);
				
				((Player) sender).getInventory().addItem(wand);
				((Player) sender).getInventory().addItem(offset);
				return true;
			}
			}
			}
		return false;
	}

}