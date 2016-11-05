/**
 * 
 */
package org.syndaryl.utilityblocks.item;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.syndaryl.utilityblocks.NamespaceManager;
import org.syndaryl.utilityblocks.UtilityBlocks;

import com.google.common.collect.BiMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author syndaryl
 *
 */
public class WandItemList extends Item implements IItemName {
	private static BiMap<Class<? extends Entity>, EntityRegistration> entityList;
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
		@SuppressWarnings("unused")
		boolean success;
		UtilityBlocks.INFO
				.info("=================== ITEM LIST ===================");
		success = generateItemCsvFile("items.csv");
		
		//UtilityBlocks.INFO
		//		.info("=================== BLOCK LIST ===================");
		// generateBlockCsvFile("blocks.csv");

		UtilityBlocks.INFO
				.info("=================== ORE DICTIONARY =================== ");
		UtilityBlocks.INFO.info("       OreDict contains "
				+ OreDictionary.getOreNames().length + " names");
		success = generateOreDictCsvFile("oredict.csv");

		UtilityBlocks.INFO
				.info("=================== ENTITY CLASS LIST =================== ");
		success = generateEntityCsvFile("entity.csv");
		
		ActionResult<ItemStack> result = new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
		
		return result;
	}

	private boolean generateEntityCsvFile(String fileName) {
		FileWriter writer;
		if (entityList == null)
		{
			boolean success = initializeEntityRegistry();
			if (!success) return false;
		}
		try{

			writer = new FileWriter(fileName);
			UtilityBlocks.INFO.info(String.format("I have found %d entities", EntityList.CLASS_TO_NAME.size()));
			UtilityBlocks.LOG.info(String.format("Writing %d entities", EntityList.CLASS_TO_NAME.size()));
			String[] headers = new String[] {"java_class", "internal_name"};
			
			for (Class<? extends Entity> name : EntityList.CLASS_TO_NAME.keySet() ) {
				if (name != null){
					String[] line = new String[] {name.getName(), EntityList.CLASS_TO_NAME.get(name)}; 
					writeCsvLine(writer, line);
				}
			}
			UtilityBlocks.LOG.info(String.format("Finished writing entities", entityList.size()));
			//writeBlockCSVBody(writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			UtilityBlocks.LOG.error("Error in Oredict dump");
			UtilityBlocks.LOG.error(e.toString());
			for(StackTraceElement stacktrace : e.getStackTrace() )
			{
				UtilityBlocks.LOG.error(stacktrace.toString());
			}
			return false;
		}
		
		return true;
	}

	/**
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("unchecked")
	private boolean initializeEntityRegistry() throws SecurityException, IllegalArgumentException {
		try {
			Field f = EntityRegistry.instance().getClass().getDeclaredField("entityClassRegistrations"); //NoSuchFieldException
			f.setAccessible(true);
			entityList = (BiMap<Class<? extends Entity>, EntityRegistration>) f.get(EntityRegistry.instance()); //IllegalAccessException
		} catch (NoSuchFieldException e){

			UtilityBlocks.LOG.error("Error in Oredict dump");
			UtilityBlocks.LOG.error(e.toString());
			for(StackTraceElement stacktrace : e.getStackTrace() )
			{
				UtilityBlocks.LOG.error(stacktrace.toString());
			}
			return false;
		} catch (IllegalAccessException e){

			UtilityBlocks.LOG.error("Error in Oredict dump");
			UtilityBlocks.LOG.error(e.toString());
			for(StackTraceElement stacktrace : e.getStackTrace() )
			{
				UtilityBlocks.LOG.error(stacktrace.toString());
			}
			return false;
		}catch (Exception e){
			e.printStackTrace();
			UtilityBlocks.LOG.error(e.toString());
			for(StackTraceElement stacktrace : e.getStackTrace() )
			{
				UtilityBlocks.LOG.error(stacktrace.toString());
			}
			return false;
		}
		return true;
	}

	/**
	 * @param fileName 
	 * @return 
	 * 
	 */
	private boolean generateOreDictCsvFile(String fileName) {
		FileWriter writer;
		try {
			writer = new FileWriter(fileName);

			for (String name : OreDictionary.getOreNames()) {
				List<String> oreLine = new LinkedList<String>();
				oreLine.add(name);
				try{
					for (ItemStack ore : OreDictionary.getOres(name))
					{
							oreLine.add(ore.getUnlocalizedName());
					}
				} catch (Exception e)
				{
					e.printStackTrace();
					UtilityBlocks.LOG.error("Error in Oredict dump of dictEntries");
					UtilityBlocks.LOG.error(e.toString());
					for(StackTraceElement stacktrace : e.getStackTrace() )
					{
						UtilityBlocks.LOG.error(stacktrace.toString());
					}
				}
				writeCsvLine(writer, oreLine.toArray(new String[0]));
			}
			//writeBlockCSVBody(writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			UtilityBlocks.LOG.error("Error in Oredict dump");
			UtilityBlocks.LOG.error(e.toString());
			for(StackTraceElement stacktrace : e.getStackTrace() )
			{
				UtilityBlocks.LOG.error(stacktrace.toString());
			}
			return false;
		}
		return true;
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
			writeCsvLine(writer, headers);
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

	private boolean generateItemCsvFile(String fileName) {
		FileWriter writer;
		UtilityBlocks.LOG.info("Writing ItemCSV file");
		try {
			writer = new FileWriter(fileName);
			String[] headers = new String[]{
					"id","modid:objectname","meta","display_name","unlocalized_name","food_value","saturation_value","object_class","oredict_entries"
			}; 
			writeCsvLine(writer, headers);
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
			return false;
		}
		UtilityBlocks.LOG.info("Finished ItemCSV file");
		return true;
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
			List<String> fields = new ArrayList<String>();
			//id
			fields.add(String.format("%05d",  Item.REGISTRY.getIDForObject(objItem) ) );
			//resource_domain
			fields.add(key.getResourceDomain() + ':' + key.getResourcePath());
			//meta
			fields.add(String.format("%d",  subItem.getMetadata()));
			//display_name
			fields.add(subItem.getDisplayName());
			//unlocalized_name
			fields.add(objItem.getUnlocalizedName());
			//food_value
			try{
				if(net.minecraft.item.ItemFood.class.isAssignableFrom(objItem.getClass()))
				{
					fields.add( String.valueOf(((ItemFood)objItem).getHealAmount(subItem)) );
				}
				else
				{
					fields.add("0.0");
				}
			}
			catch (java.lang.ClassCastException e)
			{
				UtilityBlocks.LOG.error(e);
				fields.add("0.0");
			}
			try{
			//saturation_value
				if(net.minecraft.item.ItemFood.class.isAssignableFrom(objItem.getClass()))
				{
					fields.add( String.valueOf(((ItemFood)objItem).getSaturationModifier(subItem)) );
				}
				else
				{
					fields.add("0.0");
				}
			}
			catch (java.lang.ClassCastException e)
			{
				UtilityBlocks.LOG.error(e);
				fields.add("0.0");
			}
			//object_class
			fields.add(objItem.getClass().getName());
			// ore dictionary
			List<String> ore = new ArrayList<String>();
			for (int id : OreDictionary.getOreIDs(subItem))
			{
				ore.add( OreDictionary.getOreName(id));
			}
			// oredict entries
			if (! ore.isEmpty())
				fields.add(String.join(";", ore));
			else
				fields.add("");
			
			writeCsvLine(writer, fields.toArray(new String[0]));
		}
	}

	private void writeCsvLine(FileWriter writer, String[] record) {
		try {
			for (String field : record)
			{
				writer.append('"');
				writer.append(field);
				writer.append('"');
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
