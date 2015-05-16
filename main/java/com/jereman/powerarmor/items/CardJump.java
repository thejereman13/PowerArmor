package com.jereman.powerarmor.items;

import java.util.List;

import scala.Console;

import com.jereman.powerarmor.ExtendedProperties;
import com.jereman.powerarmor.PowerCards;
import com.jereman.powerarmor.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CardJump extends PowerCards{
	public static double jumpHeight = 0;
	public String validArmor = "powerPants";
	public static double limit = 3.5;
	public boolean shouldSetArmor= true;
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		return false;
    }
	
	public static void Upgrade(double playerSpeed, EntityPlayer player){
		ExtendedProperties props = ExtendedProperties.get((EntityPlayer) player);
		if (player.getCurrentArmor(1).hasTagCompound()){
				player.getCurrentArmor(1).getTagCompound().setBoolean("HasJump", true);
			player.getCurrentArmor(1).getTagCompound().setDouble("JumpAmount", playerSpeed);
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5){ //Setting default NBT Data
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
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced){
	
	}
	
}
