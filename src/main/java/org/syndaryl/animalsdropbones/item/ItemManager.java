/**
 * 
 */
package org.syndaryl.animalsdropbones.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;

import org.syndaryl.animalsdropbones.AnimalsDropBones;

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
	
	private final static int NAME = 0;
	private final static int HUNGER = 1;
	private final static int SATIATION = 2;
	
	private static String[][] foodData = new String[][]{
		{"apple_juice",       "1",     "0.5"  },
		{"apple_pie",         "5",     "2"    },
		{"eggnog",            "2",     "1"    },
		{"energy_drink",      "2",     "0.5"  },
		//{"golden_carrot",     "4",     "2"    },
		{"potato_juice",      "2",     "0.75"  },
		{"carrot_juice",      "2",     "1"  },
		{"sugar_water",       "1",     "0.5"  },
		{"sugar_cube",        "1",     "0.5"  },
		{"sushi",             "3",     "1"    }
	};
	
	public ItemManager() {
		// TODO Auto-generated constructor stub
	}
	public static void mainRegistry() {
		initialiseItems();
		registerItems();
		addCraftingRecipies();
	}
	public static void initialiseItems () {
		String[] names;
		int[] hunger;
		float[] satiation;
		names = getStringFromList(foodData, ItemManager.NAME);
		hunger = (int[]) getIntFromList(foodData, ItemManager.HUNGER);
		satiation = getFloatFromList(foodData, ItemManager.SATIATION);
		
		foods = (ItemMetadataFood) new ItemMetadataFood(hunger, satiation, names).setCreativeTab(CreativeTabs.tabFood);
		
		mattockWood = (ToolMattock) new ToolMattock(ToolMaterial.WOOD);
		sledgehammerWood = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.WOOD);
		mattockStone = (ToolMattock) new ToolMattock(ToolMaterial.STONE);
		sledgehammerStone = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.STONE);
		mattockIron = (ToolMattock) new ToolMattock(ToolMaterial.IRON);
		sledgehammerIron = (ToolSledgehammer) new ToolSledgehammer(ToolMaterial.IRON);
		
	}
	public static void registerItems() {
		
	}
	
	public static void addCraftingRecipies() {
		// TODO Auto-generated method stub
		
	}

	public static void graphicRegistry() {
		registerWithMesher(mattockWood, 0);
		registerWithMesher(sledgehammerWood, 0);
		registerWithMesher(mattockStone, 0);
		//registerWithMesher(sledgehammerStone, 0);
		registerWithMesher(mattockIron, 0);
		registerWithMesher(sledgehammerIron, 0);
		
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		String name = AnimalsDropBones.MODID + ":" + sledgehammerStone.getName();
		AnimalsDropBones.LOG.warn("SYNDARYL: item " + name);
		mesher.register(sledgehammerStone, 0, new ModelResourceLocation(name));
		
	    
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
		
	    AnimalsDropBones.LOG.warn("SYNDARYL: item ModelResourceLocation: Where the hell are my models?: "  + name );
	    mesher.register((Item)item, metadata, new ModelResourceLocation( name ));
	    // I'm using a different model for each metadata so I don't need this. I think.
		//		if ( metadata != 0)
		//		{
		//			ModelBakery.addVariantName((Item)item, name);
		//
		//		}
		return mesher;
	}
	
	private static String[] getStringFromList(String[][] list, int item)
	{
		String[] names = new String[list.length];
		for(int i = 0; i < list.length; i++)
		{
			names[i] = list[i][item];
		}
		return names;
		
	}
	
	private static float[] getFloatFromList(String[][] list, int item) {
		float[] names = new float[list.length];
		for(int i = 0; i < list.length; i++)
		{
			names[i] = Float.parseFloat( list[i][item]);
		}
		return names;
	}
	private static int[] getIntFromList(String[][] list, int item) {
		int[] names = new int[list.length];
		for(int i = 0; i < list.length; i++)
		{
			names[i] = (int) Float.parseFloat( list[i][item]);
		}
		return names;
	}

}
