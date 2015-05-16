package com.jereman.powerarmor.blocks;

import scala.Console;

import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.tileentities.TileEntityArmorWorkbench;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class armorWorkbench extends Block implements ITileEntityProvider{
	
	public static String name = "armorWorkbench";
	public armorWorkbench(Material materialIn) {
		super(materialIn);
		this.stepSound = Block.soundTypeStone;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
		TileEntityArmorWorkbench workbench = (TileEntityArmorWorkbench) worldIn.getTileEntity(pos);
		super.breakBlock(worldIn, pos, state);
		if (workbench.getStackInSlot(5) != null){
			float f = this.RANDOM.nextFloat() * 0.8F + 0.1F;
			float f1 = this.RANDOM.nextFloat() * 0.8F + 0.1F;
			float f2 = this.RANDOM.nextFloat() * 0.8F + 0.1F;
			EntityItem item = new EntityItem(worldIn, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, workbench.getStackInSlotOnClosing(5));
			worldIn.spawnEntityInWorld(item);
		}
		
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
