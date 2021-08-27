package net.chococraft.common.entities.breeding;

import net.chococraft.common.ChocoConfig;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.init.ModAttributes;
import net.chococraft.common.init.ModEntities;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.World;

public class BreedingHelper {
    public static ChocoboBreedInfo getBreedInfo(ChocoboEntity mother, ChocoboEntity father) {
        return new ChocoboBreedInfo(new ChocoboStatSnapshot(mother), new ChocoboStatSnapshot(father));
    }

    public static ChocoboEntity createChild(ChocoboBreedInfo breedInfo, World world) {
        ChocoboEntity chocobo = ModEntities.CHOCOBO.get().create(world);

        ChocoboStatSnapshot mother = breedInfo.getMother();
        ChocoboStatSnapshot father = breedInfo.getFather();

        chocobo.setGeneration(((mother.generation + father.generation) / 2) + 1);

        float health = Math.round(((mother.health + father.health) / 2) * (ChocoConfig.COMMON.poslossHealth.get().floatValue() + ((float) Math.random() * ChocoConfig.COMMON.posgainHealth.get().floatValue())));
        chocobo.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.min(health, ChocoConfig.COMMON.maxHealth.get().floatValue()));

        float speed = ((mother.speed + father.speed) / 2f) * (ChocoConfig.COMMON.poslossSpeed.get().floatValue() + ((float) Math.random() * ChocoConfig.COMMON.posgainSpeed.get().floatValue()));
        chocobo.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Math.min(speed, (ChocoConfig.COMMON.maxSpeed.get().floatValue() / 100f)));

        float stamina = Math.round((mother.stamina + father.stamina) / 2) * (ChocoConfig.COMMON.poslossStamina.get().floatValue() + ((float) Math.random() * ChocoConfig.COMMON.posgainStamina.get().floatValue()));
        chocobo.getAttribute(ModAttributes.MAX_STAMINA.get()).setBaseValue(Math.min(stamina, ChocoConfig.COMMON.maxStamina.get().floatValue()));

        float canFlyChance = calculateChance(0.005f, 0.15f, 0.35f, mother.canFly, father.canFly);
        float canflychancerandom = (float) Math.random();
        chocobo.setCanFly(canFlyChance > canflychancerandom);

        float canDiveChance = calculateChance(0.01f, 0.20f, 0.40f, mother.canDive, father.canDive);
        float candivechancerandom = (float) Math.random();
        chocobo.setCanDive(canDiveChance > candivechancerandom);

        float canGlideChance = calculateChance(0.01f, 0.20f, 0.45f, mother.canGlide, father.canGlide);
        float canglidechancerandom = (float) Math.random();
        chocobo.setCanGlide(canGlideChance > canglidechancerandom);

        float canSprintChance = calculateChance(0.03f, 0.25f, 0.5f, mother.canSprint, father.canSprint);
        float cansprintchancerandom = (float) Math.random();
        chocobo.setCanSprint(canSprintChance > cansprintchancerandom);

        chocobo.setMale(.50f > (float) Math.random());

        ChocoboColor color = ChocoboColor.YELLOW;

        if (mother.color == ChocoboColor.FLAME && father.color == ChocoboColor.FLAME) {
            color = ChocoboColor.FLAME;
        } else if (canFlyChance > canflychancerandom) {
            color = ChocoboColor.GOLD;
        } else if (canDiveChance > candivechancerandom) {
            color = ChocoboColor.BLUE;
        } else if (canGlideChance > canglidechancerandom) {
            color = ChocoboColor.WHITE;
        } else if (canSprintChance > cansprintchancerandom) {
            color = ChocoboColor.GREEN;
        }
        // BLACK PINK RED PURPLE ?

        chocobo.setChocoboColor(color);

        chocobo.setGrowingAge(-24000);

        return chocobo;
    }

    private static float calculateChance(float baseChance, float perParentChance, float bothParentsChance, boolean motherHasAbility, boolean fatherHasAbility) {
        return baseChance + (motherHasAbility || fatherHasAbility ? perParentChance : 0) + (motherHasAbility && fatherHasAbility ? bothParentsChance : 0);
    }
}
