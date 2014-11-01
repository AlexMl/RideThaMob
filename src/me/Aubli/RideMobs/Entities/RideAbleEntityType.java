package me.Aubli.RideMobs.Entities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_7_R4.BiomeBase;
import net.minecraft.server.v1_7_R4.BiomeMeta;
import net.minecraft.server.v1_7_R4.EntityHorse;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityTypes;

import org.bukkit.entity.EntityType;

public enum RideAbleEntityType {	
	HORSE("EntityHorse", 100, EntityType.HORSE, RideAbleHorse.class);
	
	private String name;
	private int id;
	private EntityType entityType;
	private Class<? extends EntityInsentient> customClass;

	private RideAbleEntityType(String name, int id, EntityType entityType, Class<? extends EntityInsentient> customClass) {
		this.name = name;
		this.id = id;
		this.entityType = entityType;
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
	
	public Class<? extends EntityInsentient> getCustomClass() {
		return customClass;
	}	
	
	@SuppressWarnings("unchecked")
    public static void registerEntity(String name, int id, Class<? extends EntityInsentient> customClass) {
    	try {
    		System.out.println("r: " + name + " ," + id + " ," + customClass);
    		List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
    		for (Field f : EntityTypes.class.getDeclaredFields()) {
    			if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
    				f.setAccessible(true);
    				dataMaps.add((Map<?, ?>) f.get(null));
    			}
    		}
    
    		((Map<Class<? extends EntityInsentient>, String>) dataMaps.get(1)).put(customClass, name);
    		((Map<Class<? extends EntityInsentient>, Integer>) dataMaps.get(3)).put(customClass, id);
    
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void registerHorse(){	
		try{			
			((Map) getPrivateStatic(EntityTypes.class, "c")).put(HORSE.getName(), HORSE.getCustomClass());
			((Map) getPrivateStatic(EntityTypes.class, "d")).put(HORSE.getCustomClass(),HORSE.getName());
			((Map) getPrivateStatic(EntityTypes.class, "e")).put(Integer.valueOf(HORSE.getID()), HORSE.getCustomClass());
			((Map) getPrivateStatic(EntityTypes.class, "f")).put(HORSE.getCustomClass(),Integer.valueOf(HORSE.getID()));
			((Map) getPrivateStatic(EntityTypes.class, "g")).put(HORSE.getName(),Integer.valueOf(HORSE.getID()));			
		}catch (Exception e){
			e.printStackTrace();
		}
	     
		for (BiomeBase biomeBase : BiomeBase.getBiomes()){
			if (biomeBase == null){
				break;
			}
	         
			for (String field : new String[]{"as", "at", "au", "av"}){
				try{
					Field list = BiomeBase.class.getDeclaredField(field);
					list.setAccessible(true);
			
					List<BiomeMeta> mobList = (List<BiomeMeta>) list.get(biomeBase);
					
					for (BiomeMeta meta : mobList){						
						if (EntityHorse.class.equals(meta.b)){
							meta.b = HORSE.getCustomClass();
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	
	@SuppressWarnings("rawtypes")
	private static Object getPrivateStatic(Class clazz, String f) throws Exception {
		Field field = clazz.getDeclaredField(f);
		field.setAccessible(true);
		return field.get(null);
	}
	
}