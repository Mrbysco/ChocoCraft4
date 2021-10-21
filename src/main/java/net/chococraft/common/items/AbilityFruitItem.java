package net.chococraft.common.items;

import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.items.enums.AbilityFruitType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AbilityFruitItem extends Item {
    private final AbilityFruitType fruitType;

    public AbilityFruitItem(Properties properties, AbilityFruitType type) {
        super(properties);
        this.fruitType = type;
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (target instanceof ChocoboEntity) {
            if (fruitType.useFruitOn((ChocoboEntity) target)) {
                if (!playerIn.abilities.instabuild)
                    stack.shrink(1);
                return ActionResultType.SUCCESS;
            }
        }
        return super.interactLivingEntity(stack, playerIn, target, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(this.getDescriptionId(stack) + ".tooltip"));
    }
}
