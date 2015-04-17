/**
 * 
 */
package org.syndaryl.animalsdropbones.item;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
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
	
	private final static int NAME = 0;
	private final static int HUNGER = 1;
	private final static int SATIATION = 2;
	
	private static String[][] foodData = new String[][]{
		{"apple_juice",       "1",     "0.5"  },
		{"apple_pie",         "5",     "2"    },
		{"eggnog",            "2",     "1"    },
		{"energy_drink",      "1",     "0.5"  },
		//{"golden_carrot",     "4",     "2"    },
		{"potato_juice",      "1",     "0.5"  },
		{"sugar_water",       "1",     "0.5"  },
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
		
	}
	public static void registerItems() {
		
	}
	
	public static void addCraftingRecipies() {
		// TODO Auto-generated method stub
		
	}

	public static void graphicRegistry() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
	    for (int i = 0; i < foodData.length; i ++) {
			String name = AnimalsDropBones.MODID + ":" + ((ItemMetadataFood) foods).getUnlocalizedName(i).replaceAll("item.", "");
	    	mesher.register(foods, i, new ModelResourceLocation(name, "inventory") );
	    	AnimalsDropBones.LOG.warn("MetaFood item ModelResourceLocation " + i + ": "  + name + "\n" );
    		System.out.println("MetaFood item ModelResourceLocation " + i + ": "  + name + "\n");
	    }
	    
	    mesher.register(mattockWood, 0, new ModelResourceLocation(mattockWood.getUnlocalizedName().replaceAll("item.", "")));
	    mesher.register(sledgehammerWood, 0, new ModelResourceLocation(sledgehammerWood.getUnlocalizedName().replaceAll("item.", "")));
	    
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
