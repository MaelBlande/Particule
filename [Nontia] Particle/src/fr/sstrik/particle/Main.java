package fr.sstrik.particle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.sstrik.api.CustomInventory;
import fr.sstrik.api.ParticleList;

public class Main extends JavaPlugin implements Listener {

	
	private static Main main;
	public static Main getMain() {
		return main;
	}
	
	public String n_retour = "§c§lRetour";
	public ItemStack getItemRetour(){
		ArrayList<String> desc = new ArrayList<>();
		desc.clear();
		desc.add("§7(Clic droit)");
		
		return newItem(Material.ARROW, n_retour, desc, false);
	}
	
	public String n_fermer = "§4§lFermer";
	public ItemStack getItemFermer(){
		ArrayList<String> desc = new ArrayList<>();
		desc.clear();
		desc.add("§7(Clic droit)");
		
		return newItem(Material.BARRIER, n_fermer, desc, false);
	}
	
	
	
	
	@Override
	public void onEnable() {
		log("Plugin Particule by Sstrik lancé avec succès.");

		this.registerListeners();

		main = this;
		
		addMenu(new ParticleGUI());
		getCommand("particule").setExecutor(this);
		getCommand("p").setExecutor(this);
			
		InputStream is = this.getResource(this.playerFile.getName());
		if (is != null)
		{
		    this.playerC.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(is)));
		    this.playerC.options().copyDefaults(true);
		    this.savePlayerFile();
		}

	}
	
	
	@Override
	public void onDisable() {
		
		log("Plugin Particule by Sstrik éteint avec succès. Bonne journée.");
		
	}
	
	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		for (Listener l : listeners) {
			pm.registerEvents(l, this);
		}
	}
	Listener[] listeners = { this };
	

	
	public void log(String string){
		System.out.println(string);
	}
	
	
	

	public File playerFile = new File(getDataFolder(), "player.yml");
	public YamlConfiguration playerC = YamlConfiguration.loadConfiguration(this.playerFile);

	public void savePlayerFile(){
		try{
			this.playerC.save(this.playerFile);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	

	public String prefix = "§6Nontia §f» §f";

	public String go = "§7§l> ";
	
	

	public void pSound(Location loc, Sound son){
		loc.getWorld().playSound(loc, son, 1, 1);
	}
	public void pSound(Location loc, Sound son, int volume){
		pSound(loc, son, volume, 1);
	}
	public void pSound(Location loc, Sound son, int volume, float pitch){
		loc.getWorld().playSound(loc, son, volume, pitch);
	}
	
	public void pSound(Entity p, Sound son){
		pSound(p, son, 1, 1);
	}
	public void pSound(Entity p, Sound son, int volume){
		pSound(p, son, volume, 1);
	}
	public void pSound(Entity p, Sound son, int volume, float pitch){
		if(p != null){
			p.getWorld().playSound(p.getLocation(), son, volume, pitch);
		}
	}
	
	public void pOnlySound(Player p, Sound son){
		pOnlySound(p, son, 1, 1);
	}
	public void pOnlySound(Player p, Sound son, int volume){
		pOnlySound(p, son, volume, 1);
	}
	public void pOnlySound(Player p, Sound son, int volume, float pitch){
		if(p != null){
			p.playSound(p.getLocation(), son, volume, pitch);
		}
	}

	public void clearEffect(Entity p) {
		if(p instanceof LivingEntity){
			for (PotionEffect effect : ((LivingEntity) p).getActivePotionEffects())
		        ((LivingEntity) p).removePotionEffect(effect.getType());
		}
	}
	
	
	public void pEffect(Entity p, PotionEffectType PotionEffectType, int duration, int amplified, boolean ambiant, boolean HideParticles){
		EntityType entT = p.getType();
		if(!(entT == EntityType.MINECART || entT == EntityType.ITEM_FRAME || entT == EntityType.PAINTING || entT == EntityType.BOAT || entT == EntityType.ENDER_PEARL || entT == EntityType.ARROW || entT == EntityType.PRIMED_TNT || entT == EntityType.FALLING_BLOCK)){
			if(!p.isDead()){
				((LivingEntity) p).addPotionEffect(new PotionEffect(PotionEffectType, duration, amplified - 1, ambiant, HideParticles));
			}
		}
		
	}
	public void pEffect(Entity p, PotionEffectType PotionEffectType, int duration, int amplified){
		pEffect(p, PotionEffectType, duration, amplified, true, false);
	}
	
	
	
	
	public String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
	
	

	
	
	public Map<Class<? extends CustomInventory>, CustomInventory > registeredMenus = new HashMap<>();
	
	@EventHandler
	public void onClickMenu(InventoryClickEvent e){
	
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack current = e.getCurrentItem();
		
		InventoryView invv = e.getView();
		String invN = invv.getTitle();
		
		if(current == null) return;
		
		if(current.hasItemMeta() && current.getItemMeta().hasDisplayName()){
			String name = current.getItemMeta().getDisplayName();
			
			if(name.equals(n_fermer)){
				e.setCancelled(true);
				p.closeInventory();
				return;
			}
			
			registeredMenus.values().stream()
			.filter(menu -> invN.equalsIgnoreCase(menu.name()))
			.forEach(menu -> {
				e.setCancelled(true);
				menu.onClick(p, inv, current, e.getSlot());
			});
		}
	}
	
	private void addMenu(CustomInventory m){
		this.registeredMenus.put(m.getClass(), m);
	}

	public void open(Player p, Class<? extends CustomInventory> gClass){
		if(!this.registeredMenus.containsKey(gClass)) return;

		CustomInventory menu = this.registeredMenus.get(gClass);
		Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
		menu.contents(p, inv);
		p.openInventory(inv);
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


	
	
	
	
	
	public ParticleList getParticleOfP(String pN){
		if(playerC.contains(pN)){
			return ParticleList.valueOf(playerC.getString(pN));
		}
		return ParticleList.NONE;
	}
	public ParticleList getParticleOfP(Player p){
		return getParticleOfP(p.getName());
	}
	
	public void setParticleP(String pN, ParticleList part){
		playerC.set(pN, part.name());
		savePlayerFile();
	}
	
	public void removeParticle(String pN){
		setParticleP(pN, ParticleList.NONE);
	}
	
	public boolean hasParticule(Player p){
		if(playerC.contains(p.getName())){
			if(getParticleOfP(p) != ParticleList.NONE){
				return true;
			}
		}
		return false;
	}
	
	

	@EventHandler
	private void onWalk(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();

		if(hasParticule(p)){

				
				ParticleList part = getParticleOfP(p);

				Location loc = p.getLocation();
				
				new BukkitRunnable() {
					public void run() {
					
						for(double t = 0; t < 1.8; t = t + 0.2){
							part.display(0.1, 0, loc.clone().add(0, t, 0), 1);
						}
						
					}
				}.runTaskLater(main, 3);
				
		
		}
	}
	
	
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("particule") ||
			label.equalsIgnoreCase("p") ){
			
			if(sender instanceof Player){
				Player p = (Player) sender;
				open(p, ParticleGUI.class);
				pOnlySound(p, Sound.WOOD_CLICK);
			}
			
		}
		
		
		
		return false;
	}
	
	
	
	
	

	
}
