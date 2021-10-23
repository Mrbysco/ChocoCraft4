package net.chococraft.common.items;

import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.init.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

public class ChocoboSpawnEggItem extends Item {
    private final ChocoboColor color;
    public ChocoboSpawnEggItem(Properties properties, ChocoboColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World worldIn = context.getLevel();
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        }

        final ChocoboEntity chocobo = ModEntities.CHOCOBO.get().create(worldIn);
        if (chocobo != null) {
            final BlockPos pos = context.getClickedPos();
            final PlayerEntity player = context.getPlayer();
            if (player != null) {
                if (player.isCrouching()) {
                    chocobo.setAge(-24000);
                }
            }

            chocobo.moveTo(pos.getX() + .5, pos.getY() + 0.5F, pos.getZ() + .5, MathHelper.wrapDegrees(worldIn.random.nextFloat() * 360.0F), 0.0F);
            chocobo.yHeadRot = chocobo.yRot;
            chocobo.yBodyRot = chocobo.yRot;
            chocobo.setChocoboColor(color);
            chocobo.finalizeSpawn((ServerWorld)worldIn, worldIn.getCurrentDifficultyAt(chocobo.blockPosition()), SpawnReason.SPAWN_EGG, null, null);
            worldIn.addFreshEntity(chocobo);
            chocobo.playAmbientSound();
        }

        return ActionResultType.SUCCESS;
    }
}
