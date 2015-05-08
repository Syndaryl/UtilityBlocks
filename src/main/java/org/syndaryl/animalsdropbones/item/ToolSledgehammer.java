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
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.syndaryl.animalsdropbones.NamespaceManager;
import org.syndaryl.animalsdropbones.block.BlockWithLocation;

public class ToolSledgehammer extends ItemPickaxe implements IItemName, IToolBlockSmasher {
    static Random r = new Random();
    private String name = "sledgehammer"; 
    
	public ToolSledgehammer(ToolMaterial material) {
		super(material);
		String materialName = material.toString().toLowerCase();// NamespaceManager.capitalizeWord(material.toString()) ;
		name = NamespaceManager.GetModNameLC() + "_" + materialName +"_sledgehammer";
		setUnlocalizedName( getName() );
		this.setCreativeTab(CreativeTabs.tabTools);
        this.setMaxDamage(material.getMaxUses()*6);

        this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial()/4;
        

		GameRegistry.registerItem(this, getName());
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IToolBlockSmasher#onBlockDestroyed(net.minecraft.item.ItemStack, net.minecraft.world.World, net.minecraft.block.Block, net.minecraft.util.BlockPos, net.minecraft.entity.EntityLivingBase)
	 */
	@Override
    public boolean onBlockDestroyed(ItemStack toolInstance, World gameWorld_, Block blockStruck, BlockPos pos, EntityLivingBase actor)
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
	 * @see org.syndaryl.animalsdropbones.item.IToolBlockSmasher#hitManyBlocks(net.minecraft.item.ItemStack, net.minecraft.world.World, int, int, int, net.minecraft.entity.EntityLivingBase, java.util.Deque)
	 */
	
    @Override
	public void hitManyBlocks(ItemStack toolInstance, World gameWorld_,
			int worldX, int worldY, int worldZ, EntityLivingBase actor,
			Deque<BlockWithLocation> blockDeck) {
	    
        for(Iterator<BlockWithLocation> iter = blockDeck.iterator(); iter.hasNext();)
        {
        	BlockWithLocation neighbourBlockContainer = iter.next();

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
            		((EntityPlayer) actor).getFoodStats().addExhaustion(0.75F);
            }
        }
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IToolBlockSmasher#breakBlock(net.minecraft.world.World, org.syndaryl.animalsdropbones.block.BlockWithLocation, net.minecraft.entity.EntityLivingBase)
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

	/* (non-Javadoc)
	 * @see org.syndaryl.animalsdropbones.item.IToolBlockSmasher#getNeighbouringBlocksToDeque(net.minecraft.world.World, net.minecraft.block.Block, int, int, int, java.util.Deque)
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
