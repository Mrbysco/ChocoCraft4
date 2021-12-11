package net.chococraft.common.items.enums;

import net.chococraft.common.entities.ChocoboEntity;

import java.util.function.Consumer;
import java.util.function.Predicate;

public enum AbilityFruitType {
	SPRINT("spike_fruit", c -> !c.canSprint(), c -> c.setCanSprint(true)),
	GLIDE("aeroshroom", c -> !c.canGlide(), c -> c.setCanGlide(true)),
	DIVE("aqua_berry", c -> !c.canDive(), c -> c.setCanDive(true)),
	FLY("dead_pepper", c -> !c.canFly(), c -> c.setCanFly(true));

	private final String name;
	private final Consumer<ChocoboEntity> abilityApplier;
	private final Predicate<ChocoboEntity> canLearnAbilityPredicate;

	AbilityFruitType(String name, Predicate<ChocoboEntity> canLearnAbilityPredicate, Consumer<ChocoboEntity> abilityApplier) {
		this.name = name;
		this.canLearnAbilityPredicate = canLearnAbilityPredicate;
		this.abilityApplier = abilityApplier;
	}

	public String getName() {
		return this.name;
	}

	public boolean useFruitOn(ChocoboEntity chocobo) {
		if (this.canLearnAbilityPredicate.test(chocobo)) {
			this.abilityApplier.accept(chocobo);
			return true;
		}
		return false;
	}
}
