package net.chococraft.common.network.packets;

import net.chococraft.common.entities.Chocobo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class ChocoboSprintingMessage {
	private boolean sprinting;

	public ChocoboSprintingMessage(boolean sprinting) {
		this.sprinting = sprinting;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBoolean(sprinting);
	}

	public static ChocoboSprintingMessage decode(final FriendlyByteBuf buffer) {
		return new ChocoboSprintingMessage(buffer.readBoolean());
	}

	public void handle(Supplier<Context> context) {
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide() == LogicalSide.SERVER) {
				Player player = ctx.getSender();
				if (player != null) {
					if (player.getVehicle() == null) return;

					Entity mount = player.getVehicle();
					if (!(mount instanceof Chocobo)) return;

					mount.setSprinting(sprinting);
				}
			}
		});
		ctx.setPacketHandled(true);
	}

}
