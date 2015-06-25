package com.jereman.powerarmor;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PowerCards extends Item{
	//Extend this class to allow your item to be placed in the Armor Workbench upgrade slots.
        public PowerCards(){
        	this.maxStackSize = 1;
        }
}
