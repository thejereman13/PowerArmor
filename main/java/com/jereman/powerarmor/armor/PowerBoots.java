package com.jereman.powerarmor.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

import com.jereman.powerarmor.ExtendedProperties;
import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.Reference;

public class PowerBoots extends PowerBase{

	public PowerBoots(){
		super(3);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
		return Reference.MOD_ID + ":textures/models/armor/powerarmor_layer_1.png";
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		super.onArmorTick(world, player, stack);
		ExtendedProperties props = ExtendedProperties.get(player); //NBT data for the player wearing the chestplate
		if (props.getBoots() == false){
			props.setBoots(true);
		}
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		ArmorProperties properties;
		if ((source == DamageSource.fall) && findAllUpgrades(armor, "cardFall")){
			properties= new ArmorProperties(2, 1, (int) (500 * (.2 * player.getCurrentArmor(0).getTagCompound().getDouble("FallDamage"))));
		}else{
			properties = new ArmorProperties(0, .25, 5);
		}
		return properties;
	}
}
