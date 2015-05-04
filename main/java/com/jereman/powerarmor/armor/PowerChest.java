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
	double upgradeAmount;
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
						//Determining what slot the card is in, setting the amount for execution to the NBT data for that slot
					if (upgradeString.equals(stack.getTagCompound().getString("SlotOne"))){
						this.upgradeAmount = stack.getTagCompound().getDouble("SlotOneAmount");
					}
					if (upgradeString.equals(stack.getTagCompound().getString("SlotTwo"))){
						this.upgradeAmount = stack.getTagCompound().getDouble("SlotTwoAmount");
					}
					if (upgradeString.equals(stack.getTagCompound().getString("SlotThree"))){
						this.upgradeAmount = stack.getTagCompound().getDouble("SlotThreeAmount");
					}
					if (upgradeString.equals(stack.getTagCompound().getString("SlotFour"))){
						this.upgradeAmount = stack.getTagCompound().getDouble("SlotFourAmount");
					}
					if (upgradeString.equals(stack.getTagCompound().getString("SlotFive"))){
						this.upgradeAmount = stack.getTagCompound().getDouble("SlotFiveAmount");
					}
					Console.println("Upgrade: " + upgradeString.substring(5));
					try {
						this.method = this.getClass().getMethod(upgradeString.substring(5), double.class, EntityPlayer.class);
					} catch (NoSuchMethodException e) {
						Console.println("Uhh Oh!!!: Someone didn't register a function for a card!");
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
					try {
						
						EntityPlayer playerIn = player;
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
	public void cardSpeed(double playerSpeed, EntityPlayer player){
		float actuallSpeed = ((float) playerSpeed / 10);
		player.capabilities.setPlayerWalkSpeed(actuallSpeed);
	}
	
		//CardJump Function
	public void cardJump(double jumpHeight, EntityPlayer player){
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
	
	public static void NBTUpgrades(String upgradeName, ItemStack stack, double amount){ //Set the NBT data for upgrade Amount
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = new NBTTagCompound();
		stack.getTagCompound().setDouble(upgradeName, amount);
		
	}
	
	public static void NBTUpgradeList(String slotNum, ItemStack stack, String upgrade){ //Set the NBT data for the name of the upgrades
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = new NBTTagCompound();
		stack.getTagCompound().setString(slotNum, upgrade);
		
	}

}
