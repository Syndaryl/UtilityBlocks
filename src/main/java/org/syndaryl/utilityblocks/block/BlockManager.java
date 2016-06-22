package org.syndaryl.utilityblocks.block;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
//import cpw.mods.fml.common.registry.GameRegistry;
//import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import org.syndaryl.utilityblocks.UtilityBlocks;
import org.syndaryl.utilityblocks.NamespaceManager;
import org.syndaryl.utilityblocks.handler.FurnaceFuelHandler;

public class BlockManager {

	public BlockManager() {
		// TODO Auto-generated constructor stub
		
	}
	public static void mainRegistry() {
		initialiseBlock();
		addCraftingRecipies();
	}
	
	public static final LinkedList<BlockCompressed> COMPRESSEDBLOCKS = new LinkedList<BlockCompressed>();
	
	public static class BlockData {
		static int GROUND = 0;
		static int BLOCKNAME = 1;
		static int SOUNDTYPE = 2;
		static int HARDNESS = 3;
		static int BLASTRESISTANCE =4;
		static int ISFUEL=5;
		static int COMPONENTITEM=6;
		static int FUELVALUE=7;
	};
	
	public static LinkedList<Object[]> BlockConstruct =
		new LinkedList<Object[]> ();
	static Object[][] BlockStruct = new Object[][]{
			{Material.GROUND, 	"utilityblocks_gravel_compressed",     	SoundType.GROUND, 	0.6F, 3.0F, 	false,	new ItemStack(Blocks.GRAVEL,1),		0},
			{Material.ROCK, 	"utilityblocks_cobblestone_compressed", 	SoundType.STONE, 	2.0F, 10.0F, 	false,	new ItemStack(Blocks.COBBLESTONE,1),		0},
			{Material.ROCK, 	"utilityblocks_stone_compressed",      	SoundType.STONE, 	2.2F, 11.0F, 	false,	new ItemStack(Blocks.STONE,1),		0},
			{Material.ROCK, 	"utilityblocks_sandstone_compressed",      	SoundType.STONE, 	1.5F, 8.0F, 	false,	new ItemStack(Blocks.SANDSTONE,1),		0},
			{Material.GROUND, 	"utilityblocks_dirt_compressed",       	SoundType.GROUND, 	0.5F, 2.0F, 	false,	new ItemStack(Blocks.DIRT,1,0),		0},
			{Material.SAND, 	"utilityblocks_sand_compressed",       	SoundType.SAND, 	0.5F, 2.0F, 	false,	new ItemStack(Blocks.SAND,1,0),		0},
			{Material.CORAL, 	"utilityblocks_charcoal_compressed",   	SoundType.STONE, 	0.8F, 5.0F, 	true,	new ItemStack(Items.COAL,1,1),  	16000.0F},
			{Material.CORAL, 	"utilityblocks_bone_compressed",       	SoundType.STONE, 	0.7F, 4.0F, 	false,	new ItemStack(Items.BONE,1,0),		0},
			{Material.WOOD, 	"utilityblocks_sugarcane_compressed",   	SoundType.PLANT, 	0.4F, 2.0F, 	true,	new ItemStack(Items.REEDS,1,0), 	8000.0F },
			{Material.ROCK, 	"utilityblocks_flint_compressed", 	SoundType.STONE, 	2.1F, 10.5F, 	false,	new ItemStack(Items.FLINT,1),		0},
			};
	public static void initialiseBlock() {

		for (Object[] o : 		BlockStruct)
		{
			BlockManager.BlockConstruct.add(o);
		}
		
		for (int i = 0; i < BlockManager.BlockConstruct.size(); i++)
		{
			UtilityBlocks.LOG.info("building block "  + BlockManager.BlockConstruct.get(i)[1] );
			BlockManager.COMPRESSEDBLOCKS.add(
					new BlockCompressed(
							(Material) BlockManager.BlockConstruct.get(i)[BlockData.GROUND], // ground
							(String) BlockManager.BlockConstruct.get(i)[BlockData.BLOCKNAME],   // block-name
							(SoundType) BlockManager.BlockConstruct.get(i)[BlockData.SOUNDTYPE], // sound-type
							(Float) BlockManager.BlockConstruct.get(i)[BlockData.HARDNESS] * 1.2F, // hardness
							(Float) BlockManager.BlockConstruct.get(i)[BlockData.BLASTRESISTANCE]) // blast resistance
					);
		}
	}

