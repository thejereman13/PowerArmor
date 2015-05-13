package com.jereman.powerarmor.packets;

import scala.Console;

import com.jereman.powerarmor.IElementHandler;
import com.jereman.powerarmor.workbench.GuiArmorWorkbench;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GUISlotHandler implements IMessageHandler<GUISlotMessage, IMessage> {

	@Override
	public IMessage onMessage(GUISlotMessage message, MessageContext ctx) {
		if (!Minecraft.getMinecraft().inGameHasFocus && Minecraft.getMinecraft().currentScreen instanceof GuiArmorWorkbench){
			try{
				((GuiArmorWorkbench)Minecraft.getMinecraft().currentScreen).recieveSlot(message.amount);
			}catch (NullPointerException e){
				
			}
		}
		return null;
	}
}
