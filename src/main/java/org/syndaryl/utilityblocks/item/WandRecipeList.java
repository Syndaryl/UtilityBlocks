/**
 * 
 */
package org.syndaryl.utilityblocks.item;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.syndaryl.utilityblocks.NamespaceManager;
import org.syndaryl.utilityblocks.UtilityBlocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * @author syndaryl
 *
 */
public class WandRecipeList extends Item implements IItemName {
	private String name = NamespaceManager.GetModNameLC() + "_"
			+ "wand_recipe_list";

	/**
	 * 
	 */
	public WandRecipeList() {
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
				.info("=================== RECIPE LIST ===================");
		success = generateStandardRecipeCsvFile("standard_recipes.csv");
		
		
		ActionResult<ItemStack> result = new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
		
		return result;
	}

	private boolean generateStandardRecipeCsvFile(String fileName) {
		FileWriter writer;
		UtilityBlocks.LOG.info("Writing StandardRecipeCSV file");
		try {
			writer = new FileWriter(fileName);
			String[] headers = new String[]{
					"id","result_modid:objectname:meta","type","ingredient_1","ingredient_2","ingredient_3","ingredient_4","ingredient_5","ingredient_6","ingredient_7","ingredient_8","ingredient_9"
			}; 
			writeCsvLine(writer, headers);
			writeRecipeCsvBody(writer);
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
		UtilityBlocks.LOG.info("Finished RecipeCSV file");
		return false;
	}

	private void writeRecipeCsvBody(FileWriter writer) {
		try
		{		
			UtilityBlocks.LOG.info(String.format("Sifting through %d Item recipes",net.minecraft.item.crafting.CraftingManager.getInstance().getRecipeList().size()));
			for(IRecipe recipeInstance :  net.minecraft.item.crafting.CraftingManager.getInstance().getRecipeList() )
			{
				if (recipeInstance == null){
					UtilityBlocks.LOG.debug("	recipeInstance is null!");
					continue;
					}
				final ItemStack recipeOutput = recipeInstance.getRecipeOutput();
				if (recipeOutput == null){
					UtilityBlocks.LOG.debug("	recipeOutput is null!");
					continue;
					}
				try {
					UtilityBlocks.LOG.debug("	working on a recipe for " + recipeOutput.getUnlocalizedName());
					Class<? extends IRecipe> recipeType = recipeInstance.getClass();
					if (recipeType == null)
						continue;
					UtilityBlocks.LOG.debug("	it is of a type: " + recipeType.getName() + " recipe");
					
					// Item-recipes
					UtilityBlocks.LOG.debug("	... assignable from Shaped Recipes? " +  ShapedRecipes.class.isAssignableFrom(recipeType));
					if (ShapedRecipes.class.isAssignableFrom(recipeType))
					{
						ItemStack result = recipeOutput;
						writeCsvLineRecipe(writer, result, "shaped",(Object[])((ShapedRecipes)recipeInstance).recipeItems);
						continue;
					}
					UtilityBlocks.LOG.debug("	... assignable from Shapeless Recipes? " +  ShapelessRecipes.class.isAssignableFrom(recipeType));
					if (ShapelessRecipes.class.isAssignableFrom(recipeType))
					{
						ItemStack result = recipeOutput;
						writeCsvLineRecipe(writer, result, "shapeless",(Object[])((ShapelessRecipes)recipeInstance).recipeItems.toArray(new ItemStack[0]));
						continue;
					}
					
					// Oredict recipes
					UtilityBlocks.LOG.debug("	... assignable from ShapedOre Recipes? " +  ShapedOreRecipe.class.isAssignableFrom(recipeType));
					if (ShapedOreRecipe.class.isAssignableFrom(recipeType))
					{
						writeOreRecipe(writer, recipeInstance, recipeOutput, "shapedOre");
						continue;
					}
					UtilityBlocks.LOG.debug("	... assignable from Shapeless Recipes? " +  ShapelessOreRecipe.class.isAssignableFrom(recipeType));
					if (ShapelessOreRecipe.class.isAssignableFrom(recipeType))
					{
						writeOreRecipe(writer, recipeInstance, recipeOutput, "shapelessOre");
						continue;
					}
				} catch (NullPointerException e) {
					continue;
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
		try
		{		
			final Map<ItemStack, ItemStack> smeltingList = net.minecraft.item.crafting.FurnaceRecipes.instance().getSmeltingList();
			UtilityBlocks.LOG.info(String.format("Sifting through %d smelting thingies",smeltingList.size()));
			for(ItemStack input :  smeltingList.keySet() )
			{
				ItemStack result = smeltingList.get(input);
				UtilityBlocks.LOG.debug("	working on a furnace recipe that produces a " + result.getDisplayName() );
				writeCsvLineRecipe(writer, result, "furnace", input);
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
	 * @param recipeInstance
	 * @param recipeOutput
	 * @param type
	 */
	private void writeOreRecipe(FileWriter writer, IRecipe recipeInstance, final ItemStack recipeOutput, String type) {
		ItemStack result = recipeOutput;
		Object[] recipeInput;
		if (recipeInstance instanceof ShapedOreRecipe)
			recipeInput = ((ShapedOreRecipe)recipeInstance).getInput();
		else if (recipeInstance instanceof ShapelessOreRecipe)
			recipeInput = ((ShapelessOreRecipe)recipeInstance).getInput().toArray();
		else recipeInput = new Object[0];
		final List<Object> transformedToOutput = makeItemStacks(recipeInput);
		writeCsvLineRecipe(writer, result, type, transformedToOutput);
	}

	/**
	 * @param recipeInput
	 * @return
	 */
	protected List<Object> makeItemStacks(final Object[] recipeInput) {
		final List<Object> transformedToOutput = new LinkedList<Object>();
		for (Object in : recipeInput)
		{
		    if (in instanceof ItemStack)
		    {
		    	transformedToOutput.add( (ItemStack)in);
		    }
		    else if (in instanceof Item)
		    {
		    	transformedToOutput.add( new ItemStack((Item)in));
		    }
		    else if (in instanceof Block)
		    {
		    	transformedToOutput.add( new ItemStack((Block)in, 1, OreDictionary.WILDCARD_VALUE));
		    }
		    else if (in instanceof String)
		    {
		    	transformedToOutput.add( OreDictionary.getOres((String)in));
		    }
		}
		return transformedToOutput;
	}
	@SuppressWarnings("unchecked")
	private void writeCsvLineRecipe(FileWriter writer, ItemStack result, String recipeType, Object... recipeItems) {
		List<String> fields = new ArrayList<String>();
		fields.add(String.format("%05d",  Item.REGISTRY.getIDForObject(result.getItem()) ) );
		//modid:itemname:meta
		fields.add(getFQItemName(result));
		//recipetype
		fields.add(recipeType);
		//ingredients
		for(Object ingredient : recipeItems)
		{
			if (null != ingredient)
			{
				if (ingredient instanceof ItemStack)
					fields.add(getFQItemName((ItemStack)ingredient));
				if (ingredient instanceof List)
				{
					String list = "";
					for (Object item : (List<Object>)ingredient)
						if (item instanceof ItemStack)
							list+= getFQItemName((ItemStack)item) + ";";
						else if (item instanceof String)
							list += item + ";";
					if (list.length() > 0)
					{
						list = list.substring(0,list.length()-1);
						fields.add(list);
					}
				}
			}
		}
		writeCsvLine(writer, fields.toArray(new String[0]));
	}

	/**
	 * @param result
	 * @return
	 */
	private String getFQItemName(ItemStack result) {
		try {
			ResourceLocation loc = Item.REGISTRY.getNameForObject(result.getItem());
			String fqName = loc.getResourceDomain() + ':' + loc.getResourcePath();
			if (result.getHasSubtypes())
				fqName = fqName + String.format(":%d", result.getMetadata() );
			return fqName;
		} catch (NullPointerException e)
		{
			UtilityBlocks.LOG.error( "ERROR GETTING RESOURCE DOMAIN" );
			UtilityBlocks.LOG.error(e);
			return "ERROR GETTING RESOURCE DOMAIN";
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
