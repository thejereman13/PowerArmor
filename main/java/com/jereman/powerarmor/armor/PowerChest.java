package com.jereman.powerarmor.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.jereman.powerarmor.ExtendedProperties;
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
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		super.onArmorTick(world, player, stack);
		ExtendedProperties props = ExtendedProperties.get(player); //NBT data for the player wearing the chestplate
		if (props.getChestPlate() == false){
			props.setChestPlate(true);
		}
	}
}
