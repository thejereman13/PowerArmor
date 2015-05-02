package com.jereman.powerarmor;

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
import com.jereman.powerarmor.init.JeremanItems;
import com.jereman.powerarmor.init.armor;
import com.jereman.powerarmor.packets.CardGuiMessage;
import com.jereman.powerarmor.packets.CardNumberHandler;
import com.jereman.powerarmor.packets.CardNumberMessage;
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
	public static int powerDurability = 100;
	public static ArmorMaterial PowerArmorMaterial = EnumHelper.addArmorMaterial("PowerArmorMaterial", "powerarmor/textures/models/armor/powerarmor", powerDurability, powerarmorDamageReduction, 0);
	public static SimpleNetworkWrapper network;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		Blocks.init();
		Blocks.register();
		JeremanItems.init();
		JeremanItems.register();
		armor.init();
		network = NetworkRegistry.INSTANCE.newSimpleChannel("powerarmor");
		network.registerMessage(CardNumberHandler.class, CardNumberMessage.class, 0, Side.SERVER);
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		proxy.registerTileEntites();
		proxy.registerRenders();
		MinecraftForge.EVENT_BUS.register(new JeremanEventHandler());
		FMLCommonHandler.instance().bus().register(new JeremanFMLEventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new JeremanGuiHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
			
	}
	
	public static boolean DebugMode = true;
}
