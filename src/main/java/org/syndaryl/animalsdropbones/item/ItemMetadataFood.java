/**
 * 
 */
package org.syndaryl.animalsdropbones.item;

import java.util.List;



import org.syndaryl.animalsdropbones.NamespaceManager;

//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
//import net.minecraft.util.IIcon;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author syndaryl
 *
 */
public class ItemMetadataFood extends ItemFood implements IItemName {

	private int[] hungerValues;
	private float[] saturationModifiers;
	private String[] names;
	private final String  name = "ADB_Food";
	private String[] icons;
//	private IIcon[] icons;
	/**
	 * @param hungerValues
	 * @param saturationModifiers
	 */
	public ItemMetadataFood(int[] hungerValues, float[] saturationModifiers, String[] names)
	{
		super(0, 0f, false);
		this.hungerValues = hungerValues;
		this.saturationModifiers = saturationModifiers;
		this.names = names;
//		icons = new IIcon[names.length];
		icons = new String[names.length];
		icons = names.clone();
		this.setHasSubtypes(true);
		this.setUnlocalizedName(NamespaceManager.getUnLocalized(getName()));
		
		GameRegistry.registerItem(this, getName());
	}

	/**
	* @return The hunger value of the ItemStack
	*/
	@Override
	public int getHealAmount(ItemStack itemStack)
	{
		int meta = itemStack.getMetadata();
		if (meta < names.length)
			return hungerValues[meta];
		return 0;
	}
	/**
	* @return The saturation modifier of the ItemStack
	*/
	@Override
	public float getSaturationModifier(ItemStack itemStack)
	{
		int meta = itemStack.getMetadata();
		if (meta < names.length)
			return saturationModifiers[meta];
		return 0.0F;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List subItems)
	{
		for (int meta = 0; meta < Math.min(hungerValues.length, saturationModifiers.length); meta++)
		{
			subItems.add(new ItemStack(item, 1, meta));
		}
	}
	
	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IItemName#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}
	
	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IItemName#getName(net.minecraft.item.ItemStack)
	 */
	@Override
	public String getName(ItemStack stack){
        return this.getName(stack.getMetadata());
	}
	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IItemName#getName(int)
	 */
	@Override
	public String getName(int meta){
	    if (meta < names.length)
	        return this.getName() + "_" + names[meta];
	    return this.getName();
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName(stack.getMetadata());
	}

	public String getIconFromDamage(int meta) {
	    if (meta > icons.length)
	        meta = 0;

	    return this.icons[meta];
	}

	public String getUnlocalizedName(int meta) {
		if (meta < names.length)
			return super.getUnlocalizedName() + "_" + this.getName(meta);
        return super.getUnlocalizedName();
	}
}
