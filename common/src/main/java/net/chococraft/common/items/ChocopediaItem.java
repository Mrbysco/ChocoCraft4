package net.chococraft.common.items;

import net.chococraft.Chococraft;
import net.chococraft.ChococraftExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChocopediaItem extends Item {

	public ChocopediaItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
		if (level.isClientSide) {
			if (ChococraftExpectPlatform.isModLoaded("patchouli")) {
				vazkii.patchouli.api.PatchouliAPI.get().openBookGUI(new ResourceLocation(Chococraft.MOD_ID, "chocopedia"));
			} else {
				net.chococraft.client.gui.ChocoboBookScreen.openScreen();
			}
		}
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
	}
}
