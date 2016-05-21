/**
 * 
 */
package org.syndaryl.utilityblocks.item;

import java.util.List;

//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.syndaryl.utilityblocks.NamespaceManager;
import org.syndaryl.utilityblocks.UtilityBlocks;
//import net.minecraft.util.IIcon;

/**
 * @author syndaryl
 *
 */
public class ItemMetadataFood extends ItemFood implements IItemName {

	private int[] hungerValues;
	private float[] saturationModifiers;
	private String[] names;
	private final String  name = NamespaceManager.GetModNameLC() + "_food";
	private String[] actions;
	//private String[] icons;
	//	private IIcon[] icons;
	/**
	 * @param hungerValues
	 * @param saturationModifiers
	 * @param actions 
	 */
	public ItemMetadataFood(int[] hungerValues, float[] saturationModifiers, String[] names, String[] actions)
	{
		super(0, 0f, false);
		this.hungerValues = hungerValues;
		this.saturationModifiers = saturationModifiers;
		this.names = names;
		this.actions = actions;
		//icons = names.clone();
		
		this.setHasSubtypes(true);
		this.setUnlocalizedName(getName());
		this.setCreativeTab(CreativeTabs.FOOD);
		ItemManager.registerItem(this, getName());
	}
	

	/**
	* @return The hunger value of the ItemStack
	*/
	@Override
	public int getHealAmount(ItemStack itemStack)
	{
		int meta = itemStack.getItemDamage();
		UtilityBlocks.LOG.info("SYNDARYL: heal amount Item"+itemStack.getDisplayName() + " meta " + meta + " getMaxMetadata() " + getMaxMetadata());
		if (meta < getMaxMetadata())
			return hungerValues[meta];
		
		UtilityBlocks.LOG.warn("SYNDARYL: heal amount fell through to 0!");
		return 0;
	}
	/**
	* @return The saturation modifier of the ItemStack
	*/
	@Override
	public float getSaturationModifier(ItemStack itemStack)
	{
		int meta = itemStack.getItemDamage();
		UtilityBlocks.LOG.info("SYNDARYL: saturation amount Item"+itemStack.getDisplayName() + " meta " + meta + " getMaxMetadata() " + getMaxMetadata());
		if (meta < getMaxMetadata())
			return saturationModifiers[meta];
		UtilityBlocks.LOG.warn("SYNDARYL: saturation amount fell through to 0!");
		return 0.0F;
	}
	
	@Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
	    int meta = stack.getItemDamage();
		if (meta < getMaxMetadata())
	    	return EnumAction.valueOf(this.actions[stack.getItemDamage()]);
	    return EnumAction.EAT;
    }
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
		if (stack.getItem().getItemUseAction(stack) == EnumAction.DRINK )
		{
			ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE, 1);
			playerIn.inventory.addItemStackToInventory(bottle);
		}
        return super.onItemUseFinish(stack, worldIn, playerIn);
        
        /* --stack.stackSize;
        playerIn.getFoodStats().addStats(this, stack);
        worldIn.playSoundAtEntity(playerIn, "random.burp", 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, worldIn, playerIn);
        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]); 
        return stack; */
    }
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List subItems)
	{
		for (int meta = 0; meta < getMaxMetadata(); meta++)
		{
			subItems.add(new ItemStack(item, 1, meta));
		}
	}
	
	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IItemName#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}
	
	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IItemName#getName(net.minecraft.item.ItemStack)
	 */
	@Override
	public String getName(ItemStack stack){
        return this.getName(stack.getItemDamage());
	}
	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IItemName#getName(int)
	 */
	@Override
	public String getName(int meta){
	    if (meta < getMaxMetadata())
	        return this.getName() + "_" + names[meta];
	    return this.getName();
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName(stack.getMetadata());
	}

	/* public String getIconFromDamage(int meta) {
	    if (meta > icons.length)
	        meta = 0;

	    return UtilityBlocks.MODID + ":" + this.icons[meta];
	} */

	public String getUnlocalizedName(int meta) {
		if (meta < getMaxMetadata())
			return "item." + this.getName(meta);
        return super.getUnlocalizedName();
	}

	public int getMaxMetadata() {
		return Math.min(names.length, Math.min(hungerValues.length, saturationModifiers.length));
	}
}
