package net.chococraft.common.blocks;

import net.chococraft.Chococraft;
import net.chococraft.common.blockentities.ChocoboEggBlockEntity;
import net.chococraft.common.entities.breeding.ChocoboBreedInfo;
import net.chococraft.common.entities.breeding.ChocoboStatSnapshot;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class ChocoboEggBlock extends BaseEntityBlock {
    public final static String NBTKEY_HATCHINGSTATE_TIME = "Time";
    public final static String NBTKEY_HATCHINGSTATE = "HatchingState";
    public final static String NBTKEY_BREEDINFO = "BreedInfo";

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 0, 5, 11, 1, 11),
            Block.box(4, 1, 4, 12, 6, 12),
            Block.box(5, 6, 5, 11, 8, 11),
            Block.box(6, 8, 6, 10, 10, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public ChocoboEggBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public static boolean isChocoboEgg(ItemStack itemStack) {
        return itemStack.getItem() instanceof BlockItem &&
                ((BlockItem) itemStack.getItem()).getBlock() instanceof ChocoboEggBlock;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChocoboEggBlockEntity(pos, state);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!worldIn.isClientSide) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if (!(tile instanceof ChocoboEggBlockEntity)) {
                return;
            }

            ChocoboBreedInfo breedInfo = ChocoboBreedInfo.getFromNbtOrDefault(stack.getTagElement(NBTKEY_BREEDINFO));

            ((ChocoboEggBlockEntity) tile).setBreedInfo(breedInfo);
        }
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        if (worldIn.isClientSide) {
            return;
        }

        if (te instanceof ChocoboEggBlockEntity) {
            player.awardStat(Stats.BLOCK_MINED.get(this));
            player.causeFoodExhaustion(0.005F);

            ItemStack itemStack = new ItemStack(ModRegistry.CHOCOBO_EGG.get());
            ChocoboBreedInfo breedInfo = ((ChocoboEggBlockEntity) te).getBreedInfo();
            if (breedInfo == null) {
                Chococraft.log.error("Unable to create ItemStack for egg @ {}, the eggy has no breeding info attached");
                return;
            }
            itemStack.addTagElement(NBTKEY_BREEDINFO, breedInfo.serialize());
            popResource(worldIn, pos, itemStack);
            return;
        }
        super.playerDestroy(worldIn, player, pos, state, te, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        final CompoundTag nbtBreedInfo = stack.getTagElement(NBTKEY_BREEDINFO);
        if (nbtBreedInfo != null) {
            final ChocoboBreedInfo info = new ChocoboBreedInfo(nbtBreedInfo);
            final ChocoboStatSnapshot mother = info.getMother();
            final ChocoboStatSnapshot father = info.getFather();

            tooltip.add(new TranslatableComponent("item." + Chococraft.MODID + ".chocobo_egg.tooltip.mother_info", (int) mother.health, (int) (mother.speed * 100), (int) mother.stamina, mother.color.getEggText()));
            tooltip.add(new TranslatableComponent("item." + Chococraft.MODID + ".chocobo_egg.tooltip.father_info", (int) father.health, (int) (father.speed * 100), (int) father.stamina, father.color.getEggText()));
        } else {
            tooltip.add(new TranslatableComponent("item." + Chococraft.MODID + ".chocobo_egg.tooltip.invalid_egg"));
        }
    }
}
