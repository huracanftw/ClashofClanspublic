package me.crolemol.coc.arena.panels;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuildingShop {
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

}
