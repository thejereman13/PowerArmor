package com.jereman.powerarmor.gui;

import org.lwjgl.opengl.GL11;

import scala.Console;

import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.Reference;
import com.jereman.powerarmor.packets.CardNumberMessage;
import com.jereman.powerarmor.tileentities.TileEntityArmorWorkbench;
import com.jereman.powerarmor.workbench.ContainerArmorWorkbench;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class GuiArmorWorkbench extends GuiContainer{
	
	public static int xCord;
	public static int yCord;
	public double slider1;
	private final ResourceLocation backgroundImage = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/client/gui/guiArmorWorkbench.png");
	
	public GuiArmorWorkbench(EntityPlayer player, InventoryPlayer invPlayer, TileEntityArmorWorkbench entity){
		super(new ContainerArmorWorkbench(player, invPlayer, entity));		
		this.xSize = 197;
		this.ySize = 200;

	}
	
	@Override
	public void initGui(){
		super.initGui();
		xCord = (this.width - this.xSize) / 2;
		yCord = (this.height - this.ySize) / 2;
		
		this.buttonList.add(new GuiButton(0, xCord + 47, yCord + 6, 10, 19, ""));
		this.buttonList.add(new GuiButton(1, xCord + 47, yCord + 26, 10, 19, ""));
		this.buttonList.add(new GuiButton(2, xCord + 47, yCord + 46, 10, 19, ""));
		this.buttonList.add(new GuiButton(3, xCord + 47, yCord + 66, 10, 19, ""));
		this.buttonList.add(new GuiButton(4, xCord + 47, yCord + 86, 10, 19, ""));
	}
	
	public void actionPerformed(GuiButton button){
			Main.network.sendToServer(new CardNumberMessage(button.id));
			Console.println("Sent Message");
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks){
		super.drawScreen(mouseX, mouseY, renderPartialTicks);
		int xSize_lo = mouseX;
		int ySize_lo = mouseY;
		
	}
	
	@Override
	public boolean doesGuiPauseGame(){
		return false;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(backgroundImage);
		xCord = (this.width - this.xSize) / 2;
		yCord = (this.height - this.ySize) / 2;
		drawTexturedModalRect(xCord, yCord, 0, 0, xSize, ySize);
	}
}
