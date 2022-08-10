package net.chococraft.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

public class ChocopediaItem extends Item {

	public ChocopediaItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> use(World level, PlayerEntity playerIn, Hand handIn) {
		if (ModList.get().isLoaded("patchouli")) {
			if (level.isClientSide) {
				vazkii.patchouli.api.PatchouliAPI.get().openBookGUI(new ResourceLocation("chococraft:chocopedia"));
			}
		} else {
			if (level.isClientSide) {
				net.chococraft.client.gui.ChocoboBookScreen.openScreen();
			}
		}

		return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));
	}
}
