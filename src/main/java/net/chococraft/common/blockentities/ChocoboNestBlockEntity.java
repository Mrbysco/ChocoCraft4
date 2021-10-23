package net.chococraft.common.blockentities;

import net.chococraft.Chococraft;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.blocks.ChocoboEggBlock;
import net.chococraft.common.blocks.StrawNestBlock;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.entities.breeding.BreedingHelper;
import net.chococraft.common.entities.breeding.ChocoboBreedInfo;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.inventory.NestContainer;
import net.chococraft.common.items.ChocoboEggBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class ChocoboNestBlockEntity extends BlockEntity implements MenuProvider {
    private final static CheckOffset[] SHELTER_CHECK_OFFSETS = new CheckOffset[]
            {
                    new CheckOffset(new Vec3i(0, 1, 0), true),
                    new CheckOffset(new Vec3i(0, 2, 0), true),
                    new CheckOffset(new Vec3i(-1, 3, -1), false),
                    new CheckOffset(new Vec3i(-1, 3, 0), false),
                    new CheckOffset(new Vec3i(-1, 3, 1), false),
                    new CheckOffset(new Vec3i(0, 3, -1), false),
                    new CheckOffset(new Vec3i(0, 3, 0), false),
                    new CheckOffset(new Vec3i(0, 3, 1), false),
                    new CheckOffset(new Vec3i(1, 3, -1), false),
                    new CheckOffset(new Vec3i(1, 3, 0), false),
                    new CheckOffset(new Vec3i(1, 3, 1), false),
            };

    @SuppressWarnings("WeakerAccess")
    public static final String NBTKEY_IS_SHELTERED = "IsSheltered";
    @SuppressWarnings("WeakerAccess")
    public static final String NBTKEY_TICKS = "Ticks";
    @SuppressWarnings("WeakerAccess")
    public static final String NBTKEY_NEST_INVENTORY = "Inventory";

    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof ChocoboEggBlockItem;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            ChocoboNestBlockEntity.this.onInventoryChanged();
        }
    };
    private LazyOptional<IItemHandler> inventoryHolder = LazyOptional.of(() -> inventory);

    private boolean isSheltered;
    private int ticks = 0;

    public ChocoboNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.STRAW_NEST_TILE.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ChocoboNestBlockEntity nestBlockEntity) {
        nestBlockEntity.ticks++;
        if (nestBlockEntity.ticks > 1_000_000)
            nestBlockEntity.ticks = 0;

        boolean changed = false;

        if (nestBlockEntity.ticks % 5 == 0 && !nestBlockEntity.getEggItemStack().isEmpty()) {
            changed = nestBlockEntity.updateEgg();
        }

        if (nestBlockEntity.ticks % 200 == 100) {
            changed |= nestBlockEntity.updateSheltered();
        }

        if (changed)
            nestBlockEntity.level.setBlockAndUpdate(pos, state);
    }

    private boolean updateEgg() {
        ItemStack egg = this.getEggItemStack();

        if (!ChocoboEggBlock.isChocoboEgg(egg))
            return false;

        if (!egg.hasTag())
            return false;

        CompoundTag nbt = egg.getOrCreateTagElement(ChocoboEggBlock.NBTKEY_HATCHINGSTATE);
        int time = nbt.getInt(ChocoboEggBlock.NBTKEY_HATCHINGSTATE_TIME);
        time += this.isSheltered ? 2 : 1;
        nbt.putInt(ChocoboEggBlock.NBTKEY_HATCHINGSTATE_TIME, time);

        if (time < ChocoConfig.COMMON.eggHatchTimeTicks.get())
            return false;

        // egg is ready to hatch
        ChocoboBreedInfo breedInfo = ChocoboBreedInfo.getFromNbtOrDefault(egg.getTagElement(ChocoboEggBlock.NBTKEY_BREEDINFO));
        ChocoboEntity baby = BreedingHelper.createChild(breedInfo, this.level);
        if (baby == null) {
            return false;
        }

        baby.moveTo(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.2, this.worldPosition.getZ() + 0.5, 0.0F, 0.0F);
        this.level.addFreshEntity(baby);

        Random random = baby.getRandom();
        for (int i = 0; i < 7; ++i) {
            double d0 = random.nextGaussian() * 0.02D;
            double d1 = random.nextGaussian() * 0.02D;
            double d2 = random.nextGaussian() * 0.02D;
            double d3 = random.nextDouble() * baby.getBbWidth() * 2.0D - baby.getBbWidth();
            double d4 = 0.5D + random.nextDouble() * baby.getBbHeight();
            double d5 = random.nextDouble() * baby.getBbWidth() * 2.0D - baby.getBbWidth();
            this.level.addParticle(ParticleTypes.HEART, baby.getX() + d3, baby.getY() + d4, baby.getZ() + d5, d0, d1, d2);
        }

        this.setEggItemStack(ItemStack.EMPTY);
        return true;
    }

    private boolean updateSheltered() {
        // TODO: Make this better, use "can see sky" for shelter detection
        boolean sheltered = isSheltered();

        if (this.isSheltered != sheltered) {
            this.isSheltered = sheltered;
            return true;
        }

        return false;
    }

    public ItemStack getEggItemStack() {
        return this.inventory.getStackInSlot(0);
    }

    public void setEggItemStack(ItemStack itemStack) {
        if (itemStack.isEmpty())
            this.inventory.setStackInSlot(0, ItemStack.EMPTY);
        else if (ChocoboEggBlock.isChocoboEgg(itemStack)) {
            this.inventory.setStackInSlot(0, itemStack);
            if (itemStack.hasTag()) {
                CompoundTag nbt = itemStack.getOrCreateTagElement(ChocoboEggBlock.NBTKEY_HATCHINGSTATE);
                int time = nbt.getInt(ChocoboEggBlock.NBTKEY_HATCHINGSTATE_TIME);
                nbt.putInt(ChocoboEggBlock.NBTKEY_HATCHINGSTATE_TIME, time);
            }
        }
    }

    public IItemHandler getInventory() {
        return this.inventory;
    }

    //region Data Synchronization/Persistence

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.isSheltered = nbt.getBoolean(NBTKEY_IS_SHELTERED);
        this.ticks = nbt.getInt(NBTKEY_TICKS);
        this.inventory.deserializeNBT(nbt.getCompound(NBTKEY_NEST_INVENTORY));
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putBoolean(NBTKEY_IS_SHELTERED, this.isSheltered);
        nbt.putInt(NBTKEY_TICKS, this.ticks);
        nbt.put(NBTKEY_NEST_INVENTORY, this.inventory.serializeNBT());
        return super.save(nbt);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = new CompoundTag();
        save(nbt);
        return new ClientboundBlockEntityDataPacket(this.getBlockPos(), 0, nbt);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.inventory.deserializeNBT(pkt.getTag().getCompound(NBTKEY_NEST_INVENTORY));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        nbt.put(NBTKEY_NEST_INVENTORY, this.inventory.serializeNBT());
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new NestContainer(id, playerInventory, this);
    }
    //endregion

    private static class CheckOffset {
        Vec3i offset;
        boolean shouldBeAir;

        CheckOffset(Vec3i offset, boolean shouldBeAir) {
            this.offset = offset;
            this.shouldBeAir = shouldBeAir;
        }
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(Chococraft.MODID + ".container.nest");
    }

    public void onInventoryChanged() {
        this.setChanged();
        BlockState newState = ModRegistry.STRAW_NEST.get().defaultBlockState().setValue(StrawNestBlock.HAS_EGG, !this.getEggItemStack().isEmpty());
        this.getLevel().setBlockAndUpdate(this.getBlockPos(), newState);
    }

    public boolean isSheltered() {
        boolean sheltered = true;
        for (CheckOffset checkOffset : SHELTER_CHECK_OFFSETS) {
            if (level.isEmptyBlock(this.getBlockPos().offset(checkOffset.offset)) != checkOffset.shouldBeAir) {
                sheltered = false;
                break;
            }
        }
        return sheltered;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventoryHolder.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.inventoryHolder.invalidate();
    }
}
