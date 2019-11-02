package fr.sstrik.api;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomInventory {

	public abstract String name();
	
	public abstract void contents(Player p, Inventory inv);
	
	public abstract void onClick(Player p, Inventory inv, ItemStack current, int slot);
	
	public abstract int getSize();
	

	public boolean ItemHasName(ItemStack current){
		if(current == null || current.getItemMeta() == null || current.getItemMeta().getDisplayName() == null){
			return false;
		}
		return true;
	}
	
	public ItemStack newItem(Material m, int n, int meta, String name, ArrayList<String> array, boolean enchant){
		ItemStack item = new ItemStack(m, n, (short) meta);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(name);
		if(array != null){
			itemM.setLore(array);
		}
		if(enchant == true){
			itemM.addEnchant(Enchantment.DURABILITY, 10, true);
			itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			itemM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		}
		item.setItemMeta(itemM);
		
		return item;
	}
	public ItemStack newItem(Material m, String name, ArrayList<String> array, boolean enchant){
		return newItem(m, 1, 0, name, array, enchant);
	}

	
	
}
