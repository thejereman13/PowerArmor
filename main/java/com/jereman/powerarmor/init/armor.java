package com.jereman.powerarmor.init;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class armor{
	
	
	
	public static void init(){
		final int powerarmorDamageReduction[] = {3,8,6,3};
		final int powerDurability = 100;
		final ArmorMaterial PowerArmorMaterial = EnumHelper.addArmorMaterial("PowerArmorMaterial", "powerarmor/textures/models/armor/powerarmor", powerDurability, powerarmorDamageReduction, 0);
	}
	
	public static void register(){
	}
	
	public static void registerRenders(){
		
	}
	
	
}
