package me.Aubli.RideMobs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("ridethamob")) {
			if ((sender instanceof Player)) {
				Player p = (Player) sender;
				if (args.length != 0) {
					if (args.length == 1) {
						// Speed Command
						if (args[0].equalsIgnoreCase("speed")) {
							if (!p.hasPermission("ridethamob.speed")) {
								p.sendMessage(RideThaMob.cprefix
										+ Lang._(LangType.SPEEDMODE_NO_PERM));
								return true;
							}
							if (!RideThaMob.speed.contains(p.getName())) {
								p.sendMessage(RideThaMob.cprefix
										+ Lang._(LangType.SPEEDMODE_ACTIVATED));
								RideThaMob.speed.add(p.getName());
							} else {
								p.sendMessage(RideThaMob.cprefix
										+ Lang._(LangType.SPEEDMODE_DEACTIVATED));
								RideThaMob.speed.remove(p.getName());
							}
						} else
						// Reload Command
						if (args[0].equalsIgnoreCase("reload")) {
							if (p.hasPermission("ridethamob.reload")) {
								RideThaMob.pl.reloadConfig();
								RideThaMob.pl.loadConfig();
								new Lang();
								p.sendMessage(RideThaMob.cprefix
										+ Lang._(LangType.CONFIG_RELOADED));
							} else {
								p.sendMessage(RideThaMob.cprefix
										+ Lang._(LangType.NO_PERM));
								return true;
							}

						} else
						// Control Command
						if (args[0].equalsIgnoreCase("control")) {
							if (p.hasPermission("ridethamob.control")) {
								if (!RideThaMob.control.contains(p.getName())) {
									p.sendMessage(RideThaMob.cprefix
											+ Lang._(LangType.CONTROLMODE_ACTIVATED));
									RideThaMob.control.add(p.getName());
								} else {
									p.sendMessage(RideThaMob.cprefix
											+ Lang._(LangType.CONTROLMODE_DEACTIVATED));
									RideThaMob.control.remove(p.getName());
								}
							} else {
								p.sendMessage(RideThaMob.cprefix
										+ Lang._(LangType.CONTROLMODE_NO_PERM));
							}

						} else // Nyan Command
						if (args[0].equalsIgnoreCase("nyan")) {
							if (p.hasPermission("ridethamob.nyan")) {
								if (p.isInsideVehicle()
										&& p.getVehicle().getType() == EntityType.SHEEP) {
									NyanTask task = new NyanTask(RideThaMob.pl);
									task.start(p);
								} else {
									p.sendMessage(RideThaMob.cprefix
											+ Lang._(LangType.NYAN_NO_SHEEP));
								}

							} else {
								p.sendMessage(RideThaMob.cprefix
										+ Lang._(LangType.NYAN_NO_PERM));
							}
						} else  // Fly command
						if (args[0].equalsIgnoreCase("fly")) {
							if (p.hasPermission("ridethamob.fly")) {
								if (!RideThaMob.fly.contains(p.getName())) {
									p.sendMessage(RideThaMob.cprefix
											+ Lang._(LangType.FLYMODE_ACTIVATED));
									RideThaMob.fly.add(p.getName());
								} else {
									p.sendMessage(RideThaMob.cprefix
											+ Lang._(LangType.FLYMODE_DEACTIVATED));
									RideThaMob.fly.remove(p.getName());
								}
							} else {
								p.sendMessage(RideThaMob.cprefix
										+ Lang._(LangType.NO_PERM));
							}
						} else {
							// command not found
							p.sendMessage(RideThaMob.cprefix
									+ Lang._(LangType.COMMAND_NOT_FOUND));
						}

					} else {
						p.sendMessage(RideThaMob.cprefix
								+ Lang._(LangType.TOO_MANY_ARGS));
					}

					return true;
				}
				// aufsteigen
				if (p.getVehicle() == null) {
					RideThaMobListener.checkNearRideable(p);
				}
				// Aussteigen
				else {
					RideThaMob.player.remove(p.getName());
					p.getVehicle().eject();
					p.sendMessage(RideThaMob.cprefix
							+ Lang._(LangType.RIDE_HOP_OFF));
				}
			}
		}
		return true;
	}
}
