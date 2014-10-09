package de.MiniDigger.RideThaMob;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.plugin.Plugin;

public class NyanTask implements Runnable {
	private Plugin pl;
	private int taskId;
	private Player p;

	public NyanTask(Plugin myPlugin) {
		this.pl = myPlugin;
	}

	public void start(Player p) {
		this.p = p;
		this.taskId = pl
				.getServer()
				.getScheduler()
				.scheduleSyncRepeatingTask(pl, this, 0L,
						RideThaMob.nyan_change_speed);
	}

	@SuppressWarnings("deprecation")
	public void run() {
		if (p.isInsideVehicle() && p.getVehicle().getType() == EntityType.SHEEP) {
			Sheep sheep = (Sheep) p.getVehicle();
			DyeColor color = sheep.getColor();
			Byte data = color.getDyeData();
			if (data == 0xF) {
				data = 0x0;
			} else {
				data++;
			}
			sheep.setColor(DyeColor.getByDyeData(data));
		} else {
			pl.getServer().getScheduler().cancelTask(this.taskId);
		}
	}
}
