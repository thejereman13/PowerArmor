package com.jereman.powerarmor.proxy;

import com.jereman.powerarmor.blocks.armorWorkbench;
import com.jereman.powerarmor.tileentities.TileEntityArmorWorkbench;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerRenders(){
		
	}
	
	public void registerTileEntites(){
		GameRegistry.registerTileEntity(TileEntityArmorWorkbench.class, TileEntityArmorWorkbench.name);
	}
}
