package com.jereman.powerarmor.armor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import scala.Console;

import com.jereman.powerarmor.ExtendedProperties;
import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.Reference;
import com.jereman.powerarmor.init.JeremanItems;
import com.jereman.powerarmor.items.*;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PowerBase extends net.minecraft.item.ItemArmor implements ISpecialArmor{
	private Method method;
	double upgradeAmount;
	int upgradeNumber;
	public PowerBase(int armorSlot, int upgradeNumber){
		super(Main.PowerArmorMaterial, 3, armorSlot);
		this.upgradeNumber = upgradeNumber;
	}
	
	//Checks for information about the chestplate being worn
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		if (!world.isRemote){
			if (stack.hasTagCompound()){
			
			final String[] upgradeList = {stack.getTagCompound().getString("SlotOne"), stack.getTagCompound().getString("SlotTwo"), stack.getTagCompound().getString("SlotThree"), stack.getTagCompound().getString("SlotFour"), stack.getTagCompound().getString("SlotFive")};
			for (String upgradeString: upgradeList){
				if (!upgradeString.equals("none") && stack.getTagCompound().hasKey("SlotOne")){
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
					Item upgradeItem = GameRegistry.findItem("powerarmor", upgradeString.substring(5));
					try {
						this.method = upgradeItem.getClass().getMethod("Upgrade", double.class, EntityPlayer.class);
					} catch (NoSuchMethodException e) {
						Console.println("Uhh Oh!!!: Someone didn't register a function for a card!");
					} catch (Exception e) {
					}
					try {
						Object upgradeClass = upgradeItem.getClass();
						EntityPlayer playerIn = player;
						method.invoke(upgradeClass, upgradeAmount, playerIn); //Passing data to the correct function for each upgrade in use
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}else {
					//None
				}
			}
		}
	}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5){ //Setting default NBT Data
		if (stack.hasTagCompound()){
				stack.getTagCompound().setInteger("JeremanUpgradeNumber", this.upgradeNumber);
		}else if (stack.hasTagCompound() == false){
			stack.setTagCompound(new NBTTagCompound());
		}
	}
	
	public static boolean findAllUpgrades(ItemStack stack, String upgradeCheck){
		final String[] upgradeList = {stack.getTagCompound().getString("SlotOne"), stack.getTagCompound().getString("SlotTwo"), stack.getTagCompound().getString("SlotThree"), stack.getTagCompound().getString("SlotFour"), stack.getTagCompound().getString("SlotFive")};
		for (String upgradeString: upgradeList){
			if (stack.getTagCompound().hasKey("SlotOne")){
				if (upgradeString != null && !upgradeString.equals("none")){
					if (upgradeString.substring(5).equals(upgradeCheck)){
						return true;
					}
				}
			}
		}
		return false;
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
	
	public static void NBTUpgradeLimit(String upgradeName, ItemStack stack, double amount){ //Set the NBT data for upgrade Amount
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

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		//Need to override in each Armor class for functionalitys
		ArmorProperties properties = new ArmorProperties(0, .25, 1);
		return properties;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 4;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		//Armor Does not take natural damage, will remain empty until Power system is in place
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced){
		int upgradeNum = 0;
		if (stack.hasTagCompound()){
			upgradeNum = stack.getTagCompound().getInteger("JeremanUpgradeNumber");
			tooltip.add(EnumChatFormatting.GOLD + "Upgrade Capacity: " + upgradeNum);
		}
		tooltip.add(EnumChatFormatting.LIGHT_PURPLE + "Hold Ctrl for installed upgrades");
		if (GuiScreen.isCtrlKeyDown()){
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("SlotOne") && stack.getTagCompound().hasKey("JeremanUpgradeNumber")){
				tooltip.remove(2);
				tooltip.add(EnumChatFormatting.DARK_AQUA + "Installed Upgrades:");
				if (upgradeNum > 0 && !stack.getTagCompound().getString("SlotOne").equals("none")){
					tooltip.add(EnumChatFormatting.DARK_GREEN + "Slot One: " + stack.getTagCompound().getString("SlotOne").substring(9));
				}else if (upgradeNum > 0 && stack.getTagCompound().getString("SlotOne").equals("none")){
					tooltip.add(EnumChatFormatting.DARK_GREEN + "Slot One: none");
				}
				if (upgradeNum > 1 && !stack.getTagCompound().getString("SlotTwo").equals("none")){
					tooltip.add(EnumChatFormatting.DARK_GREEN + "Slot Two: " + stack.getTagCompound().getString("SlotTwo").substring(9));
				}else if (upgradeNum > 1 && stack.getTagCompound().getString("SlotTwo").equals("none")){
					tooltip.add(EnumChatFormatting.DARK_GREEN + "Slot Two: none");
				}
				if (upgradeNum > 2 && !stack.getTagCompound().getString("SlotThree").equals("none")){
					tooltip.add(EnumChatFormatting.DARK_GREEN + "Slot Three: " + stack.getTagCompound().getString("SlotThree").substring(9));
				}else if (upgradeNum > 2 && stack.getTagCompound().getString("SlotThree").equals("none")){
					tooltip.add(EnumChatFormatting.DARK_GREEN + "Slot Three: none");
				}
				if (upgradeNum > 3 && !stack.getTagCompound().getString("SlotFour").equals("none")){
					tooltip.add(EnumChatFormatting.DARK_GREEN + "Slot Four: " + stack.getTagCompound().getString("SlotFour").substring(9));
				}else if (upgradeNum > 3 && stack.getTagCompound().getString("SlotFour").equals("none")){
					tooltip.add(EnumChatFormatting.DARK_GREEN + "Slot Four: none");
				}
				if (upgradeNum > 4 && !stack.getTagCompound().getString("SlotFive").equals("none")){
					tooltip.add(EnumChatFormatting.DARK_GREEN + "Slot Five: " + stack.getTagCompound().getString("SlotFive").substring(9));
				}else if (upgradeNum > 4 && stack.getTagCompound().getString("SlotFive").equals("none")){
					tooltip.add(EnumChatFormatting.DARK_GREEN + "Slot Five: none");
				}
			}else{
				tooltip.remove(2);
				tooltip.add(EnumChatFormatting.RED + "Not Configured Yet");
			}
		}
	}

}
