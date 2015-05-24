package org.syndaryl.animalsdropbones;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.syndaryl.animalsdropbones.block.BlockManager;
import org.syndaryl.animalsdropbones.handler.ConfigurationHandler;
import org.syndaryl.animalsdropbones.handler.FurnaceFuelHandler;
import org.syndaryl.animalsdropbones.item.ItemManager;

@Mod(
		modid      = AnimalsDropBones.MODID,
		name       = AnimalsDropBones.NAME,
		version    = AnimalsDropBones.VERSION,
		
		guiFactory = "org.syndaryl.animalsdropbones.handler.GUIFactory"
	)
public class AnimalsDropBones {
	public static final String MODID   = "animalsdropbones";
	public static final String NAME    = "Animals Drop Bones";
	public static final String VERSION = "1.0.9";
	
	public static Configuration config;
	public static final FurnaceFuelHandler fuelHandler = new FurnaceFuelHandler();
	public static final SimpleLogger LOG = new SimpleLogger(
			"SyndarylLog", Level.INFO, false, false, true, false, "yyyy/mm/dd hh:mm:ss", 
			new StringFormatterMessageFactory(), new PropertiesUtil(""), null
			);
//	public static final Logger LOG = FMLLog.getLogger(); 
	@Mod.Instance(MODID)
	public static AnimalsDropBones instance;
	
	@Mod.EventHandler
	public void initPre(FMLPreInitializationEvent event) {
		// Initiating configuration
		ConfigurationHandler.setConfig(event.getSuggestedConfigurationFile());
		try {
			LOG.setStream(new PrintStream("logs/syndaryl.log"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BlockManager.initialiseBlock();
		ItemManager.initialiseItems();
		
		//BlockManager.registerOreDict();
		//ItemManager.registerOreDict();
		
		if(event.getSide() == Side.CLIENT)
		{
			ItemManager.variantRegistry();
		}
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(instance);
		BlockManager.addCraftingRecipies();
		BlockManager.addFuels(fuelHandler);
		ItemManager.addCraftingRecipies();
		
		
		GameRegistry.registerFuelHandler(fuelHandler);
		if (ConfigurationHandler.isEnabled)
			MinecraftForge.EVENT_BUS.register(this);
		
		if(event.getSide() == Side.CLIENT)
		{
			BlockManager.graphicRegistry();
			ItemManager.graphicRegistry();
		}
	}

	@Mod.EventHandler
	public void initPost(FMLPostInitializationEvent event) {

		AnimalsDropBones.LOG.info("       OreDict contains " + OreDictionary.getOreNames().length + " names");
		for(String name : OreDictionary.getOreNames())
		{
			AnimalsDropBones.LOG.info("SYNDARYL OREDICT: '" + name + "'");
		}
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equals(MODID)) {
			ConfigurationHandler.syncConfig();
		}
	}
	
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		//		boolean setFeather = false;
		//		EntityChicken chicken;
		
		// Break checking
		if (event.entity.worldObj.isRemote || !(event.entity instanceof EntityAnimal) )
			return;

		int numberDropped;
		
		// all animals
		if(event.entityLiving instanceof EntityAnimal && ConfigurationHandler.animalsDropBones)
		{
			numberDropped = event.entityLiving.worldObj.rand.nextInt(3)-1;
			if ( numberDropped > 0 )
				event.entityLiving.entityDropItem(new ItemStack(Items.bone), numberDropped);
		}
		
		// specific animals
		if(
			(event.entityLiving instanceof EntityPig && ConfigurationHandler.pigsDropLeather) 
			|| 
			(event.entityLiving instanceof EntityHorse  && ConfigurationHandler.horsesDropLeather)
			)
		{
			numberDropped = event.entityLiving.worldObj.rand.nextInt(4)-1;
			if ( numberDropped > 0 )
				event.entityLiving.entityDropItem(new ItemStack(Items.leather), numberDropped);
		}
	}
}
