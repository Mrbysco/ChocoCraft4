package net.chococraft.common.entities.breeding;

import net.chococraft.common.ChocoConfig;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.init.ModAttributes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.nbt.CompoundTag;

public class ChocoboStatSnapshot {
    public static final ChocoboStatSnapshot DEFAULT;
    public static final String NBTKEY_GENERATION = "Generation";
    public static final String NBTKEY_HEALTH = "Health";
    public static final String NBTKEY_SPEED = "Speed";
    public static final String NBTKEY_STAMINA = "Stamina";
    public static final String NBTKEY_CAN_SPRINT = "CanSprint";
    public static final String NBTKEY_CAN_GLIDE = "CanGlide";
    public static final String NBTKEY_CAN_DIVE = "CanDive";
    public static final String NBTKEY_CAN_FLY = "CanFly";
    public static final String NBTKEY_COLOR = "Color";

    public int generation;
    public float health;
    public float speed;
    public float stamina;
    public boolean canSprint;
    public boolean canGlide;
    public boolean canDive;
    public boolean canFly;
    public ChocoboColor color;

    static {
        DEFAULT = new ChocoboStatSnapshot();
        DEFAULT.generation = 1;
        DEFAULT.health = ChocoConfig.COMMON.defaultHealth.get();
        DEFAULT.stamina = ChocoConfig.COMMON.defaultStamina.get();
        DEFAULT.speed = ChocoConfig.COMMON.defaultSpeed.get() / 100f;

        DEFAULT.canSprint = false;
        DEFAULT.canGlide = false;
        DEFAULT.canDive = false;
        DEFAULT.canFly = false;
        DEFAULT.color = ChocoboColor.YELLOW;
    }

    private ChocoboStatSnapshot() {
    }

    public ChocoboStatSnapshot(ChocoboEntity chocobo) {
        this.generation = chocobo.getGeneration();
        this.health = (float) chocobo.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
        this.speed = (float) chocobo.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
        this.stamina = (float) chocobo.getAttribute(ModAttributes.MAX_STAMINA.get()).getBaseValue();

        this.canSprint = chocobo.canSprint();
        this.canGlide = chocobo.canGlide();
        this.canDive = chocobo.canDive();
        this.canFly = chocobo.canFly();
        this.color = chocobo.getChocoboColor();
    }

    public ChocoboStatSnapshot(CompoundTag nbt) {
        this.generation = nbt.getInt(NBTKEY_GENERATION);
        this.health = nbt.getFloat(NBTKEY_HEALTH);
        this.speed = nbt.getFloat(NBTKEY_SPEED);
        this.stamina = nbt.getFloat(NBTKEY_STAMINA);

        this.canSprint = nbt.getBoolean(NBTKEY_CAN_SPRINT);
        this.canGlide = nbt.getBoolean(NBTKEY_CAN_GLIDE);
        this.canDive = nbt.getBoolean(NBTKEY_CAN_DIVE);
        this.canFly = nbt.getBoolean(NBTKEY_CAN_FLY);
        this.color = ChocoboColor.values()[nbt.getByte(NBTKEY_COLOR)];
    }

    public CompoundTag serialize() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt(NBTKEY_GENERATION, this.generation);
        nbt.putFloat(NBTKEY_HEALTH, this.health);
        nbt.putFloat(NBTKEY_SPEED, this.speed);
        nbt.putFloat(NBTKEY_STAMINA, this.stamina);

        nbt.putBoolean(NBTKEY_CAN_FLY, this.canFly);
        nbt.putBoolean(NBTKEY_CAN_GLIDE, this.canGlide);
        nbt.putBoolean(NBTKEY_CAN_SPRINT, this.canSprint);
        nbt.putBoolean(NBTKEY_CAN_DIVE, this.canDive);
        nbt.putByte(NBTKEY_COLOR, (byte) this.color.ordinal());
        return nbt;
    }
}
