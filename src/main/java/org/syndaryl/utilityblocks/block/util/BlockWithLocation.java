package org.syndaryl.utilityblocks.block.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class BlockWithLocation {
	public BlockWithLocation(Block sourceBlock, BlockPos location) {
		setBblock(sourceBlock);
		setX(location.getX());
		setY(location.getY());
		setZ(location.getZ());
		setMetadata(0);
		setPos(new BlockPos( location));
	}
	public BlockWithLocation(Block sourceBlock, BlockPos location, int meta) {
		setBblock(sourceBlock);
		setX(location.getX());
		setY(location.getY());
		setZ(location.getZ());
		setMetadata(meta);
		setPos(new BlockPos( location));
	}
	public BlockWithLocation(Block sourceBlock, int worldX, int worldY, int worldZ) {
		setBblock(sourceBlock);
		setX(worldX);
		setY(worldY);
		setZ(worldZ);
		setMetadata(0);
		setPos(new BlockPos( getX(),getY(),getZ()));
	}
	public BlockWithLocation(Block sourceBlock, int worldX, int worldY, int worldZ, int meta) {
		setBblock(sourceBlock);
		setX(worldX);
		setY(worldY);
		setZ(worldZ);
		setMetadata(meta);
		setPos(new BlockPos( getX(),getY(),getZ()));
	}
	/**
	 * @return the b
	 */
	public Block getBlock() {
		return b;
	}
	/**
	 * @param b the b to set
	 */
	public void setBblock(Block b) {
		this.b = b;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}
	/**
	 * @param z the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}
	/**
	 * @return the metadata of the block
	 */
	public int getMetadata() {
		return metadata;
	}
	/**
	 * @param metadata the metadata of the block to set
	 */
	public void setMetadata(int metadata) {
		this.metadata = metadata;
	}
	/**
	 * @return the position as a BlockPos
	 */
	public BlockPos getPos() {
		return pos;
	}
	/**
	 * @param pos the pos to set
	 */
	public void setPos(BlockPos pos) {
		this.pos = pos;
	}
	private Block b;
	private int x;
	private int y;
	private int z;
	private int metadata;
	private BlockPos pos;
}
