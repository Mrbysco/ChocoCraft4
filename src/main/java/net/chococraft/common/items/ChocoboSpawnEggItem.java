package net.chococraft.common.items;

import net.chococraft.common.entities.Chocobo;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ChocoboSpawnEggItem extends Item {
	private final ChocoboColor color;

	public ChocoboSpawnEggItem(Properties properties, ChocoboColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}

		final Chocobo chocobo = ModEntities.CHOCOBO.get().create(level);
		if (chocobo != null) {
			final BlockPos pos = context.getClickedPos();
			final Player player = context.getPlayer();
			if (player != null) {
				if (player.isCrouching()) {
					chocobo.setAge(-24000);
				}
			}

			chocobo.moveTo(pos.getX() + .5, pos.getY() + 0.5F, pos.getZ() + .5, Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
			chocobo.yHeadRot = chocobo.getYRot();
			chocobo.yBodyRot = chocobo.getYRot();
			chocobo.setChocoboColor(color);
			chocobo.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(chocobo.blockPosition()), MobSpawnType.SPAWN_EGG, (SpawnGroupData) null, (CompoundTag) null);
			level.addFreshEntity(chocobo);
			chocobo.playAmbientSound();
		}

		return InteractionResult.SUCCESS;
	}
}
