package com.jereman.powerarmor;

import scala.Console;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.jereman.powerarmor.armor.PowerBase;
import com.jereman.powerarmor.init.JeremanItems;
import com.jereman.powerarmor.items.CardJump;

public class JeremanEventHandler {			//Client
	public ItemStack playerHelmet;
	public ItemStack playerChestplate;
	public ItemStack playerLeggings;
	public ItemStack playerBoots;
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event){
		if (event.entity instanceof EntityPlayer){			//Should probably fix this code to be like the chestplate code below
			EntityPlayer player = (EntityPlayer) event.entity;
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
			
			
			
			if (props.getBoots()){
				if (playerBoots != null && playerBoots.hasTagCompound() && playerBoots.getTagCompound().hasKey("JeremanUpgradeNumber")){	//Use JeremanUpgradeNumber to check if a powerarmor piece is being worn
					if (!PowerBase.findAllUpgrades(playerBoots, "cardWaterWalk")){
						
					}else if (PowerBase.findAllUpgrades(playerBoots, "cardWaterWalk")){
						if (player.worldObj.getBlockState(new BlockPos(player.posX, player.posY - .1, player.posZ)).getBlock() == Blocks.water){
							player.motionY = 0D;
							if (player.worldObj.getBlockState(new BlockPos(player.posX, player.posY - .01, player.posZ)).getBlock() == Blocks.water){
								player.motionY = .1D;
							}else{
								player.motionY=0D;
							}
							player.fallDistance = 0;
							player.onGround = true;
						}
					}
				}else{
					props.setBoots(false);
				}
				
			}
			if (props.getLeggings()){
				if (playerLeggings != null && playerLeggings.hasTagCompound() && playerLeggings.getTagCompound().hasKey("JeremanUpgradeNumber")){	//Use JeremanUpgradeNumber to check if a powerarmor piece is being worn
					if (!PowerBase.findAllUpgrades(playerLeggings, "cardStep")){
						if (player.stepHeight != .5F){
							player.stepHeight = .5F;
						}
					}else if (PowerBase.findAllUpgrades(playerLeggings, "cardStep")){
						if (player.stepHeight != 1F){
							player.stepHeight = 1F;
						}
					}
					
				}else{
					player.stepHeight = .5F;
					props.setLeggings(false);
				}
			}
			
			if (props.getChestPlate()){
				if (playerChestplate != null && playerChestplate.hasTagCompound() && playerChestplate.getTagCompound().hasKey("JeremanUpgradeNumber")){	//Use JeremanUpgradeNumber to check if a powerarmor piece is being worn
					if (!PowerBase.findAllUpgrades(playerChestplate, "cardCreativeFlight")){
						if (player.capabilities.isCreativeMode){
							player.capabilities.allowFlying = true;
						}else{
							player.capabilities.allowFlying = false;
							player.capabilities.isFlying = false;
							player.fall(player.height, 0);
						}
					}else if (PowerBase.findAllUpgrades(playerChestplate, "cardCreativeFlight")){
						player.capabilities.allowFlying = true;
					}
					
				}else{
					if (player.capabilities.isCreativeMode){
						player.capabilities.allowFlying = true;
					}else{
						player.capabilities.allowFlying = false;
						player.capabilities.isFlying = false;
						props.setChestPlate(false);
					}
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
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event){
		if (event.entity instanceof EntityPlayer){
			Console.println("Player Died: " + event.entity);
		}
	}
	
}
