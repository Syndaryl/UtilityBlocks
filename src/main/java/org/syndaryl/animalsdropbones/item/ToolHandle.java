package org.syndaryl.animalsdropbones.item;


import org.syndaryl.animalsdropbones.NamespaceManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ToolHandle extends Item implements IItemName {

	private static String name = NamespaceManager.GetModNameLC() + "_" + "tool_handle";
	
	public ToolHandle() {
		// TODO Auto-generated constructor stub
	    this.setCreativeTab(CreativeTabs.tabMisc);   // the item will appear on the Miscellaneous tab in creative
		//setUnlocalizedName(org.syndaryl.animalsdropbones.NamespaceManager.getLocalized( getName() ));

		setUnlocalizedName(getName());
		
		GameRegistry.registerItem(this, getName());
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IItemName#getName()
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
