package org.syndaryl.utilityblocks.handler;

import java.util.Collection;
import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

import org.syndaryl.utilityblocks.UtilityBlocks;
import org.syndaryl.utilityblocks.materials.UBMaterial;

public class MaterialHandler {
	private static HashMap<String,UBMaterial> allMaterials = new HashMap<>();
	private static HashMap<UBMaterial,ArmorMaterial> armorMaterialMap= new HashMap<>();
	private static HashMap<UBMaterial,ToolMaterial> toolMaterialMap= new HashMap<>();
	private static UBMaterial obsidianToolUBMaterial;
	
	ToolMaterial obsidianTool;

	public MaterialHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public static void addAllMaterials(){
		obsidianToolUBMaterial = MaterialHandler.addMaterial("obsidian", 13, 6, 8, 5, new ItemStack(Blocks.OBSIDIAN));
	}

	public static UBMaterial addMaterial(String name, double hardness, double strength, double magic, double rarity, ItemStack repairMaterial){
		UBMaterial m = new UBMaterial(name,(float)hardness,(float)strength,(float)magic,(int)(Math.max(50*rarity,1)), repairMaterial);
		registerMaterial(name, m);
		return m;
	}
	
	protected static void registerMaterial(String name, UBMaterial m){

		allMaterials.put(name, m);
		
		String enumName = m.getEnumName();
		String texName = m.getName();
		int[] protection = m.getDamageReductionArray();
		int durability = m.getArmorMaxDamageFactor();
		ArmorMaterial am = EnumHelper.addArmorMaterial(enumName, texName, durability, protection, m.getEnchantability(), SoundEvents.ITEM_ARMOR_EQUIP_IRON, durability);
		if(am == null){
			// uh-oh
			UtilityBlocks.LOG.error("Failed to create armor material enum for "+m);
		}
		armorMaterialMap.put(m, am);
		UtilityBlocks.LOG.info("Created armor material enum "+am);
		
		ToolMaterial tm = EnumHelper.addToolMaterial(enumName, m.getToolHarvestLevel(), m.getToolDurability(), m.getToolEfficiency(), m.getBaseAttackDamage(), m.getEnchantability());
		if(tm == null){
			// uh-oh
			UtilityBlocks.LOG.error("Failed to create tool material enum for "+m);
		}
		tm.setRepairItem(m.getRepairMaterial());
		toolMaterialMap.put(m, tm);
		UtilityBlocks.LOG.info("Created tool material enum "+tm);
	}
	/**
	 * Gets the armour material for a given metal 
	 * @param m The metal of interest
	 * @return The armour material for this metal, or null if there isn't one
	 */
	public static ArmorMaterial getArmorMaterialFor(UBMaterial m){
		return armorMaterialMap.get(m);
	}


	/**
	 * Gets the tool material for a given metal 
	 * @param m The metal of interest
	 * @return The tool material for this metal, or null if there isn't one
	 */
	public static ToolMaterial getToolMaterialFor(UBMaterial m){
		return toolMaterialMap.get(m);
	}

	/**
	 * Returns a list of all metal materials in Base Metals. All of the metals 
	 * in this list are also available as static public members of this class. 
	 * @return A Collection of MetalMaterial instances.
	 */
	public static Collection<UBMaterial> getAllMetals() {
		return allMaterials.values();
	}
	/**
	 * Gets a metal material by its name (e.g. "copper").
	 * @param metalName The name of a metal
	 * @return The material representing the named metal, or null if no metals 
	 * have been registered under that name.
	 */
	public static UBMaterial getMetalByName(String metalName){
		return allMaterials.get(metalName);
	}

}
