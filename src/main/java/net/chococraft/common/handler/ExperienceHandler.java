package net.chococraft.common.handler;

import net.minecraft.world.entity.player.Player;

public final class ExperienceHandler {
    // The following code was ripped from EPlus https://github.com/Epoxide-Software/Enchanting-Plus/ which adapted it from OpenModsLib https://github.com/OpenMods/OpenModsLib
    public static int getExperience(Player player) {

        return (int) (getExperienceForLevels(player.experienceLevel) + player.experienceProgress * player.getXpNeededForNextLevel());
    }

    public static boolean removeExperience(Player player, int amount) {
        if (getExperience(player) >= amount) {
            addExperience(player, -amount);
            return true;
        }
        return false;
    }

    public static void addExperience(Player player, int amount) {

        final int experience = getExperience(player) + amount;
        player.totalExperience = experience;
        player.experienceLevel = getLevelForExperience(experience);
        final int expForLevel = getExperienceForLevels(player.experienceLevel);
        player.experienceProgress = (float) (experience - expForLevel) / (float) player.getXpNeededForNextLevel();
    }

    public static int getExperienceForLevels(int level) {

        if (level == 0) {

            return 0;
        }

        if (level > 0 && level < 17) {

            return level * level + 6 * level;
        } else if (level > 16 && level < 32) {

            return (int) (2.5 * level * level - 40.5 * level + 360);
        }

        return (int) (4.5 * level * level - 162.5 * level + 2220);
    }

    public static int getLevelForExperience(int experience) {

        int level = 0;

        while (getExperienceForLevels(level) <= experience) {

            level++;
        }

        return level - 1;
    }
}