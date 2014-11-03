package me.Aubli.RideMobs;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.util.Vector;

public class RideThaMobListener implements Listener {	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player eventPlayer = event.getPlayer();
		RideThaMob.enableHorseFlying.put(eventPlayer.getUniqueId(), true);
		eventPlayer.sendMessage(RideThaMob.getPrefix() + "Fliegen ist für dich nun aktiviert!");
	}
	
	
	@EventHandler
	public void onVehicleEnter(VehicleEnterEvent event) {
		
		if(event.getEntered() instanceof Player) {
			if(RideThaMob.allowedTypes.contains(event.getVehicle().getType())) {				
				Player eventPlayer = (Player)event.getEntered();
				ride(eventPlayer, event.getVehicle());
				return;
			}
		}
	}
	
	@EventHandler
	public void onVehicleExit(VehicleExitEvent event) {
		
		if(event.getExited() instanceof Player) {
			if(RideThaMob.allowedTypes.contains(event.getVehicle().getType())) {
				
				Player eventPlayer = (Player) event.getExited();
				RideThaMob.player.remove(eventPlayer.getName());
			}
		}
		
	}
	
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getEntity().isInsideVehicle()) {
				if (e.getDamager().getEntityId() == e.getEntity().getVehicle().getEntityId()) {
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
				if (e.getEntity().getEntityId() == e.getTarget().getVehicle().getEntityId()) {
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
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		double d = RideThaMob.defaultspeed;
		
		if ((p.getVehicle() != null) && RideThaMob.allowedTypes.contains(p.getVehicle().getType())){
			if(RideThaMob.enableHorseFlying.containsKey(p.getUniqueId())) {
				if(RideThaMob.enableHorseFlying.get(p.getUniqueId())==false) {
					return;
				}
			}
				
			if(RideThaMob.global && RideThaMob.player.contains(p.getName())) {
										
				Entity vehicle = p.getVehicle();
				Vector forward = p.getEyeLocation().getDirection().multiply(d);	
					
				//System.out.println(forward);
					
				vehicle.setVelocity(forward);
				p.setFallDistance(0.0F);
				vehicle.setFallDistance(0.0F);
				vehicle.teleport(p.getLocation(), TeleportCause.PLUGIN);			
			}					
		}
		
	}
	
	private static void ride(Player p, Entity e) {
		if (e instanceof Creature) {
			((Creature) e).setTarget(null);
		}		
		if(e.getType() == EntityType.HORSE) {
			Horse h = (Horse)e;
			if(h.getStyle()==Style.WHITE) {
				RideThaMob.player.add(p.getName());
				h.setTamed(true);
				h.setOwner(p);	
				return;			
			}
		}
	}

}
