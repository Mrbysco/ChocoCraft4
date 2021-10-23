package net.chococraft.common.items;

import net.chococraft.common.entities.ChocoboEntity;
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
        Level worldIn = context.getLevel();
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        final ChocoboEntity chocobo = ModEntities.CHOCOBO.get().create(worldIn);
        if (chocobo != null) {
            final BlockPos pos = context.getClickedPos();
            final Player player = context.getPlayer();
            if (player != null) {
                player.isCrouching();
                if (player.isShiftKeyDown()) {
                    chocobo.setAge(-24000);
                }
            }

            chocobo.moveTo(pos.getX() + .5, pos.getY() + 0.5F, pos.getZ() + .5, Mth.wrapDegrees(worldIn.random.nextFloat() * 360.0F), 0.0F);
            chocobo.yHeadRot = chocobo.getYRot();
            chocobo.yBodyRot = chocobo.getYRot();
            chocobo.setChocoboColor(color);
            chocobo.finalizeSpawn((ServerLevel)worldIn, worldIn.getCurrentDifficultyAt(chocobo.blockPosition()), MobSpawnType.SPAWN_EGG, (SpawnGroupData)null, (CompoundTag)null);
            worldIn.addFreshEntity(chocobo);
            chocobo.playAmbientSound();
        }

        return InteractionResult.SUCCESS;
    }
}
