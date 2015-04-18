package org.syndaryl.animalsdropbones;

import net.minecraft.client.resources.I18n;

public class NamespaceManager {
	public static String modName = "AnimalsDropBones";
	
	public static String GetModName(){
		return new String(modName);
	}
	public static String GetModNameLC(){
		return new String(modName.toLowerCase());
	}
	public static String getI18n(String key)
    {
    	return I18n.format(getLocalized(key), new Object[0]);
    }
	public static String getLocalized(String key)
    {
    	return NamespaceManager.GetModNameLC() + ":" + key;
    }
	public static String getUnLocalized(String key)
    {
    	return NamespaceManager.GetModNameLC() + "_" + key;
    }


	/**
	 * @param word
	 * @return Titlecased word
	 */
	public static String capitalizeWord(String word) {
		return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
	}
}
