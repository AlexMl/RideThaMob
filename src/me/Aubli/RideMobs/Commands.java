package me.Aubli.RideMobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("Only for Players!");
			return true;
		}
		
		Player playerSender = (Player)sender;
		
		if(cmd.getName().equalsIgnoreCase("horsefly")) {
			
			if(args.length==0) {
				if(!RideThaMob.enableHorseFlying.containsKey(playerSender.getUniqueId())) {
					RideThaMob.enableHorseFlying.put(playerSender.getUniqueId(), false);
					playerSender.sendMessage(RideThaMob.getPrefix() + "Fliegen ist für dich nun deaktiviert!");
					return true;
				}else {
					if(!RideThaMob.enableHorseFlying.get(playerSender.getUniqueId())) {
						RideThaMob.enableHorseFlying.put(playerSender.getUniqueId(), true);
						playerSender.sendMessage(RideThaMob.getPrefix() + "Fliegen ist für dich aktiviert!");
						return true;
					}else {
						RideThaMob.enableHorseFlying.put(playerSender.getUniqueId(), false);
						playerSender.sendMessage(RideThaMob.getPrefix() + "Fliegen ist für dich nun deaktiviert!");
						return true;
					}
				}
			}else if(args.length==1) {
				if(args[0].equalsIgnoreCase("all")) {
					if(playerSender.hasPermission("horsefly.admin")) {
						
						if(RideThaMob.global) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								RideThaMob.enableHorseFlying.put(p.getUniqueId(), false);
								p.sendMessage(RideThaMob.getPrefix() + "Fliegen ist für dich nun deaktiviert!");
							}
							
							RideThaMob.global = false;
							playerSender.sendMessage(RideThaMob.getPrefix() + "Fliegen global deaktiviert!");
							return true;
						}else {
							for(Player p : Bukkit.getOnlinePlayers()) {
								RideThaMob.enableHorseFlying.put(p.getUniqueId(), true);
								p.sendMessage(RideThaMob.getPrefix() + "Fliegen ist für dich nun aktiviert!");
							}
							
							RideThaMob.global = true;
							playerSender.sendMessage(RideThaMob.getPrefix() + "Fliegen global aktiviert!");
							return true;
						}
					}else {
						playerSender.sendMessage(ChatColor.DARK_RED + "Du hast nicht die nötigen Rechte!");
						return true;
					}
				}
			}
			
			
		}
		return true;
	}
}
