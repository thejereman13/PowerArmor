package com.jereman.powerarmor.armor;

import scala.Console;

import com.jereman.powerarmor.ExtendedProperties;
import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.Reference;
import com.jereman.powerarmor.init.JeremanItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class PowerChest extends net.minecraft.item.ItemArmor{
	public PowerChest(){
		super(Main.PowerArmorMaterial, 0, 1);
		this.setMaxDamage(1000);
		
	}
	
	//Checks for information about the chestplate being worn
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		if (!world.isRemote){
			ExtendedProperties props = ExtendedProperties.get(player); //NBT data for the player wearing the chestplate
		if (player.getCurrentArmor(2).getItem() == JeremanItems.powerChest){
			if (props.getChestPlate() == false){ //setup for ability reversal upon armor removal
				props.setChestPlate(true);
				Console.println("Chestplate is being worn"); 							//CHAT
				
			}
			if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("SpeedUpgrade") != 0){ //Speed Upgrade Stuff
			if (stack.getTagCompound().hasKey("SpeedUpgrade")){
				if (stack.getTagCompound().getInteger("SpeedUpgrade") == 1){
					player.capabilities.setPlayerWalkSpeed(.3f);
				}
			}
			}else{
				player.capabilities.setPlayerWalkSpeed(.1f);
			}
			
			/*if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("JumpUpgrade") != 0){ //Jump Upgrade Stuff
				
			} */
		}
	}
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
		return Reference.MOD_ID + ":textures/models/armor/powerarmor_layer_1.png";
	}
	
	@Override
	public boolean isDamageable(){
		return false;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack armor, ItemStack stack){
		return false;
	}
	
	public static void NBTUpgrades(String upgradeName, ItemStack stack, int amount){ //NBT stuff
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = new NBTTagCompound();
		stack.getTagCompound().setInteger(upgradeName, amount);
		Console.println("Setting NBT Data");
		
	}
	
	public static void NBTUpgradeList(String slotNum, ItemStack stack, String upgrade){ //NBT stuff
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = new NBTTagCompound();
		stack.getTagCompound().setString(slotNum, upgrade);
		Console.println("Setting NBT Data as: " + upgrade);
		
	}

}
