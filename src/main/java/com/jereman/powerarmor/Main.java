package com.jereman.powerarmor;

import java.util.*;
import java.util.function.Function;

import scala.actors.threadpool.Arrays;
import scala.math.Ordering;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.jereman.powerarmor.init.Blocks;
import com.jereman.powerarmor.init.Config;
import com.jereman.powerarmor.init.Crafting;
import com.jereman.powerarmor.init.JeremanItems;
import com.jereman.powerarmor.packets.CardNumberHandler;
import com.jereman.powerarmor.packets.CardNumberMessage;
import com.jereman.powerarmor.packets.GUIAmountHandler;
import com.jereman.powerarmor.packets.GUIAmountMessage;
import com.jereman.powerarmor.packets.GUINameHandler;
import com.jereman.powerarmor.packets.GUINameMessage;
import com.jereman.powerarmor.packets.GUISlotHandler;
import com.jereman.powerarmor.packets.GUISlotMessage;
import com.jereman.powerarmor.proxy.CommonProxy;
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class Main {
	@Mod.Instance(Reference.MOD_ID)
	public static Main instance;
	
	public static final int guiIDWorkbench = 0;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	public static final PowerarmorTab tabPowerarmor = new PowerarmorTab("tabPowerarmor");
	
	public static boolean ArmorWorn;
	public static int powerarmorDamageReduction[] = {3,8,6,3};
	public static int powerDurability = 1000;
	public static ArmorMaterial PowerArmorMaterial = EnumHelper.addArmorMaterial("PowerArmorMaterial", "powerarmor/textures/models/armor/powerarmor", -1, powerarmorDamageReduction, 0);
	public static SimpleNetworkWrapper network;
	public static Comparator<ItemStack> tabSorter;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		Config.Setup(event);
		Blocks.init();
		Blocks.register();
		JeremanItems.init();
		JeremanItems.register();
		network = NetworkRegistry.INSTANCE.newSimpleChannel("powerarmor");
		network.registerMessage(CardNumberHandler.class, CardNumberMessage.class, 0, Side.SERVER);
		network.registerMessage(GUIAmountHandler.class, GUIAmountMessage.class, 1, Side.CLIENT);
		network.registerMessage(GUISlotHandler.class, GUISlotMessage.class, 2, Side.CLIENT);
		network.registerMessage(GUINameHandler.class, GUINameMessage.class, 3, Side.CLIENT);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		proxy.registerTileEntites();
		proxy.registerRenders();
		Crafting.initVanillaRecipes();
		MinecraftForge.EVENT_BUS.register(new JeremanEventHandler());
		FMLCommonHandler.instance().bus().register(new JeremanFMLEventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new JeremanGuiHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
			
	}
	
	public static boolean DebugMode = true;
}
