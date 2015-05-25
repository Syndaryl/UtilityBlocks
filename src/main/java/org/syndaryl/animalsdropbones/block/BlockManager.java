package org.syndaryl.animalsdropbones.block;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
//import cpw.mods.fml.common.registry.GameRegistry;
//import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import org.syndaryl.animalsdropbones.AnimalsDropBones;
import org.syndaryl.animalsdropbones.NamespaceManager;
import org.syndaryl.animalsdropbones.handler.FurnaceFuelHandler;

public class BlockManager {

	public BlockManager() {
		// TODO Auto-generated constructor stub
		
	}
	public static void mainRegistry() {
        //registerBlock(8, "flowing_water", (new BlockDynamicLiquid(Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
        //registerBlock(9, "water", (new BlockStaticLiquid(Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
        //registerBlock(10, "flowing_lava", (new BlockDynamicLiquid(Material.lava)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
        //registerBlock(11, "lava", (new BlockStaticLiquid(Material.lava)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());

		
		initialiseBlock();
		addCraftingRecipies();
	}
	
	public static final LinkedList<BlockCompressed> COMPRESSEDBLOCKS = new LinkedList<BlockCompressed>();
	
	private static final Object[][] BlockSpecifications =
		new Object[][] {
		{Material.ground, 	"animalsdropbones_gravel_compressed",     	Block.soundTypeGravel, 	0.6F, 3.0F, 	false,	new ItemStack(Blocks.gravel,1),		0},
		{Material.rock, 	"animalsdropbones_cobblestone_compressed", 	Block.soundTypePiston, 	2.0F, 10.0F, 	false,	new ItemStack(Blocks.cobblestone,1),		0},
		{Material.rock, 	"animalsdropbones_stone_compressed",      	Block.soundTypeStone, 	2.2F, 11.0F, 	false,	new ItemStack(Blocks.stone,1),		0},
		{Material.ground, 	"animalsdropbones_dirt_compressed",       	Block.soundTypeGravel, 	0.5F, 2.0F, 	false,	new ItemStack(Blocks.dirt,1,0),		0},
		{Material.sand, 	"animalsdropbones_sand_compressed",       	Block.soundTypeSand, 	0.5F, 2.0F, 	false,	new ItemStack(Blocks.sand,1,0),		0},
		{Material.coral, 	"animalsdropbones_charcoal_compressed",   	Block.soundTypePiston, 	0.8F, 5.0F, 	true,	new ItemStack(Items.coal,1,1),  	16000.0F},
		{Material.coral, 	"animalsdropbones_bone_compressed",       	Block.soundTypePiston, 	0.7F, 4.0F, 	false,	new ItemStack(Items.bone,1,0),		0},
		{Material.wood, 	"animalsdropbones_sugarcane_compressed",   	Block.soundTypeGrass, 	0.4F, 2.0F, 	true,	new ItemStack(Items.reeds,1,0), 	8000.0F }
		};

	public static void initialiseBlock() {
		for (int i = 0; i < BlockManager.BlockSpecifications.length; i++)
		{
			AnimalsDropBones.LOG.info("SYNDARYL: building block "  + BlockManager.BlockSpecifications[i][1] );
			BlockManager.COMPRESSEDBLOCKS.add(
					new BlockCompressed(
							(Material) BlockManager.BlockSpecifications[i][0], 
							(String) BlockManager.BlockSpecifications[i][1], 
							(SoundType) BlockManager.BlockSpecifications[i][2], 
							(Float) BlockManager.BlockSpecifications[i][3] * 1.2F, 
							(Float) BlockManager.BlockSpecifications[i][4])
					);
		}
	}
	
	public static void registerOreDict() {

		for (int i = 0; i < BlockManager.COMPRESSEDBLOCKS.size(); i++)
		{
			String name = BlockManager.COMPRESSEDBLOCKS.get(i).getName()
			.replaceFirst(NamespaceManager.GetModNameLC() + "_", "")
			.replaceFirst("(^.+)_(compressed)", "blockCompressed$1"); 
			GameRegistry.registerBlock(BlockManager.COMPRESSEDBLOCKS.get(i), BlockManager.COMPRESSEDBLOCKS.get(i).getName());
			OreDictionary.registerOre(name, 
				new ItemStack(
						BlockManager.COMPRESSEDBLOCKS.get(i),1
					)
			);
		}
	}
	
	public static void graphicRegistry()
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (int i = 0; i < BlockManager.COMPRESSEDBLOCKS.size(); i++)
		{
			String name = AnimalsDropBones.MODID + ":" + ((BlockCompressed) BlockManager.COMPRESSEDBLOCKS.get(i)).getName();
			AnimalsDropBones.LOG.info("SYNDARYL: meshing block "  + name );
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
			if ((Boolean) BlockManager.BlockSpecifications[i][5] )
			fuelhandler.addFuel((Block)BlockManager.COMPRESSEDBLOCKS.get(i), Math.round( 
					(Float)BlockManager.BlockSpecifications[i][7]) 
				);
		}
	}
	
	public static void addCraftingRecipies() {
		for (int i = 0; i < BlockManager.COMPRESSEDBLOCKS.size(); i++)
		{
			// Shaped Recipe
			registerCompressedRecipie((ItemStack)BlockManager.BlockSpecifications[i][6], BlockManager.COMPRESSEDBLOCKS.get(i));
			//Shapeless Recipe
			registerDecompressedRecipie(new ItemStack(((ItemStack)BlockManager.BlockSpecifications[i][6]).getItem(), 9, ((ItemStack)BlockManager.BlockSpecifications[i][6]).getMetadata()), BlockManager.COMPRESSEDBLOCKS.get(i));
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