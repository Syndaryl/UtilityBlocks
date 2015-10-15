/**
 * 
 */
package org.syndaryl.animalsdropbones.item;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.syndaryl.animalsdropbones.NamespaceManager;
import org.syndaryl.animalsdropbones.block.BlockWithLocation;
import org.syndaryl.animalsdropbones.handler.ConfigurationHandler;
/**
 * @author syndaryl
 *
 */
public class ToolMattock extends ItemSpade implements IItemName, IToolBlockSmasher {
    static Random r = new Random();
    private String name = "mattock"; 

	/**
	 * @param material
	 */
	public ToolMattock(ToolMaterial material) {
		super(material);
		String materialName = material.toString().toLowerCase() ;
		name = NamespaceManager.GetModNameLC() + "_" + materialName +"_mattock";
		setUnlocalizedName(getName());
		this.setCreativeTab(CreativeTabs.tabTools);
        this.setMaxDamage((int) (material.getMaxUses()*ConfigurationHandler.smasherDurabilityMultiplier));

        this.efficiencyOnProperMaterial = (float) (material.getEfficiencyOnProperMaterial() * ConfigurationHandler.smasherEfficiencyMultiplier);
        

		GameRegistry.registerItem(this, getName());
	}
	//@SideOnly(Side.CLIENT)
	//	public void registerIcons(IIconRegister register)
	//	{
	//		this.itemIcon = register.registerIcon(NamespaceManager.GetModName() +":" + this.toolMaterial.toString().toLowerCase() +"_mattock");
	//	}

	//@SideOnly(Side.SERVER)
	@Override
    public boolean onBlockDestroyed(ItemStack toolInstance, World gameWorld_, Block blockStruck, BlockPos pos, EntityLivingBase actor)
    {
	    //AnimalsDropBones.LOG.debug("SYNDARYL: onBlockDestroyed: "  + toolInstance.getDisplayName() );
    	if(!gameWorld_.isRemote)
    	{
    	    //AnimalsDropBones.LOG.debug("SYNDARYL: onBlockDestroyed: Not is remote" );
			//System.out.println(String.format("onBlockDestroyed() nuking block at %d %d %d", worldX, worldY, worldZ));
	        Deque<BlockWithLocation> blockDeck = new LinkedList<BlockWithLocation>();
	        getNeighbouringBlocksToDeque(gameWorld_, blockStruck, pos.getX(), pos.getY(),
					pos.getZ(), blockDeck);
			//System.out.println(String.format("getNeighbouringBlocksToDeque(): returned deck of %d", blockDeck.size()));
	        hitManyBlocks(toolInstance, gameWorld_, pos.getX(), pos.getY(),
					pos.getZ(),
					actor, blockDeck);
    	}
        return true;
    }

	/**
	 * @param toolInstance
	 * @param gameWorld_ world object to search for blocks
	 * @param worldX x coordinate of blockStruck
	 * @param worldY y coordinate of blockStruck
	 * @param worldZ z coordinate of blockStruck
	 * @param actor holding tool
	 * @param blockDeck collection of blocks to hammer against
	 */

	@Override
    public void hitManyBlocks(ItemStack toolInstance, World gameWorld_,
			int worldX, int worldY, int worldZ, EntityLivingBase actor,
			Deque<BlockWithLocation> blockDeck) {
		//System.out.println(String.format("hitManyBlocks(): Working with deck of %d", blockDeck.size()));
		// Damage tool for each neighbour as well as for the originally struck block.
		// then nuke the neighbours and drop items
	    //AnimalsDropBones.LOG.debug("SYNDARYL: hitManyBlocks deck size: " + blockDeck.size() );
	    
	    
        for(Iterator<BlockWithLocation> iter = blockDeck.iterator(); iter.hasNext();)
        {
        	BlockWithLocation neighbourBlockContainer = iter.next();
    		//System.out.println(String.format("checking block at %d %d %d", neighbourBlockContainer.x, neighbourBlockContainer.y, neighbourBlockContainer.z));

    	    //AnimalsDropBones.LOG.debug("SYNDARYL: X; " + neighbourBlockContainer.x + ":" + worldX );
    	    //AnimalsDropBones.LOG.debug("SYNDARYL: Y; " + neighbourBlockContainer.y + ":" + worldY );
    	    //AnimalsDropBones.LOG.debug("SYNDARYL: Z; " + neighbourBlockContainer.z + ":" + worldZ );
            if (! (neighbourBlockContainer.x == worldX && neighbourBlockContainer.y == worldY && neighbourBlockContainer.z == worldZ)) // is not source block
            {
            	BlockPos pos = new BlockPos(worldX, worldY, worldZ);
            	double hardness = neighbourBlockContainer.b.getBlockHardness(gameWorld_, pos);
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
            		((EntityPlayer) actor).getFoodStats().addExhaustion(0.5F);
            }
        }
	}

	/**
	 * dumb thing to replace a proper "break block" method until otherwise found
	 * Tries to generate a proper item drop, spawn the appropriate item, and then get the block to commit suicide. 
	 * 
	 * @param gameWorld_
	 * @param blockXYZ
	 */

	@Override
    public void breakBlock(World gameWorld_,
			BlockWithLocation blockXYZ, EntityLivingBase player) {
        int fortune = EnchantmentHelper.getFortuneModifier(player);
        boolean dropItems = true;
        BlockPos pos = new BlockPos(blockXYZ.x, blockXYZ.y, blockXYZ.z);
        //int metadata =  blockXYZ.metadata;// gameWorld_.getBlockMetadata(pos);
        int xpDrop = blockXYZ.b.getExpDrop(gameWorld_, pos, fortune);
        gameWorld_.destroyBlock(pos, dropItems);
		blockXYZ.b.dropXpOnBlockBreak(gameWorld_, pos, r.nextBoolean()? xpDrop:0);
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

	@Override
    public void getNeighbouringBlocksToDeque(World gameWorld_,
			Block blockStruck, int worldX, int worldY, int worldZ,
			Deque<BlockWithLocation> blockDeck) {
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
        			if ( gameWorld_.getBlockState(pos).getBlock().getUnlocalizedName() != Blocks.air.getUnlocalizedName() )	
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

	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IItemName#getName()
	 */
    @Override
	public String getName()
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IItemName#getName(net.minecraft.item.ItemStack)
	 */
	@Override
	public String getName(ItemStack stack){
	    return this.getName();
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IItemName#getName(int)
	 */
	@Override
	public String getName(int meta){
	    return this.getName();
	}
	
}
