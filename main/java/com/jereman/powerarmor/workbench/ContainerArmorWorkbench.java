package com.jereman.powerarmor.workbench;

import scala.Console;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.jereman.powerarmor.IElementHandler;
import com.jereman.powerarmor.armor.PowerChest;
import com.jereman.powerarmor.gui.GuiArmorWorkbench;
import com.jereman.powerarmor.init.JeremanItems;
import com.jereman.powerarmor.tileentities.TileEntityArmorWorkbench;

public class ContainerArmorWorkbench extends Container implements IElementHandler{
	private TileEntityArmorWorkbench workbench;
	public double SpeedCurrent;
	public boolean shouldUpdate = true;
	
	private static final int ARMOR_START = 27, ARMOR_END = ARMOR_START + 3,
			INV_START = 1, INV_END = INV_START+26,
			HOTBAR_START = 0, HOTBAR_END = HOTBAR_START+8;
	InventoryPlayer inventory;
	IInventory containerinv;
	protected TileEntityArmorWorkbench tileEntity;
	
	public Item slotOne, slotTwo, slotThree, slotFour, slotFive;
	
	public ContainerArmorWorkbench(final EntityPlayer player, InventoryPlayer invPlayer, TileEntityArmorWorkbench entity){
		int m;
		int armorOffset = 6;
		int invStartY = 117;
		int invStartX = 30;
		this.workbench = entity;
		inventory = invPlayer;
		
		//Add slots from hotbar
		for (int x = 0; x < 9; x++){
			addSlotToContainer(new Slot(invPlayer, x, 29 + x * 18, 174));
		}
		//Add slots from inventory
		for (m = 0; m < 3; ++m){
			for (int j = 0; j < 9; ++j){
				this.addSlotToContainer(new Slot(invPlayer, j + m * 9 + 9, 29 + j * 18, 116 + m * 18));
			}
		}
		//Add Armor slots to Container
		for (int i = 0; i < 4; ++i) {
		      final int k = i;
		      addSlotToContainer(new Slot(invPlayer, invPlayer.getSizeInventory() - 1 - i, armorOffset, 62 + i * 18) {

		        @Override
		        public int getSlotStackLimit() {
		          return 1;
		        }

		        @Override
		        public boolean isItemValid(ItemStack par1ItemStack) {
		          if(par1ItemStack == null) {
		            return false;
		          }
		          return par1ItemStack.getItem().isValidArmor(par1ItemStack, k, player);
		        }

		        /* @Override
		        @SideOnly(Side.CLIENT)
		        public IIcon getBackgroundIconIndex() {
		          return ItemArmor.func_94602_b(k);
		        } */
		      });
		    }
		
		//Add Card Slots to Container
		for (int y = 0; y < 5; y++){
			this.addSlotToContainer(new CardSlot(entity, y, 29, 8 + 20*y));
		}
		//Add Armor Piece to Container
		this.addSlotToContainer(new ArmorpieceSlot(entity, 5, 6, 14));
	}
	

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.workbench.isUseableByPlayer(playerIn);
	}
	
	@Override
	public void detectAndSendChanges(){						//Container Update Tick
		super.detectAndSendChanges();
		ItemStack armor = workbench.getStackInSlot(5);
		if (workbench.getStackInSlot(0)!= null){
		this.slotOne = workbench.getStackInSlot(0).getItem();
		}else{
			this.slotOne = null;
		}
		if (workbench.getStackInSlot(1) != null){
		this.slotTwo = workbench.getStackInSlot(1).getItem();
		}else{
			this.slotTwo = null;
		}
		if (workbench.getStackInSlot(2) != null){
		this.slotThree = workbench.getStackInSlot(2).getItem();
		}else{
			this.slotThree = null;
		}
		if (workbench.getStackInSlot(3) != null){
		this.slotFour = workbench.getStackInSlot(3).getItem();
		}else{
			this.slotFour = null;
		}
		if (workbench.getStackInSlot(4) != null){
		this.slotFive = workbench.getStackInSlot(4).getItem();
		}else{
			this.slotFive = null;
		}
		
		if (armor != null){
			if (armor.getItem() instanceof PowerChest){
				
				//Getting the Cards from NBT data and putting them in the gui
			if (armor.getTagCompound() != null & this.shouldUpdate == true){
				if (armor.getTagCompound().getString("SlotOne") != null){
					String tagOne =  armor.getTagCompound().getString("SlotOne");
					Console.println("Tag One: " + tagOne);
					if (tagOne.equals("none")){
						Console.println("Der be nothing");
						workbench.setInventorySlotContents(0, null);
					}else if (!tagOne.equals("none")){
						Console.println("Item: " + armor.getTagCompound().getString("SlotOne"));
						Item inputItem1 = GameRegistry.findItem("powerarmor", armor.getTagCompound().getString("SlotOne").substring(5));
						workbench.setInventorySlotContents(0, new ItemStack(inputItem1));
					} 
				}
				if (armor.getTagCompound().getString("SlotTwo") != null){
					if (!armor.getTagCompound().getString("SlotTwo").equals("none")){
						Console.println("Item: " + armor.getTagCompound().getString("SlotTwo"));
						Item inputItem2 = GameRegistry.findItem("powerarmor", armor.getTagCompound().getString("SlotTwo").substring(5));
						workbench.setInventorySlotContents(1, new ItemStack(inputItem2));
					}else if (armor.getTagCompound().getString("SlotTwo").equals("none")){
						workbench.setInventorySlotContents(1, null);
					}
				}
				if (armor.getTagCompound().getString("SlotThree") != null){
					if (!armor.getTagCompound().getString("SlotThree").equals("none")){
						Console.println("Item: " + armor.getTagCompound().getString("SlotThree"));
						Item inputItem3 = GameRegistry.findItem("powerarmor", armor.getTagCompound().getString("SlotThree").substring(5));
						workbench.setInventorySlotContents(2, new ItemStack(inputItem3));
					}else if (armor.getTagCompound().getString("SlotThree").equals("none")){
						workbench.setInventorySlotContents(2, null);
					}
				}
				if (armor.getTagCompound().getString("SlotFour") != null){
					if (!armor.getTagCompound().getString("SlotFour").equals("none")){
						Console.println("Item: " + armor.getTagCompound().getString("SlotFour"));
						Item inputItem4 = GameRegistry.findItem("powerarmor", armor.getTagCompound().getString("SlotFour").substring(5));
						workbench.setInventorySlotContents(3, new ItemStack(inputItem4));
					}else if (armor.getTagCompound().getString("SlotFour").equals("none")){
						workbench.setInventorySlotContents(3, null);
					}
				}
				if (armor.getTagCompound().getString("SlotFive") != null){
					if (!armor.getTagCompound().getString("SlotFive").equals("none")){
						Console.println("Item: " + armor.getTagCompound().getString("SlotFive"));
						Item inputItem5 = GameRegistry.findItem("powerarmor", armor.getTagCompound().getString("SlotFive").substring(5));
						workbench.setInventorySlotContents(4, new ItemStack(inputItem5));
					}else if (armor.getTagCompound().getString("SlotFive").equals("none")){
						workbench.setInventorySlotContents(4, null);
					}
				}
				this.shouldUpdate = false;
				workbench.markDirty();
			}else{
				
			}
				//Storing the data from slots
				if (slotOne != null){
					PowerChest.NBTUpgradeList("SlotOne", armor, slotOne.getUnlocalizedName());
					Console.println(slotOne.getUnlocalizedName());
				}else{
					PowerChest.NBTUpgradeList("SlotOne", armor, "none");
				}
				if (slotTwo != null){
					PowerChest.NBTUpgradeList("SlotTwo", armor, slotTwo.getUnlocalizedName());
					Console.println(slotTwo.getUnlocalizedName());
				}else{
					PowerChest.NBTUpgradeList("SlotTwo", armor, "none");
				}
				if (slotThree !=null){
					PowerChest.NBTUpgradeList("SlotThree", armor, slotThree.getUnlocalizedName());
					Console.println(slotThree.getUnlocalizedName());
				}else{
					PowerChest.NBTUpgradeList("SlotThree", armor, "none");
				}
				if (slotFour != null){
					PowerChest.NBTUpgradeList("SlotFour", armor, slotFour.getUnlocalizedName());
					Console.println(slotFour.getUnlocalizedName());
				}else{
					PowerChest.NBTUpgradeList("SlotFour", armor, "none");
				}
				if (slotFive != null){
					PowerChest.NBTUpgradeList("SlotFive", armor, slotFive.getUnlocalizedName());
					Console.println(slotFive.getUnlocalizedName());
				}else{
					PowerChest.NBTUpgradeList("SlotFive", armor, "none");
				}
				
				
			}
		}else{
			for (int i = 0; i < 5; i++){
				workbench.setInventorySlotContents(i, null);
				this.shouldUpdate = true;
			}
		}
	}
	
	public void buttonClick(int buttonId){
		Console.println("Test");
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean backwards)
	{
		boolean flag1 = false;
		int k = (backwards ? end - 1 : start);
		Slot slot;
		ItemStack itemstack1;


		if (stack.isStackable())
		{
			while (stack.stackSize > 0 && (!backwards && k < end || backwards && k >= start))
			{
				slot = (Slot) inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (!slot.isItemValid(stack)) {
					k += (backwards ? -1 : 1);
					continue;
				}

				if (itemstack1 != null && itemstack1.getItem() == stack.getItem() &&
						(!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) &&
						ItemStack.areItemStackTagsEqual(stack, itemstack1))
				{
					int l = itemstack1.stackSize + stack.stackSize;

					if (l <= stack.getMaxStackSize() && l <= slot.getSlotStackLimit()) {
						stack.stackSize = 0;
						itemstack1.stackSize = l;
						inventory.markDirty();
						flag1 = true;
					} else if (itemstack1.stackSize < stack.getMaxStackSize() && l < slot.getSlotStackLimit()) {
						stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
						itemstack1.stackSize = stack.getMaxStackSize();
						inventory.markDirty();
						flag1 = true;
					}
				}

				k += (backwards ? -1 : 1);
			}
		}

		if (stack.stackSize > 0)
		{
			k = (backwards ? end - 1 : start);

			while (!backwards && k < end || backwards && k >= start) {
				slot = (Slot) inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (!slot.isItemValid(stack)) {
					k += (backwards ? -1 : 1);
					continue;
				}

				if (itemstack1 == null) {
					int l = stack.stackSize;

					if (l <= slot.getSlotStackLimit()) {
						slot.putStack(stack.copy());
						stack.stackSize = 0;
						inventory.markDirty();
						flag1 = true;
						break;
					} else {
						putStackInSlot(k, new ItemStack(stack.getItem(), slot.getSlotStackLimit(), stack.getItemDamage()));
						stack.stackSize -= slot.getSlotStackLimit();
						inventory.markDirty();
						flag1 = true;
					}
				}

				k += (backwards ? -1 : 1);
			}
		}

		return flag1;
	}
	
	 @Override
     public ItemStack transferStackInSlot(EntityPlayer player, int index) {
             return null;
             /*ItemStack itemstack = null;
             Slot slot = (Slot)this.inventorySlots.get(index);

             if (slot != null && slot.getHasStack())
             {
                 ItemStack itemstack1 = slot.getStack();
                 itemstack = itemstack1.copy();

                 if (index < this.numRows * 9)
                 {
                     if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                     {
                         return null;
                     }
                 }
                 else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
                 {
                     return null;
                 }

                 if (itemstack1.stackSize == 0)
                 {
                     slot.putStack((ItemStack)null);
                 }
                 else
                 {
                     slot.onSlotChanged();
                 }
             }

             return itemstack;
         }*/
     }
}
