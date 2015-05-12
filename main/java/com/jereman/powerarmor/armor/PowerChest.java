package com.jereman.powerarmor.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.Reference;

public class PowerChest extends PowerBase{

	public PowerChest(){
		super(1);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
		return Reference.MOD_ID + ":textures/models/armor/powerarmor_layer_1.png";
	}
	
}
