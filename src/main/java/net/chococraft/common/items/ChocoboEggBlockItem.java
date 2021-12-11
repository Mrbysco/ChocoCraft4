package net.chococraft.common.items;

import net.chococraft.common.ChocoConfig;
import net.chococraft.common.blocks.ChocoboEggBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class ChocoboEggBlockItem extends BlockItem {
    public ChocoboEggBlockItem(Block block, Item.Properties builder) {
        super(block, builder);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (!ChocoboEggBlock.isChocoboEgg(stack))
            return super.showDurabilityBar(stack);

        if (!stack.hasTag())
            return false;

        CompoundTag nbtHatchIngstate = stack.getTagElement(ChocoboEggBlock.NBTKEY_HATCHINGSTATE);
        return nbtHatchIngstate != null;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if (!ChocoboEggBlock.isChocoboEgg(stack))
            return super.getBarWidth(stack);

        if (!stack.hasTag())
            return 0;

        CompoundTag nbtHatchIngstate = stack.getTagElement(ChocoboEggBlock.NBTKEY_HATCHINGSTATE);
        if (nbtHatchIngstate != null) {
            int time = nbtHatchIngstate.getInt(ChocoboEggBlock.NBTKEY_HATCHINGSTATE_TIME);
            return Math.round(time * 13.0F / (float)ChocoConfig.COMMON.eggHatchTimeTicks.get());
        }

        return 1;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if (!ChocoboEggBlock.isChocoboEgg(stack))
            return super.getBarColor(stack);

        return 0x0000FF00;
    }
}
