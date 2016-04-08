/**
 * 
 */
package org.syndaryl.utilityblocks.item;


import java.util.HashMap;
import java.util.Locale;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.syndaryl.utilityblocks.UtilityBlocks;
import org.syndaryl.utilityblocks.handler.ConfigurationHandler;
import org.syndaryl.utilityblocks.handler.MaterialHandler;
import org.syndaryl.utilityblocks.materials.UBMaterial;

import cyano.basemetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;

/**
 * @author syndaryl
 *
 */
public class ItemManager {
	public static ItemMetadataFood foods;

	public static HashMap<String, ToolMattock> Mattocks = new HashMap<String, ToolMattock>();
	public static HashMap<String, ToolSledgehammer> Sledgehammers = new HashMap<String, ToolSledgehammer>();
	public static HashMap<IToolBlockSmasher, String> ToolToIngot = new HashMap<IToolBlockSmasher, String>();
	
	public static ToolHandle handle;
	public static ItemSack[] sacks;
	public static WandItemList wand;
	
	private final static int NAME = 0;
	private final static int HUNGER = 1;
	private final static int SATIATION = 2;
	private final static int ACTIONS = 3;
	
	private static Object[][] foodData = new Object[][]{
		{"apple_juice",       "2",     "1", 	"DRINK"},	
		{"apple_pie",         "5",     "3", 	"EAT" },	
		{"eggnog",            "2",     "1", 	"DRINK" },	
		{"energy_drink",      "3",     "0.5", 	"DRINK" },	
		{"potato_juice",      "2",     "0.75", 	"DRINK" },	
		{"carrot_juice",      "2",     "1", 	"DRINK" },	
		{"sugar_water",       "1",     "0.5", 	"DRINK" },	
		{"sugar_cube",        "1",     "0.5", 	"EAT" },	
		{"sushi",             "6",     "6", 	"EAT" },	

	};
	
	public ItemManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static void initialiseItems () {
		String[] names;
		int[] hunger;
		float[] satiation;
		String[] actions;
		names = getStringFromList(foodData, ItemManager.NAME);
		hunger = (int[]) getIntFromList(foodData, ItemManager.HUNGER);
		satiation = getFloatFromList(foodData, ItemManager.SATIATION);
		actions = getStringFromList(foodData, ItemManager.ACTIONS);
		
		//Object result = EnumHelper.addArmorMaterial(name, textureName, durability, reductionAmounts, enchantability);
		UBMaterial obsidianTool = MaterialHandler.addMaterial("obsidian", 12, 6, 8, 1, new ItemStack(Blocks.obsidian)); 
		
		foods = (ItemMetadataFood) new ItemMetadataFood(hunger, satiation, names, actions);
		
		ToolMattock 		mattockWood;
		ToolSledgehammer 	sledgehammerWood;
		ToolMattock 		mattockStone;
		ToolSledgehammer 	sledgehammerStone;
		ToolMattock 		mattockIron;
		ToolSledgehammer 	sledgehammerIron;
		ToolMattock 		mattockDiamond;
		ToolSledgehammer 	sledgehammerDiamond;
		
		ToolMattock 		mattockObsidian;
		ToolSledgehammer 	sledgehammerObsidian;
		
		mattockWood = (ToolMattock) new ToolMattock(ToolMaterial.WOOD);
		sledgehammerWood = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.WOOD);
		
