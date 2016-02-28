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
		if (event.entity.worldObj.isRemote || !(event.entity instanceof EntityAnimal) )
			return;

		int dropCheck;
		
		// all animals except squid, because squid don't have bones dangit
		if(event.entityLiving instanceof EntityAnimal && !( event.entityLiving instanceof EntitySquid)  && ConfigurationHandler.animalsDropBones)
		{
			dropCheck = event.entityLiving.worldObj.rand.nextInt(ConfigurationHandler.boneFrequency);
			if ( dropCheck > 0 )
				event.entityLiving.entityDropItem(new ItemStack(Items.bone), 1);
		}
		
		// specific animals
		if(
			(event.entityLiving instanceof EntityPig && ConfigurationHandler.pigsDropLeather) 
				)
		{
			dropCheck = event.entityLiving.worldObj.rand.nextInt(ConfigurationHandler.pigsLeatherFrequency);
			if ( dropCheck == 0 )
				event.entityLiving.entityDropItem(new ItemStack(Items.leather), 1);
		}
		if(
			(event.entityLiving instanceof EntityHorse  && ConfigurationHandler.horsesDropLeather)
			)
		{
			dropCheck = event.entityLiving.worldObj.rand.nextInt(ConfigurationHandler.horseLeatherFrequency);
			if ( dropCheck == 0 )
				event.entityLiving.entityDropItem(new ItemStack(Items.leather), 1);
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