    static void registerBlockWithItem(Block block, String blockName, Class<? extends ItemBlock> itemBlockClass)
    {
            ResourceLocation location = new ResourceLocation(UtilityBlocks.MODID, blockName);

    		try {
    			GameRegistry.register(block, location);
			} catch ( IllegalArgumentException | SecurityException e) {
				// TODO Auto-generated catch block
				UtilityBlocks.LOG.fatal(e);
			}
            Item itemBlock;
        	if (itemBlockClass == null)
    		{
        		ResourceLocation registryName = block.getRegistryName();
				itemBlock = new ItemBlock(block).setRegistryName(registryName);
        		try {
        			GameRegistry.register(itemBlock);
				} catch ( IllegalArgumentException | SecurityException e) {
					// TODO Auto-generated catch block
					UtilityBlocks.LOG.fatal(e);
				}
    		}
        	else
        	{
        		try {
					itemBlock = (Item)itemBlockClass.getConstructor(Block.class).newInstance(block);
		            GameRegistry.register(itemBlock);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					UtilityBlocks.LOG.fatal(e);
				}
        	}
    }
	public static void registerOreDict() {

		for (int i = 0; i < BlockManager.COMPRESSEDBLOCKS.size(); i++)
		{
			try
			{
				BlockCompressed compressed = BlockManager.COMPRESSEDBLOCKS.get(i);
				ItemStack block = new ItemStack(Item.getItemFromBlock( compressed),1);
				String nameBase = block.getItem().getUnlocalizedName();
				String name = nameBase.replaceFirst(NamespaceManager.GetModNameLC() + "_", "")
				.replaceFirst("(^.+)_(compressed)", "blockCompressed$1");
				UtilityBlocks.LOG.info("Registering %s with oredict as %s", nameBase, name );
				OreDictionary.registerOre(
							name, 
							block
				);
			}
			catch (Exception e)
			{
				UtilityBlocks.LOG.error("Failed while trying to register compressed blocks with oredict!?");
				UtilityBlocks.LOG.error(e.getMessage());
				UtilityBlocks.LOG.error(e.getStackTrace());
			}
		}
		
		for (int i = 0; i < 16; i++)
		{
			try
			{
				OreDictionary.registerOre( "blockWool", new ItemStack(Blocks.WOOL,1, i) );
			}
			catch (Exception e)
			{
				UtilityBlocks.LOG.error("Failed while trying to register blockWool with oredict!?");
				UtilityBlocks.LOG.error(e.getMessage());
				UtilityBlocks.LOG.error(e.getStackTrace());
			}
			finally{
			
			}
		}
	}
	
	public static void graphicRegistry()
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (int i = 0; i < BlockManager.COMPRESSEDBLOCKS.size(); i++)
		{
			String name = UtilityBlocks.MODID + ":" + ((BlockCompressed) BlockManager.COMPRESSEDBLOCKS.get(i)).getName();
			UtilityBlocks.LOG.warn("SYNDARYL: meshing block "  + name );
			mesher.register(
					Item.getItemFromBlock(BlockManager.COMPRESSEDBLOCKS.get(i)), 0, new ModelResourceLocation(
							name, "inventory"
							)
					);
		}
	}


	
	public static void addFuels(FurnaceFuelHandler fuelhandler) {
		for (int i = 0; i < BlockManager.COMPRESSEDBLOCKS.size(); i++)
		{
			if ((Boolean) BlockManager.BlockConstruct.get(i)[BlockData.ISFUEL] )
			fuelhandler.addFuel((Block)BlockManager.COMPRESSEDBLOCKS.get(i), Math.round( 
					(Float)BlockManager.BlockConstruct.get(i)[BlockData.FUELVALUE]) 
				);
		}
	}
	
	public static void addCraftingRecipies() {
		for (int i = 0; i < BlockManager.COMPRESSEDBLOCKS.size(); i++)
		{
			// Shaped Recipe
			registerCompressedRecipie((ItemStack)BlockManager.BlockConstruct.get(i)[6], BlockManager.COMPRESSEDBLOCKS.get(i));
			//Shapeless Recipe
			registerDecompressedRecipie(new ItemStack(((ItemStack)BlockManager.BlockConstruct.get(i)[BlockData.COMPONENTITEM]).getItem(), 9, ((ItemStack)BlockManager.BlockConstruct.get(i)[BlockData.COMPONENTITEM]).getMetadata()), BlockManager.COMPRESSEDBLOCKS.get(i));
		}
	}
	/**
	 * @param outputStack
	 * @param inputBlock
	 */
	private static void registerDecompressedRecipie(final ItemStack outputStack, final Block inputBlock) {
		GameRegistry.addShapelessRecipe(outputStack, new Object[]{inputBlock});
	}
	
	/**
	 * @param inputStack
	 * @param outputBlock
	 */
	private static void registerCompressedRecipie(final ItemStack inputStack, final Block outputBlock) {
		GameRegistry.addRecipe(new ItemStack(outputBlock, 1), new Object[] { 
			"###", 
			"###", 
			"###", 
			'#', inputStack  });
	}
}