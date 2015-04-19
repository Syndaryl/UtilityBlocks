/**
 * 
 */
package org.syndaryl.animalsdropbones.item;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.syndaryl.animalsdropbones.AnimalsDropBones;
import org.syndaryl.animalsdropbones.block.BlockManager;

/**
 * @author syndaryl
 *
 */
public class ItemManager {
	public static ItemMetadataFood foods;
	public static ToolMattock mattockWood;
	public static ToolSledgehammer sledgehammerWood;
	public static ToolMattock mattockStone;
	public static ToolSledgehammer sledgehammerStone;
	public static ToolMattock mattockIron;
	public static ToolSledgehammer sledgehammerIron;
	public static ToolMattock mattockDiamond;
	private static ToolSledgehammer sledgehammerDiamond;
	public static ToolHandle handle;
	
	private final static int NAME = 0;
	private final static int HUNGER = 1;
	private final static int SATIATION = 2;
	private static final int ACTIONS = 3;
	
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
		
		foods = (ItemMetadataFood) new ItemMetadataFood(hunger, satiation, names, actions);
		
		mattockWood = (ToolMattock) new ToolMattock(ToolMaterial.WOOD);
		sledgehammerWood = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.WOOD);
		mattockStone = (ToolMattock) new ToolMattock(ToolMaterial.STONE);
		sledgehammerStone = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.STONE);
		mattockIron = (ToolMattock) new ToolMattock(ToolMaterial.IRON);
		sledgehammerIron = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.IRON);
		mattockDiamond = (ToolMattock) new ToolMattock(ToolMaterial.EMERALD);
		sledgehammerDiamond = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.EMERALD);
		handle = (ToolHandle) new ToolHandle();
		
	}

	public static void variantRegistry() {
	    ModelBakery.addVariantName(foods,  AnimalsDropBones.MODID + ":" + foods.getName(0),
	    		AnimalsDropBones.MODID + ":" + foods.getName(1),
	    		AnimalsDropBones.MODID + ":" + foods.getName(2),
	    		AnimalsDropBones.MODID + ":" + foods.getName(3),
	    		AnimalsDropBones.MODID + ":" + foods.getName(4),
	    		AnimalsDropBones.MODID + ":" + foods.getName(5),
	    		AnimalsDropBones.MODID + ":" + foods.getName(6),
	    		AnimalsDropBones.MODID + ":" + foods.getName(7),
	    		AnimalsDropBones.MODID + ":" + foods.getName(8)
	    );
	}
	
	
	public static void registerOreDict() {
    	OreDictionary.registerOre("toolHandle", new ItemStack(handle,1));
		
	}
	
	public static void addCraftingRecipies() {
//		Object[][] recipies = getRecepieListFromList(foodData, ItemManager.RECIPIES);
//		for (int i = 0; i < foods.getMaxMetadata(); i++)
//		{
//			// Shaped Recipe
//			registerShapedRecipie( foods, i,  recipies[i]);
//		}
		ItemStack waterbottle = new ItemStack( Items.potionitem, 1, 0);
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
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(handle,1), 
        		"s  ",
        		" s ",
        		"  s",
        		's', "stickWood"
        		)
        );

        
        makeMattockRecepie(new ItemStack(mattockWood,1), "logWood", "toolHandle");
        makeMattockRecepie(new ItemStack(mattockStone,1), "stone", "toolHandle");
        makeMattockRecepie(new ItemStack(mattockIron,1), "ingotIron", "toolHandle");
        makeMattockRecepie(new ItemStack(mattockDiamond,1), "gemDiamond", "toolHandle");
        
        makeHammerRecepie(new ItemStack(sledgehammerWood,1), "logWood", "toolHandle");
        makeHammerRecepie(new ItemStack(sledgehammerStone,1), "stone", "toolHandle");
        makeHammerRecepie(new ItemStack(sledgehammerIron,1), "ingotIron", "toolHandle");
        makeHammerRecepie(new ItemStack(sledgehammerDiamond,1), "gemDiamond", "toolHandle");
        
	}

	private static void makeMattockRecepie(ItemStack mattockStack,
			String headMaterial, String handleMaterial) {
        GameRegistry.addRecipe(new ShapedOreRecipe(mattockStack, 
        		"iii",
        		" s ",
        		" s ",
        		'i', headMaterial, 's', handleMaterial
        		)
        );
		
	}
	private static void makeHammerRecepie(ItemStack sledgeHammerStack,
			String headMaterial, String handleMaterial) {
        GameRegistry.addRecipe(new ShapedOreRecipe(sledgeHammerStack, 
        		"iii",
        		"iii",
        		" s ",
        		'i', headMaterial, 's', handleMaterial
        		)
        );
		
	}
	private static void registerShapedRecipie(ItemMetadataFood item, int meta,
			Object...recipie) {
		ItemStack outputItem = new ItemStack(item, 1, meta);
		GameRegistry.addShapedRecipe(outputItem, recipie);
	}
	/* private static void registerShapedRecipie(ItemStack outputItem, Object[] recipie) {
		GameRegistry.addRecipe(outputItem, recipie);
	} */
	public static void graphicRegistry() {
		registerWithMesher(mattockWood, 0);
		registerWithMesher(sledgehammerWood, 0);
		registerWithMesher(mattockStone, 0);
		registerWithMesher(sledgehammerStone, 0);
		registerWithMesher(mattockIron, 0);
		registerWithMesher(sledgehammerIron, 0);
		registerWithMesher(handle, 0);
			    
	    for (int i = 0; i < foodData.length; i ++) {
			registerWithMesher(foods, i);
	    }

    	 
	}
	/**
	 * @param item 
	 * @param metadata 
	 * @return
	 */
	private static ItemModelMesher registerWithMesher(IItemName item, int metadata) {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		String name = AnimalsDropBones.MODID + ":" + item.getName(metadata);
		
	    //AnimalsDropBones.LOG.warn("SYNDARYL: item ModelResourceLocation: Where the hell are my models?: "  + name );
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
