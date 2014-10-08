package me.crolemol.coc.arena.panels;

import java.util.ArrayList;
import java.util.List;

import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.BuildingType;
import me.crolemol.coc.arena.building.AirDefense;
import me.crolemol.coc.arena.building.ArcherQueenAltar;
import me.crolemol.coc.arena.building.ArcherTower;
import me.crolemol.coc.arena.building.ArmyCamp;
import me.crolemol.coc.arena.building.BarbarianKingAltar;
import me.crolemol.coc.arena.building.Barracks;
import me.crolemol.coc.arena.building.BuildersHut;
import me.crolemol.coc.arena.building.Cannon;
import me.crolemol.coc.arena.building.DarkElixirDrill;
import me.crolemol.coc.arena.building.DarkElixirStorage;
import me.crolemol.coc.arena.building.ElixirCollector;
import me.crolemol.coc.arena.building.ElixirStorage;
import me.crolemol.coc.arena.building.GoldStorage;
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.HiddenTesla;
import me.crolemol.coc.arena.building.Mortar;
import me.crolemol.coc.arena.building.RelativeBuilding;
import me.crolemol.coc.arena.building.SpellFactory;
import me.crolemol.coc.arena.building.Townhall;
import me.crolemol.coc.arena.building.WizardTower;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.ResourceBuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.StorageBuildingSpecs;
import me.crolemol.coc.economy.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
	private void openResourcesShop(Player player){
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
	
	private void openArmyShop(Player player){
		Inventory inv = Bukkit.createInventory(null, 18, "ArmyBuildingShop");
		
		ItemStack ArmyCamp = new ItemStack(Material.BED);
		ItemMeta ArmyCampMeta = ArmyCamp.getItemMeta();
		ArmyCampMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Army Camp");
		ArmyCamp.setItemMeta(ArmyCampMeta);
		
		ItemStack Barracks = new ItemStack(Material.WOOD_SWORD);
		ItemMeta BarracksMeta = Barracks.getItemMeta();
		BarracksMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Barracks");
		Barracks.setItemMeta(BarracksMeta);
		
		ItemStack BarbarianKingAltar = new ItemStack(Material.GOLD_HELMET);
		ItemMeta BarbarianKingAltarMeta = BarbarianKingAltar.getItemMeta();
		BarbarianKingAltarMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Barbarian King");
		BarbarianKingAltar.setItemMeta(BarbarianKingAltarMeta);
		
		ItemStack ArcherQueenAltar = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta helmmeta = (LeatherArmorMeta) ArcherQueenAltar.getItemMeta();
		helmmeta.setColor(Color.BLACK);
		helmmeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Archer Queen");
		ArcherQueenAltar.setItemMeta(helmmeta);
		
		ItemStack SpellFactory = new ItemStack(Material.BREWING_STAND_ITEM);
		ItemMeta SpellFactoryMeta = SpellFactory.getItemMeta();
		SpellFactoryMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Spell Factory");
		SpellFactory.setItemMeta(SpellFactoryMeta);
		
		ItemStack Laboratory = new ItemStack(Material.CAULDRON_ITEM);
		ItemMeta LaboratoryMeta = Laboratory.getItemMeta();
		LaboratoryMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Laboratory");
		Laboratory.setItemMeta(LaboratoryMeta);
		
		inv.setItem(1, ArmyCamp);
		inv.setItem(3, Barracks);
		inv.setItem(5, SpellFactory);
		inv.setItem(7, Laboratory);
		inv.setItem(12, BarbarianKingAltar);
		inv.setItem(14, ArcherQueenAltar);
		player.openInventory(inv);
	}
	private void openDefensesShop(Player player){
		Inventory inv = Bukkit.createInventory(null, 27,"DefensesShop");
		
		ItemStack Cannon = new ItemStack(Material.FIREWORK_CHARGE);
		ItemMeta CannonMeta = Cannon.getItemMeta();
		CannonMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Cannon");
		Cannon.setItemMeta(CannonMeta);
		
		ItemStack ArcherTower = new ItemStack(Material.ARROW);
		ItemMeta ArcherTowerMeta = ArcherTower.getItemMeta();
		ArcherTowerMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"ArcherTower");
		ArcherTower.setItemMeta(ArcherTowerMeta);
		
		ItemStack Mortar = new ItemStack(Material.FIREBALL);
		ItemMeta MortarMeta = Mortar.getItemMeta();
		MortarMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Mortar");
		Mortar.setItemMeta(MortarMeta);
		
		ItemStack WizardTower = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta WizardTowerMeta = WizardTower.getItemMeta();
		WizardTowerMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Wizard Tower");
		WizardTower.setItemMeta(WizardTowerMeta);
		
		ItemStack AirDefense = new ItemStack(Material.FIREWORK);
		ItemMeta AirDefenseMeta = AirDefense.getItemMeta();
		AirDefenseMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Air Defense");
		AirDefense.setItemMeta(AirDefenseMeta);
		
		ItemStack HiddenTesla = new ItemStack(Material.TRIPWIRE_HOOK);
		ItemMeta HiddenTeslaMeta = HiddenTesla.getItemMeta();
		HiddenTeslaMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Hidden Tesla");
		HiddenTesla.setItemMeta(HiddenTeslaMeta);
		
		inv.setItem(0, Cannon);
		inv.setItem(2, ArcherTower);
		inv.setItem(6, Mortar);
		inv.setItem(9, AirDefense);
		inv.setItem(13, WizardTower);
		inv.setItem(18, HiddenTesla);
		
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
		}else if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("DefensesShop")){
			DefensesShopClick(event);
		}
	}
	private void MainShopClick(InventoryClickEvent event){
		event.setCancelled(true);
		switch(event.getCurrentItem().getType()){
		case IRON_PICKAXE:
			openResourcesShop((Player) event.getWhoClicked());
			break;
		case DIAMOND_SWORD:
			openArmyShop((Player) event.getWhoClicked());
			break;
		case IRON_CHESTPLATE:
			openDefensesShop((Player) event.getWhoClicked());
			break;
		case DIAMOND:
			break;
		default: event.getWhoClicked().closeInventory();
	}
	}
	private void ResourceShopClick(InventoryClickEvent event){
		event.setCancelled(true);
		TownhallLimit[] thl = TownhallLimit.values();
		Base base = Base.getBase((Player)event.getWhoClicked());
		Player player = (Player)event.getWhoClicked();
		RelativeBuilding rb;
		Townhall townhall = Townhall.getTownhall((OfflinePlayer) event.getWhoClicked());
		switch(event.getCurrentItem().getType()){
		case GOLD_NUGGET:
			ResourceBuildingSpecs[] spec = new Goldmine(0).getBuildingSpecs();
			if(base.getAmountofBuilding("goldmine")>=thl[townhall.getLevel()-1].getMaxGoldmines()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of goldmines for this townhall level!");
				return;
			}
			if(PlayerData.getElixir(player) <= spec[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new Goldmine(1));
			rb.putRelativeBuilding((Player)event.getWhoClicked());
			event.getWhoClicked().closeInventory();
			break;
		case GOLD_BLOCK:
			BuildingSpecs[] spec2 = new GoldStorage(0).getBuildingSpecs();
			if(base.getAmountofBuilding("golstorage")>=thl[townhall.getLevel()-1].getMaxGoldStrage()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of goldstorages for this townhall level!");
				return;
			}
			if(PlayerData.getElixir(player) <= spec2[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
				return;
			}
			rb = new RelativeBuilding(new GoldStorage(1));
			rb.putRelativeBuilding((Player)event.getWhoClicked());
			event.getWhoClicked().closeInventory();
			break;
		case IRON_PICKAXE:	
			BuildersHut bh = new BuildersHut(0);
			if(base.getAmountofBuilding("buildershut") >= 5){
				event.getWhoClicked().closeInventory();
				player.sendMessage(ChatColor.RED+"[ClashofClans] You did reached the max number of Builder's huts!");
				return;
			}
			if(PlayerData.getGems((OfflinePlayer) event.getWhoClicked()) <= bh.getBuildingSpecs()[0].getUpgradePrice().getAmount()){
				((Player)event.getWhoClicked()).sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gems to build this building!");
				return;
			}
			rb = new RelativeBuilding(new BuildersHut(1));
			rb.putRelativeBuilding((Player)event.getWhoClicked());

			PlayerData.Take(BuildersHut.getNextGemCost((Player) event.getWhoClicked()), player);
			event.getWhoClicked().closeInventory();
			break;
		case GHAST_TEAR:	
			ResourceBuildingSpecs[] spec4 = new ElixirCollector(0).getBuildingSpecs();
			if(base.getAmountofBuilding("elixircollector")>=thl[townhall.getLevel()-1].getMaxElixirCollector()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Elixir Collectors for this townhall level!");
				return;
			}
			if(PlayerData.getGold(player) <= spec4[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gold to build this building!");
			return;
			}
			rb = new RelativeBuilding(new ElixirCollector(1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case POTION: 
			StorageBuildingSpecs[] spec5 = new ElixirStorage(0).getBuildingSpecs();
			if(base.getAmountofBuilding("elixirstorage")>=thl[townhall.getLevel()-1].getMaxElixirStorage()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Elixir Storages for this townhall level!");
				return;
			}
			if(PlayerData.getGold(player) <= spec5[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gold to build this building!");
			return;
			}
			rb = new RelativeBuilding(new ElixirStorage(1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case COAL_BLOCK:
			StorageBuildingSpecs[] spec6 = new DarkElixirStorage(0).getBuildingSpecs();
			if(base.getAmountofBuilding("darkelixirstorage")>=thl[townhall.getLevel()-1].getMaxDarkElixirStorage()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Dark Elixir Storages for this townhall level!");
				return;
			}
			if(PlayerData.getElixir(player) <= spec6[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new DarkElixirStorage(1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case COAL:
			ResourceBuildingSpecs[] spec7 = new DarkElixirDrill(0).getBuildingSpecs();
			if(base.getAmountofBuilding("darkelixirstorage")>=thl[townhall.getLevel()-1].getMaxDarkElixirDrill()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Dark Elixir Drills for this townhall level!");
				return;
			}
			if(PlayerData.getElixir(player) <= spec7[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new DarkElixirDrill(1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		default: event.getWhoClicked().closeInventory();
			
		}
		
	}
	private void ArmyShopClick(InventoryClickEvent event){
		event.setCancelled(true);
		TownhallLimit[] thl = TownhallLimit.values();
		Base base = Base.getBase((Player)event.getWhoClicked());
		Player player = (Player)event.getWhoClicked();
		RelativeBuilding rb;
		Townhall townhall = Townhall.getTownhall((OfflinePlayer) event.getWhoClicked());

		switch(event.getCurrentItem().getType()){
		case BED:
			BuildingSpecs[] spec = new ArmyCamp(0).getBuildingSpecs();
			if(base.getAmountofBuilding("armycamp")>=thl[townhall.getLevel()-1].getMaxArmyCamp()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of army camps for this townhall level!");
				return;
			}
			if(PlayerData.getElixir(player) <= spec[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new ArmyCamp(1));
			rb.putRelativeBuilding((Player)event.getWhoClicked());
			event.getWhoClicked().closeInventory();
			break;
		case GOLD_HELMET: 
			BuildingSpecs[] spec2 = new BarbarianKingAltar(0).getBuildingSpecs();
			if(base.getAmountofBuilding("barbariankingaltar")>=thl[townhall.getLevel()-1].getMaxBarbarianKingAltar()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of barbarian kings!");
				return;
			}
			if(PlayerData.getDarkElixir(player) <= spec2[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough dark elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new BarbarianKingAltar(1));
			rb.putRelativeBuilding(player);
			event.getWhoClicked().closeInventory();
			break;
		case WOOD_SWORD: 
			BuildingSpecs[] spec3 = new Barracks(0).getBuildingSpecs();
			if(base.getAmountofBuilding("barracks")>=thl[townhall.getLevel()-1].getMaxBarracks()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of barracks!");
				return;
			}
			if(PlayerData.getElixir(player) <= spec3[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new Barracks(1));
			rb.putRelativeBuilding(player);
			event.getWhoClicked().closeInventory();
			break;
		case LEATHER_HELMET: 
			BuildingSpecs[] spec4 = new ArcherQueenAltar(0).getBuildingSpecs();
			player.sendMessage(base.getAmountofBuilding(BuildingType.ArcherQueenAltar.getName())+"");
			if(base.getAmountofBuilding(BuildingType.ArcherQueenAltar.getName())>=thl[townhall.getLevel()-1].getMaxArcherQueenAltar()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of archer queens!");
				return;
			}
			if(PlayerData.getDarkElixir(player) <= spec4[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough dark elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new ArcherQueenAltar(1));
			rb.putRelativeBuilding(player);
			event.getWhoClicked().closeInventory();
			break;
		case BREWING_STAND_ITEM: 
			if(base.getAmountofBuilding("spellfactory")>=thl[townhall.getLevel()-1].getMaxSpellFactory()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of spell factories!");
				return;
			}
			if(PlayerData.getElixir(player) <=  new SpellFactory(0).getBuildingSpecs()[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough elixir to build this building!");
			return;
			}
			rb = new RelativeBuilding(new SpellFactory(1));
			rb.putRelativeBuilding(player);
			event.getWhoClicked().closeInventory();
			break;
		default: event.getWhoClicked().closeInventory();
			
		}
	}

	private void DefensesShopClick(InventoryClickEvent event){
		event.setCancelled(true);
		TownhallLimit[] thl = TownhallLimit.values();
		Player player = (Player)event.getWhoClicked();
		Base base = Base.getBase(player);
		RelativeBuilding rb;
		Townhall townhall = Townhall.getTownhall(player);
		switch(event.getCurrentItem().getType()){
		case FIREWORK_CHARGE:
			BuildingSpecs[] spec7 = new Cannon(0).getBuildingSpecs();
			if(base.getAmountofBuilding("cannon")>=thl[townhall.getLevel()-1].getMaxCannon()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Cannons for this townhall level!");
				return;
			}
			if(PlayerData.getGold(player) <= spec7[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gold to build this building!");
			return;
			}
			rb = new RelativeBuilding(new Cannon(1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case ARROW: 
			BuildingSpecs[] spec8 = new ArcherTower(0).getBuildingSpecs();
			if(base.getAmountofBuilding("archertower")>=thl[townhall.getLevel()-1].getMaxArcherTower()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Archer Towers for this townhall level!");
				return;
			}
			if(PlayerData.getGold(player) <= spec8[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gold to build this building!");
			return;
			}
			rb = new RelativeBuilding(new ArcherTower(1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case FIREBALL:
			BuildingSpecs[] spec9 = new Mortar(0).getBuildingSpecs();
			if(base.getAmountofBuilding("mortar")>=thl[townhall.getLevel()-1].getMaxMortar()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Mortars for this townhall level!");
				return;
			}
			if(PlayerData.getGold(player) <= spec9[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gold to build this building!");
			return;
			}
			rb = new RelativeBuilding(new Mortar(1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case BLAZE_POWDER:
			BuildingSpecs[] spec10 = new WizardTower(0).getBuildingSpecs();
			if(base.getAmountofBuilding("wizardtower")>=thl[townhall.getLevel()-1].getMaxWizardTower()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Wizard Towers for this townhall level!");
				return;
			}
			if(PlayerData.getGold(player) <= spec10[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gold to build this building!");
			return;
			}
			rb = new RelativeBuilding(new WizardTower(1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case FIREWORK:
			BuildingSpecs[] spec11 = new AirDefense(0).getBuildingSpecs();
			if(base.getAmountofBuilding("airdefense")>=thl[townhall.getLevel()-1].getMaxAirDefense()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Air Defenses for this townhall level!");
				return;
			}
			if(PlayerData.getGold(player) <= spec11[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gold to build this building!");
			return;
			}
			rb = new RelativeBuilding(new AirDefense(1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		case TRIPWIRE_HOOK:
			BuildingSpecs[] spec12 = new HiddenTesla(0).getBuildingSpecs();
			if(base.getAmountofBuilding("hiddentesla")>=thl[townhall.getLevel()-1].getMaxHiddenTesla()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of Hidden Teslas for this townhall level!");
				return;
			}
			if(PlayerData.getGold(player) <= spec12[0].getUpgradePrice().getAmount()){
				player.sendMessage(ChatColor.RED+"[ClashofClans] You don't have enough gold to build this building!");
			return;
			}
			rb = new RelativeBuilding(new HiddenTesla(1));
			rb.putRelativeBuilding(player);
			player.closeInventory();
			break;
		default:
			event.getWhoClicked().closeInventory();;
			break;
		}
	}
}
