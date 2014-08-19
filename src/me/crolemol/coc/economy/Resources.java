package me.crolemol.coc.economy;

import java.io.File;
import java.io.IOException;

import me.crolemol.coc.Coc;
import me.crolemol.coc.scoreboard.ScoreboardApi;

<<<<<<< HEAD
import org.bukkit.OfflinePlayer;
=======
>>>>>>> origin/master
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Resources {
<<<<<<< HEAD
	public static void giveGold(OfflinePlayer receiver,int amount){
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Gold", getGold(receiver)+amount);
=======
	public void giveGold(Player receiver,int amount){
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Gold", dataconf.getInt("Gold")+amount);
>>>>>>> origin/master
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
<<<<<<< HEAD
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
=======
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
	public void giveElixir(Player receiver,int amount){
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Elixir", dataconf.getInt("Elixir")+amount);
>>>>>>> origin/master
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
<<<<<<< HEAD
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
=======
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
	public void giveGems(Player receiver,int amount){
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Gems", dataconf.getInt("Gems")+amount);
>>>>>>> origin/master
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
<<<<<<< HEAD
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
=======
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
	public void takeGold(Player receiver,int amount){
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Gold", dataconf.getInt("Gold")-amount);
>>>>>>> origin/master
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
<<<<<<< HEAD
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
=======
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
	public void takeElixir(Player receiver,int amount){
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Elixir", dataconf.getInt("Elixir")-amount);
>>>>>>> origin/master
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
<<<<<<< HEAD
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
=======
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
	public void takeGems(Player receiver,int amount){
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(receiver);
		dataconf.set("Gems", dataconf.getInt("Gems")-amount);
>>>>>>> origin/master
		Coc.getPlugin();
		File datafile = Coc.getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
<<<<<<< HEAD
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
=======
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
>>>>>>> origin/master
	}
}
