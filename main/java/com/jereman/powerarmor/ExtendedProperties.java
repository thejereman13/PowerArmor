package com.jereman.powerarmor;

import scala.Console;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedProperties implements IExtendedEntityProperties{
	
	public static final String identifier = Reference.MOD_ID + "_properties";
	private EntityPlayer player;
	
	public boolean armorChestWorn;
	public boolean armorPantsWorn;
	
	public ExtendedProperties(EntityPlayer player){
		this.player = player;
		this.armorChestWorn = false;
		this.armorPantsWorn = false;
	}
	
	public static final void register(EntityPlayer player){
		player.registerExtendedProperties(ExtendedProperties.identifier, new ExtendedProperties(player));
	}
	
	public static final ExtendedProperties get(EntityPlayer player){
		return (ExtendedProperties) player.getExtendedProperties(identifier);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		
		properties.setBoolean("ChestWorn", armorChestWorn);
		properties.setBoolean("PantsWorn", armorPantsWorn);
		compound.setTag(identifier, properties);
		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(identifier);
		
		this.armorChestWorn = compound.getBoolean("ChestWorn");
		this.armorPantsWorn = compound.getBoolean("PantsWorn");
	}

	@Override
	public void init(Entity entity, World world) {
		//nothing to do here...YET!
	}
	
	public boolean getChestPlate(){
		return this.armorChestWorn;
	}
	public void setChestPlate(boolean value){
		this.armorChestWorn = value;
	}
	
	public boolean getLeggings(){
		return this.armorPantsWorn;
	}
	public void setLeggings(boolean value){
		this.armorPantsWorn = value;
	}

}
