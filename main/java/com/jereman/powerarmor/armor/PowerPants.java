package com.jereman.powerarmor.armor;

import scala.Console;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

import com.jereman.powerarmor.ExtendedProperties;
import com.jereman.powerarmor.Reference;

public class PowerPants extends PowerBase{
	public PowerPants() {
		super(2, 5);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
		if (!PowerBase.findAllUpgrades(stack, "cardArmorInvis")){
			return Reference.MOD_ID + ":textures/models/armor/powerarmor_layer_2.png";
		}else{
			return Reference.MOD_ID + ":textures/models/armor/powerarmor_layer_20.png";
		}
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		super.onArmorTick(world, player, stack);
		ExtendedProperties props = ExtendedProperties.get(player); //NBT data for the player wearing the chestplate
		if (props.getLeggings() == false){
			props.setLeggings(true);
		}
		if (stack.hasTagCompound()){
			if (findAllUpgrades(stack, "cardStep")){
				stack.getTagCompound().setBoolean("HasStep", true);
			}else if (findAllUpgrades(stack, "cardStep") == false){
				stack.getTagCompound().setBoolean("HasStep", false);
			}
			if (findAllUpgrades(stack, "cardJump")){
				stack.getTagCompound().setBoolean("HasJump", true);
			}else if (findAllUpgrades(stack, "cardJump") == false){
				stack.getTagCompound().setBoolean("HasJump", false);
			}
		}
	}
	
	
}
