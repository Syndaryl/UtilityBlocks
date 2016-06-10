/**
 * 
 */
package org.syndaryl.utilityblocks.handler;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author syndaryl
 *
 */
public class EventHandler {

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
				bonemealDropCheck = event.getEntityLiving().worldObj.rand.nextDouble();
				if (bonemealDropCheck <= ConfigurationHandler.bonemealFrequency)
					event.getEntityLiving().entityDropItem(new ItemStack(Items.DYE,15), 1);
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
