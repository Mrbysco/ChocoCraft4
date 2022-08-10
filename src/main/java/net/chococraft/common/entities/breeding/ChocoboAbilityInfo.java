package net.chococraft.common.entities.breeding;

import net.minecraft.potion.EffectInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ChocoboAbilityInfo {
	private int maxHP;
	private boolean canClimb = false;
	private boolean canWalkOnWater = false;
	private boolean canFly = false;
	private boolean immuneToFire = false;
	private float landSpeed;
	private float waterSpeed;
	private float airbornSpeed;
	private float mountedStepHeight;
	private float normalStepHeight;
	private List<Supplier<EffectInstance>> effectList = new ArrayList<>();

	public ChocoboAbilityInfo() {
	}

	public ChocoboAbilityInfo setMaxHP(int maxHP) {
		this.maxHP = maxHP;
		return this;
	}

	public ChocoboAbilityInfo setCanClimb(boolean value) {
		this.canClimb = value;
		return this;
	}

	public boolean canClimb() {
		return this.canClimb;
	}

	public ChocoboAbilityInfo setCanWalkOnWater(boolean value) {
		this.canWalkOnWater = value;
		return this;
	}

	public boolean canWalkOnWater() {
		return this.canWalkOnWater;
	}

	public ChocoboAbilityInfo setCanFly(boolean value) {
		this.canFly = value;
		return this;
	}

	public ChocoboAbilityInfo setImmuneToFire(boolean value) {
		this.immuneToFire = value;
		return this;
	}

	public boolean isImmuneToFire() {
		return this.immuneToFire;
	}

	public ChocoboAbilityInfo setSpeeds(float landSpeed, float waterSpeed, float airbornSpeed) {
		this.landSpeed = landSpeed;
		this.waterSpeed = waterSpeed;
		this.airbornSpeed = airbornSpeed;
		return this;
	}

	public float getLandSpeed() {
		return this.landSpeed;
	}

	public float getWaterSpeed() {
		return this.waterSpeed;
	}

	public float getAirbornSpeed() {
		return this.airbornSpeed;
	}

	public ChocoboAbilityInfo setStepHeight(float mountedStepHeight, float normalStepHeight) {
		this.mountedStepHeight = mountedStepHeight;
		this.normalStepHeight = normalStepHeight;
		return this;
	}

	public ChocoboAbilityInfo setRiderAbilities(List<Supplier<EffectInstance>> effectList) {
		this.effectList = effectList;
		return this;
	}

	public ChocoboAbilityInfo addRiderAbility(Supplier<EffectInstance> effectSupplier) {
		this.effectList.add(effectSupplier);
		return this;
	}

	public List<Supplier<EffectInstance>> getRiderAbilities() {
		return effectList;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public float getStepHeight(boolean mounted) {
		return mounted ? mountedStepHeight : normalStepHeight;
	}

	public boolean getCanFly() {
		return canFly;
	}
}
