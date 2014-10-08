package me.crolemol.coc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.InteractStick;
import me.crolemol.coc.scoreboard.ScoreboardApi;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CocCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equals("coc")) {
			if (sender instanceof Player) {
				if (args.length == 1 && args[0].equals("home")) {
					Player player = (Player) sender;
					if (playerHasBase(player) == true) {
						Base base = Base.getBase(player);
						player.teleport(base.getArenaSpawn());
						player.getInventory().clear();
						
						ItemStack book = new ItemStack(Material.BOOK);
						ItemMeta bookMeta = book.getItemMeta();
						bookMeta.setDisplayName("Shop");
						book.setItemMeta(bookMeta);
						player.getInventory().setItem(8, book);
						ItemStack Attack = new ItemStack(Material.WOOD_SWORD);
						ItemMeta AttackMeta = Attack.getItemMeta();
						AttackMeta.setDisplayName("Attack");
						Attack.setItemMeta(AttackMeta);
						player.getInventory().setItem(7, Attack);
						InteractStick.getInteractStick(player);
						ScoreboardApi sb = new ScoreboardApi();
						sb.setCurrencyBoard(player);
						return true;
					} else {
						Base.newBase(player);
						return true;
					}

				} else if (args.length == 1 && args[0].equals("building")) {
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
	
	private boolean playerHasBase(Player  player){
        boolean b=false;
        try {
            Statement  sta=Coc.getPlugin().getDataBase().getConnection().createStatement();
            ResultSet  exists=sta.executeQuery(
                    "SELECT BaseID" +
                            " FROM Bases" +
                            " WHERE owner='"+player.getUniqueId()+"'");
            exists.next();
            int x = exists.getInt(1);
            player.sendMessage(""+x);
            b=exists.getInt(1)>0;
            exists.close();
            sta.close();
        } catch (SQLException  e) {
            return false;
        }
        return b;
    }

}