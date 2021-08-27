package net.chococraft.common.tileentities;

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
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class ChocoboNestTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private final static CheckOffset[] SHELTER_CHECK_OFFSETS = new CheckOffset[]
            {
                    new CheckOffset(new Vector3i(0, 1, 0), true),
                    new CheckOffset(new Vector3i(0, 2, 0), true),
                    new CheckOffset(new Vector3i(-1, 3, -1), false),
                    new CheckOffset(new Vector3i(-1, 3, 0), false),
                    new CheckOffset(new Vector3i(-1, 3, 1), false),
                    new CheckOffset(new Vector3i(0, 3, -1), false),
                    new CheckOffset(new Vector3i(0, 3, 0), false),
                    new CheckOffset(new Vector3i(0, 3, 1), false),
                    new CheckOffset(new Vector3i(1, 3, -1), false),
                    new CheckOffset(new Vector3i(1, 3, 0), false),
                    new CheckOffset(new Vector3i(1, 3, 1), false),
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
            ChocoboNestTile.this.onInventoryChanged();
        }
    };
    private LazyOptional<IItemHandler> inventoryHolder = LazyOptional.of(() -> inventory);

    private boolean isSheltered;
    private int ticks = 0;

    public ChocoboNestTile() {
        super(ModRegistry.STRAW_NEST_TILE.get());
    }

    @Override
    public void tick() {
        if (this.world.isRemote)
            return;

        this.ticks++;
        if (ticks > 1_000_000)
            ticks = 0;

        boolean changed = false;

        if (this.ticks % 5 == 0 && !this.getEggItemStack().isEmpty()) {
            changed = this.updateEgg();
        }

        if (this.ticks % 200 == 100) {
            changed |= this.updateSheltered();
        }

        if (changed)
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos));
    }

    private boolean updateEgg() {
        ItemStack egg = this.getEggItemStack();

        if (!ChocoboEggBlock.isChocoboEgg(egg))
            return false;

        if (!egg.hasTag())
            return false;

        CompoundNBT nbt = egg.getOrCreateChildTag(ChocoboEggBlock.NBTKEY_HATCHINGSTATE);
        int time = nbt.getInt(ChocoboEggBlock.NBTKEY_HATCHINGSTATE_TIME);
        time += this.isSheltered ? 2 : 1;
        nbt.putInt(ChocoboEggBlock.NBTKEY_HATCHINGSTATE_TIME, time);

        if (time < ChocoConfig.COMMON.eggHatchTimeTicks.get())
            return false;

        // egg is ready to hatch
        ChocoboBreedInfo breedInfo = ChocoboBreedInfo.getFromNbtOrDefault(egg.getChildTag(ChocoboEggBlock.NBTKEY_BREEDINFO));
        ChocoboEntity baby = BreedingHelper.createChild(breedInfo, this.world);
        baby.setLocationAndAngles(this.pos.getX() + 0.5, this.pos.getY() + 0.2, this.pos.getZ() + 0.5, 0.0F, 0.0F);
        this.world.addEntity(baby);

        Random random = baby.getRNG();
        for (int i = 0; i < 7; ++i) {
            double d0 = random.nextGaussian() * 0.02D;
            double d1 = random.nextGaussian() * 0.02D;
            double d2 = random.nextGaussian() * 0.02D;
            double d3 = random.nextDouble() * baby.getWidth() * 2.0D - baby.getWidth();
            double d4 = 0.5D + random.nextDouble() * baby.getHeight();
            double d5 = random.nextDouble() * baby.getWidth() * 2.0D - baby.getWidth();
            this.world.addParticle(ParticleTypes.HEART, baby.getPosX() + d3, baby.getPosY() + d4, baby.getPosZ() + d5, d0, d1, d2);
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
                CompoundNBT nbt = itemStack.getOrCreateChildTag(ChocoboEggBlock.NBTKEY_HATCHINGSTATE);
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
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.isSheltered = nbt.getBoolean(NBTKEY_IS_SHELTERED);
        this.ticks = nbt.getInt(NBTKEY_TICKS);
        this.inventory.deserializeNBT(nbt.getCompound(NBTKEY_NEST_INVENTORY));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.putBoolean(NBTKEY_IS_SHELTERED, this.isSheltered);
        nbt.putInt(NBTKEY_TICKS, this.ticks);
        nbt.put(NBTKEY_NEST_INVENTORY, this.inventory.serializeNBT());
        return super.write(nbt);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return new SUpdateTileEntityPacket(this.getPos(), 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.inventory.deserializeNBT(pkt.getNbtCompound().getCompound(NBTKEY_NEST_INVENTORY));
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        nbt.put(NBTKEY_NEST_INVENTORY, this.inventory.serializeNBT());
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new NestContainer(id, playerInventory, this);
    }
    //endregion

    private static class CheckOffset {
        Vector3i offset;
        boolean shouldBeAir;

        CheckOffset(Vector3i offset, boolean shouldBeAir) {
            this.offset = offset;
            this.shouldBeAir = shouldBeAir;
        }
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(Chococraft.MODID + ".container.nest");
    }

    public void onInventoryChanged() {
        this.markDirty();
        BlockState newState = ModRegistry.STRAW_NEST.get().getDefaultState().with(StrawNestBlock.HAS_EGG, !this.getEggItemStack().isEmpty());
        this.getWorld().setBlockState(this.getPos(), newState);
    }

    public boolean isSheltered() {
        boolean sheltered = true;
        for (CheckOffset checkOffset : SHELTER_CHECK_OFFSETS) {
            if (world.isAirBlock(this.getPos().add(checkOffset.offset)) != checkOffset.shouldBeAir) {
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
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.inventoryHolder.invalidate();
    }
}
