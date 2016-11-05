package org.syndaryl.utilityblocks.item.util;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
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
    public static boolean onBlockDestroyed(ItemStack toolInstance, World gameWorld_, IBlockState blockStruck, BlockPos pos, EntityLivingBase actor)
    {
    	if(!gameWorld_.isRemote)
    	{
	        Deque<BlockWithLocation> blockDeck = getNeighbouringBlocksToDeque(gameWorld_, blockStruck, pos);
	        hitManyBlocks(toolInstance, gameWorld_, pos, actor, blockDeck);
    	}
        return true;
    }

	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IToolBlockSmasher#hitManyBlocks(net.minecraft.item.ItemStack, net.minecraft.world.World, int, int, int, net.minecraft.entity.EntityLivingBase, java.util.Deque)
	 */

	private static void hitManyBlocks(ItemStack toolInstance, World gameWorld_,
			BlockPos pos, EntityLivingBase actor,
			Deque<BlockWithLocation> blockDeck) {
		// hitManyBlocks(toolInstance, gameWorld_, pos.getX(), pos.getY(), pos.getZ(), actor,  blockDeck);
		UtilityBlocks.LOG.info(String.format("hitManyBlocks() is starting from origin %s and hitting %d blocks", pos.toString(), blockDeck.size()));
        
        for(Iterator<BlockWithLocation> iter = blockDeck.iterator(); iter.hasNext();)
        {
        	BlockWithLocation neighbourBlockContainer = iter.next();

            if (neighbourBlockContainer.getPos().compareTo(pos) != 0 ) // is not source block
            {
            	double hardness = neighbourBlockContainer.getBlock().getBlockState().getBaseState().getBlockHardness(gameWorld_, neighbourBlockContainer.getPos());
            	int damage = 0;
                if (hardness != 0.0D)
                {
                	damage = 1;
					//ForgeHooks.isToolEffective(gameWorld_, pos , toolInstance);
                    if ( ! ForgeHooks.isToolEffective(gameWorld_, pos , toolInstance) )
                    {
                        damage = 2;
                    }
                }

                if (actor instanceof EntityPlayer)
                {
                	UtilityBlocks.LOG.info(String.format("hitManyBlocks() is breaking a child %s block at %s", neighbourBlockContainer.getBlock().getUnlocalizedName(), neighbourBlockContainer.getPos().toString()));
                    breakBlock(gameWorld_, neighbourBlockContainer, actor);
                    toolInstance.damageItem(damage, actor);
            		((EntityPlayer) actor).getFoodStats().addExhaustion((float) (ConfigurationHandler.smasherExhaustionPerBonusBlock * 1.5));
                }
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
	 * @param theWorld
	 * @param blockXYZ
	 */
	public static void breakBlock(World theWorld,
			BlockWithLocation blockXYZ, EntityLivingBase player) {
		BlockPos pos = blockXYZ.getPos();
		UtilityBlocks.LOG.info(String.format("breakBlock() is firing on a %s block at %s", blockXYZ.getBlock().getUnlocalizedName(), pos.toString()));
//        int fortune = EnchantmentHelper.getLootingModifier(player);
        
//        boolean dropItems = true;
        IBlockState iblockstate = theWorld.getBlockState(pos);
        //int metadata =  blockXYZ.metadata;// gameWorld_.getBlockMetadata(pos);

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
//        int xpDrop = blockXYZ.getBlock().getExpDrop(iblockstate, theWorld, pos, fortune);
        int exp = net.minecraftforge.common.ForgeHooks.onBlockBreakEvent(theWorld, entityPlayerMP.interactionManager.getGameType(), entityPlayerMP, pos);
        if (exp == -1)
        {
           return;
        }
        boolean removedBlock;
		if (entityPlayerMP.isCreative())
        {
			removedBlock = BlockSmasher.removeBlock(pos, theWorld, entityPlayerMP, false);
            entityPlayerMP.connection.sendPacket(new SPacketBlockChange(theWorld, pos));
        }
		else
		{
			removedBlock = BlockSmasher.removeBlock(pos, theWorld, entityPlayerMP, true);
			if (removedBlock)
				blockXYZ.getBlock().harvestBlock(theWorld, entityPlayerMP, pos, iblockstate, theWorld.getTileEntity(pos), entityPlayerMP.getHeldItemMainhand());
			//theWorld.destroyBlock(blockXYZ.getPos(), dropItems);
			//blockXYZ.getBlock().dropXpOnBlockBreak(theWorld, blockXYZ.getPos(), r.nextBoolean()? xpDrop:0);
		}
		if (!entityPlayerMP.isCreative() && removedBlock && exp > 0)
            {
            	blockXYZ.getBlock().dropXpOnBlockBreak(theWorld, pos, exp);
            }
//        boolean success = ((EntityPlayerMP)player).interactionManager.tryHarvestBlock(pos);
		//        if (success)
		//        {
		//            //blockXYZ.getBlock().harvestBlock(theWorld, (EntityPlayer) player, pos, iblockstate, null, null);
		//            blockXYZ.getBlock().onBlockDestroyedByPlayer(theWorld, pos, iblockstate);
		//        }
	}

	/**
	 * Removes a block and triggers the appropriate events
	 */

	private static boolean removeBlock(BlockPos pos, World theWorld, EntityPlayerMP thisPlayerMP, boolean canHarvest)
	{
	        IBlockState iblockstate = theWorld.getBlockState(pos);
	        boolean flag = iblockstate.getBlock().removedByPlayer(iblockstate, theWorld, pos, thisPlayerMP, canHarvest);

	        if (flag)
	        {
	            iblockstate.getBlock().onBlockDestroyedByPlayer(theWorld, pos, iblockstate);
	        }

	        return flag;
	}

	/**
	 * Collects the neighbouring blocks which are of the same block type as the struck block into a deQue object.
	 * @param gameWorld_ world object to search for neighbouring blocks
	 * @param blockStruck reference block, should be at the centre of the block effect
	 * @param worldX x coordinate of blockStruck
	 * @param worldY y coordinate of blockStruck
	 * @param worldZ z coordinate of blockStruck
	 * @param blockDeck deque object to add the found neighbours to
	 */
	public static LinkedList<BlockWithLocation> getNeighbouringBlocksToDeque(World gameWorld_,
			IBlockState blockStruck, BlockPos posOrigin)
			{
				LinkedList<BlockWithLocation> blockDeck = new LinkedList<BlockWithLocation>();
				IBlockState coreState = gameWorld_.getBlockState( posOrigin);
		        int coreData = blockStruck.getBlock().getMetaFromState(coreState);
				String blockName = blockStruck.getBlock().getRegistryName().toString();
				UtilityBlocks.LOG.info(String.format("getNeighbouringBlocksToDeque() is firing on %s, a %s:%d block", posOrigin.toString(), blockName, coreData));
				
				int blockOffset = (ConfigurationHandler.smasherRange -1)/2;
				
				BlockPos bottom = posOrigin.subtract(new Vec3i(-1*blockOffset,-1*blockOffset,-1*blockOffset));
				BlockPos top = posOrigin.subtract(new Vec3i(blockOffset,blockOffset,blockOffset));
				UtilityBlocks.LOG.info(String.format("   searching from %s to %s", bottom.toString(), top.toString()));
				
				
				Iterable<BlockPos> candidates = BlockPos.getAllInBox(bottom, top);
				
				for (BlockPos posNeighbour : candidates )
				{
        			//BlockPos posNeighbour = new BlockPos(x,y,z);
					
					String neighborName = gameWorld_.getBlockState(posNeighbour).getBlock().getRegistryName().toString();
					if ( neighborName != Blocks.AIR.getRegistryName().toString() )	
        			{
        				Block neighbour = gameWorld_.getBlockState(posNeighbour).getBlock();
        		        IBlockState neighbourState = gameWorld_.getBlockState(posNeighbour);
        		        int neighbourMeta = neighbour.getMetaFromState(neighbourState);
        		        UtilityBlocks.LOG.info(String.format("getNeighbouringBlocksToDeque() examining neighbor %s:%d at %s", neighborName,  neighbourMeta, posNeighbour.toString()));
            			if (neighborName.compareTo(blockName) == 0)
						{
		        			if( neighbourMeta == coreData)  //  && neighbour != blockStruck
	            			{
								UtilityBlocks.LOG.info(String.format("getNeighbouringBlocksToDeque() decided this is breakable"));
	            				BlockWithLocation container = new BlockWithLocation(neighbour, posNeighbour, neighbourMeta);
	            				blockDeck.add(container);
	            			}
						}
        			}
		        }
				UtilityBlocks.LOG.info(String.format("getNeighbouringBlocksToDeque() found %d breakable blocks", blockDeck.size()));
				return blockDeck;
//			return getNeighbouringBlocksToDeque(gameWorld_,
//					blockStruck, posOrigin.getX(), posOrigin.getY(), posOrigin.getZ());
			}
	public static LinkedList<BlockWithLocation> getNeighbouringBlocksToDeque(World gameWorld_,
			IBlockState blockStruck, int worldX, int worldY, int worldZ) {
        return getNeighbouringBlocksToDeque(gameWorld_, blockStruck, new BlockPos(worldX,worldY,worldZ));
	}
}
