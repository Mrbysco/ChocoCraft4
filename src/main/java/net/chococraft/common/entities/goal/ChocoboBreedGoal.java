package net.chococraft.common.entities.goal;

import net.chococraft.common.entities.Chocobo;
import net.minecraft.world.entity.ai.goal.BreedGoal;

import javax.annotation.Nullable;
import java.util.List;

public class ChocoboBreedGoal extends BreedGoal {

	public ChocoboBreedGoal(Chocobo chocobo, double speedIn) {
		super(chocobo, speedIn);
	}

	public boolean canUse() {
		if (!this.animal.isInLove()) {
			return false;
		} else {
			this.partner = this.getNearbyMate();
			return this.partner != null;
		}
	}

	@Nullable
	private Chocobo getNearbyMate() {
		List<? extends Chocobo> list = this.level.getNearbyEntities(Chocobo.class, PARTNER_TARGETING, this.animal, this.animal.getBoundingBox().inflate(8.0D));
		double d0 = Double.MAX_VALUE;
		Chocobo animal = null;

		for (Chocobo animal1 : list) {
			if (this.animal.canMate(animal1) && this.animal.distanceToSqr(animal1) < d0) {
				animal = animal1;
				d0 = this.animal.distanceToSqr(animal1);
			}
		}

		return animal;
	}
}
