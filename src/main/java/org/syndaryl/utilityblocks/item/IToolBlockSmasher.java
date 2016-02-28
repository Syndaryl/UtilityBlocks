package org.syndaryl.utilityblocks.item;

import java.util.Deque;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import org.syndaryl.utilityblocks.block.BlockWithLocation;

public interface IToolBlockSmasher {

	public abstract boolean onBlockDestroyed(ItemStack toolInstance,
			World gameWorld_, Block blockStruck, BlockPos pos,
			EntityLivingBase actor);

	/**
	 * @param toolInstance
	 * @param gameWorld_ world object to search for blocks
	 * @param worldX x coordinate of blockStruck
	 * @param worldY y coordinate of blockStruck
	 * @param worldZ z coordinate of blockStruck
	 * @param actor holding tool
	 * @param blockDeck collection of blocks to hammer against
	 */

	public abstract void hitManyBlocks(ItemStack toolInstance,
			World gameWorld_, int worldX, int worldY, int worldZ,
			EntityLivingBase actor, Deque<BlockWithLocation> blockDeck);

	/**
	 * dumb thing to replace a proper "break block" method until otherwise found
	 * Tries to generate a proper item drop, spawn the appropriate item, and then get the block to commit suicide. 
	 * 
	 * @param gameWorld_
	 * @param blockXYZ
	 */

	public abstract void breakBlock(World gameWorld_,
			BlockWithLocation blockXYZ, EntityLivingBase player);

	/**
	 * Collects the neighbouring blocks which are of the same block type as the struck block into a deQue object.
	 * @param gameWorld_ world object to search for neighbouring blocks
	 * @param blockStruck reference block, should be at the centre of the block effect
	 * @param worldX x coordinate of blockStruck
	 * @param worldY y coordinate of blockStruck
	 * @param worldZ z coordinate of blockStruck
	 * @param blockDeck deque object to add the found neighbours to
	 */

	public abstract void getNeighbouringBlocksToDeque(World gameWorld_,
			Block blockStruck, int worldX, int worldY, int worldZ,
			Deque<BlockWithLocation> blockDeck);

}