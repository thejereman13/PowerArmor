package com.jereman.powerarmor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class PowerarmorTab extends CreativeTabs{
	
	public PowerarmorTab(String label) {
		super(label);
	}
	
	@Override
	public Item getTabIconItem() {
		return com.jereman.powerarmor.init.JeremanItems.powerChest;
	}

}
