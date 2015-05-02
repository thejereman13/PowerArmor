package com.jereman.powerarmor.armor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import scala.Console;

import com.jereman.powerarmor.ExtendedProperties;
import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.Reference;
import com.jereman.powerarmor.init.JeremanItems;
import com.jereman.powerarmor.items.CardJump;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class PowerChest extends net.minecraft.item.ItemArmor{
	private Method method;

	public PowerChest(){
		super(Main.PowerArmorMaterial, 0, 1);
		this.setMaxDamage(1000);
		
	}
	
	//Checks for information about the chestplate being worn
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		if (!world.isRemote){
			ExtendedProperties props = ExtendedProperties.get(player); //NBT data for the player wearing the chestplate
		if (player.getCurrentArmor(2).getItem() == JeremanItems.powerChest){
			
			final String[] upgradeList = {stack.getTagCompound().getString("SlotOne"), stack.getTagCompound().getString("SlotTwo"), stack.getTagCompound().getString("SlotThree"), stack.getTagCompound().getString("SlotFour"), stack.getTagCompound().getString("SlotFive")};
			for (String upgradeString: upgradeList){
				if (upgradeString.equals("none")){
					
				}else if (!upgradeString.equals("none")){
				Console.println("Upgrade: " + upgradeString.substring(5));
				try {
					this.method = this.getClass().getMethod(upgradeString.substring(5), int.class, EntityPlayer.class);
				} catch (NoSuchMethodException e) {
					Console.println("Uhh Oh!!!: Someone didn't register a function for a card!");
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				try {
					//Setup code to get the Upgrade Amount
					EntityPlayer playerIn = player;
					int upgradeAmount = 3; //Remove This Later
					method.invoke(this, upgradeAmount, playerIn); //Passing data to the correct function for each upgrade in use
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				}
			}
			

			if (props.getChestPlate() == false){ //setup for ability reversal upon armor removal in FMLEventHandler
				props.setChestPlate(true);
				Console.println("Chestplate is being worn");
			}
			
		}
	}
	}
	
	//CardSpeed function
	public void cardSpeed(int playerSpeed, EntityPlayer player){
		float actuallSpeed = ((float) playerSpeed / 10);
		Console.println("Speed Card being executed, speed is: " + actuallSpeed);
		player.capabilities.setPlayerWalkSpeed(actuallSpeed);
	}
	
	//CardJump Function
	public void cardJump(int jumpHeight, EntityPlayer player){
		//Put stuff here, probably for the eventhandler
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
		Console.println("Setting NBT Upgrade Amount as: " + amount);
		
	}
	
	public static void NBTUpgradeList(String slotNum, ItemStack stack, String upgrade){ //NBT stuff
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = new NBTTagCompound();
		stack.getTagCompound().setString(slotNum, upgrade);
		//Console.println("Setting NBT Data as: " + upgrade);
		
	}

}
