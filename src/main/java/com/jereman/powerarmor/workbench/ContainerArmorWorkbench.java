package com.jereman.powerarmor.workbench;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

import scala.Console;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.jereman.powerarmor.ExtendedProperties;
import com.jereman.powerarmor.IElementHandler;
import com.jereman.powerarmor.Main;
import com.jereman.powerarmor.PowerCards;
import com.jereman.powerarmor.Reference;
import com.jereman.powerarmor.armor.PowerBase;
import com.jereman.powerarmor.init.JeremanItems;
import com.jereman.powerarmor.packets.GUIAmountMessage;
import com.jereman.powerarmor.packets.GUINameMessage;
import com.jereman.powerarmor.packets.GUISlotMessage;
import com.jereman.powerarmor.tileentities.TileEntityArmorWorkbench;
import com.jereman.powerarmor.PowerCards;

public class ContainerArmorWorkbench extends Container implements IElementHandler{
	public TileEntityArmorWorkbench workbench;
	public boolean shouldUpdate = true;
	public EntityPlayer player;
	ItemStack armor = null;
	public boolean nbttoGui,checkSlotValid,storeSlotItems,guitoNbt,sendValues, returnCards, containerCleanup;
	private static final int ARMOR_START = 27, ARMOR_END = ARMOR_START + 3,
			INV_START = 1, INV_END = INV_START+26,
			HOTBAR_START = 0, HOTBAR_END = HOTBAR_START+8;
	InventoryPlayer inventory;
	IInventory containerinv;
	protected TileEntityArmorWorkbench tileEntity;
	
	public Item slotOne, slotTwo, slotThree, slotFour, slotFive;
	public int slotSelected = 0;
	public boolean sentSlot = false;
	public boolean sentAmount = false;
	public boolean newArmor;
	public int upgradeNumber;
	private boolean sendCrashed;
	private World worldObj;
	public double slotOneAmount, slotTwoAmount, slotThreeAmount, slotFourAmount, slotFiveAmount;
	public boolean slotOneValid = false, slotTwoValid = false, slotThreeValid = false, slotFourValid = false, slotFiveValid = false;
	public double slotOneLimit, slotTwoLimit, slotThreeLimit, slotFourLimit, slotFiveLimit;
	
