package org.syndaryl.animalsdropbones.handler;

import java.util.Collection;
import java.util.List;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.syndaryl.animalsdropbones.AnimalsDropBones;
import org.syndaryl.animalsdropbones.block.BlockManager;
import org.syndaryl.animalsdropbones.materials.ADBMaterial;

public class MaterialHandler {
	private static HashMap<String,ADBMaterial> allMaterials = new HashMap<>();
	private static HashMap<ADBMaterial,ArmorMaterial> armorMaterialMap= new HashMap<>();
	private static HashMap<ADBMaterial,ToolMaterial> toolMaterialMap= new HashMap<>();
	
	ToolMaterial obsidianTool;

	public MaterialHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public static void addAllMaterials(){
		ADBMaterial obsidianTool = MaterialHandler.addMaterial("obsidian", 12, 6, 8, 1); 
	}

	public static ADBMaterial addMaterial(String name, double hardness, double strength, double magic, double rarity){
		ADBMaterial m = new ADBMaterial(name,(float)hardness,(float)strength,(float)magic,(int)(Math.max(50*rarity,1)));
		registerMaterial(name, m);
		return m;
	}
	
	protected static void registerMaterial(String name, ADBMaterial m){

		allMaterials.put(name, m);
		
		String enumName = m.getEnumName();
		String texName = m.getName();
		int[] protection = m.getDamageReductionArray();
		int durability = m.getArmorMaxDamageFactor();
		ArmorMaterial am = EnumHelper.addArmorMaterial(enumName, texName, durability, protection, m.getEnchantability());
		if(am == null){
			// uh-oh
			AnimalsDropBones.LOG.error("Failed to create armor material enum for "+m);
		}
		armorMaterialMap.put(m, am);
		AnimalsDropBones.LOG.info("Created armor material enum "+am);
		
		ToolMaterial tm = EnumHelper.addToolMaterial(enumName, m.getToolHarvestLevel(), m.getToolDurability(), m.getToolEfficiency(), m.getBaseAttackDamage(), m.getEnchantability());
		if(tm == null){
			// uh-oh
			AnimalsDropBones.LOG.error("Failed to create tool material enum for "+m);
		}
		toolMaterialMap.put(m, tm);
		AnimalsDropBones.LOG.info("Created tool material enum "+tm);
	}
	/**
	 * Gets the armor material for a given metal 
	 * @param m The metal of interest
	 * @return The armor material for this metal, or null if there isn't one
	 */
	public static ArmorMaterial getArmorMaterialFor(ADBMaterial m){
		return armorMaterialMap.get(m);
	}


	/**
	 * Gets the tool material for a given metal 
	 * @param m The metal of interest
	 * @return The tool material for this metal, or null if there isn't one
	 */
	public static ToolMaterial getToolMaterialFor(ADBMaterial m){
		return toolMaterialMap.get(m);
	}

	/**
	 * Returns a list of all metal materials in Base Metals. All of the metals 
	 * in this list are also available as static public members of this class. 
	 * @return A Collection of MetalMaterial instances.
	 */
	public static Collection<ADBMaterial> getAllMetals() {
		return allMaterials.values();
	}
	/**
	 * Gets a metal material by its name (e.g. "copper").
	 * @param metalName The name of a metal
	 * @return The material representing the named metal, or null if no metals 
	 * have been registered under that name.
	 */
	public static ADBMaterial getMetalByName(String metalName){
		return allMaterials.get(metalName);
	}

}
