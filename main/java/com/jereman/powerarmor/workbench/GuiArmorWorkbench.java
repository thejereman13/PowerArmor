package com.jereman.powerarmor.workbench;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lwjgl.opengl.GL11;

import scala.Console;

import com.jereman.powerarmor.ExtendedProperties;
import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.Reference;
import com.jereman.powerarmor.packets.CardNumberMessage;
import com.jereman.powerarmor.tileentities.TileEntityArmorWorkbench;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class GuiArmorWorkbench extends GuiContainer{
	public TileEntityArmorWorkbench workbench;
	public static int xCord;
	public static int yCord;
	public int slotActivated = 0;
	public double upgrade = 0;
	public boolean isButton = false;
	IThreadListener mainThread;
	public boolean isEmpty = true;
	public String nullName = "none";
	public EntityPlayer player;
	public ContainerArmorWorkbench container;
	private final ResourceLocation backgroundImage = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/client/gui/guiArmorWorkbench.png");
	
	public GuiArmorWorkbench(EntityPlayer player, InventoryPlayer invPlayer, TileEntityArmorWorkbench entity){
		super(new ContainerArmorWorkbench(player, invPlayer, entity));
		this.workbench = entity;
		this.container = (ContainerArmorWorkbench)this.inventorySlots;
		this.player = player;
		this.xSize = 176;
		this.ySize = 200;
	}
	@Override
	public void initGui(){
		super.initGui();
		xCord = (this.width - this.xSize) / 2;
		yCord = (this.height - this.ySize) / 2;
		
		this.buttonList.add(new GuiButton(0, xCord + 57, yCord + 6, 10, 18, ""));
		this.buttonList.add(new GuiButton(1, xCord + 57, yCord + 26, 10, 18, ""));
		this.buttonList.add(new GuiButton(2, xCord + 57, yCord + 46, 10, 18, ""));
		this.buttonList.add(new GuiButton(3, xCord + 57, yCord + 66, 10, 18, ""));
		this.buttonList.add(new GuiButton(4, xCord + 57, yCord + 86, 10, 18, ""));

	}
	
	public void actionPerformed(GuiButton button){
			Main.network.sendToServer(new CardNumberMessage(button.id));
			if (button.id <= 4){
				if (isButton == false){
					this.buttonList.add(new GuiButton(5, xCord + 80, yCord + 20, 25, 20, "-1"));
					this.buttonList.add(new GuiButton(6, xCord + 105, yCord + 20, 25, 20, "-.1"));
					this.buttonList.add(new GuiButton(7, xCord + 130, yCord + 20, 25, 20, "-.05"));
					this.buttonList.add(new GuiButton(8, xCord + 80, yCord + 41, 25, 20, "+1"));
					this.buttonList.add(new GuiButton(9, xCord + 105, yCord + 41, 25, 20, "+.1"));
					this.buttonList.add(new GuiButton(10, xCord + 130, yCord + 41, 25, 20, "+.05"));
					this.isButton = true;
				}
			}

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
	
	public void recieveAmount(double upgradeAmount){
		this.upgrade = upgradeAmount;
		if (this.upgrade == -1){
			this.isEmpty = true;
		}else{
			this.isEmpty = false;
		}
		if (this.upgrade == -2){
			this.nullName = "null";
		}else{
			this.nullName = "none";
		}
	}
	
	public void recieveSlot(double slotNum){
		this.slotActivated = (int) slotNum;
		if (this.slotActivated == 0){
			this.isEmpty = true;
			this.upgrade = -1;
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(backgroundImage);
		xCord = (this.width - this.xSize) / 2;
		yCord = (this.height - this.ySize) / 2;
		drawTexturedModalRect(xCord, yCord, 0, 0, xSize, ySize);
		if (slotActivated != 0 && isEmpty == false){
			drawTexturedModalRect(xCord + 35, yCord - 13 + (slotActivated * 20), 177, 1, 4, 16);
		}
		if (this.isEmpty || this.upgrade == -2){
			this.fontRendererObj.drawString("Upgrade: " + this.nullName, xCord + 98, yCord + 10, 0x000e74);
			if (isButton == true){
				this.buttonList.remove(5);
				this.buttonList.remove(5);
				this.buttonList.remove(5);
				this.buttonList.remove(5);
				this.buttonList.remove(5);
				this.buttonList.remove(5);
				this.isButton = false;
			}
		}else{
			this.fontRendererObj.drawString("Upgrade: " + this.upgrade, xCord + 98, yCord + 10, 0x000e74);
		}

	}
	
}