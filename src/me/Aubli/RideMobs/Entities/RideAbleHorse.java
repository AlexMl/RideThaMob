package me.Aubli.RideMobs.Entities;

import me.Aubli.RideMobs.RideThaMob;
import net.minecraft.server.v1_7_R4.EntityHorse;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.World;

public class RideAbleHorse extends EntityHorse {

	public RideAbleHorse(World world) {
		super(world);
	}
		
	@Override
	public void e(float sideMot, float forMot) {		
				
		if(this.passenger!=null && (this.passenger instanceof EntityHuman)) {
			EntityHuman human = (EntityHuman)this.passenger;
			if(RideThaMob.enableHorseFlying.get(human.getUniqueID())!=null) {
				if(RideThaMob.enableHorseFlying.get(human.getUniqueID())==false || RideThaMob.global==false) {
					super.e(sideMot, forMot);
					this.W = 0.5F;
					return;
				}
			}
		}		
		
		if (this.passenger == null || !(this.passenger instanceof EntityHuman)) {
			super.e(sideMot, forMot);
			this.W = 0.5F; // Make sure the entity can walk over half slabs, instead of jumping
			return;
		}		
		
		this.lastYaw = this.yaw = this.passenger.yaw;
		this.pitch = this.passenger.pitch * 0.5F;

		// this.b() sets the entity's pitch, yaw, head rotation etc.
		this.b(this.yaw, this.pitch);
		this.aO = this.aM = this.yaw;

		//this.W = 1.0F; // The custom entity will now automatically climb up 1 high blocks
		super.e(sideMot, forMot); // Apply the motion to the entity
	}
}
