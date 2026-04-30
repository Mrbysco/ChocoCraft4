package net.chococraft.common.items;

import net.chococraft.Chococraft;
import net.chococraft.platform.Services;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ChocopediaItem extends Item {

	public ChocopediaItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
		if (level.isClientSide()) {
			if (Services.PLATFORM.isModLoaded("patchouli")) {
				vazkii.patchouli.api.PatchouliAPI.get().openBookGUI(Chococraft.modLoc("chocopedia"));
			} else {
				net.chococraft.client.gui.ChocoboBookScreen.openScreen();
			}
		}
		return super.use(level, player, interactionHand);
	}
}
