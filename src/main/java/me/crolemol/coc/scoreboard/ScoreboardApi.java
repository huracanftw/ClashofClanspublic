package me.crolemol.coc.scoreboard;

import me.crolemol.coc.economy.Resources;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardApi {
	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	@SuppressWarnings("deprecation")
	public void setCurrencyBoard(Player player){
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(player.getName());
		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD+"Gold"));
		score.setScore(Resources.getGold(player));
		Score score2 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE+"Elixir"));
		score2.setScore(Resources.getElixir(player));
		if(Resources.getDarkElixir(player) != 0){
		Score score3 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY+"Dark Elixir"));
		score3.setScore(Resources.getDarkElixir(player));
		}
		Score score4 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN+"Gems"));
		score4.setScore(Resources.getGems(player));
		player.setScoreboard(board);
	}
	public static void removeCurrencyBoardt(Player player){
		player.setScoreboard(manager.getNewScoreboard());
	}
}
