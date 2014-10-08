package me.crolemol.coc.arena;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.crolemol.coc.Coc;
import me.crolemol.coc.economy.PlayerData;

public class Raid {
	public static Base getRaidableBase(Base BaseofRaider){
		UUID uuid = null;
		OfflinePlayer player;
		for(int counter=100;counter<=1000;counter+=100){
			int maxtroph = PlayerData.getTrophies(BaseofRaider.getOwner()) + counter;
			int mintroph = PlayerData.getTrophies(BaseofRaider.getOwner()) - counter;
			ResultSet result = Coc.getPlugin().getDataBase().query("SELECT owner FROM PlayerData WHERE Trophies <= "+maxtroph+" AND Trophies >= "+mintroph+"");
			try {
				uuid = UUID.fromString(result.getString("owner"));
				break;
			} catch (SQLException e) {
			}
			
		}
		player = Coc.getPlugin().getServer().getOfflinePlayer(uuid);
		return Base.getBase(player);
	}
	public static void RaidBase(Base base,Player Raider){
		Raider.teleport(base.getArenaSpawn());
	}
}
