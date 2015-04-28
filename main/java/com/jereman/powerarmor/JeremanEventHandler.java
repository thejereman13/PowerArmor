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
		
	}
	
	@SubscribeEvent
	public void onLivingJumpEvent(LivingJumpEvent event){
		
		if (event.entity != null && event.entityLiving.getHeldItem() != null && event.entity instanceof EntityPlayer){
			if (event.entityLiving.getHeldItem().getItem().equals(JeremanItems.cardJump)){
			event.entity.addVelocity(event.entity.motionX, CardJump.jumpHeight, event.entity.motionZ);
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
