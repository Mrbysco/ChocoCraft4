package net.chococraft.common.entities.properties;

import net.chococraft.common.entities.breeding.ChocoboAbilityInfo;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.Random;

public enum ChocoboColor {
	YELLOW(new ChocoboAbilityInfo().setSpeeds(20, 10, 0).setMaxHP(30).setStepHeight(1, 0.5f)),
	GREEN(new ChocoboAbilityInfo().setSpeeds(27, 10, 0).setMaxHP(30).setStepHeight(2, 0.5f)),
	BLUE(new ChocoboAbilityInfo().setSpeeds(27, 55, 0).setMaxHP(30).setStepHeight(1, 0.5f).setCanWalkOnWater(true).addRiderAbility(() -> new EffectInstance(Effects.WATER_BREATHING, 100, 0, true, false))),
	WHITE(new ChocoboAbilityInfo().setSpeeds(35, 45, 0).setMaxHP(40).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true)),
	BLACK(new ChocoboAbilityInfo().setSpeeds(40, 20, 0).setMaxHP(40).setStepHeight(2, 0.5f).setCanWalkOnWater(true).setCanClimb(true).addRiderAbility(() -> new EffectInstance(Effects.NIGHT_VISION, 100, 0, true, false))),
	GOLD(new ChocoboAbilityInfo().setSpeeds(50, 20, 55).setMaxHP(50).setStepHeight(2, 0.5f).setCanWalkOnWater(true).setCanClimb(true).setCanFly(true).setImmuneToFire(true)),
	PINK(new ChocoboAbilityInfo().setSpeeds(55, 25, 60).setMaxHP(50).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true).setCanFly(true)),
	RED(new ChocoboAbilityInfo().setSpeeds(55, 25, 60).setMaxHP(50).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true).setCanFly(true)),
	PURPLE(new ChocoboAbilityInfo().setSpeeds(40, 10, 55).setMaxHP(50).setStepHeight(1, 0.5f).setCanClimb(true).setCanFly(true).setImmuneToFire(true).addRiderAbility(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 100, -1, true, false))),
	FLAME(new ChocoboAbilityInfo().setSpeeds(40, 10, 55).setMaxHP(50).setStepHeight(1, 0.5f).setCanClimb(true).setCanFly(true).setImmuneToFire(true).addRiderAbility(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 100, -1, true, false)));

	private final ChocoboAbilityInfo abilityInfo;

	ChocoboColor(ChocoboAbilityInfo abilityInfo) {
		this.abilityInfo = abilityInfo;
	}

	public static ChocoboColor getRandomColor(Random random) {
		return values()[random.nextInt(values().length)];
	}

	public ChocoboAbilityInfo getAbilityInfo() {
		return abilityInfo;
	}
}
