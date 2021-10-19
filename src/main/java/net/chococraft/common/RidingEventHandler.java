package net.chococraft.common;

import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Chococraft.MODID)
public class RidingEventHandler {
    @SubscribeEvent
    public static void onMountEntity(EntityMountEvent event) {
        if (event.isMounting()) return;
        if (!event.getEntityBeingMounted().isAlive()) return;
        if (!(event.getEntityBeingMounted() instanceof ChocoboEntity)) return;

        if (!event.getEntityBeingMounted().isOnGround())
            event.setCanceled(true);
    }

    /* This Foricbly dismounts players that log out
     * when riding a chocobo to prevent them from
     * maintaining control over it upon logging back in
     */

    @SubscribeEvent
    public static void onPlayerDisconnect(PlayerLoggedOutEvent event) {
        Player player = event.getPlayer();
        if (player.getVehicle() != null) {
            Entity entityRide = player.getVehicle();
            if (entityRide instanceof ChocoboEntity) {
                player.removeVehicle();
            }
        }
    }
}
