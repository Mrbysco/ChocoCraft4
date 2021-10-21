package net.chococraft.common.blocks;

import net.chococraft.Chococraft;
import net.chococraft.common.entities.breeding.ChocoboBreedInfo;
import net.chococraft.common.entities.breeding.ChocoboStatSnapshot;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.tileentities.ChocoboEggTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class ChocoboEggBlock extends Block {
    public final static String NBTKEY_HATCHINGSTATE_TIME = "Time";
    public final static String NBTKEY_HATCHINGSTATE = "HatchingState";
    public final static String NBTKEY_BREEDINFO = "BreedInfo";

    protected static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 0, 5, 11, 1, 11),
            Block.box(4, 1, 4, 12, 6, 12),
            Block.box(5, 6, 5, 11, 8, 11),
            Block.box(6, 8, 6, 10, 10, 10)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public ChocoboEggBlock(Properties properties) {
        super(properties.harvestTool(ToolType.PICKAXE).harvestLevel(0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public static boolean isChocoboEgg(ItemStack itemStack) {
        return itemStack.getItem() instanceof BlockItem &&
                ((BlockItem) itemStack.getItem()).getBlock() instanceof ChocoboEggBlock;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ChocoboEggTile();
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!worldIn.isClientSide) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if (!(tile instanceof ChocoboEggTile)) return;

            ChocoboBreedInfo breedInfo = ChocoboBreedInfo.getFromNbtOrDefault(stack.getTagElement(NBTKEY_BREEDINFO));

            ((ChocoboEggTile) tile).setBreedInfo(breedInfo);
        }
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (te instanceof ChocoboEggTile) {
            if (worldIn.isClientSide) return;
            //noinspection ConstantConditions | this will never be null when we are getting called - otherwise, its a MC bug
            player.awardStat(Stats.BLOCK_MINED.get(this));
            player.causeFoodExhaustion(0.005F);

            ItemStack itemStack = new ItemStack(ModRegistry.CHOCOBO_EGG.get());
            ChocoboBreedInfo breedInfo = ((ChocoboEggTile) te).getBreedInfo();
            if (breedInfo == null) {
                Chococraft.log.error("Unable to create ItemStack for egg @ {}, the eggy has no breeding info attached");
                return;
            }
            if (breedInfo != null) {
                itemStack.addTagElement(NBTKEY_BREEDINFO, breedInfo.serialize());
            }
            popResource(worldIn, pos, itemStack);
            return;
        }
        super.playerDestroy(worldIn, player, pos, state, te, stack);
    }

    public ITextComponent getColorText(ChocoboColor color) {
        switch (color) {
            case YELLOW: return new TranslationTextComponent("item.chococraft.chocobo_egg.tooltip.yellow");
            case GREEN: return new TranslationTextComponent("item.chococraft.chocobo_egg.tooltip.green");
            case BLUE: return new TranslationTextComponent("item.chococraft.chocobo_egg.tooltip.blue");
            case WHITE: return new TranslationTextComponent("item.chococraft.chocobo_egg.tooltip.white");
            case BLACK: return new TranslationTextComponent("item.chococraft.chocobo_egg.tooltip.black");
            case GOLD: return new TranslationTextComponent("item.chococraft.chocobo_egg.tooltip.gold");
            case PINK: return new TranslationTextComponent("item.chococraft.chocobo_egg.tooltip.pink");
            case RED: return new TranslationTextComponent("item.chococraft.chocobo_egg.tooltip.red");
            case PURPLE: return new TranslationTextComponent("item.chococraft.chocobo_egg.tooltip.purple");
            case FLAME: return new TranslationTextComponent("item.chococraft.chocobo_egg.tooltip.flame");
        }

        return StringTextComponent.EMPTY;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundNBT nbtBreedInfo = stack.getTagElement(NBTKEY_BREEDINFO);
        if (nbtBreedInfo != null) {
            ChocoboBreedInfo info = new ChocoboBreedInfo(nbtBreedInfo);
            ChocoboStatSnapshot mother = info.getMother();
            ChocoboStatSnapshot father = info.getFather();

            tooltip.add(new TranslationTextComponent("item." + Chococraft.MODID + ".chocobo_egg.tooltip.mother_info", (int) mother.health, (int) (mother.speed * 100), (int) mother.stamina, getColorText(mother.color)));
            tooltip.add(new TranslationTextComponent("item." + Chococraft.MODID + ".chocobo_egg.tooltip.father_info", (int) father.health, (int) (father.speed * 100), (int) father.stamina, getColorText(mother.color)));
        } else {
            tooltip.add(new TranslationTextComponent("item." + Chococraft.MODID + ".chocobo_egg.tooltip.invalid_egg"));
        }
    }
}
