package net.chococraft.common.entity.breeding;

import com.google.gson.internal.LinkedTreeMap;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftExpectPlatform;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.entity.properties.ChocoboColor;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BreedingHelper {

	public static ChocoboColor getColor(AbstractChocobo firstParent, AbstractChocobo secondParent) {
		final ChocoboColor firstColor = firstParent.getChocoboColor();
		final ChocoboColor secondColor = secondParent.getChocoboColor();
		int randColor = firstParent.getRandom().nextInt(100);
		boolean bothParentsFedGold = firstParent.isFedGoldGysahl() && secondParent.isFedGoldGysahl();

		LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> breedingInfoHashmap = ChococraftExpectPlatform.getBreedingInfoMap();
		if (breedingInfoHashmap.isEmpty()) {
			Chococraft.LOGGER.error("BreedingInfoHashmap is empty!, make sure you have a valid breeding config file!");
		}
		if (!breedingInfoHashmap.isEmpty() && breedingInfoHashmap.containsKey(firstColor.name())) {
			LinkedTreeMap<String, List<LinkedTreeMap<String, String>>> secondParentColorMaps = breedingInfoHashmap.get(firstColor.name());
			for (Map.Entry<String, List<LinkedTreeMap<String, String>>> secondParentColorEntry : secondParentColorMaps.entrySet()) {
				if (secondParentColorEntry.getKey().contains(secondColor.name())) {
					List<LinkedTreeMap<String, String>> breedingInfoList = secondParentColorEntry.getValue();
					boolean flag = false;
					for (LinkedTreeMap<String, String> breedingInfo : breedingInfoList) {
						String childColor = breedingInfo.get("childColor");
						String conditions = breedingInfo.get("conditions");
						String random = breedingInfo.get("random");
						if (!conditions.equals("none")) {
							if (conditions.equals("bothParentsFedGold")) {
								flag = true;
								if (!bothParentsFedGold) {
									continue;
								}
							} else if (conditions.equals("bothParentsNotFedGold")) {
								flag = true;
								if (bothParentsFedGold) {
									continue;
								}
							}
						} else {
							if (flag && bothParentsFedGold) {
								continue;
							}
						}
						if (!random.equals("none")) {
							String[] parts = random.split(Pattern.quote(" "));
							if (parts[0].equals("above")) {
								if (!(randColor > Integer.parseInt(parts[1])))
									continue;
							} else if (parts[0].equals("under")) {
								if (!(randColor < Integer.parseInt(parts[1])))
									continue;
							}
						}
						if (childColor.equals("secondParent")) {
							return secondColor;
						} else {
							return ChocoboColor.valueOf(childColor);
						}
					}
				}
			}
		} else {
			Chococraft.LOGGER.error("Breeding Config does not contain " + firstColor.name());
		}
		return ChocoboColor.YELLOW;
	}
}
