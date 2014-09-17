package me.crolemol.coc.arena.panels.buildingpanels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Buildingspecs;
import me.crolemol.coc.arena.building.DarkElixirDrill;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.ResourceBuildingSpecs;
import me.crolemol.coc.utils.PanelUtils;
import me.crolemol.coc.utils.TimetoGemCalc;

public class DarkElixirDrillPanel implements BuildingPanel{


	Coc plugin = Coc.getPlugin();
	DarkElixirDrill building;
	Inventory inv;
	boolean isdefault = true;
	
	public DarkElixirDrillPanel(DarkElixirDrill building){
		this.building = building;
		if(building == null){
			inv = null;
		}else{
			inv = this.getDefaultInventory();
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void Open(Player player) {
		if(isdefault == true){
			inv = this.getDefaultInventory();
		}
	if(plugin.getServer().getPlayer(player.getName()) != null){
		player.openInventory(inv);
	}
		
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}
	@Override
	public Inventory getDefaultInventory(){
		ResourceBuildingSpecs[] spec = building.getBuildingSpecs();
		FileConfiguration dataconf = plugin.getdataconffile(building.getOwner());
		Inventory inv2 = Bukkit.createInventory(null, 9, "Dark Elixir Drill");
		
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("Our Alchemists have finally figured a way to extract pure Dark Elixir,");
		list.add("the rarest form of the magickal substance.");
		if(building.getLevel() != 0){
		list.add("Level: "+building.getLevel());
		list.add("Health: "+ spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")-1].getHealth());
		list.add("Production per hour: "+ spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")-1].getProduction());
		list.add("Capacity: "+ spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")-1].getCapacity());
		}
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta);
		
		ItemStack Collect = new ItemStack(Material.GHAST_TEAR);
		ItemMeta CollectMeta = Collect.getItemMeta();
		Enchantment myEnchantment = new EnchantmentWrapper(1);
		Collect.addUnsafeEnchantment(myEnchantment, 1);
		CollectMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Collect");
		List<String> list4 = new ArrayList<String>();
		list4.add("Collectable dark elixir: " + building.getCollectable());
		CollectMeta.setLore(list4);
		Collect.setItemMeta(CollectMeta);
		
		ItemStack Upgrade = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta UpgradeMeta = Upgrade.getItemMeta();
		UpgradeMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Upgrade");
		List<String> list2 = new ArrayList<String>();
		if(!dataconf.contains(building.getBuildingName()+"."+building.getBuildingID()+".upgrade")){
		list2.add("Upgrade your dark elixir drill to");
		list2.add("increase it's production and capacity,");
		list2.add("Costs:");
		list2.add("Elixir: "+ spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradePrice().getAmount());
		list2.add("Time: " + PanelUtils.LongtoSimpleString(spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime()));
		}else{
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong(building.getBuildingName()+"."+building.getBuildingID()+".upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime();
			Long time3 = time2 - time1;
			list2.add("Time remain:");
			list2.add(PanelUtils.LongtoSimpleString(time3));
			
		}
		UpgradeMeta.setLore(list2);
		Upgrade.setItemMeta(UpgradeMeta);
		
		ItemStack Speed = new ItemStack(Material.EMERALD);
		ItemMeta SpeedMeta = Speed.getItemMeta();
		SpeedMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Speed up");
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		Long cal2 = dataconf.getLong(building.getBuildingName()+"."+building.getBuildingID()+".upgrade");
		Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
		int time2 = spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime();
		Long time3 = time2 - time1;
		List<String> list3 = new ArrayList<String>();
		list3.add("Speed up the building progress,");
		list3.add("Cost:");
		TimetoGemCalc calc = new TimetoGemCalc();
		list3.add(calc.Calc(time3*60)+" Gems");
		SpeedMeta.setLore(list3);
		Speed.setItemMeta(SpeedMeta);
		
		inv2.setItem(0, Info);
		if(building.getLevel() != 0){
		inv2.setItem(1, Collect);}
		if(dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")!=Buildingspecs.darkelixirdrill.getMaxLevel()){
		inv2.setItem(8, Upgrade);
		}
		if(dataconf.contains(building.getBuildingName()+"."+building.getBuildingID()+".upgrade")){
			inv2.setItem(7, Speed);
		}
		return inv2;
	}
	@Override
	public void setPanelInventory(Inventory inv) {
		isdefault = false;
		this.inv = inv;
		
	}
}
