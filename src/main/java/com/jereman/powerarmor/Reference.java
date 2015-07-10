package com.jereman.powerarmor;

public class Reference {
	public static final String MOD_ID = "powerarmor";
	public static final String MOD_NAME = "Power Armor";
	public static final String VERSION = "0.0.6";
	public static final String CLIENT_PROXY_CLASS = "com.jereman.powerarmor.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.jereman.powerarmor.proxy.CommonProxy";
	public static boolean DEBUG;
	
	public static boolean powerHelmetEnabled, powerChestEnabled, powerPantsEnabled, powerBootsEnabled;
	public static boolean cardSpeedEnabled, cardJumpEnabled, cardStepEnabled, cardFireEnabled, cardFallEnabled,
	cardPerfectFallEnabled, cardProtectionEnabled, cardNightVisionEnabled, cardArmorInvisEnabled, 
	cardCreativeFlightEnabled, cardWaterWalkEnabled;
	
}
