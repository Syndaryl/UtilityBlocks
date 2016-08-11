/**
 * 
 */
package org.syndaryl.utilityblocks.handler;

import java.lang.reflect.Field;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author syndaryl
 *
 */
public class EventHandler {
//	class findSpawnSilverfishEvent implements Consumer{
//		@Override
//		public void accept(Object arg0) {
//			if (arg0 instanceof EntitySilverfish.AISummonSilverfish)
//			{
//				via 
//			}
//		}
//	}
//	EntityAITaskEntry spawnSilverfish;
	
	@SubscribeEvent
	public void onCheckSpawn(CheckSpawn event)
	{
		if (event.getEntity().worldObj.isRemote || !(event.getEntity() instanceof EntitySilverfish) )
			return;
		else if (ConfigurationHandler.noSilverfish)
		{
			event.setResult(Result.DENY);
		}
	}
	
	public void onJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntitySilverfish && ConfigurationHandler.noSilverfishForkbomb)
		{
			EntitySilverfish fishy = (EntitySilverfish)event.getEntity();
			Field f;
			try {
				f = fishy.getClass().getDeclaredField("summonSilverfish");
				f.setAccessible(true);
				fishy.tasks.removeTask((EntityAIBase)f.get(fishy));
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //NoSuchFieldException
			catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		
		// Break checking
		if (event.getEntity().worldObj.isRemote || !(event.getEntity() instanceof EntityAnimal) )
			return;

		int dropCheck;
		double bonemealDropCheck;
		// all animals except squid, because squid don't have bones dangit
		if(event.getEntityLiving() instanceof EntityAnimal && !( event.getEntityLiving() instanceof EntitySquid)  && ConfigurationHandler.animalsDropBones)
		{
			dropCheck = event.getEntityLiving().worldObj.rand.nextInt(ConfigurationHandler.boneFrequency);
			if ( dropCheck == 0 )
			{
				bonemealDropCheck = event.getEntityLiving().worldObj.rand.nextInt(ConfigurationHandler.bonemealFrequency);
				if (bonemealDropCheck != 0)
					event.getEntityLiving().entityDropItem(new ItemStack(Items.DYE,1,15), 1);
				else
					event.getEntityLiving().entityDropItem(new ItemStack(Items.BONE), 1);
			}
		}
		
		// specific animals
		if(
			(event.getEntityLiving() instanceof EntityPig && ConfigurationHandler.pigsDropLeather) 
				)
		{
			dropCheck = event.getEntityLiving().worldObj.rand.nextInt(ConfigurationHandler.pigsLeatherFrequency);
			if ( dropCheck == 0 )
				event.getEntityLiving().entityDropItem(new ItemStack(Items.LEATHER), 1);
		}
		if(
			(event.getEntityLiving() instanceof EntityHorse  && ConfigurationHandler.horsesDropLeather)
			)
		{
			dropCheck = event.getEntityLiving().worldObj.rand.nextInt(ConfigurationHandler.horseLeatherFrequency);
			if ( dropCheck == 0 )
				event.getEntityLiving().entityDropItem(new ItemStack(Items.LEATHER), 1);
		}
	}

	private static EventHandler instance = null;
	private static final Lock initLock = new ReentrantLock();
	
	public static EventHandler getInstance() {
		if(instance == null){
			initLock.lock();
			try{
				if(instance == null){instance = new EventHandler();}
			} finally{
				initLock.unlock();
			}
		}
		return instance;
	}
}
