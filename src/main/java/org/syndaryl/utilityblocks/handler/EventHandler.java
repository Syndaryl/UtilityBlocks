/**
 * 
 */
package org.syndaryl.utilityblocks.handler;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
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
				)
		{
			numberDropped = event.entityLiving.worldObj.rand.nextInt(4)-2;
			if ( numberDropped > 0 )
				event.entityLiving.entityDropItem(new ItemStack(Items.leather), numberDropped);
		}
		if(
			(event.entityLiving instanceof EntityHorse  && ConfigurationHandler.horsesDropLeather)
			)
		{
			numberDropped = event.entityLiving.worldObj.rand.nextInt(4)-1;
			if ( numberDropped > 0 )
				event.entityLiving.entityDropItem(new ItemStack(Items.leather), numberDropped);
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
