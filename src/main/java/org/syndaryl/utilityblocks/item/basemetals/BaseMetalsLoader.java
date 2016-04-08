package org.syndaryl.utilityblocks.item.basemetals;

import java.util.Locale;

import net.minecraft.item.ItemStack;

import org.syndaryl.utilityblocks.UtilityBlocks;
import org.syndaryl.utilityblocks.item.ItemManager;
import org.syndaryl.utilityblocks.item.ToolMattock;
import org.syndaryl.utilityblocks.item.ToolSledgehammer;

import cyano.basemetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;

/*
 * If BaseMetals is loaded, do things.
 */
public class BaseMetalsLoader implements IBaseMetalsLoader {

	public BaseMetalsLoader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialiseBaseMetalsItems() {
		// TODO Auto-generated method stub
		//ItemManager.initialiseBaseMetalsItems();
		UtilityBlocks.LOG.info("initialiseBaseMetalsItems");
		/* Create the versions for Base Metals */ 
		for(MetalMaterial m : Materials.getAllMetals())
		{
			if ( ! ( // if none of the following are true - skip the vanilla materials
					m.getName().toLowerCase(Locale.ENGLISH).equals("iron") ||
					m.getName().toLowerCase(Locale.ENGLISH).equals("stone") ||
					m.getName().toLowerCase(Locale.ENGLISH).equals("wood") ||
					m.getName().toLowerCase(Locale.ENGLISH).equals("diamond") ||
					m.getName().toLowerCase(Locale.ENGLISH).equals("gold") ||
					m.getName().toLowerCase(Locale.ENGLISH).equals("obsidian")
					)
				)
			{
				UtilityBlocks.LOG.info("Creating tools for " + m.getName());
				ToolMattock mattock =  new ToolMattock(Materials.getToolMaterialFor(m));
				ToolSledgehammer sledgehammer =  new ToolSledgehammer(Materials.getToolMaterialFor(m));
	
				ItemManager.Mattocks.put("mattock"+m.getCapitalizedName(), mattock);
				ItemManager.Sledgehammers.put("sledgehammer"+m.getCapitalizedName(), sledgehammer);
				
				ItemManager.ToolToIngot.put(mattock, "ingot" + m.getCapitalizedName());
				ItemManager.ToolToIngot.put(sledgehammer, "ingot" + m.getCapitalizedName());
			}
		}

	}

	@Override
	public void addRecipiesBaseMetalsItems() {
		UtilityBlocks.LOG.info("addRecipiesBaseMetalsItems");
		// TODO Auto-generated method stub
		//ItemManager.addRecipiesBaseMetalsItems();
		for ( ToolMattock m : ItemManager.Mattocks.values() )
		{
			if (ItemManager.ToolToIngot.containsKey(m))
			{
				ItemManager.makeMattockRecepie(new ItemStack(m,1), ItemManager.ToolToIngot.get(m), "toolHandle");
			}
		}
		for ( ToolSledgehammer s : ItemManager.Sledgehammers.values() )
		{
			if (ItemManager.ToolToIngot.containsKey(s))
			{
				ItemManager.makeHammerRecepie(new ItemStack(s,1), ItemManager.ToolToIngot.get(s), "toolHandle");
			}
		}
	}

	@Override
	public void graphicRegistryBaseMetalsItems() {
		// not actually doing anything here
		UtilityBlocks.LOG.info("graphicRegistryBaseMetalsItems");
	}

}
