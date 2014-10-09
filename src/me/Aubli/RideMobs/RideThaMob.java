package me.Aubli.RideMobs;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Filter;
import java.util.logging.Logger;

import me.Aubli.RideMobs.Entities.RideAbleEntityType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class RideThaMob extends JavaPlugin {
	public static String prefix;
	public static String cprefix;
	public static double defaultspeed;
	public static double maxspeed;
	public static int nyan_change_speed;
	public static ArrayList<String> speed;
	public static ArrayList<String> sneak;
	public static ArrayList<String> control;
	public static ArrayList<String> player;
	public static ArrayList<String> fly;
	public static ArrayList<EntityType> entity_blacklist;
	public static RideThaMob pl;
	public static boolean update;
	public static File file;
	public static boolean check_update;
	private String version = "(MC: 1.7.9)";

	public void onEnable() {
		pl = this;
		loadConfig();

		registerEvents();

		registerCommands();

		RideThaMob.prefix = ("[" + getDescription().getName() + "] ");
		RideThaMob.cprefix = (ChatColor.AQUA + "[" + ChatColor.RED
				+ getDescription().getName() + ChatColor.AQUA + "] " + ChatColor.RESET);

		setupEntityBlacklist();

		RideThaMob.speed = new ArrayList<String>();
		RideThaMob.sneak = new ArrayList<String>();
		RideThaMob.control = new ArrayList<String>();
		RideThaMob.player = new ArrayList<String>();
		RideThaMob.fly = new ArrayList<String>();

		// if (Bukkit.getVersion().contains(version)) {
		// RideAbleEntityType.registerEntities();
		// } else {
		try {
			RideAbleEntityType.registerEntities();
		} catch (Exception ex) {
			Bukkit.getConsoleSender()
					.sendMessage(
							ChatColor.YELLOW
									+ "[RideThaMob] WARINING:"
									+ ChatColor.RED
									+ "Failed to register the custom Entitys! You cant control the Entitys with the wasd keys now! Please make sure your are running the right Bukkit version("
									+ version
									+ ") if you want to use the wasd mode!");
			Bukkit.getConsoleSender().sendMessage(
					ChatColor.YELLOW + "[RideThaMob] WARINING:" + ChatColor.RED
							+ "You are running " + Bukkit.getVersion());
		}

		Filter filter = new ConsoleFilter();
		Bukkit.getLogger().setFilter(filter);
		Logger.getLogger("Minecraft").setFilter(filter);
	}

	public void onDisable() {
		RideAbleEntityType.unregisterEntities();
	}

	public void loadConfig() {
		if (!getConfig().contains("msg")) {
			saveDefaultConfig();
		}

		RideThaMob.defaultspeed = getConfig().getDouble("defaultspeed");
		RideThaMob.maxspeed = getConfig().getDouble("maxspeed");
		nyan_change_speed = getConfig().getInt("nyan_change_speed");
		try {
			RideThaMob.check_update = getConfig().getBoolean(
					"check_for_updates");
		} catch (Exception e) {
			RideThaMob.check_update = false;
			getConfig().set("check_for_updates", false);
		}
		new Lang();
	}

	private void registerCommands() {
		getCommand("RideThaMob").setExecutor(new Commands());
	}

	private void registerEvents() {
		getServer().getPluginManager().registerEvents(new RideThaMobListener(),
				pl);
	}

	private void setupEntityBlacklist() {
		entity_blacklist = new ArrayList<EntityType>();
		entity_blacklist.add(EntityType.DROPPED_ITEM);
		entity_blacklist.add(EntityType.ENDER_CRYSTAL);
		entity_blacklist.add(EntityType.ENDER_SIGNAL);
		entity_blacklist.add(EntityType.EXPERIENCE_ORB);
		entity_blacklist.add(EntityType.FIREBALL);
		entity_blacklist.add(EntityType.FISHING_HOOK);
		entity_blacklist.add(EntityType.FIREWORK);
		entity_blacklist.add(EntityType.ITEM_FRAME);
		entity_blacklist.add(EntityType.LIGHTNING);
		entity_blacklist.add(EntityType.PAINTING);
		entity_blacklist.add(EntityType.PRIMED_TNT);
		entity_blacklist.add(EntityType.SMALL_FIREBALL);
		entity_blacklist.add(EntityType.SPLASH_POTION);
		entity_blacklist.add(EntityType.THROWN_EXP_BOTTLE);
		entity_blacklist.add(EntityType.WEATHER);
		entity_blacklist.add(EntityType.WITHER_SKULL);
	}
}
