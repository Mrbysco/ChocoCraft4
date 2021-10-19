package net.chococraft.common;

import net.chococraft.Chococraft;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class ChocoConfig {

    public static class Common {
        public final BooleanValue addAbilityFruitsToDungeonLoot;
        public final IntValue abilityFruitDungeonLootWeight;
        public final IntValue gysahlGreenSpawnWeight;
        public final IntValue gysahlGreenPatchSize;
        public final DoubleValue gysahlGreenSpawnChance;
        public final BooleanValue gysahlGreensSpawnOnlyInOverworld;

        public final IntValue chocoboSpawnWeight;
        public final IntValue chocoboPackSizeMin;
        public final IntValue chocoboPackSizeMax;

        public final DoubleValue tameChance;
        public final DoubleValue sprintStaminaCost;
        public final DoubleValue glideStaminaCost;
        public final DoubleValue flyStaminaCost;
        public final DoubleValue jumpStaminaCost;
        public final DoubleValue staminaRegenRate;

        public final IntValue defaultStamina;
        public final IntValue defaultSpeed;
        public final IntValue defaultHealth;

        public final IntValue ExpCostSprint;
        public final IntValue ExpCostGlide;
        public final IntValue ExpCostDive;
        public final IntValue ExpCostFly;

        public final IntValue maxHealth;
        public final IntValue maxSpeed;
        public final DoubleValue maxStamina;
        public final DoubleValue posgainHealth;
        public final DoubleValue posgainSpeed;
        public final DoubleValue posgainStamina;
        public final DoubleValue poslossHealth;
        public final DoubleValue poslossSpeed;
        public final DoubleValue poslossStamina;
        public final IntValue eggHatchTimeTicks;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("World generation related configuration")
                    .push("World");

                builder.comment("Ability Fruit")
                    .push("ability_fruit");

                addAbilityFruitsToDungeonLoot = builder
                        .worldRestart()
                        .comment("Add ability fruit to dungeon loot [Default: true]")
                        .define("addAbilityFruitsToDungeonLoot", true);

                abilityFruitDungeonLootWeight = builder
                        .worldRestart()
                        .comment("This number controls the weight ability fruit has compared to other items in a loot table. [Default: 1]")
                        .defineInRange("abilityFruitDungeonLootWeight", 1, 0, Integer.MAX_VALUE);

                builder.pop();

            gysahlGreenSpawnWeight = builder
                    .worldRestart()
                    .comment("Controls the weight compared to other world gen [Default: 3]")
                    .defineInRange("gysahlGreenSpawnWeight", 3, 0, Integer.MAX_VALUE);

            gysahlGreenPatchSize = builder
                    .worldRestart()
                    .comment("Controls the Patch Size compared to other world gen [Default: 64]")
                    .defineInRange("gysahlGreenPatchSize", 64, 0, Integer.MAX_VALUE);

            gysahlGreenSpawnChance = builder
                    .worldRestart()
                    .comment("Controls the Spawn Chance compared to other world gen [Default: 0.1]")
                    .defineInRange("gysahlGreenSpawnChance", 0.1, 0, 1);

            gysahlGreensSpawnOnlyInOverworld = builder
                    .worldRestart()
                    .comment("Controls the weight compared to other world gen [Default: true]")
                    .define("gysahlGreensSpawnOnlyInOverworld", true);

            builder.pop();

            builder.comment("Spawning configuration")
                    .push("spawning");

            chocoboSpawnWeight = builder
                    .comment("Controls Chocobo Spawn Weight [Default: 10]")
                    .defineInRange("chocoboSpawnWeight", 10, 0, Integer.MAX_VALUE);

            chocoboPackSizeMin = builder
                    .comment("Controls Chocobo Pack Size Min [Default: 1]")
                    .defineInRange("chocoboPackSizeMin", 1, 0, Integer.MAX_VALUE);

            chocoboPackSizeMax = builder
                    .comment("Controls Chocobo Pack Size Max [Default: 3]")
                    .defineInRange("chocoboPackSizeMax", 3, 0, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Chocobo configuration")
                    .push("Chocobo");

            tameChance = builder
                    .comment("This multiplier controls the tame chance per gysahl used, so .15 results in 15% chance to tame [Default: 0.15]")
                    .defineInRange("tameChance", 0.15, 0, 1);


                builder.comment("Stamina Costs")
                        .push("stamina_costs");

                sprintStaminaCost = builder
                        .comment("Controls the Sprint Stamina cost [Default: 0.06]")
                        .defineInRange("sprintStaminaCost", 0.06, 0, 1);

                glideStaminaCost = builder
                        .comment("Controls the Glide Stamina cost [Default: 0.005]")
                        .defineInRange("glideStaminaCost", 0.005D, 0, 1);

                flyStaminaCost = builder
                        .comment("Controls the Fly Stamina cost [Default: 0.08]")
                        .defineInRange("flyStaminaCost", 0.08D, 0, 1);

                jumpStaminaCost = builder
                        .comment("Controls the Jump Stamina cost [Default: 0.00]")
                        .defineInRange("jumpStaminaCost", 0.00D, 0, 1);

                staminaRegenRate = builder
                        .comment("Controls the amount of Stamina recharged per tick [Default: 0.025]")
                        .defineInRange("staminaRegenRate", 0.025, 0, 1);

                builder.pop();
                builder.comment("Defaults")
                        .push("defaults");

                defaultStamina = builder
                        .comment("Controls the default Stamina [Default: 10]")
                        .defineInRange("defaultStamina", 10, 0, Integer.MAX_VALUE);

                defaultSpeed = builder
                        .comment("Controls the default Speed [Default: 20]")
                        .defineInRange("defaultSpeed", 20, 0, Integer.MAX_VALUE);

                defaultHealth = builder
                        .comment("Controls the default Health [Default: 20]")
                        .defineInRange("defaultHealth", 20, 0, Integer.MAX_VALUE);

                builder.pop();
                builder.comment("Experience Costs")
                        .push("xp_costs");

                ExpCostSprint = builder
                        .comment("Controls the experience required to unlock the Sprint Ability [Default: 1500]")
                        .defineInRange("ExpCostSprint", 1500, 1, Integer.MAX_VALUE);

                ExpCostGlide = builder
                        .comment("Controls the experience required to unlock the Glide Ability [Default: 1500]")
                        .defineInRange("ExpCostGlide", 1500, 1, Integer.MAX_VALUE);

                ExpCostDive = builder
                        .comment("Controls the experience required to unlock the Dive Ability [Default: 1000]")
                        .defineInRange("ExpCostDive", 1000, 1, Integer.MAX_VALUE);

                ExpCostFly = builder
                        .comment("Controls the experience required to unlock the Fly Ability [Default: 2000]")
                        .defineInRange("ExpCostFly", 2000, 1, Integer.MAX_VALUE);
                builder.pop();

            builder.pop();

            builder.comment("Breeding configuration")
                    .push("breeding");

                builder.comment("Max Stats")
                        .push("Max");

                maxHealth = builder
                        .comment("Controls the Max Health a Chocobo can have [Default: 50]")
                        .defineInRange("maxHealth", 50, 0, Integer.MAX_VALUE);

                maxSpeed = builder
                        .comment("Controls the Max Speed a Chocobo can have [Default: 40]")
                        .defineInRange("maxSpeed", 40, 0, Integer.MAX_VALUE);

                maxStamina = builder
                        .comment("Controls the Max Stamina a Chocobo can have [Default: 25]")
                        .defineInRange("maxStamina", 25D, 0, Integer.MAX_VALUE);

                builder.pop();
                builder.comment("Gain Stats")
                        .push("gain_stats");

                posgainHealth = builder
                        .comment("Controls the multiplier the Health stat gains (for example 0.05 would result in a max gain of 5% so 20 to 21) [Default: .1]")
                        .defineInRange("posgainHealth", .1D, 0, Integer.MAX_VALUE);

                posgainSpeed = builder
                        .comment("Controls the multiplier the Speed stat gains (for example 0.05 would result in a max gain of 5% so 20 to 21) [Default: .1]")
                        .defineInRange("posgainSpeed", .1D, 0, Integer.MAX_VALUE);

                posgainStamina = builder
                        .comment("Controls the multiplier the Stamina stat gains (for example 0.05 would result in a max gain of 5% so 20 to 21) [Default: .1]")
                        .defineInRange("posgainStamina", .1D, 0, Integer.MAX_VALUE);

                builder.pop();
                builder.comment("Loss Stats")
                        .push("loss_stats");

                poslossHealth = builder
                        .comment("Controls the multiplier the Health stat loss (for example 0.95 would result in a max loss of 5% so 20 to 19) [Default: .95]")
                        .defineInRange("poslossHealth", .95D, 0, Integer.MAX_VALUE);

                poslossSpeed = builder
                        .comment("Controls the multiplier the Speed stat gains (for example 0.95 would result in a max loss of 5% so 20 to 19) [Default: .95]")
                        .defineInRange("poslossSpeed", .95D, 0, Integer.MAX_VALUE);

                poslossStamina = builder
                        .comment("Controls the multiplier the Stamina stat gains (for example 0.95 would result in a max loss of 5% so 20 to 19) [Default: .95]")
                        .defineInRange("poslossStamina", .95D, 0, Integer.MAX_VALUE);

                builder.pop();

            eggHatchTimeTicks = builder
                    .comment("Controls the amount of ticks / time till an egg hatches. This value isn't super accurate [Default: 10000]")
                    .defineInRange("eggHatchTimeTicks", 10000, 0, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        Chococraft.log.debug("Loaded Chococraft's config file {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
        Chococraft.log.debug("Chococraft's config just got changed on the file system!");
        if(configEvent.getConfig().getModId() == Chococraft.MODID) {
            if (COMMON.chocoboPackSizeMin.get() > COMMON.chocoboPackSizeMax.get()) {
                int t = COMMON.chocoboPackSizeMax.get();
                COMMON.chocoboPackSizeMax.set(COMMON.chocoboPackSizeMin.get());
                COMMON.chocoboPackSizeMin.set(t);
            }
            configEvent.getConfig().save();
        }
    }
}
