package com.jereman.powerarmor;

import com.jereman.powerarmor.gui.GuiArmorWorkbench;
import com.jereman.powerarmor.tileentities.TileEntityArmorWorkbench;
import com.jereman.powerarmor.workbench.ContainerArmorWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class JeremanGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(new BlockPos(x,y,z));
		
		switch (ID){
		case Main.guiIDWorkbench:
			if (tileentity instanceof TileEntityArmorWorkbench){
				return new ContainerArmorWorkbench(player, player.inventory, (TileEntityArmorWorkbench) tileentity);
			}
		
		}
		
		return true;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(new BlockPos(x,y,z));
		
		switch (ID){
		case Main.guiIDWorkbench:
			if (tileentity instanceof TileEntityArmorWorkbench){
				return new GuiArmorWorkbench(player, player.inventory, (TileEntityArmorWorkbench) tileentity);
			}
		
		}
		
		return true;
	}

}
