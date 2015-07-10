package com.jereman.powerarmor.init;

import com.jereman.powerarmor.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
	public static Configuration config;
	public static void Setup(FMLPreInitializationEvent event){
		config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		config.addCustomCategoryComment("general", "General Settings");
		config.addCustomCategoryComment("upgrades", "Enable or disable specific upgrades (Requires Restart)");
		config.addCustomCategoryComment("armor", "Enable or disable specific armor pieces (Requires Restart)");
		syncConfig();
		
	}

	public static void syncConfig() {
		boolean debug = config.get("general", "Debug", false).getBoolean();
		float powerMultiplier = config.getFloat("Power Multiplier", "general", 1, 0, 100, "Ratio the power usage is multiplied by");
		boolean powerHelmetEnabled = config.get("armor", "Enable Power Helmet", true).getBoolean();
		boolean powerChestEnabled = config.get("armor", "Enable Power Chestplate", true).getBoolean();
		boolean powerPantsEnabled = config.get("armor", "Enable Power Leggings", true).getBoolean();
		boolean powerBootsEnabled = config.get("armor", "Enable Power Boots", true).getBoolean();
		boolean cardSpeedEnabled = config.get("upgrades", "Enable Speed Card", true).getBoolean();
		boolean cardJumpEnabled = config.get("upgrades", "Enable Jump Card", true).getBoolean();
		boolean cardStepEnabled = config.get("upgrades", "Enable Step Card", true).getBoolean();
		boolean cardFireEnabled = config.get("upgrades", "Enable Fire Card", true).getBoolean();
		boolean cardFallEnabled = config.get("upgrades", "Enable Fall Card", true).getBoolean();
		boolean cardPerfectFallEnabled = config.get("upgrades", "Enable Perfect Fall Card", true).getBoolean();
		boolean cardProtectionEnabled = config.get("upgrades", "Enable Protection Card", true).getBoolean();
		boolean cardNightvisionEnabled = config.get("upgrades", "Enable Nightvision Card", true).getBoolean();
		boolean cardArmorInvisEnabled = config.get("upgrades", "Enable Armor Invisibility Card", true).getBoolean();
		boolean cardCreativeFlightEnabled = config.get("upgrades", "Enable Creative Flight Card", true).getBoolean();
		boolean cardWaterWalkEnabled = config.get("upgrades", "Enable Water Walking Card", true).getBoolean();
		Reference.DEBUG = debug;
		Reference.powerBootsEnabled = powerBootsEnabled;
		Reference.powerPantsEnabled = powerPantsEnabled;
		Reference.powerChestEnabled = powerChestEnabled;
		Reference.powerHelmetEnabled = powerHelmetEnabled;
		Reference.cardSpeedEnabled = cardSpeedEnabled;
		Reference.cardJumpEnabled = cardJumpEnabled;
		Reference.cardStepEnabled = cardStepEnabled;
		Reference.cardFireEnabled = cardFireEnabled;
		Reference.cardFallEnabled = cardFallEnabled;
		Reference.cardPerfectFallEnabled = cardPerfectFallEnabled;
		Reference.cardProtectionEnabled = cardProtectionEnabled;
		Reference.cardNightVisionEnabled = cardNightvisionEnabled;
		Reference.cardArmorInvisEnabled = cardArmorInvisEnabled;
		Reference.cardCreativeFlightEnabled = cardCreativeFlightEnabled;
		Reference.cardWaterWalkEnabled = cardWaterWalkEnabled;
		if (config.hasChanged()) config.save();
		
	}
}
