package org.syndaryl.utilityblocks;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.syndaryl.utilityblocks.block.BlockManager;
import org.syndaryl.utilityblocks.handler.ConfigurationHandler;
import org.syndaryl.utilityblocks.handler.EventHandler;
import org.syndaryl.utilityblocks.handler.FurnaceFuelHandler;
import org.syndaryl.utilityblocks.handler.KeyBindHandler;
import org.syndaryl.utilityblocks.item.ItemManager;
import org.syndaryl.utilityblocks.item.basemetals.BaseMetalsLoader;
import org.syndaryl.utilityblocks.item.basemetals.BaseMetalsLoaderDummy;
import org.syndaryl.utilityblocks.item.basemetals.IBaseMetalsLoader;
import org.syndaryl.utilityblocks.mineralogy.IMineralogyLoader;
import org.syndaryl.utilityblocks.mineralogy.MineralogyLoader;
import org.syndaryl.utilityblocks.mineralogy.MineralogyLoaderDummy;

@Mod(
		modid      = UtilityBlocks.MODID,
		name       = UtilityBlocks.NAME,
		version    = UtilityBlocks.VERSION,
		
		guiFactory = "org.syndaryl.utilityblocks.handler.GUIFactory"
	)
public class UtilityBlocks {
	public static final String MODID   = "utilityblocks";
	public static final String NAME    = "UtilityBlocks";
	public static final String VERSION = "${version}";
	
	public static Configuration config;
	public static final FurnaceFuelHandler fuelHandler = new FurnaceFuelHandler();
	public static final SimpleLogger LOG = new SimpleLogger(
			"SyndarylLog", Level.INFO, false, false, true, false, "yyyy/mm/dd hh:mm:ss", 
			new StringFormatterMessageFactory(), new PropertiesUtil(""), null
			);
	public static final SimpleLogger INFO = new SimpleLogger(
			"SyndarylMinecraftInfoLog", Level.INFO, false, false, true, false, "", 
			new StringFormatterMessageFactory(), new PropertiesUtil(""), null
			);
//	public static final Logger LOG = FMLLog.getLogger(); 
	@Mod.Instance(MODID)
	public static UtilityBlocks instance;
	public IBaseMetalsLoader BASEMETALS;
	public IMineralogyLoader MINERALOGY;
	
	@Mod.EventHandler
	public void initPre(FMLPreInitializationEvent event) {
		// Initiating configuration
		ConfigurationHandler.setConfig(event.getSuggestedConfigurationFile());
		try {
			LOG.setStream(new PrintStream("logs/syndaryl.log"));
			INFO.setStream(new PrintStream("logs/syndarylminecraftinfo.log"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//MaterialHandler.addAllMaterials();
		
		if (Loader.isModLoaded("mineralogy"))
		{
			MINERALOGY = new MineralogyLoader();
		}
		else
		{
			MINERALOGY = new MineralogyLoaderDummy();
		}
		//MINERALOGY.initialiseMineralogyItems();
		
		BlockManager.initialiseBlock();
		ItemManager.initialiseItems();
		KeyBindHandler.initialiseKeyBinds();

		
		if (Loader.isModLoaded("basemetals"))
		{
				BASEMETALS = new BaseMetalsLoader();
				UtilityBlocks.LOG.info("'BaseMetals' mod is loaded! Making appropriate items.");
		}
		else
		{
			BASEMETALS = new BaseMetalsLoaderDummy();
			UtilityBlocks.LOG.info("'BaseMetals' mod not loaded! Not making any BaseMetals items.");
		}

		BASEMETALS.initialiseBaseMetalsItems();
		
		BlockManager.registerOreDict();
		ItemManager.registerOreDict();
		
		if(event.getSide() == Side.CLIENT)
		{
			ItemManager.variantRegistry();
			
		}
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(instance);
		BlockManager.addFuels(fuelHandler);
		BlockManager.addCraftingRecipies();
		ItemManager.addCraftingRecipies();
		BASEMETALS.addRecipiesBaseMetalsItems();
		
		GameRegistry.registerFuelHandler(fuelHandler);
		if (ConfigurationHandler.isEnabled)
			MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(EventHandler.getInstance());
		
		if(event.getSide() == Side.CLIENT)
		{
			BlockManager.graphicRegistry();
			ItemManager.graphicRegistry();
			BASEMETALS.graphicRegistryBaseMetalsItems();
		}
	}


	@Mod.EventHandler
	public void initPost(FMLPostInitializationEvent event) {

	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MODID)) {
			ConfigurationHandler.syncConfig();
		}
	}
	
}
