package de.MiniDigger.RideThaMob.Entity;

import java.lang.reflect.Field;

import net.minecraft.server.v1_7_R3.EntityHuman;
import net.minecraft.server.v1_7_R3.EntityLiving;
import net.minecraft.server.v1_7_R3.EntitySheep;
import net.minecraft.server.v1_7_R3.World;
import de.MiniDigger.RideThaMob.RideThaMob;

public class RideAbleSheep extends EntitySheep {

	public RideAbleSheep(World world) {
		super(world);
	}

	@Override
	public void e(float sideMot, float forMot) {
		if (this.passenger == null || !(this.passenger instanceof EntityHuman)) {
			super.e(sideMot, forMot);
			this.W = 0.5F; // Make sure the entity can walk over half slabs,
							// instead of jumping
			return;
		}

		EntityHuman human = (EntityHuman) this.passenger;
		if (!RideThaMob.control.contains(human.getBukkitEntity().getName())) {
			// Same as before
			super.e(sideMot, forMot);
			this.W = 0.5F;
			return;
		}

		this.lastYaw = this.yaw = this.passenger.yaw;
		this.pitch = this.passenger.pitch * 0.5F;

		// Set the entity's pitch, yaw, head rotation etc.
		this.b(this.yaw, this.pitch); // https://github.com/Bukkit/mc-dev/blob/master/net/minecraft/server/Entity.java#L163-L166
		this.aO = this.aM = this.yaw;

		this.W = 1.0F; // The custom entity will now automatically climb up 1
						// high blocks

		sideMot = ((EntityLiving) this.passenger).bd * 0.5F;
		forMot = ((EntityLiving) this.passenger).be;

		if (forMot <= 0.0F) {
			forMot *= 0.25F; // Make backwards slower
		}
		sideMot *= 0.75F; // Also make sideways slower

		float speed = 0.35F; // 0.2 is the default entity speed. I made it
								// slightly faster so that riding is better than
								// walking
		this.i(speed); // Apply the speed
		super.e(sideMot, forMot); // Apply the motion to the entity
		try {
			Field jump = null;
			jump = EntityLiving.class.getDeclaredField("bc");
			jump.setAccessible(true);

			if (jump != null && this.onGround) { // Wouldn't want it jumping
													// while
													// on the ground would we?
				if (jump.getBoolean(this.passenger)) {
					double jumpHeight = 0.5D;
					this.motY = jumpHeight; // Used all the time in NMS for
											// entity jumping
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
