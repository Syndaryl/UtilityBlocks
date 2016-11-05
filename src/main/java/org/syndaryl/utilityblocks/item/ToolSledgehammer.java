package org.syndaryl.utilityblocks.item;

import java.util.Random;

import org.syndaryl.utilityblocks.NamespaceManager;
import org.syndaryl.utilityblocks.handler.ConfigurationHandler;
import org.syndaryl.utilityblocks.item.util.BlockSmasher;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToolSledgehammer extends ItemPickaxe implements IItemName, IToolBlockSmasher {
    static Random r = new Random();
    private String name = "sledgehammer"; 
    
	public ToolSledgehammer(ToolMaterial material) {
		super(material);
		String materialName = material.toString().toLowerCase();// NamespaceManager.capitalizeWord(material.toString()) ;
		name = materialName +"_sledgehammer";
		if (! name.startsWith(NamespaceManager.GetModNameLC() + "_"))
		{
			name = NamespaceManager.GetModNameLC() + "_" + name;
		}
		setUnlocalizedName( getName() );
		this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxDamage((int) (material.getMaxUses() * ConfigurationHandler.smasherDurabilityMultiplier));

        this.efficiencyOnProperMaterial = (float) (material.getEfficiencyOnProperMaterial() * ConfigurationHandler.smasherEfficiencyMultiplier);
        

		ItemManager.registerItem(this, getName());
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IToolBlockSmasher#onBlockDestroyed(net.minecraft.item.ItemStack, net.minecraft.world.World, net.minecraft.block.Block, net.minecraft.util.BlockPos, net.minecraft.entity.EntityLivingBase)
	 */
	@Override
    public boolean onBlockDestroyed(ItemStack toolInstance, World gameWorld_, IBlockState blockStruck, BlockPos pos, EntityLivingBase actor)
    {
		//super.onBlockDestroyed(toolInstance, gameWorld_, blockStruck, pos, actor);
		if (ConfigurationHandler.smasherRange > 0)
		{
			return BlockSmasher.onBlockDestroyed(toolInstance, gameWorld_, blockStruck, pos, actor);
		}
		else
		{
			return false;
		}
    }

	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IItemName#getName()
	 */
    @Override
	public String getName()
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IItemName#getName(net.minecraft.item.ItemStack)
	 */
	@Override
	public String getName(ItemStack stack){
	    return this.getName();
	}

	/* (non-Javadoc)
	 * @see org.syndaryl.utilityblocks.item.IItemName#getName(int)
	 */
	@Override
	public String getName(int meta){
	    return this.getName();
	}

}
