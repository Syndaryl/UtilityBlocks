package org.syndaryl.animalsdropbones.item;

import net.minecraft.item.ItemStack;

public interface IItemName {

	public abstract String getName();

	public abstract String getName(ItemStack stack);

	public abstract String getName(int meta);

}