package net.chococraft.common.network.packets;

import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class OpenChocoboGuiMessage {
	public int entityId;
	public int windowId;
	public CompoundNBT saddle;
	@Nullable
	public CompoundNBT inventory;

	public OpenChocoboGuiMessage(ChocoboEntity chocobo, int windowId) {
		this.entityId = chocobo.getEntityId();
		this.windowId = windowId;

		this.saddle = chocobo.saddleItemStackHandler.serializeNBT();
		ItemStack saddleStack = chocobo.getSaddle();
		if(!saddleStack.isEmpty() && saddleStack.getItem() instanceof ChocoboSaddleItem) {
			ChocoboSaddleItem saddleItem = (ChocoboSaddleItem) saddleStack.getItem();
			if(saddleItem.getInventorySize() > 0) {
				this.inventory = chocobo.chocoboInventory.serializeNBT();
			}
		}
	}

	public OpenChocoboGuiMessage(int entityID, int windowId, CompoundNBT saddle, CompoundNBT inventory) {
		this.entityId = entityID;
		this.windowId = windowId;

		this.saddle = saddle;
		this.inventory = inventory;
	}

	public void encode(PacketBuffer buf) {
		buf.writeInt(this.entityId);
		buf.writeInt(this.windowId);
		buf.writeCompoundTag(saddle);
		buf.writeBoolean(this.inventory != null);
		if (this.inventory != null)
			buf.writeCompoundTag(inventory);
	}

	public static OpenChocoboGuiMessage decode(final PacketBuffer buffer) {
		return new OpenChocoboGuiMessage(buffer.readInt(), buffer.readInt(), buffer.readCompoundTag(), buffer.readBoolean() ? buffer.readCompoundTag() : null);
	}

	public void handle(Supplier<Context> context) {
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if(ctx.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
				Minecraft mc = Minecraft.getInstance();
				Entity entity = mc.world.getEntityByID(entityId);
				if (!(entity instanceof ChocoboEntity)) {
					Chococraft.log.warn("Server send OpenGUI for chocobo with id {}, but this entity does not exist on my side", entityId);
					return;
				}
				ChocoboEntity chocobo = (ChocoboEntity) entity;

				net.chococraft.client.gui.ChocoboInventoryScreen.openInventory(windowId, chocobo);

				chocobo.saddleItemStackHandler.deserializeNBT(saddle);
				if (inventory != null)
					chocobo.chocoboInventory.deserializeNBT(inventory);
			}
		});
		ctx.setPacketHandled(true);
	}
}
