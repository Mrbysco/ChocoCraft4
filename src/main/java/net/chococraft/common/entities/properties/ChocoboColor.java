package net.chococraft.common.entities.properties;

import net.chococraft.Chococraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.common.Tags.Items;

import java.util.Optional;
import java.util.Random;

public enum ChocoboColor {
    YELLOW(Items.DYES_YELLOW),
    GREEN(Items.DYES_LIME),
    BLUE(Items.DYES_BLUE),
    WHITE(Items.DYES_WHITE),
    BLACK(Items.DYES_BLACK),
    GOLD(Items.INGOTS_GOLD),
    PINK(Items.DYES_PINK),
    RED(Items.DYES_RED),
    PURPLE(Items.DYES_PURPLE),
    FLAME(null);

    private final static Random rand = new Random();
    private final IOptionalNamedTag<Item> colorTag;
    private final TranslatableComponent eggText;

    ChocoboColor(IOptionalNamedTag<Item> colorIngredient) {
        this.colorTag = colorIngredient;
        this.eggText = new TranslatableComponent("item." + Chococraft.MODID + ".chocobo_egg.tooltip." + this.name().toLowerCase());
    }

    public static ChocoboColor getRandomColor() {
        return values()[rand.nextInt(values().length)];
    }

    public static Optional<ChocoboColor> getColorForItemstack(ItemStack stack) {
        for (ChocoboColor color : values()) {
            if(color.colorTag != null && stack.is(color.colorTag))
                return Optional.of(color);
        }
        return Optional.empty();
    }

    public TranslatableComponent getEggText() {
        return eggText;
    }
}
