package com.jereman.powerarmor;

import scala.Console;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.jereman.powerarmor.armor.PowerBase;
import com.jereman.powerarmor.init.JeremanItems;
import com.jereman.powerarmor.items.CardJump;

public class JeremanEventHandler {
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event){
		if (event.entity instanceof EntityPlayer){			//Should probably fix this code to be like the chestplate code below
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendedProperties props = ExtendedProperties.get((EntityPlayer) player);
			if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).hasTagCompound()){
				if (props.getLeggings() && player.getCurrentArmor(1).getItem() != JeremanItems.powerPants){
					props.setLeggings(false);
					player.stepHeight = .5F;
				}else if (props.getLeggings() && player.getCurrentArmor(1).getItem() == JeremanItems.powerPants){
					if (player.getCurrentArmor(1).getTagCompound().getBoolean("HasStep")){
						player.stepHeight = 1F;
					}else{
						player.stepHeight = .5F;
						player.getCurrentArmor(1).getTagCompound().setBoolean("HasStep", false);
					}
				}
			}else{
				player.stepHeight = .5F;
				props.setLeggings(false);
			}
			if (props.getChestPlate()){
				if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).hasTagCompound() && player.getCurrentArmor(2).getTagCompound().hasKey("JeremanUpgradeNumber")){	//Use JeremanUpgradeNumber to check if a powerarmor piece is being worn
					if (!PowerBase.findAllUpgrades(player.getCurrentArmor(2), "cardCreativeFlight")){
						player.capabilities.allowFlying = false;
						player.capabilities.isFlying = false;
						player.fall(player.height, 0);
					}else if (PowerBase.findAllUpgrades(player.getCurrentArmor(2), "cardCreativeFlight")){
						player.capabilities.allowFlying = true;
					}
				}else{
					player.capabilities.allowFlying = false;
					player.capabilities.isFlying = false;
					props.setChestPlate(false);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingJumpEvent(LivingJumpEvent event){
		if (event.entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendedProperties props = ExtendedProperties.get((EntityPlayer) player);
			if (player.getCurrentArmor(1) != null){
				if (props.getLeggings() && player.getCurrentArmor(1).getItem() == JeremanItems.powerPants){
					if (player.getCurrentArmor(1).hasTagCompound()){
						if (player.getCurrentArmor(1).getTagCompound().getBoolean("HasJump")){
							player.motionY += (player.getCurrentArmor(1).getTagCompound().getDouble("JumpAmount") / 5);
						}else{
							player.getCurrentArmor(1).getTagCompound().setBoolean("HasJump", false);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event){
		if (event.entity instanceof EntityPlayer && ExtendedProperties.get((EntityPlayer) event.entity) == null){
			ExtendedProperties.register((EntityPlayer) event.entity); 
		}
	}
	
}
