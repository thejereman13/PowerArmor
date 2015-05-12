package com.jereman.powerarmor.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Console;

import com.jereman.powerarmor.ExtendedProperties;
import com.jereman.powerarmor.PowerCards;
import com.jereman.powerarmor.armor.PowerBase;
import com.jereman.powerarmor.init.JeremanItems;

public class CardSpeed extends PowerCards{
	public static float playerSpeed = .3f;
	public static String validArmor = "powerChest";
	public double limit = 3.25;
	public boolean shouldSetArmor = true;
	
	public CardSpeed(){
		
	}
	
	@Override
	public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5){	//Setting default NBT Data
		if (stack.getTagCompound() != null){
			if (stack.getTagCompound().hasKey("ValidArmor") != true){
				stack.getTagCompound().setString("ValidArmor", this.validArmor);
			}
			if (stack.getTagCompound().hasKey("UpgradeLimit") != true){
				stack.getTagCompound().setDouble("UpgradeLimit", this.limit);
			}
		}else{
			stack.setTagCompound(new NBTTagCompound());
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        
		return false;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
		if (!playerIn.isSneaking() && playerIn.getCurrentArmor(2) != null){
        	if (stack.getTagCompound() == null){
        		stack.setTagCompound(new NBTTagCompound());
        	}
        	NBTTagCompound nbt = new NBTTagCompound();
        	nbt.setBoolean("isenabled", true);
        	stack.getTagCompound().setTag("activatedText", nbt);
        }else if (playerIn.isSneaking() && playerSpeed >.1 && playerIn.getCurrentArmor(2) != null){
        	if(stack.getTagCompound() != null){
        		stack.getTagCompound().removeTag("activatedText");
        		stack.clearCustomName();
        		PowerBase.NBTUpgrades("SpeedUpgrade", playerIn.getCurrentArmor(2), 0);
        	}
        }
		
		return stack;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced){
	}
}
