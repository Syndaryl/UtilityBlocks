package org.syndaryl.utilityblocks.mineralogy;

import java.util.EnumMap;
import java.util.LinkedList;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import org.syndaryl.utilityblocks.NamespaceManager;
import org.syndaryl.utilityblocks.UtilityBlocks;
import org.syndaryl.utilityblocks.block.BlockCompressed;
import org.syndaryl.utilityblocks.block.BlockManager;

public class MineralogyLoader implements IMineralogyLoader {
	static class BSElement{
		 static final int MATERIAL = 0;
		 static final int NAME=1;
		 static final int SOUND=2;
		 static final int HARDNESS=3;
		 static final int BLASTPROOF=4;
		 static final int ISFUEL=5;
		 static final int ITEM=6;
		 static final int FUELVALUE=7;
	};
	//public final EnumMap<BSE, Integer> BSElement = new EnumMap<BSE,Integer>(BSE.class);
	public static final LinkedList<BlockCompressed> COMPRESSEDBLOCKS = new LinkedList<BlockCompressed>();
	private static final Object[][] BlockSpecifications =
			new Object[][] {
			{Material.GROUND, 	"utilityblocks_dustGypsum_compressed",     	SoundType.GROUND, 	0.75F, 1.0F, 	false,	new ItemStack(Item.getByNameOrId("mineralogy:gypsum_dust"),1,0),		0 }
			};
	@Override
	public void initialiseMineralogyItems() {
		UtilityBlocks.LOG.info("initialiseMineralogyItems");

//		for (int i = 0; i < MineralogyLoader.BlockSpecifications.length; i++)
//		{
//			UtilityBlocks.LOG.info("building Mineralogy block "  + MineralogyLoader.BlockSpecifications[i][1] );
//			MineralogyLoader.COMPRESSEDBLOCKS.add(
//					new BlockCompressed(
//							(Material) MineralogyLoader.BlockSpecifications[i][BSElement.MATERIAL], // ground
//							(String) MineralogyLoader.BlockSpecifications[i][BSElement.NAME],   // block-name
//							(SoundType) MineralogyLoader.BlockSpecifications[i][BSElement.SOUND], // sound-type
//							(Float) MineralogyLoader.BlockSpecifications[i][BSElement.HARDNESS] * 1.2F, // hardness
//							(Float) MineralogyLoader.BlockSpecifications[i][BSElement.BLASTPROOF]) // blast resistance
//					);
//		}
		for (Object[] block : BlockSpecifications)
			BlockManager.BlockConstruct.add(block);
	}

	@Override
	public void addRecipiesMineralogyItems() {

	}

	@Override
	public void graphicRegistryMineralogyItems() {

	}

}
