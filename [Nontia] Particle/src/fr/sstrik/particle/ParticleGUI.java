package fr.sstrik.particle;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.sstrik.api.CustomInventory;
import fr.sstrik.api.ParticleList;

public class ParticleGUI extends CustomInventory {

	private Main main(){
		return Main.getMain();
	}
	
	@Override
	public String name() {
		return "§b§nParticules";
	}

	@Override
	public void contents(Player p, Inventory inv) {
		
		inv.setItem(8, main().getItemFermer());

		ArrayList<String> desc = new ArrayList<>();
		desc.clear();
		desc.add(" ");
		desc.add("§7Retire la particule");
		desc.add("§7active.");
		inv.setItem(13, newItem(Material.REDSTONE_BLOCK, "§c§nRetirer", desc, false));
		
		String pN = p.getName();
		
		inv.setItem(19, particle(p, ParticleList.FLAME, "§6§lFlammes", Material.BLAZE_POWDER, 0, "Petit brasier", "dans le Hub.")); 
		inv.setItem(20, particle(p, ParticleList.REDSTONE, "§4§lRedStone", Material.REDSTONE, 0, "Montrez votre talent", "en Redstone.")); 
		inv.setItem(21, particle(p, ParticleList.HEART, "§d§lCoeurs", Material.RED_ROSE, 0, "Un vrai lover", "ce "+pN+" ;)")); 
		
		inv.setItem(22, particle(p, ParticleList.CLOUD, "§f§lNuages", Material.INK_SACK, 15, "Un "+pN+" à", "réaction !")); 
		
		inv.setItem(23, particle(p, ParticleList.VILLAGER_HAPPY, "§a§lÉtoiles vertes", Material.EMERALD, 0, "Le pouvoir de", "la nature.")); 
		inv.setItem(24, particle(p, ParticleList.SPELL_WITCH, "§5§lMagie", Material.POTION, 8261, "Le magicien", "du Hub.")); 
		inv.setItem(25, particle(p, ParticleList.SLIME, "§a§lSlime", Material.SLIME_BALL, 0, "Ne comporte pas les", "propriétés du Slime.")); 
		
		
		
		inv.setItem(30, particle(p, ParticleList.SPELL_MOB, "§8§lSpirales Noires", Material.POTION, 16428, "Passez du côté", "obscur de la force.")); 
		inv.setItem(31, particle(p, ParticleList.CRIT, "§e§lCoups Critiques", Material.GOLD_SWORD, 0, "Le PvP est", "votre jeu préféré !")); 
		inv.setItem(32, particle(p, ParticleList.NOTE, "§3§lMusique", Material.NOTE_BLOCK, 0, "Le musicien", "du Hub.")); 
		
		
		
	}

	@Override
	public void onClick(Player p, Inventory inv, ItemStack current, int slot) {
	
		if(ItemHasName(current)){
			
			ItemMeta itM = current.getItemMeta();
			String name = itM.getDisplayName();
			
			if(name.equals(main().n_fermer)){
				p.closeInventory();
				
			}else if(name.equalsIgnoreCase("§c§nRetirer")){
				
				if(main().hasParticule(p)){
					main().removeParticle(p.getName());
					p.sendMessage(main().prefix+"§cVous avez retiré votre particule.");
					main().pOnlySound(p, Sound.ENDERMAN_TELEPORT);
					p.closeInventory();
				}
				
			}else if(itM.hasLore()){
				
				ParticleList part = ParticleList.valueOf( itM.getLore().get(0).split("§k")[1] );
				
				if(main().getParticleOfP(p) == part){
					p.sendMessage(main().prefix+"§cVous avez déjà activé cette particule.");
	
				}else{
					
					main().setParticleP(p.getName(), part);
					main().pOnlySound(p, Sound.ORB_PICKUP);
					p.sendMessage(main().prefix+"§fVous avez activé la particule §7: "+name+"§f.");
					p.closeInventory();
				}	
			}
		}
		
	}

	@Override
	public int getSize() {
		return 5 * 9;
	}
	
	
	private ItemStack particle(Player p, ParticleList part, String name, Material m, int meta, String... list){
		
		boolean enchant = false;
		
		ArrayList<String> desc = new ArrayList<>();
		desc.clear();
		
		desc.add("§0§k"+part.name());
		for(String l : list){
			desc.add("§7"+l);
		}
		desc.add(" ");
		
		if(main().getParticleOfP(p) == part){
				
			enchant = true;
				
			desc.add("§a§l[Activé]");
			
		}else{
			
			desc.add("§fClique pour activer.");
			
		}

		return newItem(m, 1, meta, name, desc, enchant);
		
	}
	
	
	
	

}
