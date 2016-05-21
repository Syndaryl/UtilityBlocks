package org.syndaryl.utilityblocks.block;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.syndaryl.utilityblocks.UtilityBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCompressed extends Block {
	private String name;
	private boolean isFuel;
	private ItemBlock item;
	
	public BlockCompressed(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

    public BlockCompressed(Material ground, String blockName,
			SoundType soundType, float hardness, float blastResistance) {

    	super(ground);
    	this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		  this.blockSoundType = soundType;
		  this.name = blockName;
		  setUnlocalizedName(getName());
		  BlockManager.registerBlockWithItem(this, this.getName(), null);
		  ResourceLocation location = new ResourceLocation(UtilityBlocks.MODID, blockName);
          GameRegistry.register(this, location);
		  this.setHardness(hardness);
		  this.setFuel(false);
	}

	public BlockCompressed(Material ground, String blockName,
			SoundType soundType, float hardness, float blastResistance, boolean isFuel) {
		this(ground, blockName,
				 soundType, hardness, blastResistance);
		this.setFuel(isFuel);
		
	}

	public Item getItemDropped(int p_149650_1_, Random randomizer, int fortune)
    {
    	return Item.getItemFromBlock(this);
    }
	
	public String getName()
	{
		return name;
	}

	public boolean isFuel() {
		return isFuel;
	}

	public void setFuel(boolean isFuel) {
		this.isFuel = isFuel;
	}

	/**
	 * @return the item
	 */
	public ItemBlock getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(ItemBlock item) {
		this.item = item;
	}

//	@Override
//	public String getUnlocalizedName(){
//        return this.getName();
//	}
}
