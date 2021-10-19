package net.chococraft.common.items;

import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class ChocoboSpawnEggItem extends Item {
    private final ChocoboColor color;
    public ChocoboSpawnEggItem(Properties properties, ChocoboColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        if (worldIn.isClientSide) return InteractionResult.SUCCESS;

        Entity entity = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(Chococraft.MODID, "chocobo")).create(worldIn);
        if (entity instanceof ChocoboEntity) {
            ChocoboEntity chocobo = (ChocoboEntity) entity;
            Player player = context.getPlayer();
            BlockPos pos = context.getClickedPos();
            if (player.isShiftKeyDown())
                chocobo.setAge(-24000);
            entity.moveTo(pos.getX() + .5, pos.getY() + 0.5F, pos.getZ() + .5, Mth.wrapDegrees(worldIn.random.nextFloat() * 360.0F), 0.0F);
            chocobo.yHeadRot = chocobo.getYRot();
            chocobo.yBodyRot = chocobo.getYRot();
            chocobo.finalizeSpawn((ServerLevel)worldIn, worldIn.getCurrentDifficultyAt(chocobo.blockPosition()), MobSpawnType.SPAWN_EGG, (SpawnGroupData)null, (CompoundTag)null);
            chocobo.setChocoboColor(color);
            worldIn.addFreshEntity(entity);
            chocobo.playAmbientSound();
        }

        return InteractionResult.SUCCESS;
    }
}
