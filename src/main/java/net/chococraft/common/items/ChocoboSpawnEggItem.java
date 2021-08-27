package net.chococraft.common.items;

import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.entities.properties.ChocoboColor;
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
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        if (worldIn.isRemote) return ActionResultType.SUCCESS;

        Entity entity = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(Chococraft.MODID, "chocobo")).create(worldIn);
        if (entity instanceof ChocoboEntity) {
            ChocoboEntity chocobo = (ChocoboEntity) entity;
            PlayerEntity player = context.getPlayer();
            BlockPos pos = context.getPos();
            if (player.isSneaking())
                chocobo.setGrowingAge(-24000);
            entity.setLocationAndAngles(pos.getX() + .5, pos.getY() + 0.5F, pos.getZ() + .5, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
            chocobo.rotationYawHead = chocobo.rotationYaw;
            chocobo.renderYawOffset = chocobo.rotationYaw;
            chocobo.onInitialSpawn((ServerWorld)worldIn, worldIn.getDifficultyForLocation(chocobo.getPosition()), SpawnReason.SPAWN_EGG, (ILivingEntityData)null, (CompoundNBT)null);
            chocobo.setChocoboColor(color);
            worldIn.addEntity(entity);
            chocobo.playAmbientSound();
        }

        return ActionResultType.SUCCESS;
    }
}
