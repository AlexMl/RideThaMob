package me.Aubli.RideMobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

public class RideThaMobListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		// Drachen "Feuer" spucken lassen
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (e.getPlayer().getVehicle() != null)
				&& (e.getPlayer().getVehicle().getType() == EntityType.ENDER_DRAGON)) {
			EnderDragon dragon = (EnderDragon) e.getPlayer().getVehicle();
			dragon.launchProjectile(Fireball.class);
			// World w = dragon.getLocation().getWorld();
			// Entity ball = w.spawnEntity(dragon.getLocation(),
			// EntityType.FIREBALL);
			// ball.setVelocity(e.getPlayer().getVelocity());
			// Entity ball1 = w.spawnEntity(dragon.getLocation(),
			// EntityType.FIREBALL);
			// ball1.setVelocity(dragon.getVelocity());
		}
		// Riden ;D
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getPlayer().getVehicle() == null) {
			for (Entity en : e.getClickedBlock().getLocation().getWorld()
					.getEntities()) {
				if (en.getLocation().getX() == e.getClickedBlock()
						.getLocation().getX()
						&& en.getLocation().getZ() == e.getClickedBlock()
								.getLocation().getZ()) {
					ride(e.getPlayer(), en);
					break;
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if ((e.getEntity().getPassenger() != null)
				&& ((e.getEntity().getPassenger() instanceof Player))) {
			Player p = (Player) e.getEntity().getPassenger();
			if (p.hasPermission("ridethamob.god")) {
				e.setDamage(0.0);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getEntity().isInsideVehicle()) {
				if (e.getDamager().getEntityId() == e.getEntity().getVehicle()
						.getEntityId()) {
					e.setDamage(0.0);
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onTarget(EntityTargetLivingEntityEvent e) {
		if (e.getTarget().getType() == EntityType.PLAYER) {
			if (e.getTarget().isInsideVehicle()) {
				if (e.getEntity().getEntityId() == e.getTarget().getVehicle()
						.getEntityId()) {
					e.setCancelled(true);
					e.setTarget(null);
					if (e.getEntity() instanceof Creature) {
						Creature c = (Creature) e;
						c.setTarget(null);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent e) {
		if (RideThaMob.sneak.contains(e.getPlayer().getName())) {
			RideThaMob.sneak.remove(e.getPlayer().getName());
		} else {
			RideThaMob.sneak.add(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		double d = RideThaMob.defaultspeed;
		if (RideThaMob.speed.contains(e.getPlayer().getName())) {
			d = RideThaMob.maxspeed;
		}

		if ((p.getVehicle() != null)/*
									 * &&
									 * (RideThaMob.sneak.contains(p.getName()))
									 */
				&& (RideThaMob.fly.contains(p.getName()))
				&& (RideThaMob.player.contains(p.getName()))) {
			Entity v = p.getVehicle();
			Vector f = p.getEyeLocation().getDirection().multiply(d);
			v.setVelocity(f);
			v.teleport(new Location(v.getWorld(), v.getLocation().getX(), v
					.getLocation().getY(), v.getLocation().getZ(), p
					.getEyeLocation().getPitch(), p.getEyeLocation().getYaw()));
			p.setFallDistance(0.0F);
			v.setFallDistance(0.0F);
		}
	}

	public static void checkNearRideable(Player p) {
		RideThaMob.player.add(p.getName());
		List<Entity> l = new ArrayList<Entity>();
		for (int i = 1; i < (RideThaMob.pl.getConfig().getInt(
				"entity_check_radius") + 1); i++) {
			l = p.getNearbyEntities(i, i, i);
			if (!l.isEmpty()) {
				for (Entity e : l) {
					if (!RideThaMob.entity_blacklist.contains(e.getType())) {
						if (p.hasPermission("ridethamob.mob."
								+ e.getType().name())) {
							if (e.getPassenger() == null) {
								if (p.getPassenger() != null) {
									if (p.getPassenger().getEntityId() != e
											.getEntityId()) {
										ride(p, e);
										return;
									}
								} else {
									ride(p, e);
									return;
								}
							}
						}
					}
				}

			}
		}
		p.sendMessage(RideThaMob.cprefix
				+ Lang._(LangType.RIDE_NO_NEAR, RideThaMob.pl.getConfig()
						.getInt("entity_check_radius") + ""));
	}

	/**
	 * Reitet ein Entity
	 * 
	 * @param p
	 * @param e
	 */
	public static void ride(Player p, Entity e) {
		if (e instanceof Creature) {
			((Creature) e).setTarget(null);
		}

		if (e.getType() == EntityType.ENDER_DRAGON) {
			EnderDragon dr = (EnderDragon) e;
			dr.setPassenger(p);
			p.sendMessage(RideThaMob.cprefix + Lang._(LangType.RIDE_DRAGON));
			return;
		}
		if (e.getType() == EntityType.GIANT) {
			Giant g = (Giant) e;
			g.setPassenger(p);
			p.sendMessage(RideThaMob.cprefix + Lang._(LangType.RIDE_GIANT));
		}
		if (e.getType() == EntityType.PLAYER) {
			Player o = (Player) e;
			if ((p.hasPermission("ridethamob.player.*"))
					|| (p.hasPermission("ridethamob.player." + p.getName()))) {
				o.setPassenger(p);

				p.sendMessage(RideThaMob.cprefix
						+ Lang._(LangType.RIDE_PLAYER, o.getDisplayName()));
				return;
			}
			p.sendMessage(RideThaMob.cprefix
					+ Lang._(LangType.RIDE_PLAYER_NO_PERM, o.getDisplayName()));
			return;
		}

		e.setPassenger(p);

		p.sendMessage(RideThaMob.cprefix + Lang._(LangType.RIDE)
				+ e.getType().name());
	}

}
