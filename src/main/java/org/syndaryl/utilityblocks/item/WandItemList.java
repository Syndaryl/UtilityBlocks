/**
 * 
 */
package org.syndaryl.utilityblocks.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import org.syndaryl.utilityblocks.NamespaceManager;
import org.syndaryl.utilityblocks.UtilityBlocks;

/**
 * @author syndaryl
 *
 */
public class WandItemList extends Item implements IItemName {
	private String name = NamespaceManager.GetModNameLC() + "_" + "wand_item_list";
	/**
	 * 
	 */
	public WandItemList() {
	    this.setCreativeTab(CreativeTabs.tabMisc);   // the item will appear on the Miscellaneous tab in creative
		setUnlocalizedName(getName());		
		GameRegistry.registerItem(this, getName());
	}

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
	@Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
		UtilityBlocks.INFO.info("=================== ITEM LIST ===================");
		for(String key :  asSorted(Item.itemRegistry.getKeys()) )
		{
			UtilityBlocks.INFO.info(key);
		}
		UtilityBlocks.INFO.info("=================== BLOCK LIST ===================");
		for(String key :  asSorted(Block.blockRegistry.getKeys()) )
		{
			UtilityBlocks.INFO.info(key);
		}

		UtilityBlocks.INFO.info("=================== ORE DICTIONARY =================== ");
		UtilityBlocks.INFO.info("       OreDict contains " + OreDictionary.getOreNames().length + " names");
		for(String name : OreDictionary.getOreNames())
		{
			UtilityBlocks.INFO.info(name);
		}
        return itemStackIn;
    }
	
	public static
	List<String> asSorted(Set<ResourceLocation> set) {
		List<String> list = new ArrayList<String>(set.size());
		for( ResourceLocation o : set )
		{
			list.add(o.toString());
		}
		
	  java.util.Collections.sort(list);
	  return list;
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IItemName#getName()
	 */
    @Override
	public String getName()
	{
		return name;
	}


	@Override
	public String getName(ItemStack stack) {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getName(int meta) {
		// TODO Auto-generated method stub
		return name;
	}

}
