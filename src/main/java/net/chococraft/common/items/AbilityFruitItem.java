package net.chococraft.common.items;

import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.items.enums.AbilityFruitType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class AbilityFruitItem extends Item {
    private final AbilityFruitType fruitType;

    public AbilityFruitItem(Properties properties, AbilityFruitType type) {
        super(properties);
        this.fruitType = type;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        if (target instanceof ChocoboEntity) {
            if (fruitType.useFruitOn((ChocoboEntity) target)) {
                if (!playerIn.getAbilities().instabuild)
                    stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return super.interactLivingEntity(stack, playerIn, target, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent(this.getDescriptionId(stack) + ".tooltip"));
    }
}