		mattockStone = (ToolMattock) new ToolMattock(ToolMaterial.STONE);
		sledgehammerStone = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.STONE);
		
		mattockIron = (ToolMattock) new ToolMattock(ToolMaterial.IRON);
		sledgehammerIron = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.IRON);
		
		mattockDiamond = (ToolMattock) new ToolMattock(ToolMaterial.EMERALD);
		sledgehammerDiamond = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.EMERALD);
		

		Mattocks.put("mattockWood", mattockWood);
		Mattocks.put("mattockStone", mattockStone);
		Mattocks.put("mattockIron", mattockIron);
		Mattocks.put("mattockDiamond", mattockDiamond);
		
		Sledgehammers.put("sledgehammerWood", sledgehammerWood);
		Sledgehammers.put("sledgehammerStone", sledgehammerStone);
		Sledgehammers.put("sledgehammerIron", sledgehammerIron);
		Sledgehammers.put("sledgehammerDiamond", sledgehammerDiamond);

		if (ConfigurationHandler.enableObsidianTools)
		{
			mattockObsidian = (ToolMattock) new ToolMattock(MaterialHandler.getToolMaterialFor(obsidianTool));
			sledgehammerObsidian = (ToolSledgehammer) new ToolSledgehammer(MaterialHandler.getToolMaterialFor(obsidianTool));
			Mattocks.put("mattockObsidian", mattockObsidian);
			Sledgehammers.put("sledgehammerObsidian", sledgehammerObsidian);
		}
		
		handle = (ToolHandle) new ToolHandle();
		wand = (WandItemList) new WandItemList();
		
		sacks = new ItemSack[] {
				new ItemSack(),
				new ItemSack( new ItemStack(Items.dye,1,3)),
				new ItemSack(new ItemStack(Items.potato)),
				new ItemSack(new ItemStack(Items.carrot)),
				new ItemSack(new ItemStack(Items.wheat_seeds)),
				new ItemSack(new ItemStack(Items.feather))
		};
		
	}

	public static void variantRegistry() {
	    ModelBakery.registerItemVariants(foods, 
	    		new ModelResourceLocation(UtilityBlocks.MODID + ":" + foods.getName(0), "inventory"),
	    		new ModelResourceLocation(UtilityBlocks.MODID + ":" + foods.getName(1), "inventory"),
	    		new ModelResourceLocation(UtilityBlocks.MODID + ":" + foods.getName(2), "inventory"),
	    		new ModelResourceLocation(UtilityBlocks.MODID + ":" + foods.getName(3), "inventory"),
	    		new ModelResourceLocation(UtilityBlocks.MODID + ":" + foods.getName(4), "inventory"),
	    		new ModelResourceLocation(UtilityBlocks.MODID + ":" + foods.getName(5), "inventory"),
	    		new ModelResourceLocation(UtilityBlocks.MODID + ":" + foods.getName(6), "inventory"),
	    		new ModelResourceLocation(UtilityBlocks.MODID + ":" + foods.getName(7), "inventory"),
	    		new ModelResourceLocation(UtilityBlocks.MODID + ":" + foods.getName(8), "inventory")
	    		);
	}
	
	
	public static void registerOreDict() {
    	OreDictionary.registerOre("toolHandle", new ItemStack(handle,1));
    	OreDictionary.registerOre("wandMagic", new ItemStack(wand,1));
    	OreDictionary.registerOre("sack", new ItemStack(sacks[0],1));
		
	}
	
	public static void addCraftingRecipies() {
		addRecipiesForCustomItems();

        addRecipiesForVanillaItems();
	}

	public static void initialiseBaseMetalsItems() {
		/* Create the versions for Base Metals */ 
		for(MetalMaterial m : Materials.getAllMetals())
		{
			if ( ! ( // if none of the following are true - skip the vanilla materials
					m.getName().toLowerCase(Locale.ENGLISH).equals("iron") ||
					m.getName().toLowerCase(Locale.ENGLISH).equals("stone") ||
					m.getName().toLowerCase(Locale.ENGLISH).equals("wood") ||
					m.getName().toLowerCase(Locale.ENGLISH).equals("diamond") ||
					m.getName().toLowerCase(Locale.ENGLISH).equals("gold") ||
					m.getName().toLowerCase(Locale.ENGLISH).equals("obsidian")
					)
				)
			{
				UtilityBlocks.LOG.info("Creating tools for " + m.getName());
				ToolMattock mattock =  new ToolMattock(Materials.getToolMaterialFor(m));
				ToolSledgehammer sledgehammer =  new ToolSledgehammer(Materials.getToolMaterialFor(m));
	
				Mattocks.put("mattock"+m.getCapitalizedName(), mattock);
				Sledgehammers.put("sledgehammer"+m.getCapitalizedName(), sledgehammer);
				
				ToolToIngot.put(mattock, "ingot" + m.getCapitalizedName());
				ToolToIngot.put(sledgehammer, "ingot" + m.getCapitalizedName());
			}
		}
	}
	/**
	 * 
	 */
	private static void addRecipiesForVanillaItems() {
		UtilityBlocks.LOG.info( "generating vanilla recipies");
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.feather,1), 
        		"rl",
        		"rl",
        		"rl",
        		'r', new ItemStack(Items.reeds), 'l', new ItemStack( Blocks.leaves )
        		)
        );
