package com.jereman.powerarmor;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
public class PowerarmorTab extends CreativeTabs{

	
	public PowerarmorTab(String label) {
		super(label);
		//this.setBackgroundImageName("background.png");
	}

	@Override
	public Item getTabIconItem() {
		return com.jereman.powerarmor.init.JeremanItems.ingotCopper;
	}

}
