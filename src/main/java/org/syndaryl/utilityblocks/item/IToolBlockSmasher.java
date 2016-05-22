package org.syndaryl.utilityblocks.item;

import java.util.Deque;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.syndaryl.utilityblocks.block.util.BlockWithLocation;

public interface IToolBlockSmasher {

	public abstract boolean onBlockDestroyed(ItemStack toolInstance, World gameWorld_, IBlockState blockStruck, BlockPos pos, EntityLivingBase actor);

}