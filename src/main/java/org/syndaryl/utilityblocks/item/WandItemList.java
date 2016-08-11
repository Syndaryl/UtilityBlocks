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
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
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
	 * pressed. Args: ItemStack, World, EntityPlayer, EnumHand
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn, EnumHand hand) {
		//ActionResult<ItemStack> result = super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
		UtilityBlocks.INFO
				.info("=================== ITEM LIST ===================");
		generateItemCsvFile("items.csv");
		
		UtilityBlocks.INFO
				.info("=================== BLOCK LIST ===================");
		// generateBlockCsvFile("blocks.csv");

		UtilityBlocks.INFO
				.info("=================== ORE DICTIONARY =================== ");
		UtilityBlocks.INFO.info("       OreDict contains "
				+ OreDictionary.getOreNames().length + " names");
		generateOreDictCsvFile("oredict.csv");
		ActionResult<ItemStack> result = new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
		
		return result;
	}

	/**
	 * @param fileName 
	 * 
	 */
	private void generateOreDictCsvFile(String fileName) {
		for (String name : OreDictionary.getOreNames()) {
			UtilityBlocks.INFO.info(name);
		}
	}

	/**
	 * @param fileName 
	 * 
	 * Writes the CSV file for blocks
	 */
	private void generateBlockCsvFile(String fileName) {
		UtilityBlocks.LOG.info("Writing BodyCSV file");
		FileWriter writer;
		try {
			writer = new FileWriter(fileName);
			String[] headers = new String[]{
					"id","modid:objectname","meta","display_name","unlocalized_name","object_class"
			}; 
			writeCsvHeader(writer, headers);
			writeItemCsvBody(writer, true);
			//writeBlockCSVBody(writer);
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

	/**
	 * @param writer
	 * @throws IOException
	 */
	private void writeBlockCSVBody(FileWriter writer) throws IOException {
		for (ResourceLocation key : asSortedResourceLocation(Block.REGISTRY.getKeys())) {
			//UtilityBlocks.INFO.info(key);
			Block b = Block.REGISTRY.getObject(key);
			if (b != null)
			{
				ItemStack objItem = new ItemStack( b );
				List<ItemStack> subItems = new ArrayList<ItemStack>();
		        for (CreativeTabs tab : objItem.getItem().getCreativeTabs())
		        {
		        	objItem.getItem().getSubItems(objItem.getItem(), tab, subItems);
		        }
		        for (ItemStack subItem : subItems)
		        {
					//id
					writer.append(String.format("%05d",  Item.REGISTRY.getIDForObject(objItem.getItem()) ) );
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
		}
	}

	private void generateItemCsvFile(String fileName) {
		FileWriter writer;
		UtilityBlocks.LOG.info("Writing ItemCSV file");
		try {
			writer = new FileWriter(fileName);
			String[] headers = new String[]{
					"id","modid:objectname","meta","display_name","unlocalized_name","object_class"
			}; 
			writeCsvHeader(writer, headers);
			writeItemCsvBody(writer, false);
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
		UtilityBlocks.LOG.info("Finished ItemCSV file");
	}

	/**
	 * Writes the body of a CSV item list to file writer
	 * @param writer
	 * @param writeBlocksOrWriteItems - true = write blocks, false is write everything else
	 */
	private void writeItemCsvBody(FileWriter writer, boolean writeBlocksOrWriteItems) {
		try
		{		
			for(ResourceLocation key :  asSortedResourceLocation(Item.REGISTRY.getKeys()) )
			{
				Item objItem = Item.REGISTRY.getObject(key);
				
				Block dummy = new Block(Material.WOOD);
				
				// If instructed to write only blocks, and is a block, write it
				final boolean thisIsABlock = (dummy.getClass()).isInstance(objItem);
				if (thisIsABlock && writeBlocksOrWriteItems == true)
				{
					writeCsvLineItem(writer, key, objItem);
				}
				// if instructed to write only blocks, and is NOT a block, don't write it
				else if ( ! (dummy.getClass()).isInstance(objItem) && writeBlocksOrWriteItems == true)
				{
					// Don't write blocks to this file
				}
				// if NOT instructed to write blocks, and is a not-block, write it
				else if (writeBlocksOrWriteItems == false && ! (dummy.getClass()).isInstance(objItem))
				{
					writeCsvLineItem(writer, key, objItem);
				}
				else
				{
					// fall through case, don't write it
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

	/**
	 * @param writer
	 * @param key
	 * @param objItem
	 * @throws IOException
	 */
	private void writeCsvLineItem(FileWriter writer, ResourceLocation key, Item objItem) throws IOException {
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

	private void writeCsvHeader(FileWriter writer, String[] headers) {
		try {
			for (String header : headers)
			{
				writer.append(header);
				writer.append(',');
			}
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
