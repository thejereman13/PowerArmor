package com.jereman.powerarmor;

import ibxm.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import scala.Console;

import com.jereman.powerarmor.armor.PowerBase;
import com.jereman.powerarmor.init.JeremanItems;

public class JeremanFMLEventHandler {

	//Do da things to reverse the effects added later
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event){
		if (!event.player.worldObj.isRemote && event.phase == TickEvent.Phase.START){
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			ExtendedProperties props = ExtendedProperties.get((EntityPlayer) player);
			if (props.getChestPlate() && player.getCurrentArmor(2) == null){
				props.setChestPlate(false);
				player.capabilities.setPlayerWalkSpeed(.1f);
				player.fireResistance = 0;
			}else if(props.getChestPlate() && player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() != JeremanItems.powerChest){
				props.setChestPlate(false);
				player.capabilities.setPlayerWalkSpeed(.1f);
				player.fireResistance = 0;
			}
		}else{
		}
		
	}
	
}

