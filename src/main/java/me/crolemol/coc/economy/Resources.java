package me.crolemol.coc.economy;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.crolemol.coc.Coc;
import me.crolemol.coc.scoreboard.ScoreboardApi;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Resources {
	private static Coc plugin = Coc.getPlugin();
	@SuppressWarnings("deprecation")
	public static void giveGold(OfflinePlayer receiver,int amount){
		int gold = getGold(receiver)+amount;
		Coc.getPlugin().getDataBase().query("UPDATE Resources SET Gold="+gold+" WHERE owner LIKE '"+receiver.getUniqueId()+"'");
		ScoreboardApi sb = new ScoreboardApi();
		if(plugin.getServer().getPlayer(receiver.getName()) != null){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	@SuppressWarnings("deprecation")
	public static void giveElixir(OfflinePlayer receiver,int amount){
		int elixir = getElixir(receiver)+amount;
		Coc.getPlugin().getDataBase().query("UPDATE Resources SET Elixir="+elixir+" WHERE owner LIKE '"+receiver.getUniqueId()+"'");
		ScoreboardApi sb = new ScoreboardApi();
		if(plugin.getServer().getPlayer(receiver.getName()) != null){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	@SuppressWarnings("deprecation")
	public static void giveGems(OfflinePlayer receiver,int amount){
		int Gems = getGems(receiver)+amount;
		Coc.getPlugin().getDataBase().query("UPDATE Resources SET Gems="+Gems+" WHERE owner LIKE '"+receiver.getUniqueId()+"'");
		ScoreboardApi sb = new ScoreboardApi();
		if(plugin.getServer().getPlayer(receiver.getName()) != null){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	@SuppressWarnings("deprecation")
	public static void giveDarkElixir(OfflinePlayer receiver,int amount){
		int darkelixir = getDarkElixir(receiver)+amount;
		Coc.getPlugin().getDataBase().query("UPDATE Resources SET DarkElixir="+darkelixir+" WHERE owner LIKE '"+receiver.getUniqueId()+"'");
		ScoreboardApi sb = new ScoreboardApi();
		if(plugin.getServer().getPlayer(receiver.getName()) != null){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void takeGold(OfflinePlayer receiver,int amount){
		int gold = getGold(receiver)-amount;
		Coc.getPlugin().getDataBase().query("UPDATE Resources SET Gold="+gold+" WHERE owner LIKE '"+receiver.getUniqueId()+"'");
		ScoreboardApi sb = new ScoreboardApi();
		if(plugin.getServer().getPlayer(receiver.getName()) != null){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	@SuppressWarnings("deprecation")
	public static void takeElixir(OfflinePlayer receiver,int amount){
		int elixir = getElixir(receiver)-amount;
		Coc.getPlugin().getDataBase().query("UPDATE Resources SET Elixir="+elixir+" WHERE owner LIKE '"+receiver.getUniqueId()+"'");
		ScoreboardApi sb = new ScoreboardApi();
		if(plugin.getServer().getPlayer(receiver.getName()) != null){
		sb.setCurrencyBoard((Player)receiver);
		}
	}
	@SuppressWarnings("deprecation")
	public static void takeDarkElixir(OfflinePlayer receiver,int amount){
		int darkelixir = getDarkElixir(receiver)-amount;
		Coc.getPlugin().getDataBase().query("UPDATE Resources SET DarkElixir="+darkelixir+" WHERE owner LIKE '"+receiver.getUniqueId()+"'");
		ScoreboardApi sb = new ScoreboardApi();
		if(plugin.getServer().getPlayer(receiver.getName()) != null){
		sb.setCurrencyBoard((Player)receiver);
		}
		}
		@SuppressWarnings("deprecation")
		public static void takeGems(OfflinePlayer receiver,int amount){
			int Gems = getGems(receiver)-amount;
			Coc.getPlugin().getDataBase().query("UPDATE Resources SET Gems="+Gems+" WHERE owner LIKE '"+receiver.getUniqueId()+"'");
			ScoreboardApi sb = new ScoreboardApi();
			if(plugin.getServer().getPlayer(receiver.getName()) != null){
			sb.setCurrencyBoard((Player)receiver);
			}
	}
	public static void Take(Resource resource, OfflinePlayer player){
		if(resource instanceof Gold){
			takeGold(player,resource.getAmount());
		}
		else if(resource instanceof Elixir){
			takeElixir(player,resource.getAmount());
		}
		else if(resource instanceof Gem){
			takeGems(player,resource.getAmount());
		}	
		else if(resource instanceof DarkElixir){
			takeDarkElixir(player,resource.getAmount());
		}
	}
	public static void Give(Resource resource, OfflinePlayer player){
		if(resource instanceof Gold){
			giveGold(player,resource.getAmount());
		}
		else if(resource instanceof Elixir){
			giveElixir(player,resource.getAmount());
		}
		else if(resource instanceof Gem){
			giveGems(player,resource.getAmount());
		}
		else if(resource instanceof DarkElixir){
			giveDarkElixir(player,resource.getAmount());
		}
	}
	public static int getGems(OfflinePlayer player){
		ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Gems FROM Resources WHERE owner LIKE '"+player.getUniqueId()+"'");
		int gems = 0;
		try {
			gems = result.getInt("Gems");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gems;
	}
	public static int getGold(OfflinePlayer player){
		ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Gold FROM Resources WHERE owner LIKE '"+player.getUniqueId()+"'");
		int gold = 0;
		try {
			gold = result.getInt("Gold");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gold;
	}
	public static int getElixir(OfflinePlayer player){
		ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Elixir FROM Resources WHERE owner LIKE '"+player.getUniqueId()+"'");
		int elixir = 0;
		try {
			elixir = result.getInt("Elixir");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elixir;
	}
	public static int getDarkElixir(OfflinePlayer player){
		ResultSet result = Coc.getPlugin().getDataBase().query("SELECT DarkElixir FROM Resources WHERE owner LIKE '"+player.getUniqueId()+"'");
		int darkelixir = 0;
		try {
			darkelixir = result.getInt("DarkElixir");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return darkelixir;
		}
}
