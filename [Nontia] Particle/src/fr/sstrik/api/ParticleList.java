package fr.sstrik.api;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.sstrik.api.particle.ParticleEffect;

public enum ParticleList {

	EXPLOSION_NORMAL(ParticleEffect.EXPLOSION_NORMAL),

    EXPLOSION_LARGE(ParticleEffect.EXPLOSION_LARGE),

    EXPLOSION_HUGE(ParticleEffect.EXPLOSION_HUGE),

    FIREWORKS_SPARK(ParticleEffect.FIREWORKS_SPARK),

    WATER_BUBBLE(ParticleEffect.WATER_BUBBLE),

    WATER_SPLASH(ParticleEffect.WATER_SPLASH),

    WATER_WAKE(ParticleEffect.WATER_WAKE),

    SUSPENDED(ParticleEffect.SUSPENDED),

    @Deprecated
    SUSPENDED_DEPTH(ParticleEffect.SUSPENDED_DEPTH),

    CRIT(ParticleEffect.CRIT),

    CRIT_MAGIC(ParticleEffect.CRIT_MAGIC),

    SMOKE_NORMAL(ParticleEffect.SMOKE_NORMAL),

    SMOKE_LARGE(ParticleEffect.SMOKE_LARGE),

    SPELL(ParticleEffect.SPELL),

    SPELL_INSTANT(ParticleEffect.SPELL_INSTANT),

    SPELL_MOB(ParticleEffect.SPELL_MOB),

    SPELL_MOB_AMBIENT(ParticleEffect.SPELL_MOB_AMBIENT),

    SPELL_WITCH(ParticleEffect.SPELL_WITCH),

    DRIP_WATER(ParticleEffect.DRIP_WATER),

    DRIP_LAVA(ParticleEffect.DRIP_LAVA),

    VILLAGER_HAPPY(ParticleEffect.VILLAGER_HAPPY),

    VILLAGER_ANGRY(ParticleEffect.VILLAGER_ANGRY),

    TOWN_AURA(ParticleEffect.TOWN_AURA),

    NOTE(ParticleEffect.NOTE),

    PORTAL(ParticleEffect.PORTAL),

    ENCHANTMENT_TABLE(ParticleEffect.ENCHANTMENT_TABLE),

    FLAME(ParticleEffect.FLAME),

    LAVA(ParticleEffect.LAVA),

    CLOUD(ParticleEffect.CLOUD),

    REDSTONE(ParticleEffect.REDSTONE),

    SNOWBALL(ParticleEffect.SNOWBALL),

    @Deprecated
    SNOW_SHOVELL(ParticleEffect.SNOW_SHOVEL),

    SLIME(ParticleEffect.SLIME),

    HEART(ParticleEffect.HEART),

    BARRIER(ParticleEffect.BARRIER),

    ITEM_CRACK(ParticleEffect.ITEM_CRACK),

    BLOCK_CRACK(ParticleEffect.BLOCK_CRACK),

    BLOCK_DUST(ParticleEffect.BLOCK_DUST),

    WATER_DROP(ParticleEffect.WATER_DROP),

    MOB_APPEARANCE(ParticleEffect.MOB_APPEARANCE),

    
    NONE(null),
    
    

    @Deprecated
    FOOTSTEP(ParticleEffect.FOOTSTEP),

    @Deprecated
    ITEM_TAKE(ParticleEffect.ITEM_TAKE)
    ;


    private final ParticleEffect name;

	ParticleList(ParticleEffect name) {
        this.name = name;

    }
	
	public ParticleEffect getName(){
		return name;
	}
	
	
	public void display(double X, double Y, double Z, double speed, Location loc, int amount){
		getName().display((float) X, (float) Y, (float) Z, (float) speed, amount, loc, 2000);
	}
	
	public void display(double A, double speed, Location loc, int amount){
		getName().display((float) A, (float) A, (float) A, (float) speed, amount, loc, 2000);
	}
	
	public void display(Vector dir, float speed, Location center){
		int amount = 0;
		getName().display((float) dir.getX(), (float) dir.getY(), (float) dir.getZ(), speed, amount, center, 2000);
	}
	
}
