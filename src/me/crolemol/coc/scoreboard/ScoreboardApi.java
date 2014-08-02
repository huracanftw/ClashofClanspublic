package me.crolemol.coc.scoreboard;

import me.crolemol.coc.Coc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardApi {
	private static Coc plugin = Coc.getPlugin();
	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	@SuppressWarnings("deprecation")
	public void setCurrencyBoard(Player player){
		FileConfiguration dataconf = plugin.getdataconffile(player);
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(player.getName());
		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD+"Gold"));
		score.setScore(dataconf.getInt("Gold"));
		Score score2 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE+"Elixir"));
		score2.setScore(dataconf.getInt("Elixir"));
		Score score3 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN+"Gems"));
		score3.setScore(dataconf.getInt("Gems"));
		player.setScoreboard(board);
	}
	public static void removeCurrencyBoardt(Player player){
		player.setScoreboard(manager.getNewScoreboard());
	}
}
