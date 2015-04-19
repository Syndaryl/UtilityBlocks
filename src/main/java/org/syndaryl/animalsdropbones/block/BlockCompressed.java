package org.syndaryl.animalsdropbones.block;

import java.util.Random;

import org.syndaryl.animalsdropbones.AnimalsDropBones;
import org.syndaryl.animalsdropbones.NamespaceManager;
import org.syndaryl.animalsdropbones.handler.FurnaceFuelHandler;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCompressed extends Block  {
	private String name;
	private boolean isFuel;
	
	public BlockCompressed(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

    public BlockCompressed(Material ground, String blockName,
			SoundType soundType, float hardness, float blastResistance) {

    	super(ground);
    	this.setCreativeTab(CreativeTabs.tabBlock);
		  this.stepSound = soundType;
		  this.name = blockName;
		  setUnlocalizedName(getName());
		  //this.setBlockTextureName(NamespaceManager.GetModNameLC()+":"+blockName);
		  GameRegistry.registerBlock(this, getName());
		  this.setHardness(hardness);
		  this.isFuel = false;
	}

	public BlockCompressed(Material ground, String blockName,
			SoundType soundType, float hardness, float blastResistance, boolean isFuel) {
		this(ground, blockName,
				 soundType, hardness, blastResistance);
		this.isFuel = isFuel;
		
	}

	public Item getItemDropped(int p_149650_1_, Random randomizer, int fortune)
    {
    	return Item.getItemFromBlock(this);
    }
	
	public String getName()
	{
		return name;
	}
	

//	@Override
//	public String getUnlocalizedName(){
//        return this.getName();
//	}
}
