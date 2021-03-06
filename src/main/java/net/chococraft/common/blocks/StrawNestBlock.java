package net.chococraft.common.blocks;

import net.chococraft.common.tileentities.ChocoboNestTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class StrawNestBlock extends Block{
    public final static BooleanProperty HAS_EGG = BooleanProperty.create("egg");
    protected static final VoxelShape EMPTY_SHAPE = Stream.of(
            Block.box(1, 0, 1, 15, 1, 15),
            Block.box(0, 1, 0, 16, 3, 2),
            Block.box(0, 1, 14, 16, 3, 16),
            Block.box(0, 1, 2, 2, 3, 14),
            Block.box(14, 1, 2, 16, 3, 14)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    protected static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 0, 5, 11, 1, 11),
            Block.box(4, 1, 4, 12, 6, 12),
            Block.box(5, 6, 5, 11, 8, 11),
            Block.box(6, 8, 6, 10, 10, 10),
            Block.box(14, 1, 2, 16, 3, 14),
            Block.box(1, 0, 1, 15, 1, 15),
            Block.box(0, 1, 0, 16, 3, 2),
            Block.box(0, 1, 14, 16, 3, 16),
            Block.box(0, 1, 2, 2, 3, 14)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public StrawNestBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_EGG, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if(state.getValue(HAS_EGG)) {
            return SHAPE;
        }
        return EMPTY_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HAS_EGG);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tile = worldIn.getBlockEntity(pos);
        if(!(tile instanceof ChocoboNestTile))
            return ActionResultType.FAIL;

        ChocoboNestTile nest = (ChocoboNestTile)tile;
        ItemStack heldItem = playerIn.getItemInHand(handIn);
        if (ChocoboEggBlock.isChocoboEgg(heldItem)) {
            if (!nest.getEggItemStack().isEmpty()) return ActionResultType.FAIL;
            if (worldIn.isClientSide) return ActionResultType.SUCCESS;
            nest.setEggItemStack(playerIn.getItemInHand(handIn).copy());
            playerIn.getItemInHand(handIn).shrink(1);
            return ActionResultType.SUCCESS;
        } else {
            if(!worldIn.isClientSide) {
                NetworkHooks.openGui((ServerPlayerEntity) playerIn, nest, pos);
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ChocoboNestTile();
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }
}
