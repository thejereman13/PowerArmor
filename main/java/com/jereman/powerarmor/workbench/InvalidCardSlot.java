package com.jereman.powerarmor.workbench;

import com.jereman.powerarmor.PowerCards;
import com.jereman.powerarmor.items.CardJump;
import com.jereman.powerarmor.items.CardSpeed;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InvalidCardSlot extends Slot{
	
	private final int slotIndex;
	private final IInventory inventory;
	
	public InvalidCardSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.inventory = inventoryIn;
        this.slotIndex = index;
        this.xDisplayPosition = xPosition;
        this.yDisplayPosition = yPosition;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack){
		return false;
	}
	
	@Override
	public int getSlotStackLimit(){
		return 1;
		
	}



}
