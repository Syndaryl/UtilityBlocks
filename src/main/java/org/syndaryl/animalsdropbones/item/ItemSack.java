package org.syndaryl.animalsdropbones.item;


import org.syndaryl.animalsdropbones.AnimalsDropBones;
import org.syndaryl.animalsdropbones.NamespaceManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemSack extends Item implements IItemName {

	private static String name = NamespaceManager.GetModNameLC() + "_" + "sack";
	private ItemStack contents = null;
	private String contentsName = "";
	
	public ItemSack() {
	    this.setCreativeTab(CreativeTabs.tabMisc);   // the item will appear on the Miscellaneous tab in creative
	    setContents(null);
		setUnlocalizedName(getName());
		AnimalsDropBones.LOG.warn("ADB: Making Generic Sacks: "+ getName());
		
		GameRegistry.registerItem(this, getName());
	}

	public ItemSack(ItemStack itemStack) {
	    this.setCreativeTab(CreativeTabs.tabMisc);   // the item will appear on the Miscellaneous tab in creative
	    setContents(itemStack);
		setUnlocalizedName(getName());
		GameRegistry.registerItem(this, getName());
		AnimalsDropBones.LOG.warn("ADB: Making " + itemStack.getItem().getUnlocalizedName() + " Sacks: "+ getName());
	}


	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IItemName#getName()
	 */
    @Override
	public String getName()
	{
		if (getContents() == null)
			return name;
		return name + "_" + getContentsName();
	}


	@Override
	public String getName(ItemStack stack) {
		if (getContents() == null)
			return stack.getItem().getUnlocalizedName();
		if (ItemSack.class.isInstance(stack.getItem()))
			return ((ItemSack) stack.getItem()).getUnlocalizedName().substring(5).replace('.', '_').toLowerCase() + "_" + getContents().getUnlocalizedName();
		return stack.getItem().getUnlocalizedName().substring(5);
	}

	@Override
	public String getName(int meta) {
		// TODO Auto-generated method stub
		return getName();
	}

	/**
	 * @return the contents of the sack (may be null for no contents)
	 */
	public ItemStack getContents() {
		return contents;
	}

	/**
	 * @param contents set the contents of the sack
	 */
	public void setContents(ItemStack contents) {
		this.contents = contents;
		if (contents != null )
			this.setContentsName(this.contents.getUnlocalizedName().substring(5).replace('.', '_'));
	}

	/**
	 * @return the contentsName
	 */
	public String getContentsName() {
		return contentsName;
	}

	/**
	 * @param contentsName the contentsName to set
	 */
	public void setContentsName(String contentsName) {
		this.contentsName = contentsName.toLowerCase();
	}
}
