package me.Aubli.RideMobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Filter;
import java.util.logging.Logger;

import me.Aubli.RideMobs.Entities.RideAbleEntityType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class RideThaMob extends JavaPlugin {
	
	public static double defaultspeed = 1.5;
	
	public static ArrayList<EntityType> allowedTypes;
	public static ArrayList<String> player;
	public static RideThaMob plugin;

	public static HashMap<UUID, Boolean> enableHorseFlying = new HashMap<UUID, Boolean>();
	public static boolean global = true;
	
	public static ItemStack dragonEgg;
	
	private static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "FlyingHorse" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		registerEvents();

		registerCommands();
		setupEntityList();		

		RideThaMob.player = new ArrayList<String>();
		
		RideAbleEntityType.registerHorse();
		
		Filter filter = new ConsoleFilter();
		Bukkit.getLogger().setFilter(filter);
		Logger.getLogger("Minecraft").setFilter(filter);
	}

	@Override
	public void onDisable() {}
	
	private void registerCommands() {
		getCommand("horsefly").setExecutor(new Commands());
	}

	private void registerEvents() {
		getServer().getPluginManager().registerEvents(new RideThaMobListener(),	plugin);
	}

	private void setupEntityList() {
		allowedTypes = new ArrayList<EntityType>();
		allowedTypes.add(EntityType.HORSE);
		allowedTypes.add(EntityType.PIG);
	}
	
	public static String getPrefix() {
		return prefix;
	}
   
}
