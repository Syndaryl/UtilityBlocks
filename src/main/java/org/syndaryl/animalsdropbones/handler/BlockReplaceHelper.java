/**
 * 
 */
package org.syndaryl.animalsdropbones.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.syndaryl.animalsdropbones.AnimalsDropBones;

import com.google.common.collect.BiMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author syndaryl
 * based on post by chylex http://www.minecraftforge.net/forum/index.php?topic=16692.0
 * Usage:
 * BlockReplaceHelper.replaceBlock(Blocks.dragon_egg,BlockDragonEggCustom.class);
 */
public class BlockReplaceHelper{
	@SuppressWarnings({ "unchecked" })
	public static boolean replaceBlock(Block toReplace, Class<? extends Block> blockClass){
		Field modifiersField=null;
    	try{
    		modifiersField=Field.class.getDeclaredField("modifiers");
    		modifiersField.setAccessible(true);
    		
    		for(Field field:Blocks.class.getDeclaredFields()){
        		if (Block.class.isAssignableFrom(field.getType())){
    				Block block=(Block)field.get(null);
    				if (block==toReplace){
    					String registryName=(String) Block.blockRegistry.getNameForObject(block);
    					int id=Block.getIdFromBlock(block);
    					ItemBlock item=(ItemBlock)Item.getItemFromBlock(block);
    					AnimalsDropBones.LOG.warn("Replacing block - "+id+"/"+registryName);
    					
    					Block newBlock=blockClass.newInstance();
    					FMLControlledNamespacedRegistry<Block> registry=GameData.getBlockRegistry();
    					//registry.putObject(registryName,newBlock);
    					GameRegistry.registerBlock(newBlock, registryName);
    					
    					Field map=RegistryNamespaced.class.getDeclaredFields()[0];
    					map.setAccessible(true);
    					ObjectIntIdentityMap objIDMap = (ObjectIntIdentityMap)map.get(registry);
    					objIDMap.put(newBlock,id);
    					
    					map=FMLControlledNamespacedRegistry.class.getDeclaredField("namedIds");
    					map.setAccessible(true);
    					((BiMap)map.get(registry)).put(registryName,id);
    					
    					field.setAccessible(true);
    					int modifiers=modifiersField.getInt(field);
    					modifiers&=~Modifier.FINAL;
    					modifiersField.setInt(field,modifiers);
    					field.set(null,newBlock);
    					
    					Field itemblock=ItemBlock.class.getDeclaredFields()[0];
    					itemblock.setAccessible(true);
    					modifiers=modifiersField.getInt(itemblock);
    					modifiers&=~Modifier.FINAL;
    					modifiersField.setInt(itemblock,modifiers);
    					itemblock.set(item,newBlock);
    					
    					AnimalsDropBones.LOG.warn("Check field: "+field.get(null).getClass());
    					AnimalsDropBones.LOG.warn("Check registry: "+Block.blockRegistry.getObjectById(id).getClass());
    					AnimalsDropBones.LOG.warn("Check item: "+((ItemBlock)Item.getItemFromBlock(newBlock)).block.getClass());
    				}
        		}
        	}
    	}catch(Exception e){
    		e.printStackTrace();
    		AnimalsDropBones.LOG.warn(e.getStackTrace());
    		return false;
    	}
    	return true;
	}
}
