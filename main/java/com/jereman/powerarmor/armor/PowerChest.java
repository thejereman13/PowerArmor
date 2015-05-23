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

public class PowerChest extends PowerBase{

	public PowerChest(){
		super(1);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
		if (!PowerBase.findAllUpgrades(stack, "cardArmorInvis")){
			return Reference.MOD_ID + ":textures/models/armor/powerarmor_layer_1.png";
		}else{
			return Reference.MOD_ID + ":textures/models/armor/powerarmor_layer_10.png";
		}
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		super.onArmorTick(world, player, stack);
		ExtendedProperties props = ExtendedProperties.get(player); //NBT data for the player wearing the chestplate
		if (props.getChestPlate() == false){
			props.setChestPlate(true);
		}
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		ArmorProperties properties;
		if ((source == DamageSource.onFire || source == DamageSource.inFire || source == DamageSource.lava) && findAllUpgrades(armor, "cardFire")){
			properties= new ArmorProperties(2, 1, (int) (200 * (.2 * player.getCurrentArmor(2).getTagCompound().getDouble("FireDamage"))));
		}else if (findAllUpgrades(armor, "cardProtection")){
			properties = new ArmorProperties(1,1, (int) (250 * (.2 * player.getCurrentArmor(2).getTagCompound().getDouble("GenericDamage"))));
		}else{
			properties = new ArmorProperties(0, .25, 5);
		}
		return properties;
	}
}