//		GameRegistry.addShapedRecipe(new ItemStack(Items.feather,1), 
//        		"rw",
//        		'r', new ItemStack(Items.reeds,1), 'w', new ItemStack(Blocks.wool, 1)
//        );
		try{
			GameRegistry.addShapedRecipe(new ItemStack(Items.feather,1), 
	        		"rw",
	        		'r', new ItemStack(Items.reeds,1), 'w', "materialCloth"
	        );
		}
		catch (Exception e)
		{
			UtilityBlocks.LOG.error("Failed while trying to use materialCloth from oredict!?");
			UtilityBlocks.LOG.error(e.getMessage());
			UtilityBlocks.LOG.error(e.getStackTrace());

			GameRegistry.addShapedRecipe(new ItemStack(Items.feather,1), 
	        		"rw",
	        		'r', new ItemStack(Items.reeds,1), 'w', new ItemStack(Blocks.wool,1)
	        );
		}
		finally
		{
		
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.wool,1), 
        		"fff",
        		"fff",
        		"fff",
        		'f', Items.feather
        		)
        );
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sand, 4), new ItemStack(Blocks.sandstone));
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.grass),
				"sss",
				"sds",
				"sss",
				's', Items.wheat_seeds,
				'd', Item.getItemFromBlock(Blocks.dirt)
				);
	}
	/**
	 * 
	 */
	private static void addRecipiesForCustomItems() {
		ItemStack waterbottle = new ItemStack( Items.potionitem, 1, 0);
		GameRegistry.addShapedRecipe(new ItemStack(sacks[0],4,0), new Object[]{ "w w", "w w", "www", 	'w', new ItemStack(Blocks.wool)});
		GameRegistry.addShapedRecipe(new ItemStack(sacks[1],1,0), new Object[]{ "ccc", "csc", "ccc", 	'c', new ItemStack(Items.dye,1,3), 's', new ItemStack(sacks[0],1)});
		GameRegistry.addShapedRecipe(new ItemStack(sacks[2],1,0), new Object[]{ "ccc", "csc", "ccc", 	'c', new ItemStack(Items.potato), 's', new ItemStack(sacks[0],1)});
		GameRegistry.addShapedRecipe(new ItemStack(sacks[3],1,0), new Object[]{ "ccc", "csc", "ccc", 	'c', new ItemStack(Items.carrot), 's', new ItemStack(sacks[0],1)});
		GameRegistry.addShapedRecipe(new ItemStack(sacks[4],1,0), new Object[]{ "ccc", "csc", "ccc", 	'c', new ItemStack(Items.wheat_seeds), 's', new ItemStack(sacks[0],1)});
		GameRegistry.addShapedRecipe(new ItemStack(sacks[5],1,0), new Object[]{ "ccc", "csc", "ccc", 	'c', new ItemStack(Items.feather), 's', new ItemStack(sacks[0],1)});
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,3), new ItemStack(sacks[1].setContainerItem(sacks[0])));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.potato,8), new ItemStack(sacks[2].setContainerItem(sacks[0])));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.carrot,8), new ItemStack(sacks[3].setContainerItem(sacks[0])));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.wheat_seeds,8), new ItemStack(sacks[4].setContainerItem(sacks[0])));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.feather,8), new ItemStack(sacks[4].setContainerItem(sacks[0])));
		
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 0),	new Object[]{"A","B", 				'A', new ItemStack( Items.apple), 'B', waterbottle});
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 1),	new Object[]{"ASA","WWW", 			'A', new ItemStack( Items.apple), 'W', new ItemStack( Items.wheat), 'S', new ItemStack(foods, 1, 7)});
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 1),	new Object[]{"   ", "ASA","WWW", 	'A', new ItemStack( Items.apple), 'W', new ItemStack( Items.wheat), 'S', new ItemStack(foods, 1, 7)});
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 2),	new Object[]{"A","M","B", 			'A', new ItemStack( Items.egg), 'M', new ItemStack( Items.milk_bucket.setContainerItem(Items.bucket)), 'B', Items.glass_bottle});
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 3),	new Object[]{"A","B", 				'A', new ItemStack( Items.apple), 'B', waterbottle});
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 4),	new Object[]{"P","B", 				'P', new ItemStack( Items.potato), 'B', waterbottle});
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 5),	new Object[]{"C","B", 				'C', new ItemStack( Items.carrot), 'B', waterbottle});
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 6),	new Object[]{"S","B", 				'S', new ItemStack(foods, 1, 7), 'B', waterbottle});
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 7),	new Object[]{"SS","SS", 			'S', new ItemStack(Items.sugar)});
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 8),	new Object[]{"F","L", 				'F', new ItemStack( Items.fish, 1, 0), 'L', new ItemStack( Blocks.leaves )});		
		GameRegistry.addShapedRecipe(new ItemStack(foods, 1, 8),	new Object[]{"F","L", 				'F', new ItemStack( Items.fish, 1, 1), 'L', new ItemStack( Blocks.leaves )});		

        makeMattockRecepie(new ItemStack(Mattocks.get("mattockWood"),1), "logWood", "toolHandle");
        makeMattockRecepie(new ItemStack(Mattocks.get("mattockStone"),1), "stone", "toolHandle");
        makeMattockRecepie(new ItemStack(Mattocks.get("mattockIron"),1), "ingotIron", "toolHandle");
        makeMattockRecepie(new ItemStack(Mattocks.get("mattockDiamond"),1), "gemDiamond", "toolHandle");
        makeMattockRecepie(new ItemStack(Mattocks.get("mattockObsidian"),1), "obsidian", "toolHandle");
        
        makeHammerRecepie(new ItemStack(Sledgehammers.get("sledgehammerWood"),1), "logWood", "toolHandle");
        makeHammerRecepie(new ItemStack(Sledgehammers.get("sledgehammerStone"),1), "stone", "toolHandle");
        makeHammerRecepie(new ItemStack(Sledgehammers.get("sledgehammerIron"),1), "ingotIron", "toolHandle");
        makeHammerRecepie(new ItemStack(Sledgehammers.get("sledgehammerDiamond"),1), "gemDiamond", "toolHandle");
        makeMattockRecepie(new ItemStack(Sledgehammers.get("sledgehammerObsidian"),1), "obsidian", "toolHandle");
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(handle,1), 
        		"s  ",
        		" s ",
        		"  s",
        		's', "stickWood"
        		)
        );
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wand,1), 
        		"s  ",
        		" s ",
        		"  s",
        		's', "toolHandle"
        		)
        );
	}

	public static void addRecipiesBaseMetalsItems() {
		for ( ToolMattock m : Mattocks.values() )
		{
			if (ToolToIngot.containsKey(m))
			{
				makeMattockRecepie(new ItemStack(m,1), ToolToIngot.get(m), "toolHandle");
			}
		}
		for ( ToolSledgehammer s : Sledgehammers.values() )
		{
			if (ToolToIngot.containsKey(s))
			{
				makeHammerRecepie(new ItemStack(s,1), ToolToIngot.get(s), "toolHandle");
			}
		}
	}

	public static void makeMattockRecepie(ItemStack mattockStack,
			String headMaterial, String handleMaterial) {
        GameRegistry.addRecipe(new ShapedOreRecipe(mattockStack, 
        		"iii",
        		" s ",
        		" s ",
        		'i', headMaterial, 's', handleMaterial
        		)
        );
		
	}
	public static void makeHammerRecepie(ItemStack sledgeHammerStack,
			String headMaterial, String handleMaterial) {
        GameRegistry.addRecipe(new ShapedOreRecipe(sledgeHammerStack, 
        		"iii",
        		"iii",
        		" s ",
        		'i', headMaterial, 's', handleMaterial
        		)
        );
		
	}
	@SuppressWarnings("unused")
	private static void registerShapedRecipie(ItemMetadataFood item, int meta,
			Object...recipie) {
		ItemStack outputItem = new ItemStack(item, 1, meta);
		GameRegistry.addShapedRecipe(outputItem, recipie);
	}
	
	/* private static void registerShapedRecipie(ItemStack outputItem, Object[] recipie) {
		GameRegistry.addRecipe(outputItem, recipie);
	} */
	public static void graphicRegistry() {
		for (ToolMattock m : Mattocks.values())
		{
			registerWithMesher(m, 0);
		}
		
		for (ToolSledgehammer s : Sledgehammers.values())
		{
			UtilityBlocks.LOG.info("SYNDARYL: meshing "  + s.getName() );
			registerWithMesher(s, 0);
		}

		registerWithMesher(handle, 0);
		registerWithMesher(wand, 0);
		
		for (ItemSack sack : sacks){
				registerWithMesher(sack,0);
		}
	
			    
	    for (int i = 0; i < foodData.length; i ++) {
			registerWithMesher(foods, i);
	    }
	}


	public static void graphicRegistryBaseMetalsItems() {
		
	}
	
	
	/**
	 * @param item 
	 * @param metadata 
	 * @return
	 */
	private static ItemModelMesher registerWithMesher(IItemName item, int metadata) {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		String name = UtilityBlocks.MODID + ":" + item.getName(metadata);
		
	    // UtilityBlocks.LOG.warn("SYNDARYL: item ModelResourceLocation: Where the hell are my models?: "  + name );
	    mesher.register((Item)item, metadata, new ModelResourceLocation( name, "inventory" ));

	    return mesher;
	}
	
	private static String[] getStringFromList(Object[][] list, int item)
	{
		String[] names = new String[list.length];
		for(int i = 0; i < list.length; i++)
		{
			names[i] = (String) list[i][item];
		}
		return names;
		
	}
	
	private static float[] getFloatFromList(Object[][] list, int item) {
		float[] names = new float[list.length];
		for(int i = 0; i < list.length; i++)
		{
			names[i] = Float.parseFloat( (String) list[i][item]);
		}
		return names;
	}
	private static int[] getIntFromList(Object[][] list, int item) {
		int[] names = new int[list.length];
		for(int i = 0; i < list.length; i++)
		{
			names[i] = (int) Float.parseFloat( (String) list[i][item]);
		}
		return names;
	}
	
	@SuppressWarnings({ "unused"})
	private static Object[][] getRecepieListFromList(Object[][] list, int item)
	{
		Object[][] objects = new Object[list.length][];
		for(int i = 0; i < list.length; i++)
		{
			objects[i] = (Object[]) list[i][item];
		}
		return objects;
	}
}
