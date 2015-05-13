package com.jereman.powerarmor;

import scala.Console;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.jereman.powerarmor.init.JeremanItems;
import com.jereman.powerarmor.items.CardJump;

public class JeremanEventHandler {
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event){
		if (event.entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendedProperties props = ExtendedProperties.get((EntityPlayer) player);
			if (player.getCurrentArmor(1) != null){
				if (props.getLeggings() && player.getCurrentArmor(1).getItem() != JeremanItems.powerPants){
					props.setLeggings(false);
					player.stepHeight = .5F;
				}else if (props.getLeggings() && player.getCurrentArmor(1).getItem() == JeremanItems.powerPants){
					if (player.getCurrentArmor(1).getTagCompound().getBoolean("HasStep")){
						player.stepHeight = 1F;
						//player.getCurrentArmor(1).getTagCompound().setBoolean("HasStep", false);
					}else{
						player.stepHeight = .5F;
						player.getCurrentArmor(1).getTagCompound().setBoolean("HasStep", false);
					}
				}
			}else{
				player.stepHeight = .5F;
				props.setLeggings(false);
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
