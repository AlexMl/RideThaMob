package de.MiniDigger.RideThaMob.Entity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_7_R3.BiomeBase;
import net.minecraft.server.v1_7_R3.BiomeMeta;
import net.minecraft.server.v1_7_R3.EntityBat;
import net.minecraft.server.v1_7_R3.EntityBlaze;
import net.minecraft.server.v1_7_R3.EntityCaveSpider;
import net.minecraft.server.v1_7_R3.EntityChicken;
import net.minecraft.server.v1_7_R3.EntityCow;
import net.minecraft.server.v1_7_R3.EntityCreeper;
import net.minecraft.server.v1_7_R3.EntityEnderDragon;
import net.minecraft.server.v1_7_R3.EntityEnderman;
import net.minecraft.server.v1_7_R3.EntityGhast;
import net.minecraft.server.v1_7_R3.EntityGiantZombie;
import net.minecraft.server.v1_7_R3.EntityHorse;
import net.minecraft.server.v1_7_R3.EntityInsentient;
import net.minecraft.server.v1_7_R3.EntityIronGolem;
import net.minecraft.server.v1_7_R3.EntityMagmaCube;
import net.minecraft.server.v1_7_R3.EntityMushroomCow;
import net.minecraft.server.v1_7_R3.EntityOcelot;
import net.minecraft.server.v1_7_R3.EntityPig;
import net.minecraft.server.v1_7_R3.EntityPigZombie;
import net.minecraft.server.v1_7_R3.EntitySheep;
import net.minecraft.server.v1_7_R3.EntitySilverfish;
import net.minecraft.server.v1_7_R3.EntitySkeleton;
import net.minecraft.server.v1_7_R3.EntitySlime;
import net.minecraft.server.v1_7_R3.EntitySnowman;
import net.minecraft.server.v1_7_R3.EntitySpider;
import net.minecraft.server.v1_7_R3.EntitySquid;
import net.minecraft.server.v1_7_R3.EntityTypes;
import net.minecraft.server.v1_7_R3.EntityVillager;
import net.minecraft.server.v1_7_R3.EntityWitch;
import net.minecraft.server.v1_7_R3.EntityWither;
import net.minecraft.server.v1_7_R3.EntityZombie;

import org.bukkit.entity.EntityType;

public enum RideAbleEntityType {
	BAT("Bat", 65, EntityType.BAT, EntityBat.class, RideAbleBat.class), //
	BLAZE("Blaze", 61, EntityType.BLAZE, EntityBlaze.class, RideAbleBlaze.class), //
	CAVE_SPIDER("Cave Spider", 59, EntityType.CAVE_SPIDER,
			EntityCaveSpider.class, RideAbleCaveSpider.class), //
	CHICKEN("Chicken", 93, EntityType.CHICKEN, EntityChicken.class,
			RideAbleChicken.class), //
	COW("Cow", 92, EntityType.COW, EntityCow.class, RideAbleCow.class), //
	CREEPER("Creeper", 50, EntityType.CREEPER, EntityCreeper.class,
			RideAbleCreeper.class), //
	ENDER_DRAGON("Enderdragon", 63, EntityType.ENDER_DRAGON,
			EntityEnderDragon.class, RideAbleEnderDragon.class), //
	ENDERMAN("Enderman", 58, EntityType.ENDERMAN, EntityEnderman.class,
			RideAbleEnderman.class), //
	GHAST("Ghast", 56, EntityType.GHAST, EntityGhast.class, RideAbleGhast.class), //
	GIANT("Giant", 53, EntityType.GIANT, EntityGiantZombie.class,
			RideAbleGiant.class), //
	HORSE("Horse", 100, EntityType.HORSE, EntityHorse.class,
			RideAbleHorse.class), //
	IRON_GOLEM("Iron Golem", 99, EntityType.IRON_GOLEM, EntityIronGolem.class,
			RideAbleIronGolem.class), //
	MAGMA_CUBE("Magma Cube", 62, EntityType.MAGMA_CUBE, EntityMagmaCube.class,
			RideAbleMagmaCube.class), //
	MUSHROOM_COW("Mushroom Cow", 96, EntityType.MUSHROOM_COW,
			EntityMushroomCow.class, RideAbleMushroomCow.class), //
	OCELOT("Ocelot", 98, EntityType.OCELOT, EntityOcelot.class,
			RideAbleOcelot.class), //
	PIG("Pig", 90, EntityType.PIG, EntityPig.class, RideAblePig.class), //
	PIG_ZOMBIE("Pig Zombie", 57, EntityType.PIG_ZOMBIE, EntityPigZombie.class,
			RideAblePigZombie.class), //
	// PLAYER("Player", 1, EntityType.PLAYER, EntityHuman.class,
	// RideAblePlayer.class), //
	SHEEP("Sheep", 91, EntityType.SHEEP, EntitySheep.class, RideAbleSheep.class), //
	SILVERFISH("Silverfish", 60, EntityType.SILVERFISH, EntitySilverfish.class,
			RideAbleSilverfish.class), //
	SKELETON("Skeleton", 51, EntityType.SKELETON, EntitySkeleton.class,
			RideAbleSkeleton.class), //
	SLIME("Slime", 55, EntityType.SLIME, EntitySlime.class, RideAbleSlime.class), //
	SNOW_MAN("Snowman", 97, EntityType.SNOWMAN, EntitySnowman.class,
			RideAbleSnowman.class), //
	SPIDER("Spider", 52, EntityType.SPIDER, EntitySpider.class,
			RideAbleSpider.class), //
	SQUID("Squid", 94, EntityType.SQUID, EntitySquid.class, RideAbleSquid.class), //
	VILLAGER("Villager", 120, EntityType.VILLAGER, EntityVillager.class,
			RideAbleVillager.class), //
	WITCH("Witch", 66, EntityType.WITCH, EntityWitch.class, RideAbleWitch.class), //
	WITHER("Wither", 64, EntityType.WITHER, EntityWither.class,
			RideAbleWither.class), //
	// WOLF("Wolf", 95, EntityWolf.class, RideAbleWolf.class), //
	ZOMBIE("Zombie", 54, EntityType.ZOMBIE, EntityZombie.class,
			RideAbleZombie.class);

