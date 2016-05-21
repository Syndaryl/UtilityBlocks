package org.syndaryl.utilityblocks.materials;

import java.util.Locale;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import org.syndaryl.utilityblocks.NamespaceManager;

public class UBMaterial {
	/**
	 * String used to identify items and blocks using this material
	 */
	final String name;
	
	final String titleName;
	
	final String enumName;
	
	/** hardness on a scale from 0 to 10 (or more), where 0 is non-solid and 
	 * diamond is 10. For reference, wood is 3, stone is 5, iron is 8, diamond is 10.
	 * */
	private final float hardness;
	/** durability on a scale from 0 to 10 (or more).
	 * For reference, leather is 2.5, gold is 3, wood is 2, stone is 4, iron is 8, minecraft diamond is 10.
	 *  */
	private final float strength;
	/**
	 * Scale from 0 to 10 (or more) on how magical the material is.
	 * For reference, stone is 2, iron is 4.5, diamond is 4, wood is 6, gold is 10.
	 */
	private final float magic;
	/**
	 * This is how common or rare items made from this material are when 
	 * generating treasure chests. For reference, a loaf of bread is 100 and a 
	 * golden apple is 1 
	 */
	private final int rarity;

	private ItemStack repairMaterial;

	public UBMaterial(String name, float hardness, float strength,
			float magic, int rarity, ItemStack repairMaterial) {
		this.name = name;
		
		titleName = NamespaceManager.capitalizeWord(name);
		enumName = (NamespaceManager.GetModNameLC() + "_"+name).toUpperCase(Locale.ENGLISH);
		
		this.hardness = hardness;
		this.strength = strength;
		this.magic = magic;
		this.rarity = rarity;
		this.repairMaterial = repairMaterial;
	}

	public String getEnumName() {
		return enumName;
	}

	public String getName() {
		return titleName;
	}
	@Override public String toString(){
		return getName();
	}
	@Override
	public int hashCode(){
		return name.hashCode();
	}
	@Override
	public boolean equals(Object o){
		if(o == this) return true;
		if(o.hashCode() == this.hashCode() && o instanceof UBMaterial){
			UBMaterial other = (UBMaterial)o;
			return this.name.equals(other.name); 
		}
		return false;
	}

	private int[] cache =  null;
	/**
	 * Gets the protection value for helmets, chestplates, leg armor, and boots 
	 * made from this material
	 * @return the protection value for helmets, chestplates, leg armor, and boots 
	 * made from this material
	 */
	public int[] getDamageReductionArray(){
		if(cache == null){
			float base = 0.75f * hardness;
			cache = new int[4];
			cache[0] = (int)(base / 2.5f);	// head
			cache[1] = (int)(base);			// torso
			cache[2] = (int)(0.75f * base);	// legs
			cache[3] = (int)(base / 2.6f);	// feet
		}
		return cache;
	}
	/**
	 * Gets the number used to determine how much damage an armor item can 
	 * take.
	 * @return The number used to determine how much damage an armor item can 
	 * take.
	 */
	public int getArmorMaxDamageFactor(){
		return (int)(2.5f * strength);
	}

	
	/**
	 * Gets the tool harvest level
	 * @return an integer from -1 (equivalent to no tool) to 3+ (diamond tool equivalent = 3)
	 */
	public int getToolHarvestLevel(){
		return (int)(hardness / 3f); 
	}
	/**
	 * Gets the tool harvest level needed from a tool trying to mine this 
	 * metal's ore and other blocks
	 * @return an integer from -1 (equivalent to no tool) to 3 (diamond tool equivalent)
	 */
	public int getRequiredHarvestLevel(){
		return (int)clamp((0.9f*hardness / 3f),-1,3); 
	}
	static int clamp(int x, int min, int max){
		if(x < min)return min;
		if(x > max) return max;
		return x;
	}
	static float clamp(float x, float min, float max){
		if(x < min)return min;
		if(x > max) return max;
		return x;
	}
	static double clamp(double x, double min, double max){
		if(x < min)return min;
		if(x > max) return max;
		return x;
	}

	/**
	 * Gets the number of uses of a tool made from this material 
	 * @return The number of uses of a tool made from this material 
	 */
	public int getToolDurability(){
		return (int)(32 * strength);
	}

	/**
	 * Gets the number used to determine how quickly a block is mined with a 
	 * tool made from this material
	 * @return the number used to determine how quickly a block is mined
	 */
	public float getToolEfficiency(){
		return hardness;
	}

	/**
	 * Gets the base damage from attacks with tools made from this material
	 * @return the base damage from attacks with tools made from this material
	 */
	public float getBaseAttackDamage(){
		return round(Math.max(0f,(hardness / 3f) - 0.5f),1);
	}
	private float round(float number, int numDecimalPlaces){
		int x = 1;
		for(int i = 0; i < numDecimalPlaces; i++){
			x *= 10;
		}
		return (float)Math.round(number * x) / (float)x;
	}

	/**
	 * Gets the enchantibility score for this material
	 * @return the enchantibility score for this material
	 */
	public int getEnchantability(){
		return (int)(2.5f * magic);
	}

	/**
	 * Returns the treasure chest spawn frequency
	 * @return an integer from 1 to 100
	 */
	public int getLootSpawnWeight(){
		return this.rarity;
	}
	/**
	 * Returns the material used to repair this tool
	 * @return an ItemStack
	 */
	public ItemStack getRepairMaterial(){
		if (this.repairMaterial == null)
			return new ItemStack(Blocks.BEDROCK);
		return this.repairMaterial;
	}

}
