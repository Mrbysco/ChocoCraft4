package net.chococraft.common.items;

import net.chococraft.common.ChocoConfig;
import net.chococraft.common.blocks.ChocoboEggBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ChocoboEggBlockItem extends BlockItem {
    public ChocoboEggBlockItem(Block block, Item.Properties builder) {
        super(block, builder);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        if (!ChocoboEggBlock.isChocoboEgg(stack))
            return super.showDurabilityBar(stack);

        if (!stack.hasTag())
            return false;

        CompoundNBT nbtHatchIngstate = stack.getChildTag(ChocoboEggBlock.NBTKEY_HATCHINGSTATE);
        if (nbtHatchIngstate == null)
            return false;

        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (!ChocoboEggBlock.isChocoboEgg(stack))
            return super.getDurabilityForDisplay(stack);

        if (!stack.hasTag())
            return 0.0;

        int time = 0;
        CompoundNBT nbtHatchIngstate = stack.getChildTag(ChocoboEggBlock.NBTKEY_HATCHINGSTATE);
        if (nbtHatchIngstate != null)
            time = nbtHatchIngstate.getInt(ChocoboEggBlock.NBTKEY_HATCHINGSTATE_TIME);

        double percent = (double) time / (double) ChocoConfig.COMMON.eggHatchTimeTicks.get();

        return 1.0 - percent;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        if (!ChocoboEggBlock.isChocoboEgg(stack))
            return super.getRGBDurabilityForDisplay(stack);

        return 0x0000FF00;
    }
}
