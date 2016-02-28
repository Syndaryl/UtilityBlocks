/**
 * 
 */
package org.syndaryl.utilityblocks.item;

import org.syndaryl.utilityblocks.NamespaceManager;
import org.syndaryl.utilityblocks.UtilityBlocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		UtilityBlocks.LOG.info("Item list");
		for(ResourceLocation key :  Item.itemRegistry.getKeys() )
		{
			UtilityBlocks.LOG.info(key.toString());
		}
		UtilityBlocks.LOG.info("Block list");
		for(ResourceLocation key :  Block.blockRegistry.getKeys() )
		{
			UtilityBlocks.LOG.info(key.toString());
		}
        return itemStackIn;
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
