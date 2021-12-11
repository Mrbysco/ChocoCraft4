package net.chococraft.common.network.packets;

import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class OpenChocoboGuiMessage {
	public int entityId;
	public int windowId;
	public CompoundTag saddle;
	@Nullable
	public CompoundTag inventory;

	public OpenChocoboGuiMessage(ChocoboEntity chocobo, int windowId) {
		this.entityId = chocobo.getId();
		this.windowId = windowId;

		this.saddle = chocobo.saddleItemStackHandler.serializeNBT();
		ItemStack saddleStack = chocobo.getSaddle();
		if(!saddleStack.isEmpty() && saddleStack.getItem() instanceof ChocoboSaddleItem saddleItem) {
			if(saddleItem.getInventorySize() > 0) {
				this.inventory = chocobo.chocoboInventory.serializeNBT();
			}
		}
	}

	public OpenChocoboGuiMessage(int entityID, int windowId, CompoundTag saddle, CompoundTag inventory) {
		this.entityId = entityID;
		this.windowId = windowId;

		this.saddle = saddle;
		this.inventory = inventory;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeInt(this.windowId);
		buf.writeNbt(saddle);
		buf.writeBoolean(this.inventory != null);
		if (this.inventory != null)
			buf.writeNbt(inventory);
	}

	public static OpenChocoboGuiMessage decode(final FriendlyByteBuf buffer) {
		return new OpenChocoboGuiMessage(buffer.readInt(), buffer.readInt(), buffer.readNbt(), buffer.readBoolean() ? buffer.readNbt() : null);
	}

	public void handle(Supplier<Context> context) {
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if(ctx.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
				Minecraft mc = Minecraft.getInstance();
				Entity entity = mc.level.getEntity(entityId);
				if (!(entity instanceof ChocoboEntity chocobo)) {
					Chococraft.log.warn("Server send OpenGUI for chocobo with id {}, but this entity does not exist on my side", entityId);
					return;
				}

				net.chococraft.client.gui.ChocoboInventoryScreen.openInventory(windowId, chocobo);

				chocobo.saddleItemStackHandler.deserializeNBT(saddle);
				if (inventory != null)
					chocobo.chocoboInventory.deserializeNBT(inventory);
			}
		});
		ctx.setPacketHandled(true);
	}
}
