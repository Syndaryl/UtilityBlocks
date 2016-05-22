package org.syndaryl.utilityblocks.item.util;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import org.syndaryl.utilityblocks.UtilityBlocks;
import org.syndaryl.utilityblocks.block.util.BlockWithLocation;
import org.syndaryl.utilityblocks.handler.ConfigurationHandler;

public class BlockSmasher {
    static Random r = new Random();

	public BlockSmasher() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param toolInstance The instance of the tool used to break stuff.
	 * @param gameWorld_ world object to search for more blocks to break
	 * @param blockStruck The block that's been destroyed
	 * @param pos The world position of the block that's been destroyed
	 * @param actor The entity that destroyed blockStruck with this tool
	 */
    public static boolean onBlockDestroyed(ItemStack toolInstance, World gameWorld_, Block blockStruck, BlockPos pos, EntityLivingBase actor)
    {
    	if(!gameWorld_.isRemote)
    	{
	        Deque<BlockWithLocation> blockDeck = new LinkedList<BlockWithLocation>();
	        getNeighbouringBlocksToDeque(gameWorld_, blockStruck, pos.getX(), pos.getY(),
					pos.getZ(), blockDeck);
	        hitManyBlocks(toolInstance, gameWorld_, pos.getX(), pos.getY(),
					pos.getZ(),
					actor, blockDeck);
    	}
        return true;
    }

	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IToolBlockSmasher#hitManyBlocks(net.minecraft.item.ItemStack, net.minecraft.world.World, int, int, int, net.minecraft.entity.EntityLivingBase, java.util.Deque)
	 */

	/**
	 * @param toolInstance
	 * @param gameWorld_ world object to search for blocks
	 * @param worldX x coordinate of blockStruck
	 * @param worldY y coordinate of blockStruck
	 * @param worldZ z coordinate of blockStruck
	 * @param actor holding tool
	 * @param blockDeck collection of blocks to hammer against
	 */
	public static void hitManyBlocks(ItemStack toolInstance, World gameWorld_,
			int worldX, int worldY, int worldZ, EntityLivingBase actor,
			Deque<BlockWithLocation> blockDeck) {
	    
        for(Iterator<BlockWithLocation> iter = blockDeck.iterator(); iter.hasNext();)
        {
        	BlockWithLocation neighbourBlockContainer = iter.next();

            if (! (neighbourBlockContainer.getX() == worldX && neighbourBlockContainer.getY() == worldY && neighbourBlockContainer.getZ() == worldZ)) // is not source block
            {
            	BlockPos pos = new BlockPos(worldX, worldY, worldZ);
            	double hardness = neighbourBlockContainer.getBlock().getBlockHardness(null, gameWorld_, pos);
                if (hardness != 0.0D)
                {
                	int damage = 1;
					ForgeHooks.isToolEffective(gameWorld_, pos , toolInstance);
                    if ( ! ForgeHooks.isToolEffective(gameWorld_, pos , toolInstance) )
                    {
                        damage = 2;
                    }
                    toolInstance.damageItem(damage, actor);
                }
                // dumb thing to replace a proper "break block as if broken by player" method until otherwise found
            	breakBlock(gameWorld_, neighbourBlockContainer, actor);
            	if (actor instanceof EntityPlayer)
            		((EntityPlayer) actor).getFoodStats().addExhaustion((float) (ConfigurationHandler.smasherExhaustionPerBonusBlock * 1.5));
            }
        }
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IToolBlockSmasher#breakBlock(net.minecraft.world.World, org.syndaryl.utilityblocks.block.BlockWithLocation, net.minecraft.entity.EntityLivingBase)
	 */

	/**
	 * dumb thing to replace a proper "break block" method until otherwise found
	 * Tries to generate a proper item drop, spawn the appropriate item, and then get the block to commit suicide. 
	 * 
	 * @param gameWorld_
	 * @param blockXYZ
	 */
	public static void breakBlock(World gameWorld_,
			BlockWithLocation blockXYZ, EntityLivingBase player) {

        int fortune = EnchantmentHelper.getLootingModifier(player);
        
        boolean dropItems = true;
        BlockPos pos = new BlockPos(blockXYZ.getX(), blockXYZ.getY(), blockXYZ.getZ());
        //int metadata =  blockXYZ.metadata;// gameWorld_.getBlockMetadata(pos);
        int xpDrop = blockXYZ.getBlock().getExpDrop(null, gameWorld_, pos, fortune);
        gameWorld_.destroyBlock(pos, dropItems);
		blockXYZ.getBlock().dropXpOnBlockBreak(gameWorld_, pos, r.nextBoolean()? xpDrop:0);
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IToolBlockSmasher#getNeighbouringBlocksToDeque(net.minecraft.world.World, net.minecraft.block.Block, int, int, int, java.util.Deque)
	 */

	/**
	 * Collects the neighbouring blocks which are of the same block type as the struck block into a deQue object.
	 * @param gameWorld_ world object to search for neighbouring blocks
	 * @param blockStruck reference block, should be at the centre of the block effect
	 * @param worldX x coordinate of blockStruck
	 * @param worldY y coordinate of blockStruck
	 * @param worldZ z coordinate of blockStruck
	 * @param blockDeck deque object to add the found neighbours to
	 */

	public static void getNeighbouringBlocksToDeque(World gameWorld_,
			Block blockStruck, int worldX, int worldY, int worldZ,
			Deque<BlockWithLocation> blockDeck) {
		UtilityBlocks.LOG.info(String.format("getNeighbouringBlocksToDeque() is firing on %i %i %i", worldX, worldY, worldZ));
        IBlockState coreState = gameWorld_.getBlockState( new BlockPos(worldX, worldY, worldZ));
        int coreData = blockStruck.getMetaFromState(coreState);
		String blockName = blockStruck.getLocalizedName();
		for(int x = worldX-1; x <= worldX+1; x++)
        {
        	for(int y = worldY-1; y<= worldY+1; y++)
        	{
        		for(int z = worldZ-1; z<=worldZ+1; z++)
        		{
        			BlockPos pos = new BlockPos(x,y,z);
        			if ( gameWorld_.getBlockState(pos).getBlock().getUnlocalizedName() != Blocks.AIR.getUnlocalizedName() )	
        			{
        				Block neighbour = gameWorld_.getBlockState(pos).getBlock();
        		        IBlockState neighbourState = gameWorld_.getBlockState(pos);
        		        int neighbourMeta = neighbour.getMetaFromState(neighbourState);
						if (neighbour.getLocalizedName().compareTo(blockName) == 0 && neighbourMeta == coreData)  //  && neighbour != blockStruck
            			{
            				BlockWithLocation container = new BlockWithLocation(neighbour, x, y, z, neighbourMeta);
            				blockDeck.add(container);
            			}
        			}
        		}
        	}
        }
	}
}
