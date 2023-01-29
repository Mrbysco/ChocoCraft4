package net.chococraft.common.items;

import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ChocoboSpawnEggItem extends Item {
	private final ChocoboColor color;

	public ChocoboSpawnEggItem(Properties properties, ChocoboColor color) {
		super(properties);
		this.color = color;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		if (!(level instanceof ServerLevel)) {
			return InteractionResult.SUCCESS;
		} else {
			ItemStack stack = context.getItemInHand();
			BlockPos blockPos = context.getClickedPos();
			Direction direction = context.getClickedFace();
			BlockState blockState = level.getBlockState(blockPos);
			Player player = context.getPlayer();

			BlockPos pos;
			if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
				pos = blockPos;
			} else {
				pos = blockPos.relative(direction);
			}

			AbstractChocobo chocobo = ModEntities.CHOCOBO.get().create(level);
			if (chocobo != null) {
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
				if (level.addFreshEntity(chocobo)) {
					stack.shrink(1);
					level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
				}
				chocobo.playAmbientSound();
			}

			return InteractionResult.CONSUME;
		}
	}
}
