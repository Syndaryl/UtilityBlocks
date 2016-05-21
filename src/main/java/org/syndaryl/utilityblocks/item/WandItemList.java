/**
 * 
 */
package org.syndaryl.utilityblocks.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.io.FileWriter;
import java.io.IOException;

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
	private String name = NamespaceManager.GetModNameLC() + "_"
			+ "wand_item_list";

	/**
	 * 
	 */
	public WandItemList() {
		this.setCreativeTab(CreativeTabs.MISC); // the item will appear on
													// the Miscellaneous tab in
													// creative
		setUnlocalizedName(getName());
		ItemManager.registerItem(this, getName());
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {
		UtilityBlocks.INFO
				.info("=================== ITEM LIST ===================");
		generateItemCsvFile("items.csv");
		
		UtilityBlocks.INFO
				.info("=================== BLOCK LIST ===================");
		for (String key : asSorted(Block.REGISTRY.getKeys())) {
			UtilityBlocks.INFO.info(key);
		}

		UtilityBlocks.INFO
				.info("=================== ORE DICTIONARY =================== ");
		UtilityBlocks.INFO.info("       OreDict contains "
				+ OreDictionary.getOreNames().length + " names");
		for (String name : OreDictionary.getOreNames()) {
			UtilityBlocks.INFO.info(name);
		}
		return itemStackIn;
	}

	private void generateItemCsvFile(String sFileName) {
		FileWriter writer;
		try {
			writer = new FileWriter(sFileName);
			writeItemCsvHeader(writer);
			writeItemCsvBody(writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			UtilityBlocks.LOG.error(e.toString());
			for(StackTraceElement stacktrace : e.getStackTrace() )
			{
				UtilityBlocks.LOG.error(stacktrace.toString());
			}
		}
	}

	private void writeItemCsvBody(FileWriter writer) {
		// TODO Auto-generated method stub
		try
		{		
			for(ResourceLocation key :  asSortedResourceLocation(Item.REGISTRY.getKeys()) )
			{
//				//id
//				writer.append(String.format("%05d",  Item.itemRegistry.getIDForObject(objItem) ) );
//				writer.append(',');
//				//display_name
//				writer.append(objItem.getItemStackDisplayName(new ItemStack(objItem)));
//				writer.append(',');
//				//name
//				writer.append(objItem.toString());
//				writer.append(',');
//				//meta
//				writer.append("0");
//				writer.append(',');
//				//unlocalized_name
//				writer.append(objItem.getUnlocalizedName());
//				writer.append(',');
//				//object_class
//				writer.append(objItem.getClass().getName());
//				writer.append(',');
//				//resource_domain
//				writer.append(key.getResourceDomain());
//				writer.append(',');
//				//resource_path
//				writer.append(key.getResourcePath());
//				writer.append('\n');

				Item objItem = Item.REGISTRY.getObject(key);
				List<ItemStack> subItems = new ArrayList<ItemStack>();
	            for (CreativeTabs tab : objItem.getCreativeTabs())
	            {
	            	objItem.getSubItems(objItem, tab, subItems);
	            }
	            for (ItemStack subItem : subItems)
	            {
					//id
					writer.append(String.format("%05d",  Item.REGISTRY.getIDForObject(objItem) ) );
					writer.append(',');
					//resource_domain
					writer.append(key.getResourceDomain());
					writer.append(':');
					//resource_path
					writer.append(key.getResourcePath());
					writer.append(',');
					//meta
					writer.append(String.format("%d",  subItem.getMetadata()));
					writer.append(',');
					//display_name
					writer.append(subItem.getDisplayName());
					writer.append(',');
					//unlocalized_name
					writer.append(objItem.getUnlocalizedName());
					writer.append(',');
					//object_class
					writer.append(objItem.getClass().getName());
					writer.append('\n');
	            }
	            
			}
			writer.flush();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
				UtilityBlocks.LOG.error(e.toString());
				for(StackTraceElement stacktrace : e.getStackTrace() )
				{
					UtilityBlocks.LOG.error(stacktrace.toString());
				}
		} 
	}

	private void writeItemCsvHeader(FileWriter writer) {
		try {
			writer.append("id");
			writer.append(',');
			writer.append("modid:objectname");
			writer.append(',');
			writer.append("meta");
			writer.append(',');
			writer.append("display_name");
			writer.append(',');
			writer.append("unlocalized_name");
			writer.append(',');
			writer.append("object_class");
			writer.append('\n');

			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			UtilityBlocks.LOG.error(e.toString());
			for(StackTraceElement stacktrace : e.getStackTrace() )
			{
				UtilityBlocks.LOG.error(stacktrace.toString());
			}
		}
	}

	public static List<String> asSorted(Set<ResourceLocation> set) {
		List<String> list = new ArrayList<String>(set.size());
		for (ResourceLocation o : set) {
			list.add(o.toString());
		}

		java.util.Collections.sort(list);
		return list;
	}
//	public static List<String> asSorted(Set<ResourceLocation> set) {
//		List<String> list = new ArrayList<String>(set.size());
//		for (String o : set.toString()) {
//			list.add(o);
//		}
//
//		java.util.Collections.sort(list);
//		return list;
//	}

	public static List<ResourceLocation> asSortedResourceLocation(Set<ResourceLocation> set) {
		UtilityBlocks.LOG.info("%d items", set.size());
		List<ResourceLocation> sortedSet = new ArrayList<ResourceLocation>(set.size());
		for (ResourceLocation o : set) {
			sortedSet.add(o);
		}
		Collections.sort(sortedSet, new Comparator<ResourceLocation>(){
			@Override
			public int compare(final ResourceLocation object1, final ResourceLocation object2)
			{
				return object1.toString().compareTo(object2.toString());
			}
		});
		UtilityBlocks.LOG.info("%d sorted items", sortedSet.size());
		return sortedSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.syndaryl.utilityblocks.item.IItemName#getName()
	 */
	@Override
	public String getName() {
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
