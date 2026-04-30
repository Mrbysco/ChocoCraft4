package net.chococraft.fabric.common.inventory;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.MenuProvider;

public interface ExtendedMenuProvider extends MenuProvider {
	void saveExtraData(FriendlyByteBuf buf);
}
