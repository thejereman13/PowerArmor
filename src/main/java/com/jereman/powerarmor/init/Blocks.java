package com.jereman.powerarmor.init;

import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.Reference;
import com.jereman.powerarmor.blocks.BlockCopper;
import com.jereman.powerarmor.blocks.armorWorkbench;
import com.jereman.powerarmor.tileentities.TileEntityArmorWorkbench;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Blocks {
	public static Block armorWorkbench;
	
	public static void init(){
		armorWorkbench = new armorWorkbench(Material.rock).setUnlocalizedName("armorWorkbench").setCreativeTab(Main.tabPowerarmor).setHardness(8);
		
	}
	
	public static void register(){
		GameRegistry.registerBlock(armorWorkbench, armorWorkbench.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders(){
		registerRender(armorWorkbench);
	}
	
	public static void registerRender(Block block){
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