	private String name;
	private int id;
	private EntityType entityType;
	private Class<? extends EntityInsentient> nmsClass;
	private Class<? extends EntityInsentient> customClass;

	private RideAbleEntityType(String name, int id, EntityType entityType,
			Class<? extends EntityInsentient> nmsClass,
			Class<? extends EntityInsentient> customClass) {
		this.name = name;
		this.id = id;
		this.entityType = entityType;
		this.nmsClass = nmsClass;
		this.customClass = customClass;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public Class<? extends EntityInsentient> getNMSClass() {
		return nmsClass;
	}

	public Class<? extends EntityInsentient> getCustomClass() {
		return customClass;
	}

	/**
	 * Register our entities.
	 */
	public static void registerEntities() {
		for (RideAbleEntityType entity : values())
			a(entity.getCustomClass(), entity.getName(), entity.getID());

		// BiomeBase#biomes became private.
		BiomeBase[] biomes;
		try {
			biomes = (BiomeBase[]) getPrivateStatic(BiomeBase.class, "biomes");
		} catch (Exception exc) {
			// Unable to fetch.
			return;
		}
		for (BiomeBase biomeBase : biomes) {
			if (biomeBase == null)
				break;

			// This changed names from J, K, L and M.
			for (String field : new String[] { "as", "at", "au", "av" })
				try {
					Field list = BiomeBase.class.getDeclaredField(field);
					list.setAccessible(true);
					@SuppressWarnings("unchecked")
					List<BiomeMeta> mobList = (List<BiomeMeta>) list
							.get(biomeBase);

					// Write in our custom class.
					for (BiomeMeta meta : mobList)
						for (RideAbleEntityType entity : values())
							if (entity.getNMSClass().equals(meta.b))
								meta.b = entity.getCustomClass();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * Unregister our entities to prevent memory leaks. Call on disable.
	 */
	@SuppressWarnings("rawtypes")
	public static void unregisterEntities() {
		for (RideAbleEntityType entity : values()) {
			// Remove our class references.
			try {
				((Map) getPrivateStatic(EntityTypes.class, "d")).remove(entity
						.getCustomClass());
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				((Map) getPrivateStatic(EntityTypes.class, "f")).remove(entity
						.getCustomClass());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (RideAbleEntityType entity : values())
			try {
				// Unregister each entity by writing the NMS back in place of
				// the custom class.
				a(entity.getNMSClass(), entity.getName(), entity.getID());
			} catch (Exception e) {
				e.printStackTrace();
			}

		// Biomes#biomes was made private so use reflection to get it.
		BiomeBase[] biomes;
		try {
			biomes = (BiomeBase[]) getPrivateStatic(BiomeBase.class, "biomes");
		} catch (Exception exc) {
			// Unable to fetch.
			return;
		}
		for (BiomeBase biomeBase : biomes) {
			if (biomeBase == null)
				break;

			// The list fields changed names but update the meta regardless.
			for (String field : new String[] { "as", "at", "au", "av" })
				try {
					Field list = BiomeBase.class.getDeclaredField(field);
					list.setAccessible(true);
					@SuppressWarnings("unchecked")
					List<BiomeMeta> mobList = (List<BiomeMeta>) list
							.get(biomeBase);

					// Make sure the NMS class is written back over our custom
					// class.
					for (BiomeMeta meta : mobList)
						for (RideAbleEntityType entity : values())
							if (entity.getCustomClass().equals(meta.b))
								meta.b = entity.getNMSClass();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * A convenience method.
	 * 
	 * @param clazz
	 *            The class.
	 * @param f
	 *            The string representation of the private static field.
	 * @return The object found
	 * @throws Exception
	 *             if unable to get the object.
	 */
	@SuppressWarnings("rawtypes")
	private static Object getPrivateStatic(Class clazz, String f)
			throws Exception {
		Field field = clazz.getDeclaredField(f);
		field.setAccessible(true);
		return field.get(null);
	}

	/*
	 * Since 1.7.2 added a check in their entity registration, simply bypass it
	 * and write to the maps ourself.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void a(Class paramClass, String paramString, int paramInt) {
		try {
			((Map) getPrivateStatic(EntityTypes.class, "c")).put(paramString,
					paramClass);
			((Map) getPrivateStatic(EntityTypes.class, "d")).put(paramClass,
					paramString);
			((Map) getPrivateStatic(EntityTypes.class, "e")).put(
					Integer.valueOf(paramInt), paramClass);
			((Map) getPrivateStatic(EntityTypes.class, "f")).put(paramClass,
					Integer.valueOf(paramInt));
			((Map) getPrivateStatic(EntityTypes.class, "g")).put(paramString,
					Integer.valueOf(paramInt));
		} catch (Exception exc) {
			// Unable to register the new class.
		}
	}
}