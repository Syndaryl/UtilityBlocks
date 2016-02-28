package org.syndaryl.utilityblocks.handler;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.commons.lang3.tuple.Pair;
import org.syndaryl.utilityblocks.UtilityBlocks;

public class FurnaceFuelHandler implements IFuelHandler {

	private HashMap<Pair<Item, Integer>, Integer> fuelList = new HashMap<Pair<Item, Integer>, Integer>();
	
	public FurnaceFuelHandler() {
//		setFuelValues();
		System.out.print("SYNDARYL: Coal fuel value in FurnaceFuelHandler() " + GameRegistry.getFuelValue(new ItemStack(  Items.coal, 1) ) + "\n" );
	}


//	public static void setFuelValues()
//	{
//		addFuel(BlockManager.charcoalCompressed, 16000);
//	}


    public void addFuel(Block block, int value)
    {
        addFuel(Item.getItemFromBlock(block), 0, value);
    }
    public void addFuel(Block block, int meta, int value)
    {
        addFuel(Item.getItemFromBlock(block), meta, value);
    }

    public void addFuel(Item item, int value)
    {
        addFuel(item, 0, value);
    }
	public void addFuel(Item item, int metadata, int value)
	{
		UtilityBlocks.LOG.info("SYNDARYL: Making Fuel! " + item.getUnlocalizedName() + "\n" );
        fuelList.put(Pair.of(item, metadata), value);
	}


	@Override
	public int getBurnTime(ItemStack fuel) 
	{
		return getFuelValue(fuel);
	}
	
	private int getFuelValue(ItemStack stack)
	{
	    Pair<Item, Integer> pair = Pair.of(stack.getItem(), stack.getItemDamage());

	    if (fuelList.containsKey(pair))
	    {
	        return fuelList.get(pair);
	    }
	    else
	    {
	        pair = Pair.of(stack.getItem(), 0);
	        if (fuelList.containsKey(pair))
	        {
	            return fuelList.get(pair);
	        }
	    }
	    return 0;
	}

}
