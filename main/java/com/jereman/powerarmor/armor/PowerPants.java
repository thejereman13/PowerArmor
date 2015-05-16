package com.jereman.powerarmor.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.jereman.powerarmor.ExtendedProperties;
import com.jereman.powerarmor.Reference;

public class PowerPants extends PowerBase{

	public PowerPants() {
		super(2);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
		return Reference.MOD_ID + ":textures/models/armor/powerarmor_layer_2.png";
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		super.onArmorTick(world, player, stack);
		ExtendedProperties props = ExtendedProperties.get(player); //NBT data for the player wearing the chestplate
		if (props.getLeggings() == false){
			props.setLeggings(true);
		}
		if (stack.hasTagCompound()){
			final String[] upgradeList = {stack.getTagCompound().getString("SlotOne"), stack.getTagCompound().getString("SlotTwo"), stack.getTagCompound().getString("SlotThree"), stack.getTagCompound().getString("SlotFour"), stack.getTagCompound().getString("SlotFive")};
			for (String upgradeString: upgradeList){
				if (upgradeString.equals("none") || upgradeString.equals(null)){
					//None
				}else{
					if (stack.getTagCompound().getBoolean("HasStep") == false && upgradeString.substring(5).equals("cardJump")){
						stack.getTagCompound().setBoolean("HasStep", true);
					}else{
						stack.getTagCompound().setBoolean("HasStep", false);
					}
					if (stack.getTagCompound().getBoolean("HasJump") == false && upgradeString.substring(5).equals("cardJump")){
						stack.getTagCompound().setBoolean("HasJump", true);
					}else{
						stack.getTagCompound().setBoolean("HasJump", false);
					}
				}
			}
		}
	}
}
