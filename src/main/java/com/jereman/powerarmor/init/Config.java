package com.jereman.powerarmor.init;

import com.jereman.powerarmor.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
	public static void Setup(FMLPreInitializationEvent event){
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		float powerMultiplier = config.getFloat("Power Multiplier", Configuration.CATEGORY_GENERAL, 1, 0, 100, "Ratio the power usage is multiplied by");
		boolean powerHelmetEnabled = config.get("Items", "Enable Power Helmet", true).getBoolean();
		boolean powerChestEnabled = config.get("Items", "Enable Power Chestplate", true).getBoolean();
		boolean powerPantsEnabled = config.get("Items", "Enable Power Leggings", true).getBoolean();
		boolean powerBootsEnabled = config.get("Items", "Enable Power Boots", true).getBoolean();
		boolean cardSpeedEnabled = config.get("Items", "Enable Speed Card", true).getBoolean();
		boolean cardJumpEnabled = config.get("Items", "Enable Jump Card", true).getBoolean();
		boolean cardStepEnabled = config.get("Items", "Enable Step Card", true).getBoolean();
		boolean cardFireEnabled = config.get("Items", "Enable Fire Card", true).getBoolean();
		boolean cardFallEnabled = config.get("Items", "Enable Fall Card", true).getBoolean();
		boolean cardPerfectFallEnabled = config.get("Items", "Enable Perfect Fall Card", true).getBoolean();
		boolean cardProtectionEnabled = config.get("Items", "Enable Protection Card", true).getBoolean();
		boolean cardNightvisionEnabled = config.get("Items", "Enable Nightvision Card", true).getBoolean();
		boolean cardArmorInvisEnabled = config.get("Items", "Enable Armor Invisibility Card", true).getBoolean();
		boolean cardCreativeFlightEnabled = config.get("Items", "Enable Creative Flight Card", true).getBoolean();
		boolean cardWaterWalkEnabled = config.get("Items", "Enable Water Walking Card", true).getBoolean();
		config.save();
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
		
	}
}
