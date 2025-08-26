package net.chococraft.common.entity.properties;

import io.netty.buffer.ByteBuf;
import net.chococraft.common.entity.breeding.ChocoboAbilityInfo;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.Locale;
import java.util.function.IntFunction;

public enum ChocoboColor implements StringRepresentable {
	YELLOW(0, new ChocoboAbilityInfo().setSpeeds(20, 10, 0).setMaxHP(30).setStepHeight(1, 0.5f)),
	GREEN(1, new ChocoboAbilityInfo().setSpeeds(27, 10, 0).setMaxHP(30).setStepHeight(2, 0.5f)),
	BLUE(2, new ChocoboAbilityInfo().setSpeeds(27, 50, 0).setMaxHP(30).setStepHeight(1, 0.5f).setCanWalkOnWater(true).addRiderAbility(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 100, 0, true, false))),
	WHITE(3, new ChocoboAbilityInfo().setSpeeds(35, 45, 0).setMaxHP(40).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true)),
	BLACK(4, new ChocoboAbilityInfo().setSpeeds(40, 20, 0).setMaxHP(40).setStepHeight(2, 0.5f).setCanWalkOnWater(true).setCanClimb(true).addRiderAbility(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0, true, false))),
	GOLD(5, new ChocoboAbilityInfo().setSpeeds(50, 25, 25).setMaxHP(50).setStepHeight(2, 0.5f).setCanWalkOnWater(true).setCanClimb(true).setCanFly(true).setImmuneToFire(true)),
	PINK(6, new ChocoboAbilityInfo().setSpeeds(50, 25, 30).setMaxHP(50).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true).setCanFly(true)),
	RED(7, new ChocoboAbilityInfo().setSpeeds(50, 25, 35).setMaxHP(50).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true).setCanFly(true)),
	PURPLE(8, new ChocoboAbilityInfo().setSpeeds(40, 10, 25).setMaxHP(50).setStepHeight(1, 0.5f).setCanClimb(true).setCanFly(true).setImmuneToFire(true).addRiderAbility(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, -1, true, false))),
	FLAME(9, new ChocoboAbilityInfo().setSpeeds(40, 10, 25).setMaxHP(50).setStepHeight(1, 0.5f).setCanClimb(true).setCanFly(true).setImmuneToFire(true).addRiderAbility(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, -1, true, false)));

	public static final StringRepresentable.EnumCodec<ChocoboColor> CODEC = StringRepresentable.fromEnum(ChocoboColor::values);
	private static final IntFunction<ChocoboColor> BY_ID = ByIdMap.continuous(ChocoboColor::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final StreamCodec<ByteBuf, ChocoboColor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, ChocoboColor::getId);

	private final int id;
	private final ChocoboAbilityInfo abilityInfo;

	ChocoboColor(int id, ChocoboAbilityInfo abilityInfo) {
		this.id = id;
		this.abilityInfo = abilityInfo;
	}

	public int getId() {
		return id;
	}

	public static ChocoboColor getRandomColor(RandomSource random) {
		return values()[random.nextInt(values().length)];
	}

	public ChocoboAbilityInfo getAbilityInfo() {
		return abilityInfo;
	}

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase(Locale.ROOT);
	}
}
