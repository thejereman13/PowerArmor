package com.jereman.powerarmor.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
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

import com.jereman.powerarmor.PowerCards;
import com.jereman.powerarmor.armor.PowerChest;
import com.jereman.powerarmor.init.JeremanItems;

public class CardSpeed extends PowerCards{
	public static float playerSpeed = .3f;
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
        	stack.setStackDisplayName(EnumChatFormatting.AQUA + "Speed Upgrade Card");
        	final Item wornChestplate = playerIn.getCurrentArmor(2).getItem();
        	if (wornChestplate == JeremanItems.powerChest){
        		Console.println("Correct Armor is being worn");
        		PowerChest.NBTUpgrades("SpeedUpgrade", playerIn.getCurrentArmor(2), 1);
        	}else{
        		Console.println("Incorrect Armor is being worn");
        	}
        }else if (playerIn.isSneaking() && playerSpeed >.1 && playerIn.getCurrentArmor(2) != null){
        	if(stack.getTagCompound() != null){
        		stack.getTagCompound().removeTag("activatedText");
        		stack.clearCustomName();
        		PowerChest.NBTUpgrades("SpeedUpgrade", playerIn.getCurrentArmor(2), 0);
        	}
        }
		
		return stack;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced){
		if (stack.getTagCompound() != null){
			if (stack.getTagCompound().hasKey("activatedText")){
				NBTTagCompound nbt = (NBTTagCompound) stack.getTagCompound().getTag("activatedText");
				boolean activation = nbt.getBoolean("isenabled");
				tooltip.add("Speed Upgrade Card is Activated");
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        if (stack.getTagCompound() != null){
        	return stack.getTagCompound().hasKey("activatedText");
        }
		return false;
    }

}
