package com.jereman.powerarmor;

import ibxm.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import scala.Console;

import com.jereman.powerarmor.armor.PowerBase;
import com.jereman.powerarmor.init.JeremanItems;

public class JeremanFMLEventHandler {			//Server
	public ItemStack playerHelmet;
	public ItemStack playerChestplate;
	public ItemStack playerLeggings;
	public ItemStack playerBoots;
	//Do da things to reverse the effects added
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event){
		if (!event.player.worldObj.isRemote && event.phase == TickEvent.Phase.START){
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			ExtendedProperties props = ExtendedProperties.get((EntityPlayer) player);
			if (player.getCurrentArmor(3) != null){
				playerHelmet = player.getCurrentArmor(3);
			}else{
				playerHelmet = null;
			}
			if (player.getCurrentArmor(2) != null){
				playerChestplate = player.getCurrentArmor(2);
			}else{
				playerChestplate = null;
			}
			if (player.getCurrentArmor(1) != null){
				playerLeggings = player.getCurrentArmor(1);
			}else{
				playerLeggings = null;
			}
			if (player.getCurrentArmor(0) != null){
				playerBoots = player.getCurrentArmor(0);
			}else{
				playerBoots = null;
			}
			
			
			if (playerHelmet != null && playerHelmet.hasTagCompound()){		//Some armor is being worn
				if (props.getHelmet() && playerHelmet.getTagCompound().hasKey("JeremanUpgradeNumber")){		//Mah armor is being worn
					
				}else if (props.getHelmet() && !playerHelmet.getTagCompound().hasKey("JeremanUpgradeNumber")){		//Mah armor isn't being worn, but was being worn
					props.setHelmet(false);
				}
			}else{		//No armor is being worn
				props.setHelmet(false);
			}
			
			if (playerChestplate != null && playerChestplate.hasTagCompound()){		//Some armor is being worn
				if (props.getChestPlate() && playerChestplate.getTagCompound().hasKey("JeremanUpgradeNumber")){		//Mah armor is being worn
					
				}else if (props.getChestPlate() && !playerChestplate.getTagCompound().hasKey("JeremanUpgradeNumber")){		//Mah armor isn't being worn, but was being worn
					props.setChestPlate(false);
				}
			}else{		//No armor is being worn
				props.setChestPlate(false);
			}
			
			if (playerLeggings != null){		//Some armor is being worn
				if (props.getLeggings() && playerLeggings.getTagCompound().hasKey("JeremanUpgradeNumber")){		//Mah armor is being worn
					
				}else if (props.getLeggings() && !playerLeggings.getTagCompound().hasKey("JeremanUpgradeNumber")){		//Mah armor isn't being worn, but was being worn
					props.setLeggings(false);
				}
			}else{		//No armor is being worn
				if (player.capabilities.getWalkSpeed() != .1f){
					player.capabilities.setPlayerWalkSpeed(.2f);
					player.capabilities.setPlayerWalkSpeed(.1f);
				}
				if (player.capabilities.allowFlying == true){
					player.capabilities.allowFlying = false;
				}
			}
			
			if (playerBoots != null && playerBoots.hasTagCompound()){		//Some armor is being worn
				if (props.getBoots() && playerBoots.getTagCompound().hasKey("JeremanUpgradeNumber")){		//Mah armor is being worn
					
				}else if (props.getBoots() && !playerBoots.getTagCompound().hasKey("JeremanUpgradeNumber")){		//Mah armor isn't being worn, but was being worn
					props.setBoots(false);
				}
			}else{		//No armor is being worn
				props.setBoots(false);
			}
		}
	}
	
}

