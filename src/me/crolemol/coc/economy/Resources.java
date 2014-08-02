package me.crolemol.coc.economy;

import java.io.File;
import java.io.IOException;

import me.crolemol.coc.Coc;
import me.crolemol.coc.scoreboard.ScoreboardApi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Resources {
	public void giveGold(Player receiver,int amount){
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(receiver);
		dataconf.set("Gold", dataconf.getInt("Gold")+amount);
		File datafile = Coc.getPlugin().getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
	public void giveElixir(Player receiver,int amount){
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(receiver);
		dataconf.set("Elixir", dataconf.getInt("Elixir")+amount);
		File datafile = Coc.getPlugin().getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
	public void giveGems(Player receiver,int amount){
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(receiver);
		dataconf.set("Gems", dataconf.getInt("Gems")+amount);
		File datafile = Coc.getPlugin().getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
	public void takeGold(Player receiver,int amount){
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(receiver);
		dataconf.set("Gold", dataconf.getInt("Gold")-amount);
		File datafile = Coc.getPlugin().getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
	public void takeElixir(Player receiver,int amount){
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(receiver);
		dataconf.set("Elixir", dataconf.getInt("Elixir")-amount);
		File datafile = Coc.getPlugin().getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
	public void takeGems(Player receiver,int amount){
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(receiver);
		dataconf.set("Gems", dataconf.getInt("Gems")-amount);
		File datafile = Coc.getPlugin().getdatafile(receiver);
		try {
			dataconf.save(datafile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScoreboardApi sb = new ScoreboardApi();
		sb.setCurrencyBoard(receiver);
	}
}
