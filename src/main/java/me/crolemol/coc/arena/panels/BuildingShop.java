package me.crolemol.coc.arena.panels;

import java.util.ArrayList;
import java.util.List;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.building.ArmyCamp;
import me.crolemol.coc.arena.building.BuildersHut;
import me.crolemol.coc.arena.building.DarkElixirDrill;
import me.crolemol.coc.arena.building.DarkElixirStorage;
import me.crolemol.coc.arena.building.ElixirCollector;
import me.crolemol.coc.arena.building.ElixirStorage;
import me.crolemol.coc.arena.building.GoldStorage;
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.RelativeBuilding;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.ResourceBuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.StorageBuildingSpecs;
import me.crolemol.coc.economy.Resources;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuildingShop implements Listener{
	public void OpenMainShopGUI(Player player){
		Inventory inv = Bukkit.createInventory(null, 27, "BuildingShop");
		
		ItemStack Defenses = new ItemStack(Material.IRON_CHESTPLATE);
		ItemMeta DefensesMeta = Defenses.getItemMeta();
		DefensesMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Defenses");
		Defenses.setItemMeta(DefensesMeta);
		
		ItemStack Army = new  ItemStack(Material.DIAMOND_SWORD);
		ItemMeta ArmyMeta = Army.getItemMeta();
		ArmyMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Army");
		Army.setItemMeta(ArmyMeta);
		
		ItemStack Decorations = new ItemStack(Material.FLOWER_POT_ITEM);
		ItemMeta DecorationMeta = Decorations.getItemMeta();
		DecorationMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Decorations");
		Decorations.setItemMeta(DecorationMeta);
		
		ItemStack Resources = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta ResourcesMeta = Resources.getItemMeta();
		ResourcesMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Resources");
		Resources.setItemMeta(ResourcesMeta);
		
		ItemStack Diamonds = new ItemStack(Material.DIAMOND);
		ItemMeta DiamondsMeta = Decorations.getItemMeta();
		DiamondsMeta.setDisplayName("");
		List<String> list = new ArrayList<String>();
		list.add("");
		DiamondsMeta.setLore(list);
		Decorations.setItemMeta(DiamondsMeta);
		
		ItemStack Shield = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta ShieldMeta = Shield.getItemMeta();
		ShieldMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Shield");
		Shield.setItemMeta(ShieldMeta);
		
		inv.setItem(0, Diamonds);
		inv.setItem(8, Diamonds);
		inv.setItem(9, Diamonds);
		inv.setItem(17, Diamonds);
		inv.setItem(18, Diamonds);
		inv.setItem(26, Diamonds);
		inv.setItem(7, Decorations);
		inv.setItem(19, Army);
		inv.setItem(25, Shield);
		inv.setItem(22, Defenses);
		inv.setItem(4, Resources);
		player.openInventory(inv);
		
		
		
	}
	private void openResourcesPanel(Player player){
		Inventory inv = Bukkit.createInventory(player, 18, "RecoursesBuildingShop");
		
		ItemStack Goldmine = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta GoldmineMeta = Goldmine.getItemMeta();
		GoldmineMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Goldmine");
		Goldmine.setItemMeta(GoldmineMeta);
		
		ItemStack GoldStorage = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta GoldStorageMeta = GoldStorage.getItemMeta();
		GoldStorageMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"GoldStorage");
		GoldStorage.setItemMeta(GoldStorageMeta);
		
		ItemStack BuildersHut = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta BuildersHutMeta = BuildersHut.getItemMeta();
		BuildersHutMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Builder's hut");
		BuildersHut.setItemMeta(BuildersHutMeta);
		
		ItemStack ElixirCollector = new ItemStack(Material.GHAST_TEAR);
		Enchantment myEnchantment = new EnchantmentWrapper(1);
		ElixirCollector.addUnsafeEnchantment(myEnchantment, 1);
		ItemMeta ElixirCollectorMeta = ElixirCollector.getItemMeta();
		ElixirCollectorMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Elixir Collector");
		ElixirCollector.setItemMeta(ElixirCollectorMeta);
		
		ItemStack ElixirStorage = new ItemStack(Material.POTION);
		ItemMeta ElixirStorageMeta = ElixirStorage.getItemMeta();
		ElixirStorageMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Elixir Storage");
		ElixirStorage.setItemMeta(ElixirStorageMeta);
		
		ItemStack DarkElixirStorage = new ItemStack(Material.COAL_BLOCK);
		ItemMeta DarkElixirStorageMeta = DarkElixirStorage.getItemMeta();
		DarkElixirStorageMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Dark Elixir Storage");
		DarkElixirStorage.setItemMeta(DarkElixirStorageMeta);
		
		ItemStack DarkElixirDrill = new ItemStack(Material.COAL);
		ItemMeta DarkElixirDrillMeta = DarkElixirDrill.getItemMeta();
		DarkElixirDrillMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Dark Elixir Drill");
		DarkElixirDrill.setItemMeta(DarkElixirDrillMeta);
		
		inv.setItem(1, Goldmine);
		inv.setItem(3, GoldStorage);
		inv.setItem(5, ElixirCollector);
		inv.setItem(7, ElixirStorage);
		inv.setItem(14, BuildersHut);
		inv.setItem(12, DarkElixirStorage);
		inv.setItem(10, DarkElixirDrill);
		player.openInventory(inv);
	}
	
	private void openArmyPanel(Player player){
		Inventory inv = Bukkit.createInventory(null, 18, "ArmyBuildingShop");
		
		ItemStack ArmyCamp = new ItemStack(Material.BED);
		ItemMeta ArmyCampMeta = ArmyCamp.getItemMeta();
		ArmyCampMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Army Camp");
		ArmyCamp.setItemMeta(ArmyCampMeta);
		
		ItemStack Barracks = new ItemStack(Material.WOOD_SWORD);
		ItemMeta BarracksMeta = Barracks.getItemMeta();
		BarracksMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Barracks");
		Barracks.setItemMeta(BarracksMeta);
		
		inv.setItem(1, ArmyCamp);
		inv.setItem(3, Barracks);
		player.openInventory(inv);
	}
	
	@EventHandler
	private void OnInventoryClick(InventoryClickEvent event){
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("BuildingShop")){
			MainShopClick(event);
		}else if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("RecoursesBuildingShop")){
			ResourceShopClick(event);
		}else if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("ArmyBuildingShop")){
			ArmyShopClick(event);
		}
	}
	private void MainShopClick(InventoryClickEvent event){
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case IRON_PICKAXE:
			((CommandSender) event.getWhoClicked()).sendMessage("ironPickaxe");
			event.getWhoClicked().closeInventory();
			openResourcesPanel((Player) event.getWhoClicked());
			break;
		case DIAMOND_SWORD:
			event.getWhoClicked().closeInventory();
			openArmyPanel((Player) event.getWhoClicked());
			break;
		default: event.getWhoClicked().closeInventory();
	}
	}
	private void ResourceShopClick(InventoryClickEvent event){
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			((CommandSender) event.getWhoClicked()).sendMessage("resourceshop");
			return;
		}
		TownhallLimit[] thl = TownhallLimit.values();
		Base base = Base.getBase((Player)event.getWhoClicked());
		Player player = (Player)event.getWhoClicked();
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(player);
		RelativeBuilding rb;

		switch(event.getCurrentItem().getType()){
		case GOLD_NUGGET:
			ResourceBuildingSpecs[] spec = new Goldmine(null,0).getBuildingSpecs();
			if(base.getAmountofBuilding("goldmine")>=thl[dataconf.getInt("townhall.1.level")-1].getMaxGoldmines()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of goldmines for this townhall level!");
				return;
			}
			if(Resources.getElixir(player) <= spec[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new Goldmine((Player) event.getWhoClicked(), 1));
			rb.putRelativeBuilding((Player)event.getWhoClicked());
			event.getWhoClicked().closeInventory();
			break;
		case GOLD_BLOCK:
			BuildingSpecs[] spec2 = new GoldStorage(null,0).getBuildingSpecs();
			if(base.getAmountofBuilding("golstorage")>=thl[dataconf.getInt("townhall.1.level")-1].maxgoldstorage){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of goldstorages for this townhall level!");
				return;
			}
			if(Resources.getElixir(player) <= spec2[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
				return;
			}
			rb = new RelativeBuilding(new GoldStorage((Player) event.getWhoClicked(), 1));
			rb.putRelativeBuilding((Player)event.getWhoClicked());
			event.getWhoClicked().closeInventory();
			break;
		case IRON_PICKAXE:	
			BuildersHut bh = new BuildersHut(null,0);
			if(base.getAmountofBuilding("buildershut") >= 5){
				event.getWhoClicked().closeInventory();
				player.sendMessage(ChatColor.RED+"[ClashofClans] You did reached the max number of Builder's huts!");
				return;
			}
			if(Resources.getGems((OfflinePlayer) event.getWhoClicked()) <= bh.getBuildingSpecs()[0].getUpgradePrice().getAmount()){
				((Player)event.getWhoClicked()).sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gems to build this building!");
				return;
			}
			rb = new RelativeBuilding(new BuildersHut((Player)event.getWhoClicked(), 1));
			rb.putRelativeBuilding((Player)event.getWhoClicked());

			Resources.Take(BuildersHut.getNextGemCost((Player) event.getWhoClicked()), player);
			event.getWhoClicked().closeInventory();
			break;
		case GHAST_TEAR:	
			ResourceBuildingSpecs[] spec4 = new ElixirCollector(null,0).getBuildingSpecs();
			if(base.getAmountofBuilding("elixircollector")>=thl[dataconf.getInt("townhall.1.level")-1].maxelixircollector){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Elixir Collectors for this townhall level!");
				return;
			}
			if(Resources.getGold(player) <= spec4[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gold to build this building!");
			return;
			}
			rb = new RelativeBuilding(new ElixirCollector(player, 1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case POTION: 
			StorageBuildingSpecs[] spec5 = new ElixirStorage(null,0).getBuildingSpecs();
			if(base.getAmountofBuilding("elixirstorage")>=thl[dataconf.getInt("townhall.1.level")-1].maxelixirstorage){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Elixir Storages for this townhall level!");
				return;
			}
			if(Resources.getGold(player) <= spec5[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gold to build this building!");
			return;
			}
			rb = new RelativeBuilding(new ElixirStorage(player, 1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case COAL_BLOCK:
			StorageBuildingSpecs[] spec6 = new DarkElixirStorage(null,0).getBuildingSpecs();
			if(base.getAmountofBuilding("darkelixirstorage")>=thl[dataconf.getInt("townhall.1.level")-1].getMaxDarkElixirStorage()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Dark Elixir Storages for this townhall level!");
				return;
			}
			if(Resources.getElixir(player) <= spec6[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new DarkElixirStorage(player, 1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case COAL:
			ResourceBuildingSpecs[] spec7 = new DarkElixirDrill(null,0).getBuildingSpecs();
			if(base.getAmountofBuilding("darkelixirstorage")>=thl[dataconf.getInt("townhall.1.level")-1].getMaxDarkElixirDrill()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Dark Elixir Drills for this townhall level!");
				return;
			}
			if(Resources.getElixir(player) <= spec7[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new DarkElixirDrill(player, 1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		default: event.getWhoClicked().closeInventory();
			
		}
		
	}
	private void ArmyShopClick(InventoryClickEvent event){
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		TownhallLimit[] thl = TownhallLimit.values();
		Base base = Base.getBase((Player)event.getWhoClicked());
		Player player = (Player)event.getWhoClicked();
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(player);
		RelativeBuilding rb;

		switch(event.getCurrentItem().getType()){
		case BED:
			ResourceBuildingSpecs[] spec = new Goldmine(null,0).getBuildingSpecs();
			if(base.getAmountofBuilding("armycamp")>=thl[dataconf.getInt("townhall.1.level")-1].getMaxGoldmines()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of army camps for this townhall level!");
				return;
			}
			if(Resources.getElixir(player) <= spec[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new ArmyCamp((Player) event.getWhoClicked(), 1));
			rb.putRelativeBuilding((Player)event.getWhoClicked());
			event.getWhoClicked().closeInventory();
			break;
		default: event.getWhoClicked().closeInventory();
			
		}
	}

}
