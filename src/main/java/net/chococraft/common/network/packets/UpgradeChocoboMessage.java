package net.chococraft.common.network.packets;

import net.chococraft.common.ChocoConfig;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.handler.ExperienceHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class UpgradeChocoboMessage {
    public int entityID;
    public int skillID;

    public UpgradeChocoboMessage(ChocoboEntity chocobo, int skillID) {
        this.entityID = chocobo.getEntityId();
        this.skillID = skillID;
    }

    public UpgradeChocoboMessage(int entityID, int skillID) {
        this.entityID = entityID;
        this.skillID = skillID;
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityID);
        buf.writeInt(this.skillID);
    }

    public static UpgradeChocoboMessage decode(final PacketBuffer buffer) {
        return new UpgradeChocoboMessage(buffer.readInt(), buffer.readInt());
    }

    public void handle(Supplier<Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if(ctx.getDirection().getReceptionSide() == LogicalSide.SERVER) {
                PlayerEntity player = ctx.getSender();
                if (player != null) {
                    World world = player.world;
                    Entity entity = world.getEntityByID(entityID);

                    if (entity != null && entity instanceof ChocoboEntity) {
                        if (skillID == 1 && ExperienceHandler.removeExperience(player, ChocoConfig.COMMON.ExpCostSprint.get())) {
                            ((ChocoboEntity) entity).setCanSprint(true);
                        } else if (skillID == 2 && ExperienceHandler.removeExperience(player, ChocoConfig.COMMON.ExpCostGlide.get())) {
                            ((ChocoboEntity) entity).setCanGlide(true);
                        } else if (skillID == 3 && ExperienceHandler.removeExperience(player, ChocoConfig.COMMON.ExpCostDive.get())) {
                            ((ChocoboEntity) entity).setCanDive(true);
                        } else if (skillID == 4 && ExperienceHandler.removeExperience(player, ChocoConfig.COMMON.ExpCostFly.get())) {
                            ((ChocoboEntity) entity).setCanFly(true);
                        }

                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
