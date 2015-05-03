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

public class CardUpgradeHandler implements IMessageHandler<CardUpgradeMessage, IMessage> {

	@Override
	public IMessage onMessage(CardUpgradeMessage message, MessageContext ctx) {
		if (!Minecraft.getMinecraft().inGameHasFocus && Minecraft.getMinecraft().currentScreen instanceof GuiArmorWorkbench){
			Console.println("Called: " + message.amount);
				((GuiArmorWorkbench)Minecraft.getMinecraft().currentScreen).recieveAmount(message.amount);
		}
		return null;
	}
}