	public ContainerArmorWorkbench(final EntityPlayer player, InventoryPlayer invPlayer, TileEntityArmorWorkbench entity){
		int m;
		int armorOffset = 8;
		int invStartY = 117;
		int invStartX = 30;
		this.workbench = entity;
		inventory = invPlayer;
		this.player = player;
		this.worldObj = entity.getWorld();
		
		
		//Add slots from hotbar
		for (int x = 0; x < 9; x++){
			addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 173));
		}
		//Add slots from inventory
		for (m = 0; m < 3; ++m){
			for (int j = 0; j < 9; ++j){
				this.addSlotToContainer(new Slot(invPlayer, j + m * 9 + 9, 8 + j * 18, 115 + m * 18));
			}
		}
		//Add Armor slots to Container
		for (int i = 0; i < 4; ++i) {
		      final int k = i;
		      addSlotToContainer(new Slot(invPlayer, invPlayer.getSizeInventory() - 1 - i, armorOffset, 33 + i * 18) {

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
		        @Override
		        @SideOnly(Side.CLIENT)
                public String getSlotTexture()
                {
                    return ItemArmor.EMPTY_SLOT_NAMES[k];
                }
		      });
		    }
		
		//Add Card Slots to Container
		this.addSlotToContainer(new CardSlot(this.workbench, 0, 40, 7 + 20*0));
		this.addSlotToContainer(new CardSlot(this.workbench, 1, 40, 7 + 20*1));
		this.addSlotToContainer(new CardSlot(this.workbench, 2, 40, 7 + 20*2));
		this.addSlotToContainer(new CardSlot(this.workbench, 3, 40, 7 + 20*3));
		this.addSlotToContainer(new CardSlot(this.workbench, 4, 40, 7 + 20*4));
		//Add Armor Piece to Container
		this.addSlotToContainer(new ArmorpieceSlot(entity, 5, 8, 7));
	}
	

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.workbench.isUseableByPlayer(playerIn);
	}

	@Override
	public void detectAndSendChanges(){						//Container Update Tick
		super.detectAndSendChanges();
		if (this.armor == null || !this.armor.hasTagCompound()){
			if (workbench.getStackInSlot(5) != null){
				this.armor = workbench.getStackInSlot(5);
			}
		}
		if (workbench.getStackInSlot(5) != null){
			if (this.armor.getTagCompound().getString("RandomID") != workbench.getStackInSlot(5).getTagCompound().getString("RandomID")){	
				this.newArmor = true;
					Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
					//Put this code at the end, probably won't do anything but why not
				
			}else{
				this.newArmor = false;
			}
		}else{
			this.newArmor = false;
		}
		this.armor = workbench.getStackInSlot(5);
		if (armor != null){
			detectLimits();
			if (newArmor){
				this.sentSlot = false;
			}
			if (newArmor & Reference.DEBUG){
				//Console.println("New Armor");
			}
			nbttoGui();
			if (this.nbttoGui){
				checkSlotValid();
			}
			if (this.checkSlotValid){
				storeSlotItems();
			}
			if (this.checkSlotValid && this.storeSlotItems){
				guitoNbt();
			}
			if (this.guitoNbt){
				sendValues();
			}
			if (this.checkSlotValid && this.guitoNbt){
				returnCards();	//Removing things that shouldn't be removed instantly
			}
			this.nbttoGui = false;
			this.checkSlotValid = false;
			this.storeSlotItems = false;
			this.guitoNbt = false;
			this.sendValues = false;
			this.returnCards = false;
			
		}else{
			if (Reference.DEBUG){
				//Console.println("No Armor");
			}
			storeSlotItems();
			if (this.storeSlotItems){
				returnCards();
			}
			containerCleanup();
			this.returnCards = false;
			this.containerCleanup = false;
			this.storeSlotItems = false;
			this.slotSelected = 0;
			if (this.sentSlot == false && this.player instanceof EntityPlayerMP){
				Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
				this.sentSlot = true;
			}
			this.upgradeNumber = 0;
			this.shouldUpdate = true;
		}
	}
	
	public void storeSlotItems(){
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
		this.storeSlotItems = true;
	}
	
	public void sendValues(){
		if (this.sentAmount == false){
			switch (this.slotSelected){		//Sending code to GUI so it knows what slot is selected and amounts and stuff
				case 0:
					break;
				case 1:
					if (workbench.getStackInSlot(0) != null){
						if (workbench.getStackInSlot(0).getTagCompound().getDouble("UpgradeLimit") == 0){
							Main.network.sendTo(new GUIAmountMessage(-2), (EntityPlayerMP) this.player);
						}else{
							Main.network.sendTo(new GUIAmountMessage(this.slotOneAmount), (EntityPlayerMP) this.player);
						}
					}else{
						Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
					}
					break;
				case 2:
					if (workbench.getStackInSlot(1) != null){
						if (workbench.getStackInSlot(1).getTagCompound().getDouble("UpgradeLimit") == 0){
							Main.network.sendTo(new GUIAmountMessage(-2), (EntityPlayerMP) this.player);
						}else{
							Main.network.sendTo(new GUIAmountMessage(this.slotTwoAmount), (EntityPlayerMP) this.player);
						}
					}else{
						Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
					}
					break;
				case 3:
					if (workbench.getStackInSlot(2) != null){
						if (workbench.getStackInSlot(2).getTagCompound().getDouble("UpgradeLimit") == 0){
							Main.network.sendTo(new GUIAmountMessage(-2), (EntityPlayerMP) this.player);
						}else{
							Main.network.sendTo(new GUIAmountMessage(this.slotThreeAmount), (EntityPlayerMP) this.player);
						}
					}else{
						Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
					}
					break;
				case 4:
					if (workbench.getStackInSlot(4) != null){
						if (workbench.getStackInSlot(3).getTagCompound().getDouble("UpgradeLimit") == 0){
							Main.network.sendTo(new GUIAmountMessage(-2), (EntityPlayerMP) this.player);
						}else{
							Main.network.sendTo(new GUIAmountMessage(this.slotFourAmount), (EntityPlayerMP) this.player);
						}
					}else{
						Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
					}
					break;
				case 5:
					if (workbench.getStackInSlot(4) != null){
						if (workbench.getStackInSlot(4).getTagCompound().getDouble("UpgradeLimit") == 0){
							Main.network.sendTo(new GUIAmountMessage(-2), (EntityPlayerMP) this.player);
						}else{
							Main.network.sendTo(new GUIAmountMessage(this.slotFiveAmount), (EntityPlayerMP) this.player);
						}
					}else{
						Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
					}
					break;
			}
			this.sentAmount = true;
			}
		this.sendValues = true;
	}
	
	public void guitoNbt(){
		//Storing the data from slots
		if (Reference.DEBUG){
			//Console.println("Saving Data from slots");
		}
			if (this.slotOne != null && this.slotOneValid){
				if (workbench.getStackInSlot(0).hasTagCompound()){
					if (workbench.getStackInSlot(0).getTagCompound().hasKey("UpgradeLimit")){
						armor.getTagCompound().setString("SlotOne", slotOne.getUnlocalizedName());
						armor.getTagCompound().setDouble("SlotOneLimit", workbench.getStackInSlot(0).getTagCompound().getDouble("UpgradeLimit"));
					}
				}
			}else{
				armor.getTagCompound().setString("SlotOne", "none");
				armor.getTagCompound().setDouble("SlotOneLimit", 0);
			}
			if (this.slotTwo != null && this.slotTwoValid){
				if (workbench.getStackInSlot(1).hasTagCompound()){
					if (workbench.getStackInSlot(1).getTagCompound().hasKey("UpgradeLimit")){
						armor.getTagCompound().setString("SlotTwo", slotTwo.getUnlocalizedName());
						armor.getTagCompound().setDouble("SlotTwoLimit", workbench.getStackInSlot(1).getTagCompound().getDouble("UpgradeLimit"));					}
				}
			}else{
				armor.getTagCompound().setString("SlotTwo", "none");
				armor.getTagCompound().setDouble("SlotTwoLimit", 0);
			}
			if (this.slotThree !=null && this.slotThreeValid){
				if (workbench.getStackInSlot(2).hasTagCompound()){
					if (workbench.getStackInSlot(2).getTagCompound().hasKey("UpgradeLimit")){
						PowerBase.NBTUpgradeList("SlotThree", armor, slotThree.getUnlocalizedName());
						PowerBase.NBTUpgradeLimit("SlotThreeLimit", armor, workbench.getStackInSlot(2).getTagCompound().getDouble("UpgradeLimit"));
					}
				}
			}else{
				PowerBase.NBTUpgradeList("SlotThree", armor, "none");
				PowerBase.NBTUpgradeLimit("SlotThreeLimit", armor, 0);
			}
			if (this.slotFour != null && this.slotFourValid){
				if (workbench.getStackInSlot(3).hasTagCompound()){
					if (workbench.getStackInSlot(3).getTagCompound().hasKey("UpgradeLimit")){
						PowerBase.NBTUpgradeList("SlotFour", armor, slotFour.getUnlocalizedName());
						PowerBase.NBTUpgradeLimit("SlotFourLimit", armor, workbench.getStackInSlot(3).getTagCompound().getDouble("UpgradeLimit"));
					}
				}
			}else{
				PowerBase.NBTUpgradeList("SlotFour", armor, "none");
				PowerBase.NBTUpgradeLimit("SlotFourLimit", armor, 0);
			}
			if (this.slotFive != null && this.slotFiveValid){
				if (workbench.getStackInSlot(4).hasTagCompound()){
					if (workbench.getStackInSlot(4).getTagCompound().hasKey("UpgradeLimit")){
				PowerBase.NBTUpgradeList("SlotFive", armor, slotFive.getUnlocalizedName());
				PowerBase.NBTUpgradeLimit("SlotFiveLimit", armor, workbench.getStackInSlot(4).getTagCompound().getDouble("UpgradeLimit"));
					}
				}
			}else{
				PowerBase.NBTUpgradeList("SlotFive", armor, "none");
				PowerBase.NBTUpgradeLimit("SlotFiveLimit", armor, 0);
			}
			//Store new upgrade values on the armor
			switch (this.slotSelected){
			case 1:
				PowerBase.NBTUpgrades("SlotOneAmount", armor, this.slotOneAmount);
				break;
			case 2:
				PowerBase.NBTUpgrades("SlotTwoAmount", armor, this.slotTwoAmount);
				break;
			case 3:
				PowerBase.NBTUpgrades("SlotThreeAmount", armor, this.slotThreeAmount);
				break;
			case 4:
				PowerBase.NBTUpgrades("SlotFourAmount", armor, this.slotFourAmount);
				break;
			case 5:
				PowerBase.NBTUpgrades("SlotFiveAmount", armor, this.slotFiveAmount);
			}
			this.guitoNbt = true;
	}
	
	public void checkSlotValid(){
		//Checking if the cards are valid for the currently inserted armor piece
		if (workbench.getStackInSlot(0) != null){
			if (workbench.getStackInSlot(0).hasTagCompound()){
				if (workbench.getStackInSlot(0).getTagCompound().hasKey("ValidArmor")){
					if (workbench.getStackInSlot(0).getTagCompound().getString("ValidArmor").equals(armor.getUnlocalizedName().substring(5))){
						this.armor.getTagCompound().setBoolean("SlotOneValid", true);
						this.slotOneValid = true;
					}else if (workbench.getStackInSlot(0).getTagCompound().getString("ValidArmor").equals("all")){
						this.armor.getTagCompound().setBoolean("SlotOneValid", true);
						this.slotOneValid = true;
					}else if (!workbench.getStackInSlot(0).getTagCompound().getString("ValidArmor").equals(armor.getUnlocalizedName().substring(5))){
						this.armor.getTagCompound().setBoolean("SlotOneValid", false);
						this.slotOneValid = false;
					}
				}
			}
		}else{
			this.slotOneAmount = 0;
		}
		if (workbench.getStackInSlot(1) != null){
			if (workbench.getStackInSlot(1).getTagCompound() != null){
				if (workbench.getStackInSlot(1).getTagCompound().hasKey("ValidArmor")){
					if (workbench.getStackInSlot(1).getTagCompound().getString("ValidArmor").equals(armor.getUnlocalizedName().substring(5))){
						this.armor.getTagCompound().setBoolean("SlotTwoValid", true);
						this.slotTwoValid = true;
					}else if (workbench.getStackInSlot(1).getTagCompound().getString("ValidArmor").equals("all")){
						this.armor.getTagCompound().setBoolean("SlotTwoValid", true);
						this.slotTwoValid = true;
					}else if (!workbench.getStackInSlot(1).getTagCompound().getString("ValidArmor").equals(armor.getUnlocalizedName().substring(5))){
						this.armor.getTagCompound().setBoolean("SlotTwoValid", false);
						this.slotTwoValid = false;
					}
				}
			}
		}else{
			this.slotTwoAmount = 0;
		}
		if (workbench.getStackInSlot(2) != null){
			if (workbench.getStackInSlot(2).getTagCompound() != null){
				if (workbench.getStackInSlot(2).getTagCompound().hasKey("ValidArmor")){
					if (workbench.getStackInSlot(2).getTagCompound().getString("ValidArmor").equals(armor.getUnlocalizedName().substring(5))){
						this.armor.getTagCompound().setBoolean("SlotThreeValid", true);
						this.slotThreeValid = true;
					}else if (workbench.getStackInSlot(2).getTagCompound().getString("ValidArmor").equals("all")){
						this.armor.getTagCompound().setBoolean("SlotThreeValid", true);
						this.slotThreeValid = true;
					}else if (!workbench.getStackInSlot(2).getTagCompound().getString("ValidArmor").equals(armor.getUnlocalizedName().substring(5))){
						this.armor.getTagCompound().setBoolean("SlotThreeValid", false);
						this.slotThreeValid = false;
					}
				}
			}
		}else{
			this.slotThreeAmount = 0;
		}
		if (workbench.getStackInSlot(3) != null){
			if (workbench.getStackInSlot(3).getTagCompound() != null){
				if (workbench.getStackInSlot(3).getTagCompound().hasKey("ValidArmor")){
					if (workbench.getStackInSlot(3).getTagCompound().getString("ValidArmor").equals(armor.getUnlocalizedName().substring(5))){
						this.armor.getTagCompound().setBoolean("SlotFourValid", true);
						this.slotFourValid = true;
					}else if (workbench.getStackInSlot(3).getTagCompound().getString("ValidArmor").equals("all")){
						this.armor.getTagCompound().setBoolean("SlotFourValid", true);
						this.slotFourValid = true;
					}else if (!workbench.getStackInSlot(3).getTagCompound().getString("ValidArmor").equals(armor.getUnlocalizedName().substring(5))){
						this.armor.getTagCompound().setBoolean("SlotFourValid", false);
						this.slotFourValid = false;
					}
				}
			}
		}else{
			this.slotFourAmount = 0;
		}
		if (workbench.getStackInSlot(4) != null){
			if (workbench.getStackInSlot(4).getTagCompound() != null){
				if (workbench.getStackInSlot(4).getTagCompound().hasKey("ValidArmor")){
					if (workbench.getStackInSlot(4).getTagCompound().getString("ValidArmor").equals(armor.getUnlocalizedName().substring(5))){
						this.armor.getTagCompound().setBoolean("SlotFiveValid", true);
						this.slotFiveValid = true;
					}else if (workbench.getStackInSlot(4).getTagCompound().getString("ValidArmor").equals("all")){
						this.armor.getTagCompound().setBoolean("SlotFiveValid", true);
						this.slotFiveValid = true;
					}else if (!workbench.getStackInSlot(4).getTagCompound().getString("ValidArmor").equals(armor.getUnlocalizedName().substring(5))){
						this.armor.getTagCompound().setBoolean("SlotFiveValid", false);
						this.slotFiveValid = false;
					}
				}
			}
		}else{
			this.slotFiveAmount = 0;
		}
		
		//Invalidating slots if armor doesn't support that many slots
		if (armor.getTagCompound().hasKey("JeremanUpgradeNumber")){
			int upgradeNumber = armor.getTagCompound().getInteger("JeremanUpgradeNumber");
			this.upgradeNumber = upgradeNumber;
			switch (upgradeNumber){
				case 0:
					this.slotOneValid = false;
					this.slotTwoValid = false;
					this.slotThreeValid = false;
					this.slotFourValid = false;
					this.slotFiveValid = false;
					break;
				case 1:
					this.slotTwoValid = false;
					this.slotThreeValid = false;
					this.slotFourValid = false;
					this.slotFiveValid = false;
					break;
				case 2:
					this.slotThreeValid = false;
					this.slotFourValid = false;
					this.slotFiveValid = false;
					break;
				case 3:
					this.slotFourValid = false;
					this.slotFiveValid = false;
					break;
				case 4:
					this.slotFiveValid = false;
					break;
				case 5:
					break;
				default:
					break;
			}
		}
		//End validation code
		this.checkSlotValid = true;
	}
	
	public void nbttoGui(){		//Getting the Cards from NBT data and putting them in the gui
		if (armor.getTagCompound() != null && this.shouldUpdate == true){
			if (armor.getTagCompound().hasKey("SlotOne")){			//Slot One Code
				String tagOne =  armor.getTagCompound().getString("SlotOne");
				if (tagOne.equals("none")){
					workbench.setInventorySlotContents(0, null);
				}else if (!tagOne.equals("none")){
					Item inputItem1 = GameRegistry.findItem("powerarmor", armor.getTagCompound().getString("SlotOne").substring(5));
					workbench.setInventorySlotContents(0, new ItemStack(inputItem1));
					this.slotOneAmount = armor.getTagCompound().getDouble("SlotOneAmount");
					try {		//Adding the Metadata again
						workbench.getStackInSlot(0).setTagCompound(new NBTTagCompound());
						Method method = inputItem1.getClass().getMethod("onUpdate", ItemStack.class, World.class, Entity.class, int.class, boolean.class);
						method.invoke((Object) inputItem1, workbench.getStackInSlot(0), workbench.getWorld(), this.player, 0, true);
					}catch (Exception e) {
						if (Reference.DEBUG){
							Console.println(inputItem1.getUnlocalizedName());
							e.printStackTrace();
						}
					}
				}
			}
			if (armor.getTagCompound().hasKey("SlotTwo")){			//Slot Two Code
				if (!armor.getTagCompound().getString("SlotTwo").equals("none")){
					Item inputItem2 = GameRegistry.findItem("powerarmor", armor.getTagCompound().getString("SlotTwo").substring(5));
					workbench.setInventorySlotContents(1, new ItemStack(inputItem2));
					this.slotTwoAmount = armor.getTagCompound().getDouble("SlotTwoAmount");
					try {		//Adding the Metadata again
						workbench.getStackInSlot(1).setTagCompound(new NBTTagCompound());
						Method method = inputItem2.getClass().getMethod("onUpdate", ItemStack.class, World.class, Entity.class, int.class, boolean.class);
						method.invoke((Object) inputItem2, workbench.getStackInSlot(1), workbench.getWorld(), this.player, 0, true);
					}catch (Exception e) {
						if (Reference.DEBUG){
							e.printStackTrace();
						}
					}
				}else if (armor.getTagCompound().getString("SlotTwo").equals("none")){
					workbench.setInventorySlotContents(1, null);
				}
			}
			if (armor.getTagCompound().hasKey("SlotThree")){			//Slot Three Code
				if (!armor.getTagCompound().getString("SlotThree").equals("none")){
					Item inputItem3 = GameRegistry.findItem("powerarmor", armor.getTagCompound().getString("SlotThree").substring(5));
					workbench.setInventorySlotContents(2, new ItemStack(inputItem3));
					this.slotThreeAmount = armor.getTagCompound().getDouble("SlotThreeAmount");
					try {		//Adding the Metadata again
						workbench.getStackInSlot(2).setTagCompound(new NBTTagCompound());
						Method method = inputItem3.getClass().getMethod("onUpdate", ItemStack.class, World.class, Entity.class, int.class, boolean.class);
						method.invoke((Object) inputItem3, workbench.getStackInSlot(2), workbench.getWorld(), this.player, 0, true);
					}catch (Exception e) {
						if (Reference.DEBUG){
							e.printStackTrace();
						}
					}
				}else if (armor.getTagCompound().getString("SlotThree").equals("none")){
					workbench.setInventorySlotContents(2, null);
				}
			}
			if (armor.getTagCompound().hasKey("SlotFour")){			//Slot Four Code
				if (!armor.getTagCompound().getString("SlotFour").equals("none")){
					Item inputItem4 = GameRegistry.findItem("powerarmor", armor.getTagCompound().getString("SlotFour").substring(5));
					workbench.setInventorySlotContents(3, new ItemStack(inputItem4));
					this.slotFourAmount = armor.getTagCompound().getDouble("SlotFourAmount");
					try {		//Adding the Metadata again
						workbench.getStackInSlot(3).setTagCompound(new NBTTagCompound());
						Method method = inputItem4.getClass().getMethod("onUpdate", ItemStack.class, World.class, Entity.class, int.class, boolean.class);
						method.invoke((Object) inputItem4, workbench.getStackInSlot(3), workbench.getWorld(), this.player, 0, true);
					}catch (Exception e) {
						if (Reference.DEBUG){
							e.printStackTrace();
						}
					}
				}else if (armor.getTagCompound().getString("SlotFour").equals("none")){
					workbench.setInventorySlotContents(3, null);
				}
			}
			if (armor.getTagCompound().hasKey("SlotFive")){			//Slot Five Code
				if (!armor.getTagCompound().getString("SlotFive").equals("none")){
					Item inputItem5 = GameRegistry.findItem("powerarmor", armor.getTagCompound().getString("SlotFive").substring(5));
					workbench.setInventorySlotContents(4, new ItemStack(inputItem5));
					this.slotFiveAmount = armor.getTagCompound().getDouble("SlotFiveAmount");
					try {		//Adding the Metadata again
						workbench.getStackInSlot(4).setTagCompound(new NBTTagCompound());
						Method method = inputItem5.getClass().getMethod("onUpdate", ItemStack.class, World.class, Entity.class, int.class, boolean.class);
						method.invoke((Object) inputItem5, workbench.getStackInSlot(4), workbench.getWorld(), this.player, 0, true);
					}catch (Exception e) {
						if (Reference.DEBUG){
							e.printStackTrace();
						}
					}
				}else if (armor.getTagCompound().getString("SlotFive").equals("none")){
					workbench.setInventorySlotContents(4, null);
				}
			}
			this.shouldUpdate = false;
			workbench.markDirty();
		}
		this.nbttoGui = true;
	}
	
	public void detectLimits(){
		//Code to detect the limits on each card
		if (armor.hasTagCompound()){
			this.upgradeNumber = armor.getTagCompound().getInteger("JeremanUpgradeNumber");
			if (armor.getTagCompound().hasKey("SlotOneLimit")){
				this.slotOneLimit = armor.getTagCompound().getDouble("SlotOneLimit");
			}else if (!armor.getTagCompound().hasKey("SlotOneLimit")){
				this.slotOneLimit = 0;
			}
			if (armor.getTagCompound().hasKey("SlotTwoLimit")){
				this.slotTwoLimit = armor.getTagCompound().getDouble("SlotTwoLimit");
			}else if (!armor.getTagCompound().hasKey("SlotTwoLimit")){
				this.slotTwoLimit = 0;
			}
			if (armor.getTagCompound().hasKey("SlotThreeLimit")){
				this.slotThreeLimit = armor.getTagCompound().getDouble("SlotThreeLimit");
			}else if (!armor.getTagCompound().hasKey("SlotThreeLimit")){
				this.slotThreeLimit = 0;
			}
			if (armor.getTagCompound().hasKey("SlotFourLimit")){
				this.slotFourLimit = armor.getTagCompound().getDouble("SlotFourLimit");
			}else if (!armor.getTagCompound().hasKey("SlotFourLimit")){
				this.slotFourLimit = 0;
			}
			if (armor.getTagCompound().hasKey("SlotFiveLimit")){
				this.slotFiveLimit = armor.getTagCompound().getDouble("SlotFiveLimit");
			}else if (!armor.getTagCompound().hasKey("SlotFiveLimit")){
				this.slotFiveLimit = 0;
			}
		}
	}
	
	public void returnCards(){
		if (!this.slotOneValid && workbench.getStackInSlot(0) != null){
			this.player.inventory.addItemStackToInventory(new ItemStack(this.slotOne));
			workbench.setInventorySlotContents(0, null);
		}
		if (this.slotTwoValid == false && workbench.getStackInSlot(1) != null){
			this.player.inventory.addItemStackToInventory(new ItemStack(this.slotTwo));
			workbench.setInventorySlotContents(1, null);
		}
		if (this.slotThreeValid == false && workbench.getStackInSlot(2) != null){
			this.player.inventory.addItemStackToInventory(new ItemStack(this.slotThree));
			workbench.setInventorySlotContents(2, null);
		}
		if (this.slotFourValid == false && workbench.getStackInSlot(3) != null){
			this.player.inventory.addItemStackToInventory(new ItemStack(this.slotFour));
			workbench.setInventorySlotContents(3, null);
		}
		if (this.slotFiveValid == false && workbench.getStackInSlot(4) != null){
			this.player.inventory.addItemStackToInventory(new ItemStack(this.slotFive));
			workbench.setInventorySlotContents(4, null);
		}
		this.returnCards = true;
	}
	
	public void containerCleanup(){
		if (Reference.DEBUG){
			//Console.println("Container Cleanup");
		}
		if (this.armor == null){
		//removing if they are invalid
		if (this.slotOneValid){
			workbench.setInventorySlotContents(0, null);
			if (Reference.DEBUG){
				//Console.println("Removed Valid Item: Slot 0");
			}
		}
		if (this.slotTwoValid){
			workbench.setInventorySlotContents(1, null);
			if (Reference.DEBUG){
				//Console.println("Removed Valid Item: Slot 1");
			}
		}
		if (this.slotThreeValid){
			workbench.setInventorySlotContents(2, null);
			if (Reference.DEBUG){
				//Console.println("Removed Valid Item: Slot 2");
			}
		}
		if (this.slotFourValid){
			workbench.setInventorySlotContents(3, null);
			if (Reference.DEBUG){
				//Console.println("Removed Valid Item: Slot 3");
			}
		}
		if (this.slotFiveValid){
			workbench.setInventorySlotContents(4, null);
			if (Reference.DEBUG){
				//Console.println("Removed Valid Item: Slot 4");
			}
		}
		}
		workbench.markDirty();
		this.containerCleanup = true;
	}
	
		//Buttons to select the upgrade to modify
	public void buttonClick(int buttonId){
		switch (buttonId){
		case 0: //Button 1
			if (workbench.getStackInSlot(0) != null){
				if (armor.getTagCompound().hasKey("SlotOneAmount")){
					this.slotOneAmount = armor.getTagCompound().getDouble("SlotOneAmount");
				}else{
					Main.network.sendTo(new GUIAmountMessage(0), (EntityPlayerMP) this.player);
				}
				if (workbench.getStackInSlot(0).getTagCompound().hasKey("UpgradeLimit")){
					if (workbench.getStackInSlot(0).getTagCompound().getDouble("UpgradeLimit") == 0){
						Main.network.sendTo(new GUIAmountMessage(-2), (EntityPlayerMP) this.player);
					}else{
						Main.network.sendTo(new GUIAmountMessage(this.slotOneAmount), (EntityPlayerMP) this.player);
					}
				}
				Main.network.sendTo(new GUINameMessage(workbench.getStackInSlot(0)), (EntityPlayerMP) this.player);
				this.slotSelected = 1;
				Main.network.sendTo(new GUISlotMessage(this.slotSelected), (EntityPlayerMP) this.player);
			}else if (workbench.getStackInSlot(0) == null){
				Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
			}
			break;
		case 1: //Button 2
			if (workbench.getStackInSlot(1) != null){
				if (armor.getTagCompound().hasKey("SlotTwoAmount")){
					this.slotTwoAmount = armor.getTagCompound().getDouble("SlotTwoAmount");
				}else{
					Main.network.sendTo(new GUIAmountMessage(0), (EntityPlayerMP) this.player);
				}
				if (workbench.getStackInSlot(1).getTagCompound().hasKey("UpgradeLimit")){
					if (workbench.getStackInSlot(1).getTagCompound().getDouble("UpgradeLimit") == 0){
						Main.network.sendTo(new GUIAmountMessage(-2), (EntityPlayerMP) this.player);
					}else{
						Main.network.sendTo(new GUIAmountMessage(this.slotTwoAmount), (EntityPlayerMP) this.player);
					}
				}
				Main.network.sendTo(new GUINameMessage(workbench.getStackInSlot(1)), (EntityPlayerMP) this.player);
				this.slotSelected = 2;
				Main.network.sendTo(new GUISlotMessage(this.slotSelected), (EntityPlayerMP) this.player);
			}else if (workbench.getStackInSlot(1) == null){
				Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
			}
			break;
		case 2: //Button 3
			if (workbench.getStackInSlot(2) != null){
				if (armor.getTagCompound().hasKey("SlotThreeAmount")){
					this.slotThreeAmount = armor.getTagCompound().getDouble("SlotThreeAmount");
				}else{
					Main.network.sendTo(new GUIAmountMessage(0), (EntityPlayerMP) this.player);
				}
				if (workbench.getStackInSlot(2).getTagCompound().hasKey("UpgradeLimit")){
					if (workbench.getStackInSlot(2).getTagCompound().getDouble("UpgradeLimit") == 0){
						Main.network.sendTo(new GUIAmountMessage(-2), (EntityPlayerMP) this.player);
					}else{
						Main.network.sendTo(new GUIAmountMessage(this.slotThreeAmount), (EntityPlayerMP) this.player);
					}
				}
				Main.network.sendTo(new GUINameMessage(workbench.getStackInSlot(2)), (EntityPlayerMP) this.player);
				this.slotSelected = 3;
				Main.network.sendTo(new GUISlotMessage(this.slotSelected), (EntityPlayerMP) this.player);
			}else if (workbench.getStackInSlot(2) == null){
				Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
			}
			break;
		case 3: //Button 4
			if (workbench.getStackInSlot(3) != null){
				if (armor.getTagCompound().hasKey("SlotFourAmount")){
					this.slotFourAmount = armor.getTagCompound().getDouble("SlotFourAmount");
				}else{
					Main.network.sendTo(new GUIAmountMessage(0), (EntityPlayerMP) this.player);
				}
				if (workbench.getStackInSlot(3).getTagCompound().hasKey("UpgradeLimit")){
					if (workbench.getStackInSlot(3).getTagCompound().getDouble("UpgradeLimit") == 0){
						Main.network.sendTo(new GUIAmountMessage(-2), (EntityPlayerMP) this.player);
					}else{
						Main.network.sendTo(new GUIAmountMessage(this.slotFourAmount), (EntityPlayerMP) this.player);
					}
				}
				Main.network.sendTo(new GUINameMessage(workbench.getStackInSlot(3)), (EntityPlayerMP) this.player);
				this.slotSelected = 4;
				Main.network.sendTo(new GUISlotMessage(this.slotSelected), (EntityPlayerMP) this.player);
			}else if (workbench.getStackInSlot(3) == null){
				Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
			}
			break;
		case 4: //Button 5
			if (workbench.getStackInSlot(4) != null){
				if (armor.getTagCompound().hasKey("SlotFiveAmount")){
					this.slotFiveAmount = armor.getTagCompound().getDouble("SlotFiveAmount");
				}else{
					Main.network.sendTo(new GUIAmountMessage(0), (EntityPlayerMP) this.player);
				}
				if (workbench.getStackInSlot(4).getTagCompound().hasKey("UpgradeLimit")){
					if (workbench.getStackInSlot(4).getTagCompound().getDouble("UpgradeLimit") == 0){
						Main.network.sendTo(new GUIAmountMessage(-2), (EntityPlayerMP) this.player);
					}else{
						Main.network.sendTo(new GUIAmountMessage(this.slotFiveAmount), (EntityPlayerMP) this.player);
					}
				}
				Main.network.sendTo(new GUINameMessage(workbench.getStackInSlot(4)), (EntityPlayerMP) this.player);
				this.slotSelected = 5;
				Main.network.sendTo(new GUISlotMessage(this.slotSelected), (EntityPlayerMP) this.player);
			}else if (workbench.getStackInSlot(4) == null){
				Main.network.sendTo(new GUISlotMessage(0), (EntityPlayerMP) this.player);
			}
			break;
		case 5: //Upgrade -1
			switch (this.slotSelected){
			case 1:
				double slotOneish = round(this.slotOneAmount - 1.000, 2);
				this.slotOneAmount = slotOneish;
				if (this.slotOneAmount <= 0){
					this.slotOneAmount = 0.00;
				}
				if (this.slotOneAmount > this.slotOneLimit){
					this.slotOneAmount = this.slotOneLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotOneAmount), (EntityPlayerMP) this.player);
				break;
			case 2:
				double slotTwoish = round(this.slotTwoAmount - 1.000, 2);
				this.slotTwoAmount = slotTwoish;
				if (this.slotTwoAmount <= 0){
					this.slotTwoAmount = 0.00;
				}
				if (this.slotTwoAmount > this.slotTwoLimit){
					this.slotTwoAmount = this.slotTwoLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotTwoAmount), (EntityPlayerMP) this.player);
				break;
			case 3:
				double slotThreeish = round(this.slotThreeAmount - 1.000, 2);
				this.slotThreeAmount = slotThreeish;
				if (this.slotThreeAmount <= 0){
					this.slotThreeAmount = 0.00;
				}
				if (this.slotThreeAmount > this.slotThreeLimit){
					this.slotThreeAmount = this.slotThreeLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotThreeAmount), (EntityPlayerMP) this.player);
				break;
			case 4:
				double slotFourish = round(this.slotFourAmount - 1.000, 2);
				this.slotFourAmount = slotFourish;
				if (this.slotFourAmount <= 0){
					this.slotFourAmount = 0.00;
				}
				if (this.slotFourAmount > this.slotFourLimit){
					this.slotFourAmount = this.slotFourLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFourAmount), (EntityPlayerMP) this.player);
				break;
			case 5:
				double slotFiveish = round(this.slotFourAmount - 1.000, 2);
				this.slotFiveAmount = slotFiveish;
				if (this.slotFiveAmount <= 0){
					this.slotFiveAmount = 0.00;
				}
				if (this.slotFiveAmount > this.slotFiveLimit){
					this.slotFiveAmount = this.slotFiveLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFiveAmount), (EntityPlayerMP) this.player);
				break;
			}
			break;
		case 6: //Upgrade -.1
			switch (this.slotSelected){
			case 1:
				double slotOneish = round(this.slotOneAmount - .1000, 2);
				this.slotOneAmount = slotOneish;
				if (this.slotOneAmount <= 0){
					this.slotOneAmount = 0.00;
				}
				if (this.slotOneAmount > this.slotOneLimit){
					this.slotOneAmount = this.slotOneLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotOneAmount), (EntityPlayerMP) this.player);
				break;
			case 2:
				double slotTwoish = round(this.slotTwoAmount - .1000, 2);
				this.slotTwoAmount = slotTwoish;
				if (this.slotTwoAmount <= 0){
					this.slotTwoAmount = 0.00;
				}
				if (this.slotTwoAmount > this.slotTwoLimit){
					this.slotTwoAmount = this.slotTwoLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotTwoAmount), (EntityPlayerMP) this.player);
				break;
			case 3:
				double slotThreeish = round(this.slotThreeAmount - .1000, 2);
				this.slotThreeAmount = slotThreeish;
				if (this.slotThreeAmount <= 0){
					this.slotThreeAmount = 0.00;
				}
				if (this.slotThreeAmount > this.slotThreeLimit){
					this.slotThreeAmount = this.slotThreeLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotThreeAmount), (EntityPlayerMP) this.player);
				break;
			case 4:
				double slotFourish = round(this.slotFourAmount - .1000, 2);
				this.slotFourAmount = slotFourish;
				if (this.slotFourAmount <= 0){
					this.slotFourAmount = 0.00;
				}
				if (this.slotFourAmount > this.slotFourLimit){
					this.slotFourAmount = this.slotFourLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFourAmount), (EntityPlayerMP) this.player);
				break;
			case 5:
				double slotFiveish = round(this.slotFourAmount - .1000, 2);
				this.slotFiveAmount = slotFiveish;
				if (this.slotFiveAmount <= 0){
					this.slotFiveAmount = 0.00;
				}
				if (this.slotFiveAmount > this.slotFiveLimit){
					this.slotFiveAmount = this.slotFiveLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFiveAmount), (EntityPlayerMP) this.player);
				break;
			}
			break;
		case 7: //Upgrade -.05
			switch (this.slotSelected){
			case 1:
				double slotOneish = round(this.slotOneAmount - .0500, 2);
				this.slotOneAmount = slotOneish;
				if (this.slotOneAmount <= 0){
					this.slotOneAmount = 0.00;
				}
				if (this.slotOneAmount > this.slotOneLimit){
					this.slotOneAmount = this.slotOneLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotOneAmount), (EntityPlayerMP) this.player);
				break;
			case 2:
				double slotTwoish = round(this.slotTwoAmount - .0500, 2);
				this.slotTwoAmount = slotTwoish;
				if (this.slotTwoAmount <= 0){
					this.slotTwoAmount = 0.00;
				}
				if (this.slotTwoAmount > this.slotTwoLimit){
					this.slotTwoAmount = this.slotTwoLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotTwoAmount), (EntityPlayerMP) this.player);
				break;
			case 3:
				double slotThreeish = round(this.slotThreeAmount - .0500, 2);
				this.slotThreeAmount = slotThreeish;
				if (this.slotThreeAmount <= 0){
					this.slotThreeAmount = 0.00;
				}
				if (this.slotThreeAmount > this.slotThreeLimit){
					this.slotThreeAmount = this.slotThreeLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotThreeAmount), (EntityPlayerMP) this.player);
				break;
			case 4:
				double slotFourish = round(this.slotFourAmount - .0500, 2);
				this.slotFourAmount = slotFourish;
				if (this.slotFourAmount <= 0){
					this.slotFourAmount = 0.00;
				}
				if (this.slotFourAmount > this.slotFourLimit){
					this.slotFourAmount = this.slotFourLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFourAmount), (EntityPlayerMP) this.player);
				break;
			case 5:
				double slotFiveish = round(this.slotFourAmount - .0500, 2);
				this.slotFiveAmount = slotFiveish;
				if (this.slotFiveAmount <= 0){
					this.slotFiveAmount = 0.00;
				}
				if (this.slotFiveAmount > this.slotFiveLimit){
					this.slotFiveAmount = this.slotFiveLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFiveAmount), (EntityPlayerMP) this.player);
				break;
			}
			break;
		case 8: //Upgrade +1
			switch (this.slotSelected){
			case 1:
				double slotOneish = round(this.slotOneAmount + 1.000, 2);
				this.slotOneAmount = slotOneish;
				if (this.slotOneAmount <= 0){
					this.slotOneAmount = 0.00;
				}
				if (this.slotOneAmount > this.slotOneLimit){
					this.slotOneAmount = this.slotOneLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotOneAmount), (EntityPlayerMP) this.player);
				break;
			case 2:
				double slotTwoish = round(this.slotTwoAmount + 1.000, 2);
				this.slotTwoAmount = slotTwoish;
				if (this.slotTwoAmount <= 0){
					this.slotTwoAmount = 0.00;
				}
				if (this.slotTwoAmount > this.slotTwoLimit){
					this.slotTwoAmount = this.slotTwoLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotTwoAmount), (EntityPlayerMP) this.player);
				break;
			case 3:
				double slotThreeish = round(this.slotThreeAmount + 1.000, 2);
				this.slotThreeAmount = slotThreeish;
				if (this.slotThreeAmount <= 0){
					this.slotThreeAmount = 0.00;
				}
				if (this.slotThreeAmount > this.slotThreeLimit){
					this.slotThreeAmount = this.slotThreeLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotThreeAmount), (EntityPlayerMP) this.player);
				break;
			case 4:
				double slotFourish = round(this.slotFourAmount + 1.000, 2);
				this.slotFourAmount = slotFourish;
				if (this.slotFourAmount <= 0){
					this.slotFourAmount = 0.00;
				}
				if (this.slotFourAmount > this.slotFourLimit){
					this.slotFourAmount = this.slotFourLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFourAmount), (EntityPlayerMP) this.player);
				break;
			case 5:
				double slotFiveish = round(this.slotFourAmount + 1.000, 2);
				this.slotFiveAmount = slotFiveish;
				if (this.slotFiveAmount <= 0){
					this.slotFiveAmount = 0.00;
				}
				if (this.slotFiveAmount > this.slotFiveLimit){
					this.slotFiveAmount = this.slotFiveLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFiveAmount), (EntityPlayerMP) this.player);
				break;
			}
			break;
		case 9:	//Upgrade +.1
			switch (this.slotSelected){
			case 1:
				double slotOneish = round(this.slotOneAmount + .1000, 2);
				this.slotOneAmount = slotOneish;
				if (this.slotOneAmount <= 0){
					this.slotOneAmount = 0.00;
				}
				if (this.slotOneAmount > this.slotOneLimit){
					this.slotOneAmount = this.slotOneLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotOneAmount), (EntityPlayerMP) this.player);
				break;
			case 2:
				double slotTwoish = round(this.slotTwoAmount + .1000, 2);
				this.slotTwoAmount = slotTwoish;
				if (this.slotTwoAmount <= 0){
					this.slotTwoAmount = 0.00;
				}
				if (this.slotTwoAmount > this.slotTwoLimit){
					this.slotTwoAmount = this.slotTwoLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotTwoAmount), (EntityPlayerMP) this.player);
				break;
			case 3:
				double slotThreeish = round(this.slotThreeAmount + .1000, 2);
				this.slotThreeAmount = slotThreeish;
				if (this.slotThreeAmount <= 0){
					this.slotThreeAmount = 0.00;
				}
				if (this.slotThreeAmount > this.slotThreeLimit){
					this.slotThreeAmount = this.slotThreeLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotThreeAmount), (EntityPlayerMP) this.player);
				break;
			case 4:
				double slotFourish = round(this.slotFourAmount + .1000, 2);
				this.slotFourAmount = slotFourish;
				if (this.slotFourAmount <= 0){
					this.slotFourAmount = 0.00;
				}
				if (this.slotFourAmount > this.slotFourLimit){
					this.slotFourAmount = this.slotFourLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFourAmount), (EntityPlayerMP) this.player);
				break;
			case 5:
				double slotFiveish = round(this.slotFourAmount + .1000, 2);
				this.slotFiveAmount = slotFiveish;
				if (this.slotFiveAmount <= 0){
					this.slotFiveAmount = 0.00;
				}
				if (this.slotFiveAmount > this.slotFiveLimit){
					this.slotFiveAmount = this.slotFiveLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFiveAmount), (EntityPlayerMP) this.player);
				break;
			}
			break;
		case 10:
			switch (this.slotSelected){
			case 1:
				double slotOneish = round(this.slotOneAmount + .0500, 2);
				this.slotOneAmount = slotOneish;
				if (this.slotOneAmount <= 0){
					this.slotOneAmount = 0.00;
				}
				if (this.slotOneAmount > this.slotOneLimit){
					this.slotOneAmount = this.slotOneLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotOneAmount), (EntityPlayerMP) this.player);
				break;
			case 2:
				double slotTwoish = round(this.slotTwoAmount + .0500, 2);
				this.slotTwoAmount = slotTwoish;
				if (this.slotTwoAmount <= 0){
					this.slotTwoAmount = 0.00;
				}
				if (this.slotTwoAmount > this.slotTwoLimit){
					this.slotTwoAmount = this.slotTwoLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotTwoAmount), (EntityPlayerMP) this.player);
				break;
			case 3:
				double slotThreeish = round(this.slotThreeAmount + .0500, 2);
				this.slotThreeAmount = slotThreeish;
				if (this.slotThreeAmount <= 0){
					this.slotThreeAmount = 0.00;
				}
				if (this.slotThreeAmount > this.slotThreeLimit){
					this.slotThreeAmount = this.slotThreeLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotThreeAmount), (EntityPlayerMP) this.player);
				break;
			case 4:
				double slotFourish = round(this.slotFourAmount + .0500, 2);
				this.slotFourAmount = slotFourish;
				if (this.slotFourAmount <= 0){
					this.slotFourAmount = 0.00;
				}
				if (this.slotFourAmount > this.slotFourLimit){
					this.slotFourAmount = this.slotFourLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFourAmount), (EntityPlayerMP) this.player);
				break;
			case 5:
				double slotFiveish = round(this.slotFourAmount + .0500, 2);
				this.slotFiveAmount = slotFiveish;
				if (this.slotFiveAmount <= 0){
					this.slotFiveAmount = 0.00;
				}
				if (this.slotFiveAmount > this.slotFiveLimit){
					this.slotFiveAmount = this.slotFiveLimit;
				}
				Main.network.sendTo(new GUIAmountMessage(this.slotFiveAmount), (EntityPlayerMP) this.player);
				break;
			}
			break;
		default:
			break;
		}	
	}
	
	//Function to round doubles, makes things nicer...
		public static double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    BigDecimal bd = new BigDecimal(value);
		    bd = bd.setScale(places, RoundingMode.HALF_UP);
		    return bd.doubleValue();
		}
		
	@Override
	public void onContainerClosed(EntityPlayer playerIn){
        super.onContainerClosed(playerIn);

        if (!this.worldObj.isRemote)
        {
                ItemStack itemstack = this.workbench.getStackInSlotOnClosing(5);

                if (itemstack != null)
                {
                    playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
                }
                containerCleanup();
        }
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
         //Still need to work on custom slots/items, don't break it the next time you try...  
		 ItemStack itemstack = null;
		 Slot slot = (Slot)this.inventorySlots.get(index);
		 if (slot != null && slot.getHasStack()){
			 ItemStack itemstack1 = slot.getStack();
             itemstack = itemstack1.copy();
             
             if (index <= 8){
                 if (!this.mergeItemStack(itemstack1, 9, 46, false)){
                	 return null;
                 }
             }
             if (index > 8 && index < 48){
            	 if (!this.mergeItemStack(itemstack1, 0, 9, false)){
            		 return null;
            	 }
             }
             if (itemstack1.stackSize == 0){
                 slot.putStack((ItemStack) null);
             }else{
                 slot.onSlotChanged();
             }
		 }
		 return null;
	 }
}
