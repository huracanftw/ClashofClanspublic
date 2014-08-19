package me.crolemol.coc.economy;

import java.io.File;
import java.io.IOException;

import me.crolemol.coc.Coc;
import me.crolemol.coc.scoreboard.ScoreboardApi;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Resources {
	public static void giveGold(OfflinePlayer receiver,int amount){
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Gold", getGold(receiver)+amount);
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		if(Coc.getPlugin().getServer().getOnlinePlayers().contains(Coc.getPlugin().getServer().getPlayer(receiver.getUniqueId()))){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	public static void giveElixir(OfflinePlayer receiver,int amount){
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Elixir", getElixir(receiver)+amount);
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		if(Coc.getPlugin().getServer().getOnlinePlayers().contains(Coc.getPlugin().getServer().getPlayer(receiver.getUniqueId()))){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	public static void giveGems(OfflinePlayer receiver,int amount){
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Gems", getGems(receiver)+amount);
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		if(Coc.getPlugin().getServer().getOnlinePlayers().contains(Coc.getPlugin().getServer().getPlayer(receiver.getUniqueId()))){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	public static void takeGold(OfflinePlayer receiver,int amount){
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Gold", getGold(receiver)-amount);
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		if(Coc.getPlugin().getServer().getOnlinePlayers().contains(Coc.getPlugin().getServer().getPlayer(receiver.getUniqueId()))){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	public static void takeElixir(OfflinePlayer receiver,int amount){
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Elixir", getElixir(receiver)-amount);
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		if(Coc.getPlugin().getServer().getOnlinePlayers().contains(Coc.getPlugin().getServer().getPlayer(receiver.getUniqueId()))){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	public static void takeGems(OfflinePlayer receiver,int amount){
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Gems", getGems(receiver)-amount);
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		if(Coc.getPlugin().getServer().getOnlinePlayers().contains(Coc.getPlugin().getServer().getPlayer(receiver.getUniqueId()))){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	public static int getGems(OfflinePlayer player){
		FileConfiguration dataconf = Coc.getdataconffile(player);
		return dataconf.getInt("Gems");
	}
	public static int getGold(OfflinePlayer player){
		FileConfiguration dataconf = Coc.getdataconffile(player);
		return dataconf.getInt("Gold");
	}
	public static int getElixir(OfflinePlayer player){
		FileConfiguration dataconf = Coc.getdataconffile(player);
		return dataconf.getInt("Elixir");
	}
}
