package com.jereman.powerarmor.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.Reference;
import com.jereman.powerarmor.armor.PowerBase;
import com.jereman.powerarmor.armor.PowerChest;
import com.jereman.powerarmor.armor.PowerPants;
import com.jereman.powerarmor.items.CardJump;
import com.jereman.powerarmor.items.CardSpeed;

public class JeremanItems {
	
	public static Item ingotCopper;
	public static Item cardSpeed;
	public static Item cardJump;
	public static Item powerChest;
	public static Item powerPants;
	public static void init(){
		ingotCopper = new Item().setUnlocalizedName("ingotCopper").setCreativeTab(Main.tabPowerarmor);
		cardSpeed = new CardSpeed().setUnlocalizedName("cardSpeed").setCreativeTab(Main.tabPowerarmor);
		cardJump = new CardJump().setUnlocalizedName("cardJump").setCreativeTab(Main.tabPowerarmor);
		powerChest = new PowerChest().setUnlocalizedName("powerChest").setCreativeTab(Main.tabPowerarmor);
		powerPants = new PowerPants().setUnlocalizedName("powerPants").setCreativeTab(Main.tabPowerarmor);
	}
	
	public static void register(){
		GameRegistry.registerItem(ingotCopper, ingotCopper.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardSpeed, cardSpeed.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardJump, cardJump.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(powerChest, powerChest.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(powerPants, powerPants.getUnlocalizedName().substring(5));
		
	}
	
	public static void registerRenders(){
		registerRender(ingotCopper);
		registerRender(cardSpeed);
		registerRender(cardJump);
		registerRender(powerChest);
		registerRender(powerPants);
	}
	
	public static void registerRender(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
