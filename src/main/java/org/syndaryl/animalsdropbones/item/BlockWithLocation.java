package org.syndaryl.animalsdropbones.item;

import net.minecraft.block.Block;

class BlockWithLocation {
	public BlockWithLocation(Block sourceBlock, int worldX, int worldY, int worldZ) {
		b = sourceBlock;
		x = worldX;
		y = worldY;
		z = worldZ;
		metadata = 0;
	}
	public BlockWithLocation(Block sourceBlock, int worldX, int worldY, int worldZ, int meta) {
		b = sourceBlock;
		x = worldX;
		y = worldY;
		z = worldZ;
		metadata = meta;
	}
	public Block b;
	public int x;
	public int y;
	public int z;
	public int metadata;
}
