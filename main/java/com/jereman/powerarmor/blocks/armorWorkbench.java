package com.jereman.powerarmor.blocks;

import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.tileentities.TileEntityArmorWorkbench;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class armorWorkbench extends Block implements ITileEntityProvider{
	
	public static String name = "armorWorkbench";
	public armorWorkbench(Material materialIn) {
		super(materialIn);
	}
	

	@Override
	public TileEntity createNewTileEntity(World world, int meta){
		return new TileEntityArmorWorkbench();
	}
	
	public boolean hasTileEntity(int metadata){
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (true){
			FMLNetworkHandler.openGui(playerIn, Main.instance, Main.guiIDWorkbench, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

}
