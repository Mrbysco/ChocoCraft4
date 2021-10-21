package net.chococraft.common.network.packets;

import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class ChocoboSprintingMessage {
    private boolean sprinting;

    public ChocoboSprintingMessage(boolean sprinting) {
        this.sprinting = sprinting;
    }

    public void encode(PacketBuffer buf) {
        buf.writeBoolean(sprinting);
    }

    public static ChocoboSprintingMessage decode(final PacketBuffer buffer) {
        return new ChocoboSprintingMessage(buffer.readBoolean());
    }

    public void handle(Supplier<Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if(ctx.getDirection().getReceptionSide() == LogicalSide.SERVER) {
                PlayerEntity player = ctx.getSender();
                if (player != null) {
                    if (player.getVehicle() == null) return;

                    Entity mount = player.getVehicle();
                    if (!(mount instanceof ChocoboEntity)) return;

                    mount.setSprinting(sprinting);
                }
            }
        });
        ctx.setPacketHandled(true);
    }

}
