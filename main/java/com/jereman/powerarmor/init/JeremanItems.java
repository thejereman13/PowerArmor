package com.jereman.powerarmor.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.Reference;
import com.jereman.powerarmor.armor.PowerBoots;
import com.jereman.powerarmor.armor.PowerChest;
import com.jereman.powerarmor.armor.PowerHelmet;
import com.jereman.powerarmor.armor.PowerPants;
import com.jereman.powerarmor.items.CardArmorInvis;
import com.jereman.powerarmor.items.CardFall;
import com.jereman.powerarmor.items.CardFire;
import com.jereman.powerarmor.items.CardJump;
import com.jereman.powerarmor.items.CardNightvision;
import com.jereman.powerarmor.items.CardPerfectFall;
import com.jereman.powerarmor.items.CardProtection;
import com.jereman.powerarmor.items.CardSpeed;
import com.jereman.powerarmor.items.CardStep;

public class JeremanItems {
	
	public static Item ingotCopper;
	public static Item cardSpeed;
	public static Item cardJump;
	public static Item powerHelmet;
	public static Item powerChest;
	public static Item powerPants;
	public static Item powerBoots;
	public static Item cardStep;
	public static Item cardFire;
	public static Item cardFall;
	public static Item cardPerfectFall;
	public static Item cardProtection;
	public static Item cardNightvision;
	public static Item cardArmorInvis;
	
	public static void init(){
		ingotCopper = new Item().setUnlocalizedName("ingotCopper").setCreativeTab(Main.tabPowerarmor);
		cardSpeed = new CardSpeed().setUnlocalizedName("cardSpeed").setCreativeTab(Main.tabPowerarmor);
		cardJump = new CardJump().setUnlocalizedName("cardJump").setCreativeTab(Main.tabPowerarmor);
		powerHelmet = new PowerHelmet().setUnlocalizedName("powerHelmet").setCreativeTab(Main.tabPowerarmor);
		powerChest = new PowerChest().setUnlocalizedName("powerChest").setCreativeTab(Main.tabPowerarmor);
		powerPants = new PowerPants().setUnlocalizedName("powerPants").setCreativeTab(Main.tabPowerarmor);
		powerBoots = new PowerBoots().setUnlocalizedName("powerBoots").setCreativeTab(Main.tabPowerarmor);
		cardStep = new CardStep().setUnlocalizedName("cardStep").setCreativeTab(Main.tabPowerarmor);
		cardFire = new CardFire().setUnlocalizedName("cardFire").setCreativeTab(Main.tabPowerarmor);
		cardFall = new CardFall().setUnlocalizedName("cardFall").setCreativeTab(Main.tabPowerarmor);
		cardPerfectFall = new CardPerfectFall().setUnlocalizedName("cardPerfectFall").setCreativeTab(Main.tabPowerarmor);
		cardProtection = new CardProtection().setUnlocalizedName("cardProtection").setCreativeTab(Main.tabPowerarmor);
		cardNightvision = new CardNightvision().setUnlocalizedName("cardNightvision").setCreativeTab(Main.tabPowerarmor);
		cardArmorInvis = new CardArmorInvis().setUnlocalizedName("cardArmorInvis").setCreativeTab(Main.tabPowerarmor);
	}
	
	public static void register(){
		GameRegistry.registerItem(ingotCopper, ingotCopper.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(powerHelmet, powerHelmet.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(powerChest, powerChest.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(powerPants, powerPants.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(powerBoots, powerBoots.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardSpeed, cardSpeed.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardJump, cardJump.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardStep, cardStep.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardFire, cardFire.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardFall, cardFall.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardPerfectFall, cardPerfectFall.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardProtection, cardProtection.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardNightvision, cardNightvision.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(cardArmorInvis, cardArmorInvis.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders(){
		registerRender(ingotCopper);
		registerRender(cardSpeed);
		registerRender(cardJump);
		registerRender(powerHelmet);
		registerRender(powerChest);
		registerRender(powerPants);
		registerRender(powerBoots);
		registerRender(cardStep);
		registerRender(cardFire);
		registerRender(cardFall);
		registerRender(cardPerfectFall);
		registerRender(cardProtection);
		registerRender(cardNightvision);
		registerRender(cardArmorInvis);
	}
	
	public static void registerRender(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
