package com.jereman.powerarmor.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import com.jereman.powerarmor.Reference;

public class PowerPants extends PowerBase{

	public PowerPants() {
		super(2);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
		return Reference.MOD_ID + ":textures/models/armor/powerarmor_layer_2.png";
	}
	
}
