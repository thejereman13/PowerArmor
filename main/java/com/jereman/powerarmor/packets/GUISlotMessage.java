package com.jereman.powerarmor.packets;

import scala.Console;

import com.jereman.powerarmor.IElementHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GUISlotMessage implements IMessage{
	public double amount;
	public GUISlotMessage(){}
	
	public GUISlotMessage(double amount){
		this.amount = amount;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		double elementLength = buf.readDouble();
		this.amount = elementLength;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(amount);
	}

		
	

}
